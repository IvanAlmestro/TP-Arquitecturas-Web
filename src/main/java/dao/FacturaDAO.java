package dao;

import entity.Factura;

import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {

    void crearFactura(int idFactura, int idCliente);

    List<Factura> listarTodos() throws SQLException;

    void parseoCsv();
}
