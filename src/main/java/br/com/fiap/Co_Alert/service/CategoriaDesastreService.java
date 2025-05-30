package br.com.fiap.Co_Alert.service;

import br.com.fiap.Co_Alert.dto.request.CategoriaDesastreEditRequestDto;
import br.com.fiap.Co_Alert.dto.request.CategoriaDesastreSaveRequestDto;
import br.com.fiap.Co_Alert.dto.response.CategoriaDesastreResponseDto;
import br.com.fiap.Co_Alert.model.CategoriaDesastre;
import br.com.fiap.Co_Alert.repository.CategoriaDesastreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class CategoriaDesastreService {

    private final CategoriaDesastreRepository categoriaRepository;

    public Page<CategoriaDesastreResponseDto> getAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public CategoriaDesastreResponseDto getById(Long id) {
        CategoriaDesastre categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + id));
        return toResponseDto(categoria);
    }

    public CategoriaDesastreResponseDto create(CategoriaDesastreSaveRequestDto dto) {
        CategoriaDesastre categoria = new CategoriaDesastre();

        categoria.setNmTitulo(dto.getNmTitulo());
        categoria.setDsCategoria(dto.getDsCategoria());
        categoria.setNmTipo(dto.getNmTipo());

        CategoriaDesastre saved = categoriaRepository.save(categoria);
        return toResponseDto(saved);
    }

    public CategoriaDesastreResponseDto update(Long id, CategoriaDesastreEditRequestDto dto) {
        CategoriaDesastre existing = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + id));

        existing.setNmTitulo(dto.getNmTitulo());
        existing.setDsCategoria(dto.getDsCategoria());
        existing.setNmTipo(dto.getNmTipo());

        CategoriaDesastre updated = categoriaRepository.save(existing);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDesastreResponseDto toResponseDto(CategoriaDesastre categoria) {
        return new CategoriaDesastreResponseDto(
                categoria.getIdCategoriaDesastre(),
                categoria.getNmTitulo(),
                categoria.getDsCategoria(),
                categoria.getNmTipo()
        );
    }
}
