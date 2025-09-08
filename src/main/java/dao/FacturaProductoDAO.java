package dao;

import entity.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public interface FacturaProductoDAO {

    void crearFacturaProducto(Integer idProducto, Integer cantidad, Integer idFactura);

    List<FacturaProducto> listarTodos() throws SQLException;
    List<FacturaProducto> listarPorProducto(Integer idProducto) throws SQLException;

    void parseoCsv();
}
