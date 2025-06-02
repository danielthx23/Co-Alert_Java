package br.com.fiap.CoAlert.controller;

import br.com.fiap.CoAlert.dto.request.LikeSaveRequestDto;
import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import br.com.fiap.CoAlert.dto.response.LikeResponseDto;
import br.com.fiap.CoAlert.dto.response.LikeStatsDto;
import br.com.fiap.CoAlert.service.LikeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@CrossOrigin(origins = "*")
@Tag(name = "Likes", description = "Endpoints para gerenciamento de likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    @Operation(
            summary = "Alternar curtida",
            description = "Adiciona ou remove uma curtida em uma postagem ou comentário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(value = """
                                {
                                  "mensagem": "Operação realizada com sucesso.",
                                  "dados": {
                                    "totalLikes": 10,
                                    "isLiked": true
                                  }
                                }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<LikeStatsDto>> toggleLike(
            @Valid @RequestBody LikeSaveRequestDto dto) {
        LikeStatsDto stats = likeService.toggleLike(dto);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Operação realizada com sucesso.", stats));
    }

    @GetMapping("/postagem/{postagemId}")
    @Operation(
            summary = "Obter curtidas da postagem",
            description = "Retorna as informações de curtidas de uma postagem específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada")
    })
    public ResponseEntity<ApiResponseGeneric<LikeStatsDto>> getPostLikes(
            @PathVariable Long postagemId,
            @RequestParam(required = false) Long usuarioId) {
        LikeStatsDto stats = likeService.getLikeStats(postagemId, null, usuarioId);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Informações de curtidas retornadas com sucesso.", stats));
    }

    @GetMapping("/comentario/{comentarioId}")
    @Operation(
            summary = "Obter curtidas do comentário",
            description = "Retorna as informações de curtidas de um comentário específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<LikeStatsDto>> getCommentLikes(
            @PathVariable Long comentarioId,
            @RequestParam(required = false) Long usuarioId) {
        LikeStatsDto stats = likeService.getLikeStats(null, comentarioId, usuarioId);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Informações de curtidas retornadas com sucesso.", stats));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os likes",
            description = "Retorna todos os likes cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de likes retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseGeneric.class),
                    examples = @ExampleObject(value = """
            {
              "mensagem": "Lista de likes retornada com sucesso.",
              "dados": []
            }
        """)
            )
    )
    public ResponseEntity<ApiResponseGeneric<Page<LikeResponseDto>>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dtLike,desc"` ou `"dtLike,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": "dtLike,desc"
                }
            """)
            )
            @PageableDefault(size = 10, sort = "dtLike", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<LikeResponseDto> likes = likeService.getAll(pageable);
        return ResponseEntity.ok(new ApiResponseGeneric<>("Lista de likes retornada com sucesso.", likes));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir like", description = "Remove um like pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Like excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Like não encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<Void>> delete(
            @Parameter(description = "ID do like", example = "1") @PathVariable Long id) {
        likeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseGeneric<>("Like excluído com sucesso.", null));
    }
} 