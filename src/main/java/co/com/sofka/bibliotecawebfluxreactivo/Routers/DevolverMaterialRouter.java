package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.UseCases.DevolverMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.PrestarMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DevolverMaterialRouter {
    @Bean
    public RouterFunction<ServerResponse> DevolverMaterial(DevolverMaterialUseCase devolverMaterialUseCase) {
        return route(PUT("/biblioteca/devolver/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(devolverMaterialUseCase.get(request.pathVariable("id")), Mensaje.class))
                        .onErrorResume((Error) -> ServerResponse.badRequest().build())
        );
    }
}
