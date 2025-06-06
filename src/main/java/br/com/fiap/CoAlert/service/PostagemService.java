package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.PostagemSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.PostagemEditRequestDto;
import br.com.fiap.CoAlert.dto.response.ComentarioResponseDto;
import br.com.fiap.CoAlert.dto.response.PostagemResponseDto;
import br.com.fiap.CoAlert.model.*;
import br.com.fiap.CoAlert.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public PostagemResponseDto create(PostagemSaveRequestDto dto) {
        // Validar existência das entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));

        CategoriaDesastre categoria = categoriaDesastreRepository.findById(dto.getIdCategoriaDesastre())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + dto.getIdCategoriaDesastre()));
        
        Localizacao localizacao = localizacaoRepository.findById(dto.getIdLocalizacao())
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + dto.getIdLocalizacao()));

        postagemRepository.inserirPostagem(
            dto.getNmTitulo(),
            dto.getNmConteudo(),
            dto.getDtEnvio(),
            dto.getNrLikes(),
            dto.getIdUsuario(),
            dto.getIdCategoriaDesastre(),
            dto.getIdLocalizacao()
        );
        
        // Buscar a postagem recém-criada
        Postagem postagem = postagemRepository.findByNmTituloAndNmConteudoAndUsuarioAndDtEnvio(
            dto.getNmTitulo(),
            dto.getNmConteudo(),
            usuario,
            dto.getDtEnvio()
        ).orElseThrow(() -> new IllegalStateException("Erro ao criar postagem: não foi possível encontrá-la após a criação"));
        
        return toResponseDto(postagem);
    }

    @Transactional
    public void delete(Long id) {
        if (!postagemRepository.existsById(id)) {
            throw new EntityNotFoundException("Postagem não encontrada com ID: " + id);
        }
        postagemRepository.deletarPostagem(id);
    }

    @Transactional
    public PostagemResponseDto update(Long id, PostagemEditRequestDto dto) {
        if (!postagemRepository.existsById(id)) {
            throw new EntityNotFoundException("Postagem não encontrada com ID: " + id);
        }

        // Validar existência das entidades relacionadas
        usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
        
        categoriaDesastreRepository.findById(dto.getIdCategoriaDesastre())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + dto.getIdCategoriaDesastre()));
        
        localizacaoRepository.findById(dto.getIdLocalizacao())
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + dto.getIdLocalizacao()));

        postagemRepository.atualizarPostagem(
            id,
            dto.getNmTitulo(),
            dto.getNmConteudo(),
            dto.getDtEnvio(),
            dto.getNrLikes(),
            dto.getIdUsuario(),
            dto.getIdCategoriaDesastre(),
            dto.getIdLocalizacao()
        );

        Postagem postagem = postagemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Erro ao atualizar postagem: não foi possível encontrá-la após a atualização"));
        
        return toResponseDto(postagem);
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
                                        c.getComentarioParente() != null ? c.getComentarioParente().getIdComentario() : null,
                                        c.getNmConteudo(),
                                        c.getDtEnvio(),
                                        c.getNrLikes(),
                                        c.getUsuario().getNmUsuario(),
                                        c.getPostagem().getIdPostagem()))
                                .collect(Collectors.toList()) : List.of()
        );
    }
}
