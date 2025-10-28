package org.example.businessservice.controller;

import org.example.businessservice.api.dto.*;
import org.example.businessservice.service.ProductoBusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusinessController {

    private final ProductoBusinessService productos;

    @GetMapping("/productos") public List<ProductoDTO> all(){ return productos.obtenerTodos(); }
    @GetMapping("/productos/{id}") public ProductoDTO byId(@PathVariable Long id){ return productos.porId(id); }
    @PostMapping("/productos") @ResponseStatus(HttpStatus.CREATED) public ProductoDTO crear(@Valid @RequestBody ProductoRequest req){ return productos.crear(req); }
    @GetMapping("/productos/categoria/{nombre}") public List<ProductoDTO> porCategoria(@PathVariable String nombre){ return productos.porCategoria(nombre); }
    @GetMapping("/reportes/stock-bajo") public List<ProductoDTO> stockBajo(){ return productos.stockBajo(); }
    @GetMapping("/reportes/valor-inventario") public BigDecimal valorTotal(){ return productos.valorTotalInventario(); }
}
