package co.com.sofka.bibliotecawebfluxreactivo.Routers;

import co.com.sofka.bibliotecawebfluxreactivo.Mappers.MaterialMapper;
import co.com.sofka.bibliotecawebfluxreactivo.Repositories.MaterialRepositorio;
import co.com.sofka.bibliotecawebfluxreactivo.UseCases.EliminarMaterialUseCase;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EliminarMaterialRouter.class, EliminarMaterialUseCase.class, MaterialMapper.class})
class EliminarMaterialRouterTest {

    @MockBean
    MaterialRepositorio materialRepositorio;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testEliminarMaterial(){
        Mensaje mensaje=new Mensaje(true,"Elemento Eliminado2");

        when(materialRepositorio.deleteById("xxx")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/biblioteca/xxx")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Mensaje.class)
                .value(userResponse->{
                    Assertions.assertThat(userResponse.getMensaje()).isEqualTo("Elemento Eliminado");
                });

        Mockito.verify(materialRepositorio,Mockito.times(1)).deleteById("xxx");


    }

}