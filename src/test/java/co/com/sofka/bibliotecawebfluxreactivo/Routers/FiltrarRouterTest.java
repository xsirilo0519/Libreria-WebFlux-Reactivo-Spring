package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.AreaTematica;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.TipoMaterial;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.FiltrarUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Filtro;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes ={FiltrarRouter.class, FiltrarUseCase.class, MaterialMapper.class})
class FiltrarRouterTest {

    @MockBean
    private MaterialRepositorio materialRepositorio;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testFiltrarAreayTipo(){
        Material material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);
        Filtro filtro= new Filtro("Libros","Matematica");


        when(materialRepositorio.findByAreaTematicaAndTipoMaterial(filtro.getArea(),filtro.getTipo())).thenReturn(Flux.just(material));

        webTestClient.post()
                .uri("/biblioteca/filtrar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(filtro), Filtro.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MaterialDTO.class)
                .value(useResponse->{
                    Assertions.assertThat(useResponse.get(0).getId()).isEqualTo(material.getId());
                    Assertions.assertThat(useResponse.get(0).getNombre()).isEqualTo(material.getNombre());
                    Assertions.assertThat(useResponse.get(0).getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                    Assertions.assertThat(useResponse.get(0).getFechaPrestamos()).isEqualTo(material.getFechaPrestamos());
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).findByAreaTematicaAndTipoMaterial(filtro.getArea(),filtro.getTipo());

    }

    @Test
    public void testFiltrarArea(){
        Material material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);
        Filtro filtro= new Filtro("","Matematica");


        when(materialRepositorio.findByAreaTematicaOrTipoMaterial(filtro.getArea(),filtro.getTipo())).thenReturn(Flux.just(material));

        webTestClient.post()
                .uri("/biblioteca/filtrar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(filtro), Filtro.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MaterialDTO.class)
                .value(useResponse->{
                    Assertions.assertThat(useResponse.get(0).getId()).isEqualTo(material.getId());
                    Assertions.assertThat(useResponse.get(0).getNombre()).isEqualTo(material.getNombre());
                    Assertions.assertThat(useResponse.get(0).getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                    Assertions.assertThat(useResponse.get(0).getFechaPrestamos()).isEqualTo(material.getFechaPrestamos());
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).findByAreaTematicaOrTipoMaterial(filtro.getArea(),filtro.getTipo());

    }

    @Test
    public void testFiltrarTipo(){
        Material material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);
        Filtro filtro= new Filtro("Libros","");


        when(materialRepositorio.findByAreaTematicaOrTipoMaterial(filtro.getArea(),filtro.getTipo())).thenReturn(Flux.just(material));

        webTestClient.post()
                .uri("/biblioteca/filtrar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(filtro), Filtro.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MaterialDTO.class)
                .value(useResponse->{
                    Assertions.assertThat(useResponse.get(0).getId()).isEqualTo(material.getId());
                    Assertions.assertThat(useResponse.get(0).getNombre()).isEqualTo(material.getNombre());
                    Assertions.assertThat(useResponse.get(0).getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                    Assertions.assertThat(useResponse.get(0).getFechaPrestamos()).isEqualTo(material.getFechaPrestamos());
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).findByAreaTematicaOrTipoMaterial(filtro.getArea(),filtro.getTipo());

    }


}