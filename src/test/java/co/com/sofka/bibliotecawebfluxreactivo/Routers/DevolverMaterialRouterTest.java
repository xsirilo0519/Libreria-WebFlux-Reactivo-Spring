package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.AreaTematica;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.TipoMaterial;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.DevolverMaterialUseCase;
import co.com.sofka.bibliotecawebfluxreactivo.Utils.Mensaje;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DevolverMaterialRouter.class, DevolverMaterialUseCase.class, MaterialMapper.class})
class DevolverMaterialRouterTest {

    @MockBean
    private MaterialRepositorio materialRepositorio;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testDevolverMaterialPrestado(){
        Material material = new Material("1234",false, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);

        when(materialRepositorio.findById("1234")).thenReturn(materialMono);
        when(materialRepositorio.save(any())).thenReturn(materialMono);


        webTestClient.put()
                .uri("/biblioteca/devolver/1234")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Mensaje.class)
                .value(userResponse->{
                    Assertions.assertThat(userResponse.getMensaje()).isEqualTo("Transaccion satisfactoriamente");
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).findById("1234");
        Mockito.verify(materialRepositorio,Mockito.times(1)).save(any());


    }

    @Test
    public void testDevolverMaterialDisponible(){
        Material material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);

        when(materialRepositorio.findById("1234")).thenReturn(materialMono);

        webTestClient.put()
                .uri("/biblioteca/devolver/1234")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Mensaje.class)
                .value(userResponse->{
                    Assertions.assertThat(userResponse.getMensaje()).isEqualTo("El material esta disponible");
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).findById("1234");
        Mockito.verify(materialRepositorio,Mockito.times(0)).save(any());


    }

}