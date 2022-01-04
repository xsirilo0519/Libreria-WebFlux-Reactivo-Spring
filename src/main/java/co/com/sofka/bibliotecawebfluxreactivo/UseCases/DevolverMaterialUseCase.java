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
        return repositorio.findById(id).map(materialMapper.fromCollection())
                .flatMap(material->{
                    if (!material.isEstado()){
                        material.setFechaPrestamos(LocalDate.now());
                        material.setEstado(!material.isEstado());
                        return repositorio.save(materialMapper.fromDTO(material.getId()).apply(material)).thenReturn(new Mensaje(true,"Transaccion satisfactoriamente"));
                    }
                    return Mono.just(new Mensaje(false,MENSAGE_DISPONIBLE ));
                })
                .switchIfEmpty(Mono.just(new Mensaje(true,"sadas")));
    }


}
