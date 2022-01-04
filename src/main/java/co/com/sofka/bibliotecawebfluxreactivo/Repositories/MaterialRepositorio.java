package co.com.sofka.bibliotecawebfluxreactivo.Repositories;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MaterialRepositorio extends ReactiveMongoRepository<Material, String> {
    Flux<Material> findByAreaTematicaOrTipoMaterial(String areaTematica, String tipoMaterial);
    Flux<Material> findByAreaTematicaAndTipoMaterial(String areaTematica,String tipoMaterial);



}

