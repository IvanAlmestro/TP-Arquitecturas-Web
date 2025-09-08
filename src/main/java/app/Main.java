package app;
import dao.ClienteDAO;

import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import factory.DAOFactory;
import factory.DBType;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Creamos la factory de MySQL
            DAOFactory factory = DAOFactory.getInstance(DBType.MYSQL);

            // 2. Obtenemos el DAO
            ClienteDAO clienteDAO = factory.crearClienteDAO();
            FacturaDAO facturaDAO = factory.crearFacturaDAO();
            ProductoDAO productoDAO = factory.crearProductoDAO();
            FacturaProductoDAO facturaProductoDAO = factory.crearFacturaProductoDAO();

            if(clienteDAO.listarTodos().size()==0){
                clienteDAO.parseoCsv();
            }

            if(facturaDAO.listarTodos().size()==0){
                facturaDAO.parseoCsv();
            }
            if(productoDAO.listarTodos().size()==0){
                productoDAO.parseoCsv();
            }

            if(facturaProductoDAO.listarTodos().size()==0){
                facturaProductoDAO.parseoCsv();
            }
            // 4. Listamos todas las personas
            clienteDAO.listarTodos().forEach(System.out::println);

            facturaDAO.listarTodos().forEach(System.out::println);
            productoDAO.listarTodos().forEach(System.out::println);
            facturaProductoDAO.listarTodos().forEach(System.out::println);


            System.out.println(productoDAO.obtenerRecaudacionMaxima());

        } catch (SQLException e) {
            System.out.println("Error en main: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

