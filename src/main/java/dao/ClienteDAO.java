package dao;

import entity.Cliente;


import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> listarTodos() throws SQLException;
    /*
    *     Person getPersonById(int id);

    *     void updatePerson(Person person);
    void deletePerson(int id);
    * */


    void crearCliente(int id, String nombre, String email);

    void parseoCsv();
}
