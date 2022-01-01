package co.com.sofka.bibliotecawebfluxreactivo.Repositories;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MaterialRepositorio extends ReactiveMongoRepository<Material, String> {
//    List<Material> findByAreaTematicaOrTipoMaterial(String areaTematica,String tipoMaterial);
//    List<Material> findByAreaTematicaAndTipoMaterial(String areaTematica,String tipoMaterial);

}

