package co.com.sofka.bibliotecawebfluxreactivo.Mappers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MaterialMapper {
    public Function<MaterialDTO, Material> fromDTO(String id) {
        return dto->{
            var material = new Material();
            material.setId(id);
            material.setNombre(dto.getNombre());
            material.setEstado(dto.isEstado());
            material.setFechaPrestamos(dto.getFechaPrestamos());
            material.setTipoMaterial(dto.getTipoMaterial());
            material.setAreaTematica(dto.getAreaTematica());
            material.setDescripcion(dto.getDescripcion());
            return material;
        };
    }

    public Function<Material, MaterialDTO> fromCollection() {
        return collection->{
                var materialDTO = new MaterialDTO();
        materialDTO.setId(collection.getId());
        materialDTO.setNombre(collection.getNombre());
        materialDTO.setEstado(collection.isEstado());
        materialDTO.setFechaPrestamos(collection.getFechaPrestamos());
        materialDTO.setTipoMaterial(collection.getTipoMaterial());
        materialDTO.setAreaTematica(collection.getAreaTematica());
        materialDTO.setDescripcion(collection.getDescripcion());
        return materialDTO;
        };
    }


}
