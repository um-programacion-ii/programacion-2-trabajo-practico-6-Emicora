package org.example.businessservice.client.dto;

import lombok.*; import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DataProducto {
    private Long id; private String nombre; private String descripcion; private BigDecimal precio;
    private DataCategoria categoria; private DataInventario inventario;
}