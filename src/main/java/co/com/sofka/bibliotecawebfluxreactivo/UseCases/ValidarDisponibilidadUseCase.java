package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class ValidarDisponibilidadUseCase implements ObtenerMensajePorId{
    private final String MENSAGE_DISPONIBLE="El material esta disponible";
    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public ValidarDisponibilidadUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;
    }


    @Override
    public Mono<String> get(String id) {
        return repositorio.findById(id).map(materialMapper.fromCollection())
                .map(material->getMensajeValidado(material,material.isEstado()))
                .switchIfEmpty(Mono.just("No se encontró el valor"));
    }

    private String getMensajeValidado(MaterialDTO materialDTO, boolean disponible) {
        if(disponible){
            return MENSAGE_DISPONIBLE;
        }else{
            return "El material fue prestado el: "+ materialDTO.getFechaPrestamos();
        }
    }
}
