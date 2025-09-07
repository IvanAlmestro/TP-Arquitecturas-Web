package dao;

import entity.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    List<Producto> listarTodos() throws SQLException;
    
    void crearProducto(int id, String nombre, Double valor);

    void parseoCsv();
}
