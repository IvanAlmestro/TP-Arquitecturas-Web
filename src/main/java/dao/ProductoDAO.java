package dao;

import entity.Producto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductoDAO {
    Producto getProductoById(Integer id);

    List<Producto> listarTodos() throws SQLException;
    
    void crearProducto(int id, String nombre, Double valor);

    void parseoCsv();

    Map<String,Object> obtenerRecaudacionMaxima() throws SQLException;
}
