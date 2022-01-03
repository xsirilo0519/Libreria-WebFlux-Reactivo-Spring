package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ActualizarMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.CrearMaterialUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ActualizarMaterialRouter {
    @Bean
    public RouterFunction<ServerResponse> ActualizarMaterial(ActualizarMaterialUseCase actualizarMaterialUseCase) {
        return route(PUT("/biblioteca/modificar").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(MaterialDTO.class)
                        .flatMap(materialDTO -> actualizarMaterialUseCase.apply(materialDTO)
                                .flatMap(resultMaterial -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(resultMaterial))
                        )
        );
    }
}
