package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.AreaTematica;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.TipoMaterial;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ListarMaterialUseCase;
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
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ListarMaterialRouter.class, ListarMaterialUseCase.class, MaterialMapper.class})
class ListarMaterialRouterTest {
    @MockBean
    private MaterialRepositorio materialRepositorio;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void testGetDatos() {
        var material = new Material("1234", true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica, "Libro de la vida", "muy buena");
        var material2 = new Material("56789", true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Ciencias, "Libro de la casa", "muy buena");

        when(materialRepositorio.findAll()).thenReturn(Flux.just(material, material2));

        webTestClient.get()
                .uri("/biblioteca")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MaterialDTO.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                            Assertions.assertThat(userResponse.get(1).getFechaPrestamos()).isEqualTo(material2.getFechaPrestamos());
                        }
                );

        Mockito.verify(materialRepositorio,Mockito.times(1)).findAll();

    }
}