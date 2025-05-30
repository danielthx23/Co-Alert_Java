package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.PostagemEditRequestDto;
import br.com.fiap.CoAlert.dto.request.PostagemSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.ComentarioResponseDto;
import br.com.fiap.CoAlert.dto.response.PostagemResponseDto;
import br.com.fiap.CoAlert.model.*;
import br.com.fiap.CoAlert.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostagemService {

    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaDesastreRepository categoriaDesastreRepository;
    private final LocalizacaoRepository localizacaoRepository;

    public Page<PostagemResponseDto> getAll(Pageable pageable) {
        return postagemRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public PostagemResponseDto getById(Long id) {
        Postagem postagem = postagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + id));
        return toResponseDto(postagem);
    }

    public PostagemResponseDto create(PostagemSaveRequestDto dto) {
        Postagem postagem = new Postagem();

        postagem.setNmTitulo(dto.getNmTitulo());
        postagem.setNmConteudo(dto.getNmConteudo());
        postagem.setDtEnvio(dto.getDtEnvio());
        postagem.setNrLikes(dto.getNrLikes());

        postagem.setUsuario(usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario())));

        postagem.setCategoriaDesastre(categoriaDesastreRepository.findById(dto.getIdCategoriaDesastre())
                .orElseThrow(() -> new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + dto.getIdCategoriaDesastre())));

        postagem.setLocalizacao(localizacaoRepository.findById(dto.getIdLocalizacao())
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + dto.getIdLocalizacao())));

        Postagem saved = postagemRepository.save(postagem);
        return toResponseDto(saved);
    }

    public PostagemResponseDto update(Long id, PostagemEditRequestDto dto) {
        Postagem postagem = postagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com ID: " + id));

        if (dto.getNmTitulo() != null) postagem.setNmTitulo(dto.getNmTitulo());
        if (dto.getNmConteudo() != null) postagem.setNmConteudo(dto.getNmConteudo());
        if (dto.getDtEnvio() != null) postagem.setDtEnvio(dto.getDtEnvio());
        if (dto.getNrLikes() != null) postagem.setNrLikes(dto.getNrLikes());

        if (dto.getIdCategoriaDesastre() != null) {
            CategoriaDesastre categoria = categoriaDesastreRepository.findById(dto.getIdCategoriaDesastre())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + dto.getIdCategoriaDesastre()));
            postagem.setCategoriaDesastre(categoria);
        }

        if (dto.getIdLocalizacao() != null) {
            Localizacao localizacao = localizacaoRepository.findById(dto.getIdLocalizacao())
                    .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + dto.getIdLocalizacao()));
            postagem.setLocalizacao(localizacao);
        }

        Postagem updated = postagemRepository.save(postagem);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!postagemRepository.existsById(id)) {
            throw new EntityNotFoundException("Postagem não encontrada com ID: " + id);
        }
        postagemRepository.deleteById(id);
    }

    private PostagemResponseDto toResponseDto(Postagem postagem) {
        return new PostagemResponseDto(
                postagem.getIdPostagem(),
                postagem.getNmTitulo(),
                postagem.getNmConteudo(),
                postagem.getDtEnvio(),
                postagem.getNrLikes(),
                postagem.getUsuario().getNmUsuario(),
                postagem.getCategoriaDesastre().getNmTitulo(),
                postagem.getLocalizacao().getNmCidade(),
                postagem.getComentarios() != null ?
                        postagem.getComentarios().stream()
                                .map(c -> new ComentarioResponseDto(
                                        c.getIdComentario(),
                                        c.getIdComentarioParente(),
                                        c.getNmConteudo(),
                                        c.getDtEnvio(),
                                        c.getNrLikes(),
                                        c.getUsuario().getNmUsuario(),
                                        c.getPostagem().getIdPostagem()))
                                .collect(Collectors.toList()) : List.of()
        );
    }
}
