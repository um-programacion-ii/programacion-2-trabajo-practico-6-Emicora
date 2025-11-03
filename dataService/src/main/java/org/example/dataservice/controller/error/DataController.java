package org.example.dataservice.controller.error;

import org.example.dataservice.entity.*;
import org.example.dataservice.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final InventarioService inventarioService;

    public DataController(ProductoService p, CategoriaService c, InventarioService i){
        this.productoService=p; this.categoriaService=c; this.inventarioService=i;
    }


    // PRODUCTOS
    @GetMapping("/productos") public List<Producto> allProductos(){ return productoService.obtenerTodos(); }
    @GetMapping("/productos/{id}") public Producto byId(@PathVariable Long id){ return productoService.buscarPorId(id); }
    @PostMapping("/productos") @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody Producto p){ return productoService.guardar(p); }
    @PutMapping("/productos/{id}") public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto p){
        return productoService.actualizar(id, p);
    }
    @DeleteMapping("/productos/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){ productoService.eliminar(id); }
    @GetMapping("/productos/categoria/{nombre}")
    public List<Producto> porCategoria(@PathVariable String nombre){ return productoService.buscarPorCategoria(nombre); }

    // CATEGORIAS (básico)
    @GetMapping("/categorias") public List<Categoria> allCategorias(){ return categoriaService.obtenerTodos(); }
    @PostMapping("/categorias") @ResponseStatus(HttpStatus.CREATED)
    public Categoria crearCategoria(@Valid @RequestBody Categoria c){ return categoriaService.guardar(c); }

    // INVENTARIO
    @GetMapping("/inventario/stock-bajo") public List<Inventario> stockBajo(){ return inventarioService.obtenerProductosConStockBajo(); }
    @PutMapping("/inventario/{inventarioId}/stock")
    public Inventario actualizarStock(@PathVariable Long inventarioId, @RequestParam Integer cantidad){
        return inventarioService.actualizarStock(inventarioId, cantidad);
    }
}