package org.example.dataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", unique = true)
    @JsonIgnoreProperties({"inventario", "categoria", "productos"})
    private Producto producto;

    @Min(0) @Column(nullable = false) private Integer cantidad;
    @Min(0) @Column(name = "stock_minimo") private Integer stockMinimo;
    @Column(name = "fecha_actualizacion") private LocalDateTime fechaActualizacion;
}