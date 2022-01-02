package co.com.sofka.bibliotecawebfluxreactivo.UseCases;


import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Validated
public class PrestarMaterialUseCase implements IObtenerMensajeObjByID {
    private final String MENSAGE_PRESTADO="El material se encuentra prestado";
    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public PrestarMaterialUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;
    }

    @Override
    public Mono<Mensaje> get(String id) {
        return repositorio.findById(id).map(materialMapper.fromCollection())
                .map(material->getMensajeCambioEstado(material,material.isEstado(),MENSAGE_PRESTADO));
    }
    private Mensaje getMensajeCambioEstado(MaterialDTO materialDTO, boolean disponible, String mensaje) {
        if(disponible){
            cambiarEstado(materialDTO);
            return new Mensaje(disponible,"Transaccion satisfactoriamente");
        }
        return new Mensaje(disponible,mensaje );
    }

    private void cambiarEstado(MaterialDTO materialDTO) {
        materialDTO.setEstado(!materialDTO.isEstado());
        if (!materialDTO.isEstado()) {
            materialDTO.setFechaPrestamos(LocalDate.now());
        }
        Material material = materialMapper.fromDTO(materialDTO.getId()).apply(materialDTO);
        repositorio.save(material);
    }



}
