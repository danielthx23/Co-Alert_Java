package br.com.fiap.CoAlert.controller;

import br.com.fiap.CoAlert.dto.request.LocalizacaoSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.LocalizacaoEditRequestDto;
import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import br.com.fiap.CoAlert.dto.response.LocalizacaoResponseDto;
import br.com.fiap.CoAlert.service.LocalizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/localizacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Localizações", description = "Endpoints para gerenciamento de localizações")
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    @GetMapping
    @Operation(
            summary = "Listar todas as localizações",
            description = "Retorna todas as localizações cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de localizações retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de localizações retornada com sucesso.",
              "dados": []
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<LocalizacaoResponseDto>>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nmBairro,desc"` ou `"nmBairro, asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": "nmBairro,desc"
                }
            """)
            )
            @PageableDefault(size = 10, sort = "nmBairro", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<LocalizacaoResponseDto> localizacoes = localizacaoService.getAll(pageable);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Lista de localizações retornada com sucesso.", localizacoes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar localização por ID", description = "Retorna uma localização com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Localização encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    public ResponseEntity<ApiResponseGeneric<LocalizacaoResponseDto>> getById(
            @Parameter(description = "ID da localização", example = "1") @PathVariable Long id) {
        LocalizacaoResponseDto dto = localizacaoService.getById(id);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Localização encontrada com sucesso.", dto));
    }

    @PostMapping
    @Operation(summary = "Criar nova localização", description = "Cria uma nova localização.")
    @ApiResponse(responseCode = "201", description = "Localização criada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
                {
                  "mensagem": "Localização criada com sucesso.",
                  "dados": {
                    "idLocalizacao": 1,
                    "nmBairro": "Centro",
                    "nmLogradouro": "Av. Paulista",
                    "nrNumero": 1000,
                    "nmCidade": "São Paulo",
                    "nmEstado": "SP",
                    "nrCep": "01310-100",
                    "nmPais": "Brasil",
                    "dsComplemento": "Apartamento 101"
                  }
                }
                """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<LocalizacaoResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova localização",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LocalizacaoSaveRequestDto.class),
                            examples = @ExampleObject(value = """
                        {
                          "nmBairro": "Centro",
                          "nmLogradouro": "Av. Paulista",
                          "nrNumero": 1000,
                          "nmCidade": "São Paulo",
                          "nmEstado": "SP",
                          "nrCep": "01310-100",
                          "nmPais": "Brasil",
                          "dsComplemento": "Apartamento 101"
                        }
                        """)
                    )
            )
            @Valid @RequestBody LocalizacaoSaveRequestDto dto) {
        LocalizacaoResponseDto created = localizacaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseGeneric<>("Localização criada com sucesso.", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar localização", description = "Atualiza os dados de uma localização existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                        {
                          "mensagem": "Localização atualizada com sucesso.",
                          "dados": {
                            "idLocalizacao": 1,
                            "nmBairro": "Bela Vista",
                            "nmLogradouro": "Rua Augusta",
                            "nrNumero": 500,
                            "nmCidade": "São Paulo",
                            "nmEstado": "SP",
                            "nrCep": "01305-000",
                            "nmPais": "Brasil",
                            "dsComplemento": "Bloco B"
                          }
                        }
                        """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ApiResponseGeneric<LocalizacaoResponseDto>> update(
            @Parameter(description = "ID da localização") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da localização",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LocalizacaoEditRequestDto.class),
                            examples = @ExampleObject(value = """
                        {
                          "idLocalizacao": 1,
                          "nmBairro": "Bela Vista",
                          "nmLogradouro": "Rua Augusta",
                          "nrNumero": 500,
                          "nmCidade": "São Paulo",
                          "nmEstado": "SP",
                          "nrCep": "01305-000",
                          "nmPais": "Brasil",
                          "dsComplemento": "Bloco B"
                        }
                        """)
                    )
            )
            @Valid @RequestBody LocalizacaoEditRequestDto dto) {
        LocalizacaoResponseDto updated = localizacaoService.update(id, dto);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Localização atualizada com sucesso.", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir localização", description = "Remove uma localização pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Localização excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID da localização", example = "1") @PathVariable Long id) {
        localizacaoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseGeneric<>("Localização excluída com sucesso.", null));
    }
}
