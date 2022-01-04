package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Filtro;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFitroFlux {
    public Flux<MaterialDTO> get(Filtro filtro);
}
