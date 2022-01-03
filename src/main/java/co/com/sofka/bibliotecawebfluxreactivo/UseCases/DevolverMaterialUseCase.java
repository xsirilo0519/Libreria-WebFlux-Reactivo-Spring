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
public class DevolverMaterialUseCase implements IObtenerMensajeObjByID{
    private final String MENSAGE_DISPONIBLE="El material esta disponible";
    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public DevolverMaterialUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;}
    @Override
    public Mono<Mensaje> get(String id) {
        return repositorio.findById(id).map(material->materialMapper.fromCollection().apply(material))
                .map(material->getMensajeCambioEstado(material,!material.isEstado(),MENSAGE_DISPONIBLE));
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
        System.out.println(material.toString());
        this.repositorio.save(material);

        System.out.println("--------------------------------------------------");
    }
}
