package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.UseCases.DevolverMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.EliminarMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EliminarMaterialRouter {
    @Bean
    public RouterFunction<ServerResponse> EliminarMaterial(EliminarMaterialUseCase eliminarMaterialUseCase) {
        return route(DELETE("/biblioteca/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(eliminarMaterialUseCase.get(request.pathVariable("id")), Mensaje.class))
                        .onErrorResume((Error) -> ServerResponse.noContent().build())
        );
    }
}
