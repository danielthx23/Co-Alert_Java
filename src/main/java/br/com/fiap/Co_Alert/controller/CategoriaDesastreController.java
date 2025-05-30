package br.com.fiap.Co_Alert.controller;

import br.com.fiap.Co_Alert.dto.request.CategoriaDesastreEditRequestDto;
import br.com.fiap.Co_Alert.dto.request.CategoriaDesastreSaveRequestDto;
import br.com.fiap.Co_Alert.dto.response.ApiResponseGeneric;
import br.com.fiap.Co_Alert.dto.response.CategoriaDesastreResponseDto;
import br.com.fiap.Co_Alert.service.CategoriaDesastreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias-desastre")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Categorias de Desastre", description = "Endpoints para gerenciamento de categorias de desastre")
public class CategoriaDesastreController {

    private final CategoriaDesastreService categoriaService;

    @GetMapping
    @Operation(
            summary = "Listar todas as categorias de desastre",
            description = "Retorna todas as categorias de desastre cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de categorias retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de categorias retornada com sucesso.",
              "dados": [
                {
                  "id": 1,
                  "nmTitulo": "Inundações",
                  "dsCategoria": "Desastres relacionados a enchentes e inundações",
                  "nmTipo": "Natural"
                },
                {
                  "id": 2,
                  "nmTitulo": "Terremotos",
                  "dsCategoria": "Desastres relacionados a tremores de terra",
                  "nmTipo": "Natural"
                }
              ]
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<CategoriaDesastreResponseDto>>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nmTitulo,desc"` ou `"nmTitulo,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": "nmTitulo,desc"
                }
            """)
            )
            @PageableDefault(size = 10, sort = "nmTitulo", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<CategoriaDesastreResponseDto> categorias = categoriaService.getAll(pageable);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Lista de categorias retornada com sucesso.", categorias));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Categoria encontrada com sucesso.",
                      "dados": {
                        "id": 1,
                        "nmTitulo": "Inundações",
                        "dsCategoria": "Desastres relacionados a enchentes e inundações",
                        "nmTipo": "Natural"
                      }
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Categoria de desastre não encontrada com ID: 99",
                      "dados": null
                    }
                """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<CategoriaDesastreResponseDto>> getById(
            @Parameter(description = "ID da categoria", example = "1") @PathVariable Long id
    ) {
        CategoriaDesastreResponseDto categoria = categoriaService.getById(id);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Categoria encontrada com sucesso.", categoria));
    }

    @PostMapping
    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria de desastre.")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
                {
                  "mensagem": "Categoria criada com sucesso.",
                  "dados": {
                    "id": 3,
                    "nmTitulo": "Incêndios Florestais",
                    "dsCategoria": "Desastres relacionados a queimadas e incêndios em florestas",
                    "nmTipo": "Natural"
                  }
                }
            """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<CategoriaDesastreResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova categoria",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CategoriaDesastreSaveRequestDto.class),
                            examples = @ExampleObject(value = """
                        {
                          "nmTitulo": "Incêndios Florestais",
                          "dsCategoria": "Desastres relacionados a queimadas e incêndios em florestas",
                          "nmTipo": "Natural"
                        }
                    """)
                    )
            )
            @Valid @RequestBody CategoriaDesastreSaveRequestDto dto
    ) {
        CategoriaDesastreResponseDto criada = categoriaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseGeneric<>("Categoria criada com sucesso.", criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Categoria atualizada com sucesso.",
                      "dados": {
                        "id": 1,
                        "nmTitulo": "Inundações Atualizadas",
                        "dsCategoria": "Descrição atualizada",
                        "nmTipo": "Natural"
                      }
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ApiResponseGeneric<CategoriaDesastreResponseDto>> update(
            @Parameter(description = "ID da categoria", example = "1") @PathVariable Long id,
            @Valid @RequestBody CategoriaDesastreEditRequestDto dto
    ) {
        CategoriaDesastreResponseDto atualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Categoria atualizada com sucesso.", atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria", description = "Remove uma categoria pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Categoria excluída com sucesso.",
                      "dados": null
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID da categoria", example = "1") @PathVariable Long id
    ) {
        categoriaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseGeneric<>("Categoria excluída com sucesso.", null));
    }
}
