package org.example.businessservice.api.dto;

import jakarta.validation.constraints.*; import lombok.*; import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoRequest {
    @NotBlank private String nombre; private String descripcion;
    @NotNull @DecimalMin(value="0.0", inclusive=false) private BigDecimal precio;
    @NotNull private Long categoriaId;
    @NotNull @Min(0) private Integer stock;
    @NotNull @Min(0) private Integer stockMinimo;
}