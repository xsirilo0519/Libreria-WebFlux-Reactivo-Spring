package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Collections.Material;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.AreaTematica;
import co.com.sofka.bibliotecawebfluxreactivo.Enums.TipoMaterial;
import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.ObtenerMaterialPorIdUseCase;
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

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ObtenerMaterialPorIdRouter.class, ObtenerMaterialPorIdUseCase.class, MaterialMapper.class})
class ObtenerMaterialPorIdRouterTest {

    @MockBean
    private MaterialRepositorio materialRepositorio;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testObtenerPorId(){
        Material material = new Material("1234",true, LocalDate.now(), TipoMaterial.Libros, AreaTematica.Matematica,"Libro de la vida","muy buena");
        Mono<Material> materialMono = Mono.just(material);

        when(materialRepositorio.findById(material.getId())).thenReturn(materialMono);

        webTestClient.get()
                .uri("/biblioteca/1234")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Material.class)
                .value(userResponse -> {
                    Assertions.assertThat(userResponse.getId()).isEqualTo(material.getId());
                    Assertions.assertThat(userResponse.getNombre()).isEqualTo(material.getNombre());
                    Assertions.assertThat(userResponse.getTipoMaterial()).isEqualTo(material.getTipoMaterial());
                    Assertions.assertThat(userResponse.getFechaPrestamos()).isEqualTo(material.getFechaPrestamos());

                });
        Mockito.verify(materialRepositorio,Mockito.times(1)).findById("1234");

    }

}