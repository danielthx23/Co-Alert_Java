package br.com.fiap.Co_Alert.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ✅ Corrigido: Jackson mapper instanciado

    @Bean
    OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Global Solution FIAP 2025 - Co-Alert")
                        .description("""
                                Entrega do projeto Java para a Global Solution FIAP 2025.

                                **Autores:**
                                - Daniel Saburo Akiyama (RM 558263)
                                - Danilo Correia e Silva (RM 557540)
                                - João Pedro Rodrigues da Costa (RM 558199)
                                """)
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("-"));
    }

    @Bean
    OpenApiCustomizer customerGlocalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {

                    ApiResponses apiResponses = operation.getResponses();

                    apiResponses.addApiResponse("200", createApiResponse("Operação realizada com sucesso.", "{}"));
                    apiResponses.addApiResponse("201", createApiResponse("Recurso criado com sucesso.", "{}"));
                    apiResponses.addApiResponse("204", createApiResponse("Recurso excluído com sucesso.", null));
                    apiResponses.addApiResponse("400", createApiResponse("Erro na requisição.", """
                        {
                          "campo": "mensagem de erro"
                        }
                        """));
                    apiResponses.addApiResponse("401", createApiResponse("Token JWT inválido ou ausente.", null));
                    apiResponses.addApiResponse("403", createApiResponse("Você não tem permissão para acessar este recurso.", null));
                    apiResponses.addApiResponse("404", createApiResponse("Recurso não encontrado: id=123", null));
                    apiResponses.addApiResponse("500", createApiResponse("Erro interno do servidor. Por favor, tente novamente mais tarde.", null));

                })
        );
    }

    private ApiResponse createApiResponse(String message, String exampleDataJson) {
        Map<String, Object> exampleMap = new LinkedHashMap<>();
        exampleMap.put("mensagem", message);

        if (exampleDataJson != null && !exampleDataJson.isBlank()) {
            try {
                Map<String, Object> dados = objectMapper.readValue(exampleDataJson, Map.class);
                exampleMap.put("dados", dados);
            } catch (JsonProcessingException e) {
                System.err.println("Erro ao parsear JSON do exemplo: " + e.getMessage());
                exampleMap.put("dados", new LinkedHashMap<>()); // fallback: vazio
            }
        }

        return new ApiResponse()
                .description(message)
                .content(new Content().addMediaType("application/json",
                        new MediaType().addExamples("default",
                                new Example().value(exampleMap))));
    }
}
