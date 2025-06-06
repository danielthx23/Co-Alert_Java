package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.LikeSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.LikeResponseDto;
import br.com.fiap.CoAlert.dto.response.LikeStatsDto;
import br.com.fiap.CoAlert.model.Like;
import br.com.fiap.CoAlert.model.Usuario;
import br.com.fiap.CoAlert.model.Postagem;
import br.com.fiap.CoAlert.model.Comentario;
import br.com.fiap.CoAlert.repository.LikeRepository;
import br.com.fiap.CoAlert.repository.UsuarioRepository;
import br.com.fiap.CoAlert.repository.PostagemRepository;
import br.com.fiap.CoAlert.repository.ComentarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UsuarioRepository usuarioRepository;
    private final PostagemRepository postagemRepository;
    private final ComentarioRepository comentarioRepository;

    public Page<LikeResponseDto> getAll(Pageable pageable) {
        return likeRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public LikeResponseDto getById(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Like não encontrado com ID: " + id));
        return toResponseDto(like);
    }

    @Transactional
    public LikeResponseDto create(LikeSaveRequestDto dto) {
        // Validar existência das entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));

        if (dto.getIdPostagem() != null) {
            postagemRepository.findById(dto.getIdPostagem())
                    .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));
        }

        if (dto.getIdComentario() != null) {
            comentarioRepository.findById(dto.getIdComentario())
                    .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + dto.getIdComentario()));
        }

        likeRepository.inserirLike(
            dto.getIdUsuario(),
            dto.getIdPostagem(),
            dto.getIdComentario()
        );
        
        // Buscar o like recém-criado
        Like like;
        if (dto.getIdPostagem() != null) {
            like = likeRepository.findByUsuario_IdAndPostagem_IdAndComentarioIsNull(
                dto.getIdUsuario(), dto.getIdPostagem()
            ).orElseThrow(() -> new IllegalStateException("Erro ao criar like: não foi possível encontrá-lo após a criação"));
        } else {
            like = likeRepository.findByUsuario_IdAndComentario_IdAndPostagemIsNull(
                dto.getIdUsuario(), dto.getIdComentario()
            ).orElseThrow(() -> new IllegalStateException("Erro ao criar like: não foi possível encontrá-lo após a criação"));
        }
        
        return toResponseDto(like);
    }

    @Transactional
    public void delete(Long id) {
        if (!likeRepository.existsById(id)) {
            throw new EntityNotFoundException("Like não encontrado com ID: " + id);
        }
        likeRepository.deletarLike(id);
    }

    @Transactional
    public LikeStatsDto toggleLike(LikeSaveRequestDto dto) {
        if (dto.getIdPostagem() == null && dto.getIdComentario() == null) {
            throw new IllegalArgumentException("É necessário fornecer o ID da postagem ou do comentário");
        }

        if (dto.getIdPostagem() != null && dto.getIdComentario() != null) {
            throw new IllegalArgumentException("Não é possível curtir uma postagem e um comentário ao mesmo tempo");
        }

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));

        Optional<Like> existingLike;
        if (dto.getIdPostagem() != null) {
            Postagem postagem = postagemRepository.findById(dto.getIdPostagem())
                    .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));
            existingLike = likeRepository.findByUsuario_IdAndPostagem_IdAndComentarioIsNull(
                    dto.getIdUsuario(), dto.getIdPostagem());
        } else {
            Comentario comentario = comentarioRepository.findById(dto.getIdComentario())
                    .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + dto.getIdComentario()));
            existingLike = likeRepository.findByUsuario_IdAndComentario_IdAndPostagemIsNull(
                    dto.getIdUsuario(), dto.getIdComentario());
        }

        boolean isLiked;
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            isLiked = false;
        } else {
            Like like = new Like();
            like.setUsuario(usuario);
            like.setDataLike(dto.getDtLike());

            if (dto.getIdPostagem() != null) {
                Postagem postagem = postagemRepository.findById(dto.getIdPostagem())
                        .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));
                like.setPostagem(postagem);
            } else {
                Comentario comentario = comentarioRepository.findById(dto.getIdComentario())
                        .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + dto.getIdComentario()));
                like.setComentario(comentario);
            }

            likeRepository.save(like);
            isLiked = true;
        }

        long totalLikes = dto.getIdPostagem() != null
                ? likeRepository.countByPostagem_Id(dto.getIdPostagem())
                : likeRepository.countByComentario_Id(dto.getIdComentario());

        return new LikeStatsDto(totalLikes, isLiked);
    }

    public LikeStatsDto getStats(Long postagemId, Long comentarioId, Long usuarioId) {
        if (postagemId != null) {
            if (!postagemRepository.existsById(postagemId)) {
                throw new EntityNotFoundException("Postagem não encontrada com ID: " + postagemId);
            }
            long totalLikes = likeRepository.countByPostagem_Id(postagemId);
            boolean isLiked = usuarioId != null && likeRepository
                    .findByUsuario_IdAndPostagem_IdAndComentarioIsNull(usuarioId, postagemId)
                    .isPresent();
            return new LikeStatsDto(totalLikes, isLiked);
        } else if (comentarioId != null) {
            if (!comentarioRepository.existsById(comentarioId)) {
                throw new EntityNotFoundException("Comentário não encontrado com ID: " + comentarioId);
            }
            long totalLikes = likeRepository.countByComentario_Id(comentarioId);
            boolean isLiked = usuarioId != null && likeRepository
                    .findByUsuario_IdAndComentario_IdAndPostagemIsNull(usuarioId, comentarioId)
                    .isPresent();
            return new LikeStatsDto(totalLikes, isLiked);
        }
        throw new IllegalArgumentException("É necessário fornecer o ID da postagem ou do comentário");
    }

    private LikeResponseDto toResponseDto(Like like) {
        return new LikeResponseDto(
                like.getId(),
                like.getUsuario().getNome(),
                like.getPostagem() != null ? like.getPostagem().getId() : null,
                like.getComentario() != null ? like.getComentario().getId() : null,
                like.getDataLike()
        );
    }
} 