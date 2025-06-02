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
            existingLike = likeRepository.findByUsuarioIdUsuarioAndPostagemIdPostagemAndComentarioIsNull(
                    dto.getIdUsuario(), dto.getIdPostagem());
        } else {
            Comentario comentario = comentarioRepository.findById(dto.getIdComentario())
                    .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + dto.getIdComentario()));
            existingLike = likeRepository.findByUsuarioIdUsuarioAndComentarioIdComentarioAndPostagemIsNull(
                    dto.getIdUsuario(), dto.getIdComentario());
        }

        boolean isLiked;
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            isLiked = false;
        } else {
            Like like = new Like();
            like.setUsuario(usuario);
            like.setDtLike(dto.getDtLike());

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
                ? likeRepository.countByPostagemIdPostagem(dto.getIdPostagem())
                : likeRepository.countByComentarioIdComentario(dto.getIdComentario());

        return new LikeStatsDto(totalLikes, isLiked);
    }

    public LikeStatsDto getLikeStats(Long postagemId, Long comentarioId, Long usuarioId) {
        if (postagemId != null) {
            if (!postagemRepository.existsById(postagemId)) {
                throw new EntityNotFoundException("Postagem não encontrada com ID: " + postagemId);
            }
            long totalLikes = likeRepository.countByPostagemIdPostagem(postagemId);
            boolean isLiked = usuarioId != null && likeRepository
                    .findByUsuarioIdUsuarioAndPostagemIdPostagemAndComentarioIsNull(usuarioId, postagemId)
                    .isPresent();
            return new LikeStatsDto(totalLikes, isLiked);
        } else if (comentarioId != null) {
            if (!comentarioRepository.existsById(comentarioId)) {
                throw new EntityNotFoundException("Comentário não encontrado com ID: " + comentarioId);
            }
            long totalLikes = likeRepository.countByComentarioIdComentario(comentarioId);
            boolean isLiked = usuarioId != null && likeRepository
                    .findByUsuarioIdUsuarioAndComentarioIdComentarioAndPostagemIsNull(usuarioId, comentarioId)
                    .isPresent();
            return new LikeStatsDto(totalLikes, isLiked);
        }
        throw new IllegalArgumentException("É necessário fornecer o ID da postagem ou do comentário");
    }

    public void delete(Long id) {
        if (!likeRepository.existsById(id)) {
            throw new EntityNotFoundException("Like não encontrado com ID: " + id);
        }
        likeRepository.deleteById(id);
    }

    private LikeResponseDto toResponseDto(Like like) {
        return new LikeResponseDto(
                like.getIdLike(),
                like.getUsuario().getNmUsuario(),
                like.getPostagem() != null ? like.getPostagem().getIdPostagem() : null,
                like.getComentario() != null ? like.getComentario().getIdComentario() : null,
                like.getDtLike()
        );
    }
} 