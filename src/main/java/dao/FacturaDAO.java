package dao;

import entity.Factura;
import entity.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {

    void crearFactura(int idFactura, int idCliente);

    List<Factura> listarTodos() throws SQLException;

    List<Factura> listarPorCliente(Integer idCliente) throws SQLException;

    void parseoCsv();
}
