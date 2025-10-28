package org.example.dataservice.controller;

import org.example.dataservice.entity.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class DataControllerIntegrationTest {

    @Autowired TestRestTemplate rest;

    @Test
    void crear_y_obtener_producto(){
        Producto p = new Producto();
        p.setNombre("Prod Test");
        p.setDescripcion("desc");
        p.setPrecio(BigDecimal.valueOf(123));

        ResponseEntity<Producto> r = rest.postForEntity("/data/productos", p, Producto.class);
        assertEquals(HttpStatus.CREATED, r.getStatusCode());
        assertNotNull(r.getBody());
        Long id = r.getBody().getId();

        Producto fetched = rest.getForObject("/data/productos/{id}", Producto.class, id);
        assertEquals("Prod Test", fetched.getNombre());
    }
}
