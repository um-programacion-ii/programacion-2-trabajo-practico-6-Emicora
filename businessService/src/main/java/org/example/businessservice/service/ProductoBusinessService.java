package org.example.businessservice.service;

import org.example.businessservice.api.dto.*;
import org.example.businessservice.client.DataServiceClient;
import org.example.businessservice.client.dto.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProductoBusinessService {

    private final DataServiceClient data;

    public List<ProductoDTO> obtenerTodos(){
        try { return data.obtenerProductos().stream().map(this::toDTO).toList(); }
        catch (FeignException e){ throw new MicroserviceCommunicationException("Fallo al listar productos"); }
    }

    public ProductoDTO porId(Long id){
        try { return toDTO(data.obtenerProducto(id)); }
        catch (FeignException.NotFound nf){ throw new ProductoNoEncontradoException("No existe producto "+id); }
        catch (FeignException e){ throw new MicroserviceCommunicationException("Fallo al obtener producto"); }
    }

    public ProductoDTO crear(ProductoRequest req){
        validar(req);
        try {
            DataProducto body = new DataProducto();
            body.setNombre(req.getNombre());
            body.setDescripcion(req.getDescripcion());
            body.setPrecio(req.getPrecio());
            body.setCategoria(new DataCategoria(req.getCategoriaId(), null, null));
            DataInventario inv = new DataInventario();
            inv.setCantidad(req.getStock());
            inv.setStockMinimo(req.getStockMinimo());
            body.setInventario(inv);
            return toDTO(data.crearProducto(body));
        } catch (FeignException e){ throw new MicroserviceCommunicationException("Fallo al crear producto"); }
    }

    public List<ProductoDTO> porCategoria(String nombre){
        try { return data.productosPorCategoria(nombre).stream().map(this::toDTO).toList(); }
        catch (FeignException e){ throw new MicroserviceCommunicationException("Fallo al consultar categoría"); }
    }

    public List<ProductoDTO> stockBajo(){
        return obtenerTodos().stream().filter(ProductoDTO::getStockBajo).toList();
    }

    public BigDecimal valorTotalInventario(){
        return obtenerTodos().stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getStock()==null?0:p.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validar(ProductoRequest r){
        if (r.getPrecio().compareTo(BigDecimal.ZERO) <= 0) throw new ValidacionNegocioException("El precio debe ser > 0");
        if (r.getStock() < 0) throw new ValidacionNegocioException("El stock no puede ser negativo");
    }

    private ProductoDTO toDTO(DataProducto p){
        Integer cant = p.getInventario()!=null ? p.getInventario().getCantidad() : null;
        Integer min  = p.getInventario()!=null ? p.getInventario().getStockMinimo() : null;
        boolean bajo = (cant!=null && min!=null) && cant <= min;
        String cat = p.getCategoria()!=null ? p.getCategoria().getNombre() : null;
        return ProductoDTO.builder()
                .id(p.getId()).nombre(p.getNombre()).descripcion(p.getDescripcion())
                .precio(p.getPrecio()).categoriaNombre(cat)
                .stock(cant).stockBajo(bajo).build();
    }
}