package org.example.dataservice.service;

import org.example.dataservice.entity.Categoria;
import org.example.dataservice.entity.Inventario;
import org.example.dataservice.entity.Producto;
import org.example.dataservice.repository.CategoriaRepository;
import org.example.dataservice.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Test
    void guardar_setea_relacion_inventario_producto_y_resuelve_categoria() {
        // Repos mocks
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        CategoriaRepository catRepo = mock(CategoriaRepository.class);
        ProductoService service = new ProductoService(prodRepo, catRepo);

        // Datos de entrada: categoria por id, inventario sin setear producto
        Categoria categoria = Categoria.builder().id(10L).nombre("Cat").build();
        when(catRepo.findById(10L)).thenReturn(Optional.of(categoria));

        Producto in = new Producto();
        in.setNombre("Mouse");
        in.setDescripcion("optico");
        in.setPrecio(BigDecimal.valueOf(100));
        Categoria refCat = new Categoria();
        refCat.setId(10L);
        in.setCategoria(refCat);
        Inventario inv = new Inventario();
        inv.setCantidad(3);
        inv.setStockMinimo(5);
        in.setInventario(inv); // relación aún no consistente (inv.producto == null)

        // Simular save devolviendo el mismo objeto
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        when(prodRepo.save(any(Producto.class))).thenAnswer(a -> a.getArgument(0));

        Producto out = service.guardar(in);

        // Verificar que se llamó a save con la relación consistente
        verify(prodRepo).save(captor.capture());
        Producto toSave = captor.getValue();
        assertNotNull(toSave.getInventario(), "Inventario no debe ser null");
        assertSame(toSave, toSave.getInventario().getProducto(), "Inventario.producto debe apuntar al mismo Producto");

        // Además, debe haberse resuelto la categoria por id
        assertSame(categoria, out.getCategoria(), "La categoría debe ser la entidad gestionada encontrada por id");
    }
}
