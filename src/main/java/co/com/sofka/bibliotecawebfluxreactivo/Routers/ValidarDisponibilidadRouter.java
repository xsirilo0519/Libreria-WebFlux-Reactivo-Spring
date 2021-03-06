package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ListarMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ValidarDisponibilidadUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ValidarDisponibilidadRouter {
    @Bean
    public RouterFunction<ServerResponse> validar(ValidarDisponibilidadUseCase validarDisponibilidadUseCase) {
        return route(GET("/biblioteca/validar/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(validarDisponibilidadUseCase.get(request.pathVariable("id")), Mensaje.class))
                        .onErrorResume((Error) -> ServerResponse.badRequest().build())
        );
    }
}
