package org.example.dataservice.repository;

import org.example.dataservice.entity.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @EntityGraph(attributePaths = {"categoria", "inventario"})
    List<Producto> findAll();
    
    @EntityGraph(attributePaths = {"categoria", "inventario"})
    java.util.Optional<Producto> findById(Long id);
    
    List<Producto> findByCategoria_NombreIgnoreCase(String nombre);
}