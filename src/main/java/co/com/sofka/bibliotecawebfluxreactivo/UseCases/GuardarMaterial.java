package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface GuardarMaterial {
    public Mono<MaterialDTO> apply(MaterialDTO materialDTO );
}
