package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import reactor.core.publisher.Mono;

public interface ObtenerMensajePorId {
    public Mono<String> get(String id);
}
