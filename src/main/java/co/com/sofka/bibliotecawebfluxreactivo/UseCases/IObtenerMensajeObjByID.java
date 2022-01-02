package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import reactor.core.publisher.Mono;

public interface IObtenerMensajeObjByID {
    public Mono<Mensaje> get(String id);
}
