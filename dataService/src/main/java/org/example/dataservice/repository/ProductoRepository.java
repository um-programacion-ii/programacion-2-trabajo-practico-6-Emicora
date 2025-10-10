package org.example.dataservice.repository;

import org.example.dataservice.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria_NombreIgnoreCase(String nombre);
}