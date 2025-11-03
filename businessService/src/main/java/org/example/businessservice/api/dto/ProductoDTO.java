package org.example.businessservice.api.dto;

import lombok.*; import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoDTO {
    private Long id; private String nombre; private String descripcion;
    private BigDecimal precio; private String categoriaNombre;
    private Integer stock; private Boolean stockBajo;
}