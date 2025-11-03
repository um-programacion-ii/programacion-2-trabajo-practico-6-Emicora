package org.example.businessservice.client.dto;

import lombok.*; import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DataInventario { private Long id; private Integer cantidad; private Integer stockMinimo; private LocalDateTime fechaActualizacion; }