package br.com.fiap.Co_Alert.service;

import br.com.fiap.Co_Alert.dto.request.LocalizacaoEditRequestDto;
import br.com.fiap.Co_Alert.dto.request.LocalizacaoSaveRequestDto;
import br.com.fiap.Co_Alert.dto.response.LocalizacaoResponseDto;
import br.com.fiap.Co_Alert.model.Localizacao;
import br.com.fiap.Co_Alert.repository.LocalizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public LocalizacaoResponseDto create(LocalizacaoSaveRequestDto dto) {
        Localizacao localizacao = new Localizacao();

        localizacao.setNmBairro(dto.getNmBairro());
        localizacao.setNmLogradouro(dto.getNmLogradouro());
        localizacao.setNrNumero(dto.getNrNumero());
        localizacao.setNmCidade(dto.getNmCidade());
        localizacao.setNmEstado(dto.getNmEstado());
        localizacao.setNrCep(dto.getNrCep());
        localizacao.setNmPais(dto.getNmPais());
        localizacao.setDsComplemento(dto.getDsComplemento());

        Localizacao saved = localizacaoRepository.save(localizacao);
        return toResponseDto(saved);
    }

    public LocalizacaoResponseDto update(Long id, LocalizacaoEditRequestDto dto) {
        Localizacao existing = localizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + id));

        existing.setNmBairro(dto.getNmBairro());
        existing.setNmLogradouro(dto.getNmLogradouro());
        existing.setNrNumero(dto.getNrNumero());
        existing.setNmCidade(dto.getNmCidade());
        existing.setNmEstado(dto.getNmEstado());
        existing.setNrCep(dto.getNrCep());
        existing.setNmPais(dto.getNmPais());
        existing.setDsComplemento(dto.getDsComplemento());

        Localizacao updated = localizacaoRepository.save(existing);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!localizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Localização não encontrada com ID: " + id);
        }
        localizacaoRepository.deleteById(id);
    }

    private LocalizacaoResponseDto toResponseDto(Localizacao entity) {
        return new LocalizacaoResponseDto(
                entity.getIdLocalizacao(),
                entity.getNmBairro(),
                entity.getNmLogradouro(),
                entity.getNrNumero(),
                entity.getNmCidade(),
                entity.getNmEstado(),
                entity.getNrCep(),
                entity.getNmPais(),
                entity.getDsComplemento()
        );
    }
}
