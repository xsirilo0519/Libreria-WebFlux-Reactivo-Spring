package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Supplier;

@Service
@Validated
public class ObtenerMaterialPorIdUseCase implements ObtenerPorId {

    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public ObtenerMaterialPorIdUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;
    }


    @Override
    public Mono<MaterialDTO> get(String id) {
        Objects.requireNonNull(id,"Id es requerido");
        return repositorio.findById(id).map(materialMapper.fromCollection());
    }
}
