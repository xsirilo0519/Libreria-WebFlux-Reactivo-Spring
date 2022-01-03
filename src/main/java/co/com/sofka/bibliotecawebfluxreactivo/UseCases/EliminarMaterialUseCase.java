package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class EliminarMaterialUseCase implements IObtenerMensajeObjByID{


    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public EliminarMaterialUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;
    }
    @Override
    public Mono<Mensaje> get(String id) {
        Objects.requireNonNull(id,"Id es requerido");
        return repositorio.deleteById(id).then(Mono.just(new Mensaje(true,"Elemento Eliminado")));
    }
}
