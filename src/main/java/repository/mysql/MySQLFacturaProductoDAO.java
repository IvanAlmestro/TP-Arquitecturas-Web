package repository.mysql;

import dao.FacturaProductoDAO;
import entity.FacturaProducto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO {

    private final Connection conn;

    public MySQLFacturaProductoDAO(Connection connection) throws SQLException {
        this.conn = connection;
        crearTablaSiNoExiste();

    }

    private void crearTablaSiNoExiste() {
        final String sql = "CREATE TABLE IF NOT EXISTS factura_producto (" +
                "idFactura INT NOT NULL," +
                "idProducto INT NOT NULL," +
                "cantidad INT NOT NULL," +
                "FOREIGN KEY (idFactura) REFERENCES factura(idFactura),"+
                "FOREIGN KEY (idProducto) REFERENCES producto(idProducto)"+
                ");";

//TODO: ALTER TABLE DE FK FACTURA

        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            System.out.println("Error en creación de tabla para FacturaProducto: "+e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }

    @Override
    public void parseoCsv() {
        // Abrimos y procesamos el CSV
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("csvFiles/facturas-productos.csv")) {
            // Verificamos que el archivo exista
            if (inputStream == null) {
                throw new FileNotFoundException("No se encontró facturas-productos.csv en resources");
            }
            // Creamos reader y parser dentro de try-with-resources para cerrarlos automáticamente y que no gasten recursos innecesarios
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                 CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
                // Iteramos por cada fila del CSV
                for(CSVRecord row: parser) {
                    this.crearFacturaProducto(Integer.parseInt(row.get("idProducto")), Integer.parseInt(row.get("cantidad")),Integer.parseInt(row.get("idFactura")));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en parseo de CSV para facturas: " + e.getMessage());
            e.printStackTrace();
            System.exit(4);
        }
    }
    @Override
    public void crearFacturaProducto(Integer idProducto, Integer cantidad, Integer idFactura) {
        String sql = "INSERT INTO factura_producto(idProducto, cantidad, idFactura) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, idFactura);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en creación de factura_producto: "+e.getMessage());
            e.printStackTrace();
            System.exit(4);
        }
    }


    @Override
    public List<FacturaProducto> listarTodos() throws SQLException {
        List<FacturaProducto> facturasProductos = new ArrayList<>();
        String sql = "SELECT idProducto, cantidad, idFactura FROM factura_producto";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");
                facturasProductos.add(new FacturaProducto(idProducto, cantidad,idFactura));
            }
        }
        return facturasProductos;
    }

    @Override
    public List<FacturaProducto> listarPorProducto(Integer idProducto) throws SQLException {
        List<FacturaProducto> facturasProductos = new ArrayList<>();
        String sql = "SELECT idProducto, cantidad, idFactura FROM factura_producto where idProducto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idFactura = rs.getInt("idFactura");
                    int cantidad = rs.getInt("cantidad");
                    facturasProductos.add(new FacturaProducto(idProducto, cantidad,idFactura));
                }
            }
            return facturasProductos;
        }catch (Exception e) {
            System.out.println("Error en listado de factura_producto por idProducto: "+e.getMessage());
            e.printStackTrace();
            System.exit(5);
            return null;
        }
    }

}
