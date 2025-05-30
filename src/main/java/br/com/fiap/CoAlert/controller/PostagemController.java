package br.com.fiap.CoAlert.controller;

import br.com.fiap.CoAlert.dto.request.PostagemSaveRequestDto;
import br.com.fiap.CoAlert.dto.request.PostagemEditRequestDto;
import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import br.com.fiap.CoAlert.dto.response.PostagemResponseDto;
import br.com.fiap.CoAlert.service.PostagemService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*")
@Tag(name = "Postagens", description = "Endpoints para gerenciamento de postagens")
@RequiredArgsConstructor
public class PostagemController {

    private final PostagemService postagemService;

    @GetMapping
    @Operation(
            summary = "Listar todas as postagens",
            description = "Retorna todas as postagens cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de postagens retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de postagens retornada com sucesso.",
              "dados": []
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<PostagemResponseDto>>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataCriacao,desc"` ou `"dataCriacao,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": "dataCriacao,desc"
                }
            """)
            )
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<PostagemResponseDto> postagens = postagemService.getAll(pageable);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Lista de postagens retornada com sucesso.", postagens));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar postagem por ID", description = "Retorna uma postagem com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postagem encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Postagem encontrada com sucesso.",
                                  "dados": {
                                    "id": 1,
                                    "titulo": "Título exemplo",
                                    "conteudo": "Conteúdo da postagem"
                                  }
                                }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Postagem não encontrada com ID: 99",
                                  "dados": null
                                }
                            """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<PostagemResponseDto>> getById(
            @Parameter(description = "ID da postagem", example = "1") @PathVariable Long id
    ) {
        PostagemResponseDto postagem = postagemService.getById(id);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Postagem encontrada com sucesso.", postagem));
    }

    @PostMapping
    @Operation(summary = "Criar nova postagem", description = "Cria uma nova postagem.")
    @ApiResponse(responseCode = "201", description = "Postagem criada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
                        {
                          "mensagem": "Postagem criada com sucesso.",
                          "dados": {
                            "id": 1,
                            "titulo": "Título da postagem",
                            "conteudo": "Conteúdo da postagem"
                          }
                        }
                    """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<PostagemResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova postagem",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PostagemSaveRequestDto.class),
                            examples = @ExampleObject(value = """
                                {
                                  "titulo": "Título da postagem",
                                  "conteudo": "Conteúdo descritivo"
                                }
                            """)
                    )
            )
            @Valid @RequestBody PostagemSaveRequestDto dto
    ) {
        PostagemResponseDto criada = postagemService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseGeneric<>("Postagem criada com sucesso.", criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar postagem", description = "Atualiza os dados de uma postagem existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postagem atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Postagem atualizada com sucesso.",
                                  "dados": {
                                    "id": 1,
                                    "titulo": "Título atualizado",
                                    "conteudo": "Conteúdo atualizado"
                                  }
                                }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ApiResponseGeneric<PostagemResponseDto>> update(
            @Parameter(description = "ID da postagem") @PathVariable Long id,
            @Valid @RequestBody PostagemEditRequestDto dto
    ) {
        PostagemResponseDto atualizada = postagemService.update(id, dto);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Postagem atualizada com sucesso.", atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir postagem", description = "Remove uma postagem pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Postagem excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada")
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID da postagem", example = "1") @PathVariable Long id
    ) {
        postagemService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseGeneric<>("Postagem excluída com sucesso.", null));
    }
}
