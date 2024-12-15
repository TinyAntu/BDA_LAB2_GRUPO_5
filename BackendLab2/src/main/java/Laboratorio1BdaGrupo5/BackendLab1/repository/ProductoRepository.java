package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.PriceHistory;
import Laboratorio1BdaGrupo5.BackendLab1.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

    List<Producto> getProductos(int limit, int offset); // Para paginación
    List<Producto> getProductosSearch(int limit, int offset, String search);
    Producto getProductoById(Integer idProducto);
    public List<Producto> getProductosPorCategoria(Integer idCategoria);
    public List<Producto> getProductosPorCategoria(String name);
    void createProducto(Producto producto);
    void updateProducto(Producto producto);
    void deleteProducto(Integer idProducto);
    long getTotalCount();
    String getProductoName(Integer idProducto);
    Producto getMostVariablePriceProduct();
    List<PriceHistory> getPriceHistory(Integer productoId);
    Producto getFirstProducto();
}
