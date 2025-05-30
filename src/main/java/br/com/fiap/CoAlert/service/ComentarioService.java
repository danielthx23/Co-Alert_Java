package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.ComentarioEditRequestDto;
import br.com.fiap.CoAlert.dto.request.ComentarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.ComentarioResponseDto;
import br.com.fiap.CoAlert.model.Comentario;
import br.com.fiap.CoAlert.model.Postagem;
import br.com.fiap.CoAlert.model.Usuario;
import br.com.fiap.CoAlert.repository.ComentarioRepository;
import br.com.fiap.CoAlert.repository.PostagemRepository;
import br.com.fiap.CoAlert.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public ComentarioResponseDto create(ComentarioSaveRequestDto dto) {
        Comentario comentario = new Comentario();

        comentario.setIdComentarioParente(dto.getIdComentarioParente());
        comentario.setNmConteudo(dto.getNmConteudo());
        comentario.setDtEnvio(dto.getDtEnvio());
        comentario.setNrLikes(dto.getNrLikes());

        comentario.setUsuario(usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario())));

        comentario.setPostagem(postagemRepository.findById(dto.getIdPostagem())
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem())));

        Comentario saved = comentarioRepository.save(comentario);
        return toResponseDto(saved);
    }

    public ComentarioResponseDto update(Long id, ComentarioEditRequestDto dto) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID: " + id));

        comentario.setNmConteudo(dto.getNmConteudo());
        comentario.setDtEnvio(dto.getDtEnvio());
        comentario.setNrLikes(dto.getNrLikes());
        comentario.setIdComentarioParente(dto.getIdComentarioParente());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
            comentario.setUsuario(usuario);
        }

        if (dto.getIdPostagem() != null) {
            Postagem postagem = postagemRepository.findById(dto.getIdPostagem())
                    .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + dto.getIdPostagem()));
            comentario.setPostagem(postagem);
        }

        Comentario updated = comentarioRepository.save(comentario);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentário não encontrado com ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }

    private ComentarioResponseDto toResponseDto(Comentario comentario) {
        return new ComentarioResponseDto(
                comentario.getIdComentario(),
                comentario.getIdComentarioParente(),
                comentario.getNmConteudo(),
                comentario.getDtEnvio(),
                comentario.getNrLikes(),
                comentario.getUsuario().getNmUsuario(),
                comentario.getPostagem().getIdPostagem()
        );
    }
}
