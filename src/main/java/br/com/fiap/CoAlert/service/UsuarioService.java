package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.UsuarioEditRequestDto;
import br.com.fiap.CoAlert.dto.request.UsuarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.UsuarioResponseDto;
import br.com.fiap.CoAlert.model.Usuario;
import br.com.fiap.CoAlert.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public UsuarioResponseDto create(UsuarioSaveRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNmUsuario(dto.getNmUsuario());
        usuario.setNrSenha(dto.getNrSenha());
        usuario.setNmEmail(dto.getNmEmail());

        Usuario saved = usuarioRepository.save(usuario);
        return toResponseDto(saved);
    }

    public UsuarioResponseDto update(Long id, UsuarioEditRequestDto dto) {
        Usuario existing = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        existing.setNmUsuario(dto.getNmUsuario());
        existing.setNmEmail(dto.getNmEmail());

        Usuario updated = usuarioRepository.save(existing);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDto toResponseDto(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getIdUsuario(),
                usuario.getNmUsuario(),
                usuario.getNmEmail()
        );
    }
}
