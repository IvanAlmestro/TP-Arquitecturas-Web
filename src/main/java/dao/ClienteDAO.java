package dao;

import entity.Cliente;


import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> listarTodos() throws SQLException;

    List<Cliente> listarOrdenadoPorRecaudacion()  throws SQLException ;


    void crearCliente(int id, String nombre, String email);

    void parseoCsv();
}
