package co.com.sofka.bibliotecawebfluxreactivo.UseCases;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Filtro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Validated
public class FiltrarUseCase implements IFitroFlux {

    private final MaterialRepositorio repositorio;
    private final MaterialMapper materialMapper;
    @Autowired
    public FiltrarUseCase(MaterialMapper mapperUtils, MaterialRepositorio repositorio) {
        this.repositorio = repositorio;
        this.materialMapper = mapperUtils;
    }

    @Override
    public Flux<MaterialDTO> get(Filtro filtro) {
        String tipoMaterial=filtro.getTipo();
        String areaTematica=filtro.getArea();
        Flux<MaterialDTO> materialDTOS=Flux.empty();
        if (busquedaDoble(tipoMaterial, areaTematica)){
            materialDTOS=obtenerPorareayTipo(areaTematica,tipoMaterial);
        }else{
            materialDTOS=obtenerPorareaoTipo(areaTematica,tipoMaterial);
        }
        return materialDTOS;
    }

    private Flux<MaterialDTO> obtenerPorareayTipo(String areaTematica,String tipoMaterial){
        return repositorio.findByAreaTematicaAndTipoMaterial(areaTematica,tipoMaterial).map(materialMapper.fromCollection());
    }
    private Flux<MaterialDTO> obtenerPorareaoTipo(String areaTematica,String tipoMaterial){
        return repositorio.findByAreaTematicaOrTipoMaterial(areaTematica,tipoMaterial).map(materialMapper.fromCollection());
    }

    private boolean busquedaDoble(String tipoMaterial, String areaTematica) {
        return esVacio(tipoMaterial)&& esVacio(areaTematica);
    }

    private boolean esVacio(String object) {
        return object!=null&&object!="";
    }
}
