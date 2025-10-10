package org.example.dataservice.service;

import org.example.dataservice.entity.Inventario;
import org.example.dataservice.exception.RecursoNoEncontradoException;
import org.example.dataservice.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service @Transactional
public class InventarioService {
    private final InventarioRepository repo;
    public InventarioService(InventarioRepository repo){ this.repo=repo; }

    @Transactional(readOnly = true)
    public List<Inventario> obtenerProductosConStockBajo(){
        return repo.findAll().stream()
                .filter(i -> i.getStockMinimo()!=null && i.getCantidad()!=null && i.getCantidad() <= i.getStockMinimo())
                .toList();
    }

    public Inventario actualizarStock(Long inventarioId, Integer cantidad){
        Inventario inv = repo.findById(inventarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario "+inventarioId+" no existe"));
        inv.setCantidad(cantidad);
        inv.setFechaActualizacion(LocalDateTime.now());
        return inv;
    }
}