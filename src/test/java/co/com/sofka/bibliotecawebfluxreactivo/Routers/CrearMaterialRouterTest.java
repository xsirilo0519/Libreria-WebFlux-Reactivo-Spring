package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.DTOs.MaterialDTO;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.AreaTematica;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.TipoMaterial;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.CrearMaterialUseCase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CrearMaterialRouter.class, CrearMaterialUseCase.class, MaterialMapper.class})
class CrearMaterialRouterTest {

    @MockBean
    private MaterialRepositorio materialRepositorio;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCrearMaterial(){
        var material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        var materialDto = new MaterialDTO("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);

        when(materialRepositorio.save(any())).thenReturn(materialMono);

        webTestClient.post()
                .uri("/biblioteca/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(materialDto), MaterialDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MaterialDTO.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo(material.getId());
                            Assertions.assertThat(userResponse.getNombre()).isEqualTo(material.getNombre());
                            Assertions.assertThat(userResponse.getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                            Assertions.assertThat(userResponse.getFechaPrestamos()).isEqualTo(material.getFechaPrestamos());

                        }
                );
    }
}