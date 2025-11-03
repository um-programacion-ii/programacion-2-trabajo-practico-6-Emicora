package org.example.businessservice.api.dto;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventarioDTO { private Long productoId; private Integer cantidad; private Integer stockMinimo; private boolean stockBajo; }