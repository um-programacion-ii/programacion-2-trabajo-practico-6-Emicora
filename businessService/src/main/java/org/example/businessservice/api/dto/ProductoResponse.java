package org.example.businessservice.api.dto;

import lombok.*; import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoResponse { private Long id; private String nombre; private BigDecimal precio; private Integer stock; private Boolean stockBajo; }