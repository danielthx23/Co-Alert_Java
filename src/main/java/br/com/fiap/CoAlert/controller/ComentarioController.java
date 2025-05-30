package br.com.fiap.CoAlert.controller;

import br.com.fiap.CoAlert.dto.request.ComentarioEditRequestDto;
import br.com.fiap.CoAlert.dto.request.ComentarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import br.com.fiap.CoAlert.dto.response.ComentarioResponseDto;
import br.com.fiap.CoAlert.service.ComentarioService;
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

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Comentários", description = "Endpoints para gerenciamento de comentários")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping
    @Operation(
            summary = "Listar todos os comentários",
            description = "Retorna todos os comentários cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de comentários retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de comentários retornada com sucesso.",
              "dados": []
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<ComentarioResponseDto>>> getAll(
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

        Page<ComentarioResponseDto> comentarios = comentarioService.getAll(pageable);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Lista de comentários retornada com sucesso.", comentarios));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar comentário por ID", description = "Retorna um comentário com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Comentário encontrado com sucesso.",
                                  "dados": {
                                    "idComentario": 1,
                                    "nmConteudo": "Texto do comentário"
                                  }
                                }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Comentário não encontrado com ID: 99",
                                  "dados": null
                                }
                            """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<ComentarioResponseDto>> getById(
            @Parameter(description = "ID do comentário", example = "1") @PathVariable Long id
    ) {
        ComentarioResponseDto comentario = comentarioService.getById(id);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Comentário encontrado com sucesso.", comentario));
    }

    @PostMapping
    @Operation(summary = "Criar novo comentário", description = "Cria um novo comentário associado a um usuário e uma postagem.")
    @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
                        {
                          "mensagem": "Comentário criado com sucesso.",
                          "dados": {
                            "idComentario": 1,
                            "nmConteudo": "Texto do comentário"
                          }
                        }
                    """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<ComentarioResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo comentário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ComentarioSaveRequestDto.class),
                            examples = @ExampleObject(value = """
                                {
                                  "nmConteudo": "Texto do comentário",
                                  "dtEnvio": "2025-05-30T12:00:00",
                                  "nrLikes": 0,
                                  "idUsuario": 1,
                                  "idPostagem": 1
                                }
                            """)
                    )
            )
            @Valid @RequestBody ComentarioSaveRequestDto dto
    ) {
        ComentarioResponseDto criado = comentarioService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseGeneric<>("Comentário criado com sucesso.", criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar comentário", description = "Atualiza os dados de um comentário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Comentário atualizado com sucesso.",
                                  "dados": {
                                    "idComentario": 1,
                                    "nmConteudo": "Comentário atualizado"
                                  }
                                }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ApiResponseGeneric<ComentarioResponseDto>> update(
            @Parameter(description = "ID do comentário") @PathVariable Long id,
            @Valid @RequestBody ComentarioEditRequestDto dto
    ) {
        ComentarioResponseDto atualizado = comentarioService.update(id, dto);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Comentário atualizado com sucesso.", atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir comentário", description = "Remove um comentário pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID do comentário", example = "1") @PathVariable Long id
    ) {
        comentarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseGeneric<>("Comentário excluído com sucesso.", null));
    }
}
