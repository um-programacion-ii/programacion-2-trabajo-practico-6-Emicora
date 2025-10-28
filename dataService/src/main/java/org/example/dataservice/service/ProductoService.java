package org.example.dataservice.service;

import org.example.dataservice.entity.Categoria;
import org.example.dataservice.entity.Producto;
import org.example.dataservice.exception.RecursoNoEncontradoException;
import org.example.dataservice.repository.CategoriaRepository;
import org.example.dataservice.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional
public class ProductoService {
    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoService(ProductoRepository p, CategoriaRepository c){ this.productoRepo=p; this.categoriaRepo=c; }

    @Transactional(readOnly = true) public List<Producto> obtenerTodos(){ return productoRepo.findAll(); }

    @Transactional(readOnly = true)
    public Producto buscarPorId(Long id){
        return productoRepo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Producto "+id+" no existe"));
    }

    public Producto guardar(Producto p){
        if (p.getCategoria()!=null && p.getCategoria().getId()!=null){
            Categoria cat = categoriaRepo.findById(p.getCategoria().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Categoria "+p.getCategoria().getId()+" no existe"));
            p.setCategoria(cat);
        }
        // Asegurar la consistencia de la relación bidireccional Producto <-> Inventario
        if (p.getInventario() != null && p.getInventario().getProducto() == null) {
            p.getInventario().setProducto(p);
        }
        return productoRepo.save(p);
    }

    public Producto actualizar(Long id, Producto dto){
        Producto p = buscarPorId(id);
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        if (dto.getCategoria()!=null && dto.getCategoria().getId()!=null){
            p.setCategoria(categoriaRepo.findById(dto.getCategoria().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Categoria "+dto.getCategoria().getId()+" no existe")));
        }
        // Si se envía inventario en la actualización, mantener la relación consistente
        if (dto.getInventario() != null) {
            if (dto.getInventario().getProducto() == null) {
                dto.getInventario().setProducto(p);
            }
            p.setInventario(dto.getInventario());
        }
        return p;
    }

    public void eliminar(Long id){ productoRepo.delete(buscarPorId(id)); }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(String nombre){
        return productoRepo.findByCategoria_NombreIgnoreCase(nombre);
    }
}