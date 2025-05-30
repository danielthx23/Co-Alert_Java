package br.com.fiap.CoAlert.controller;

import br.com.fiap.CoAlert.dto.request.UsuarioEditRequestDto;
import br.com.fiap.CoAlert.dto.request.UsuarioSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import br.com.fiap.CoAlert.dto.response.UsuarioResponseDto;
import br.com.fiap.CoAlert.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna todos os usuários cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de usuários retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de usuários retornada com sucesso.",
              "dados": [
                {
                  "idUsuario": 1,
                  "nmUsuario": "joao.silva",
                  "nmEmail": "joao.silva@email.com"
                }
              ]
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<UsuarioResponseDto>>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nmUsuario,desc"` ou `"nmUsuario,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": "nmUsuario,asc"
                }
            """)
            )
            @PageableDefault(size = 10, sort = "nmUsuario", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<UsuarioResponseDto> usuarios = usuarioService.getAll(pageable);
        ApiResponseGeneric<Page<UsuarioResponseDto>> response =
                new ApiResponseGeneric<>("Lista de usuários retornada com sucesso.", usuarios);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário encontrado com sucesso.",
                      "dados": {
                        "idUsuario": 1,
                        "nmUsuario": "joao.silva",
                        "nmEmail": "joao.silva@email.com"
                      }
                    }
                    """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário não encontrado com ID: 99",
                      "dados": null
                    }
                    """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<UsuarioResponseDto>> getById(
            @Parameter(description = "ID do usuário a ser buscado", example = "1") @PathVariable Long id
    ) {
        UsuarioResponseDto usuario = usuarioService.getById(id);
        ApiResponseGeneric<UsuarioResponseDto> response = new ApiResponseGeneric<>("Usuário encontrado com sucesso.", usuario);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário criado com sucesso.",
                      "dados": {
                        "idUsuario": 1,
                        "nmUsuario": "joao.silva",
                        "nmEmail": "joao.silva@email.com"
                      }
                    }
                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Campo 'nmEmail' inválido.",
                      "dados": null
                    }
                    """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<UsuarioResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo usuário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UsuarioSaveRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "nmUsuario": "joao.silva",
                  "nrSenha": "SenhaSegura123!",
                  "nmEmail": "joao.silva@email.com"
                }
                """)
                    )
            )
            @Valid @RequestBody UsuarioSaveRequestDto dto
    ) {
        UsuarioResponseDto criado = usuarioService.create(dto);
        ApiResponseGeneric<UsuarioResponseDto> response = new ApiResponseGeneric<>("Usuário criado com sucesso.", criado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário atualizado com sucesso.",
                      "dados": {
                        "idUsuario": 1,
                        "nmUsuario": "joao.silva.atualizado",
                        "nmEmail": "joao.novo@email.com"
                      }
                    }
                    """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário não encontrado com ID: 99",
                      "dados": null
                    }
                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Erro de validação nos campos.",
                      "dados": null
                    }
                    """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<UsuarioResponseDto>> update(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do usuário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UsuarioEditRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "nmUsuario": "joao.silva.atualizado",
                  "nrSenha": "NovaSenha123!",
                  "nmEmail": "joao.novo@email.com"
                }
                """)
                    )
            )
            @Valid @RequestBody UsuarioEditRequestDto dto
    ) {
        UsuarioResponseDto atualizado = usuarioService.update(id, dto);
        ApiResponseGeneric<UsuarioResponseDto> response = new ApiResponseGeneric<>("Usuário atualizado com sucesso.", atualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário excluído com sucesso.",
                      "dados": null
                    }
                    """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "mensagem": "Usuário não encontrado com ID: 99",
                      "dados": null
                    }
                    """)
                    )
            )
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID do usuário a ser excluído", example = "1") @PathVariable Long id
    ) {
        usuarioService.delete(id);
        ApiResponseGeneric<Void> response = new ApiResponseGeneric<>("Usuário excluído com sucesso.", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
