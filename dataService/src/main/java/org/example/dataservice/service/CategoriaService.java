package org.example.dataservice.service;


import org.example.dataservice.entity.Categoria;
import org.example.dataservice.exception.RecursoNoEncontradoException;
import org.example.dataservice.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo){ this.repo=repo; }

    @Transactional(readOnly = true) public List<Categoria> obtenerTodos(){ return repo.findAll(); }
    @Transactional(readOnly = true) public Categoria buscarPorId(Long id){
        return repo.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Categoria "+id+" no existe"));
    }
    public Categoria guardar(Categoria c){ return repo.save(c); }
    public Categoria actualizar(Long id, Categoria c){
        Categoria db = buscarPorId(id);
        db.setNombre(c.getNombre()); db.setDescripcion(c.getDescripcion());
        return db;
    }
    public void eliminar(Long id){ repo.delete(buscarPorId(id)); }
}