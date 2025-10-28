package org.example.businessservice.service;

import org.example.businessservice.api.dto.ProductoDTO;
import org.example.businessservice.client.DataServiceClient;
import org.example.businessservice.client.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductoBusinessServiceTest {
    @Test
    void listar_ok_mapeaDTO(){
        DataServiceClient client = Mockito.mock(DataServiceClient.class);
        DataProducto p = new DataProducto(1L,"Mouse","", BigDecimal.valueOf(100),
                new DataCategoria(9L,"Perif",null), new DataInventario(1L, 3, 5, null));
        when(client.obtenerProductos()).thenReturn(List.of(p));
        ProductoBusinessService svc = new ProductoBusinessService(client);
        List<ProductoDTO> out = svc.obtenerTodos();
        assertEquals(1, out.size());
        assertTrue(out.get(0).getStockBajo()); // 3 <= 5
    }
}
