package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.CategoriaDesastreSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.CategoriaDesastreEditRequestDto;
import br.com.fiap.CoAlert.dto.response.CategoriaDesastreResponseDto;
import br.com.fiap.CoAlert.model.CategoriaDesastre;
import br.com.fiap.CoAlert.repository.CategoriaDesastreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CategoriaDesastreResponseDto create(CategoriaDesastreSaveRequestDto dto) {
        categoriaRepository.inserirCategoria(
            dto.getNmTitulo(),
            dto.getDsCategoria(),
            dto.getNmTipo()
        );
        
        // Buscar a categoria recém-criada pelo título (assumindo que é único)
        CategoriaDesastre categoria = categoriaRepository.findByTitulo(dto.getNmTitulo())
                .orElseThrow(() -> new IllegalStateException("Erro ao criar categoria: não foi possível encontrá-la após a criação"));
        
        return toResponseDto(categoria);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + id);
        }
        categoriaRepository.deletarCategoria(id);
    }

    @Transactional
    public CategoriaDesastreResponseDto update(Long id, CategoriaDesastreEditRequestDto dto) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria de desastre não encontrada com ID: " + id);
        }
        
        categoriaRepository.atualizarCategoria(
            id,
                dto.getNmTitulo(),
                dto.getDsCategoria(),
                dto.getNmTipo()
        );

        CategoriaDesastre categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Erro ao atualizar categoria: não foi possível encontrá-la após a atualização"));
        
        return toResponseDto(categoria);
    }

    private CategoriaDesastreResponseDto toResponseDto(CategoriaDesastre categoria) {
        return new CategoriaDesastreResponseDto(
                categoria.getId(),
                categoria.getTitulo(),
                categoria.getCategoria(),
                categoria.getTipo()
        );
    }
}
