package org.example.businessservice.client;

import org.example.businessservice.client.dto.DataProducto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "data-service", url = "${data.service.url}")
public interface DataServiceClient {
    @GetMapping("/data/productos") List<DataProducto> obtenerProductos();
    @GetMapping("/data/productos/{id}") DataProducto obtenerProducto(@PathVariable Long id);
    @PostMapping("/data/productos") DataProducto crearProducto(@RequestBody DataProducto body);
    @PutMapping("/data/productos/{id}") DataProducto actualizarProducto(@PathVariable Long id, @RequestBody DataProducto body);
    @DeleteMapping("/data/productos/{id}") void eliminarProducto(@PathVariable Long id);
    @GetMapping("/data/productos/categoria/{nombre}") List<DataProducto> productosPorCategoria(@PathVariable String nombre);
    @GetMapping("/data/inventario/stock-bajo") List<DataProducto> stockBajoProxy(); // opcional
}