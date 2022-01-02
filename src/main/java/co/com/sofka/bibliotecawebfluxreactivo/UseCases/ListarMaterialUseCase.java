package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;
@Service
@Validated
public class ListarMaterialUseCase implements Supplier<Flux<MaterialDTO>> {
        private final MaterialRepositorio repositorio;
        private final MaterialMapper materialMapper;
        @Autowired
        public ListarMaterialUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
            this.repositorio = repositorio;
            this.materialMapper = mapperUtils;
        }

        @Override
        public Flux<MaterialDTO> get() {
            return repositorio.findAll().map(materialMapper.fromCollection());
        }
}
