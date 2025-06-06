package br.com.fiap.CoAlert.service;

import br.com.fiap.CoAlert.dto.request.LocalizacaoSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.LocalizacaoEditRequestDto;
import br.com.fiap.CoAlert.dto.response.LocalizacaoResponseDto;
import br.com.fiap.CoAlert.model.Localizacao;
import br.com.fiap.CoAlert.repository.LocalizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;

    public Page<LocalizacaoResponseDto> getAll(Pageable pageable) {
        return localizacaoRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public LocalizacaoResponseDto getById(Long id) {
        Localizacao localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + id));
        return toResponseDto(localizacao);
    }

    @Transactional
    public LocalizacaoResponseDto create(LocalizacaoSaveRequestDto dto) {
        localizacaoRepository.inserirLocalizacao(
            dto.getNmBairro(),
            dto.getNmLogradouro(),
            dto.getNrNumero(),
            dto.getNmCidade(),
            dto.getNmEstado(),
            dto.getNrCep(),
            dto.getNmPais(),
            dto.getDsComplemento()
        );
        
        // Buscar a localização recém-criada pelos dados únicos
        Localizacao localizacao = localizacaoRepository.findByBairroAndLogradouroAndNumeroAndCidadeAndEstadoAndCepAndPais(
                dto.getNmBairro(),
                dto.getNmLogradouro(),
                dto.getNrNumero(),
                dto.getNmCidade(),
                dto.getNmEstado(),
                dto.getNrCep(),
                dto.getNmPais()
        ).orElseThrow(() -> new IllegalStateException("Erro ao criar localização: não foi possível encontrá-la após a criação"));
        
        return toResponseDto(localizacao);
    }

    @Transactional
    public void delete(Long id) {
        if (!localizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Localização não encontrada com ID: " + id);
        }
        localizacaoRepository.deletarLocalizacao(id);
    }

    @Transactional
    public LocalizacaoResponseDto update(Long id, LocalizacaoEditRequestDto dto) {
        if (!localizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Localização não encontrada com ID: " + id);
        }
        
        localizacaoRepository.atualizarLocalizacao(
            id,
                dto.getNmBairro(),
                dto.getNmLogradouro(),
                dto.getNrNumero(),
                dto.getNmCidade(),
                dto.getNmEstado(),
                dto.getNrCep(),
                dto.getNmPais(),
                dto.getDsComplemento()
        );

        Localizacao localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Erro ao atualizar localização: não foi possível encontrá-la após a atualização"));
        
        return toResponseDto(localizacao);
    }

    private LocalizacaoResponseDto toResponseDto(Localizacao entity) {
        return new LocalizacaoResponseDto(
                entity.getId(),
                entity.getBairro(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep(),
                entity.getPais(),
                entity.getComplemento()
        );
    }
}
