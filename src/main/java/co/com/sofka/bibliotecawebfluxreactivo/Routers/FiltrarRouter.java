package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ActualizarMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.FiltrarUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Filtro;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class FiltrarRouter {
    @Bean
    public RouterFunction<ServerResponse> Filtrar(FiltrarUseCase filtrarUseCase) {

        return route(POST("/biblioteca/filtrar").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Filtro.class)
                        .flatMap(filtra->filtrarUseCase.get(filtra).collectList())
                        .flatMap(result->ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))

        );

    }
}