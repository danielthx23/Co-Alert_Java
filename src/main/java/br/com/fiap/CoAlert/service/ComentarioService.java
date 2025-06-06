package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.ComentarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.ComentarioEditRequestDto;
import br.com.fiap.CoAlert.dto.response.ComentarioResponseDto;
import br.com.fiap.CoAlert.model.Comentario;
import br.com.fiap.CoAlert.model.Usuario;
import br.com.fiap.CoAlert.model.Postagem;
import br.com.fiap.CoAlert.repository.ComentarioRepository;
import br.com.fiap.CoAlert.repository.UsuarioRepository;
import br.com.fiap.CoAlert.repository.PostagemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PostagemRepository postagemRepository;

    public Page<ComentarioResponseDto> getAll(Pageable pageable) {
        return comentarioRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public ComentarioResponseDto getById(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + id));
        return toResponseDto(comentario);
    }

    @Transactional
    public ComentarioResponseDto create(ComentarioSaveRequestDto dto) {
        // Validar existência das entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
        
        Postagem postagem = postagemRepository.findById(dto.getIdPostagem())
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));

        if (dto.getIdComentarioParente() != null) {
            comentarioRepository.findById(dto.getIdComentarioParente())
                    .orElseThrow(() -> new EntityNotFoundException("Comentário pai não encontrado com ID: " + dto.getIdComentarioParente()));
        }

        comentarioRepository.inserirComentario(
            dto.getIdUsuario(),
            dto.getIdPostagem(),
            dto.getNmConteudo(),
            dto.getDtEnvio(),
            dto.getNrLikes(),
            dto.getIdComentarioParente()
        );
        
        // Buscar o comentário recém-criado
        Comentario comentario = comentarioRepository.findByConteudoAndUsuarioAndPostagemAndDataEnvio(
            dto.getNmConteudo(),
            usuario,
            postagem,
            dto.getDtEnvio()
        ).orElseThrow(() -> new IllegalStateException("Erro ao criar comentário: não foi possível encontrá-lo após a criação"));
        
        return toResponseDto(comentario);
    }

    @Transactional
    public void delete(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentário não encontrado com ID: " + id);
        }
        comentarioRepository.deletarComentario(id);
    }

    @Transactional
    public ComentarioResponseDto update(Long id, ComentarioEditRequestDto dto) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentário não encontrado com ID: " + id);
        }

        // Validar existência das entidades relacionadas
        usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
        
        postagemRepository.findById(dto.getIdPostagem())
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));

        if (dto.getIdComentarioParente() != null) {
            comentarioRepository.findById(dto.getIdComentarioParente())
                    .orElseThrow(() -> new EntityNotFoundException("Comentário pai não encontrado com ID: " + dto.getIdComentarioParente()));
        }

        comentarioRepository.atualizarComentario(
            id,
            dto.getIdUsuario(),
            dto.getIdPostagem(),
            dto.getNmConteudo(),
            dto.getDtEnvio(),
            dto.getNrLikes(),
            dto.getIdComentarioParente()
        );

        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Erro ao atualizar comentário: não foi possível encontrá-lo após a atualização"));
        
        return toResponseDto(comentario);
    }

    private ComentarioResponseDto toResponseDto(Comentario comentario) {
        return new ComentarioResponseDto(
                comentario.getId(),
                comentario.getComentarioParente() != null ? comentario.getComentarioParente().getId() : null,
                comentario.getConteudo(),
                comentario.getDataEnvio(),
                comentario.getLikes(),
                comentario.getUsuario().getNome(),
                comentario.getPostagem().getId()
        );
    }
}
