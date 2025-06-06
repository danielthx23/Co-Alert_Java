package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.UsuarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.UsuarioEditRequestDto;
import br.com.fiap.CoAlert.dto.response.UsuarioResponseDto;
import br.com.fiap.CoAlert.model.Usuario;
import br.com.fiap.CoAlert.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Page<UsuarioResponseDto> getAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public UsuarioResponseDto getById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        return toResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto create(UsuarioSaveRequestDto dto) {
        usuarioRepository.inserirUsuario(dto.getNmUsuario(), dto.getNrSenha(), dto.getNmEmail());
        // Buscar o usuário recém-criado pelo email
        Usuario usuario = usuarioRepository.findByNmEmail( dto.getNmEmail())
                .orElseThrow(() -> new IllegalStateException("Erro ao criar usuário: não foi possível encontrá-lo após a criação"));
        return toResponseDto(usuario);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deletarUsuario(id);
    }

    @Transactional
    public UsuarioResponseDto update(Long id, UsuarioEditRequestDto dto) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.atualizarUsuario(id, dto.getNmUsuario(), dto.getNrSenha(), dto.getNmEmail());
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Erro ao atualizar usuário: não foi possível encontrá-lo após a atualização"));
        return toResponseDto(usuario);
    }

    private UsuarioResponseDto toResponseDto(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getIdUsuario(),
                usuario.getNmUsuario(),
                usuario.getNmEmail()
        );
    }
}
