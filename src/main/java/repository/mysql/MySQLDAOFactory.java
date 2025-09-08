package repository.mysql;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import factory.ConexionMySQL;
import factory.DAOFactory;

import java.sql.SQLException;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public ClienteDAO crearClienteDAO() throws SQLException {
        return new MySQLClienteDAO(ConexionMySQL.getInstance().getConnection());
    }

    @Override
    public FacturaDAO crearFacturaDAO() throws SQLException {
        return new MySQLFacturaDAO(ConexionMySQL.getInstance().getConnection());
    }
    @Override
    public ProductoDAO crearProductoDAO() throws SQLException {
        return new MySQLProductoDAO(ConexionMySQL.getInstance().getConnection(), this.crearFacturaProductoDAO());
    }
    @Override
    public FacturaProductoDAO crearFacturaProductoDAO() throws SQLException {
        return new MySQLFacturaProductoDAO(ConexionMySQL.getInstance().getConnection());
    }

}
