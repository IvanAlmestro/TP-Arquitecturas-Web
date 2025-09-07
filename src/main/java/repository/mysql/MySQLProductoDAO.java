package repository.mysql;

import dao.ProductoDAO;
import entity.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductoDAO implements ProductoDAO {

    private final Connection conn;

    public MySQLProductoDAO(Connection connection) throws SQLException {
        this.conn = connection;
        crearTablaSiNoExiste();

    }

    private void crearTablaSiNoExiste() {
        final String sql = "CREATE TABLE IF NOT EXISTS producto (" +
                "idProducto INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(45) NOT NULL," +
                "valor FLOAT NOT NULL" +
                ");";

        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseoCsv() {
        // Abrimos y procesamos el CSV
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("csvFiles/productos.csv")) {
            // Verificamos que el archivo exista
            if (inputStream == null) {
                throw new FileNotFoundException("No se encontró productos.csv en resources");
            }
            // Creamos reader y parser dentro de try-with-resources para cerrarlos automáticamente y que no gasten recursos innecesarios
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                 CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
                // Iteramos por cada fila del CSV
                for(CSVRecord row: parser) {
                    this.crearProducto(Integer.parseInt(row.get("idProducto")), row.get("nombre"), Double.valueOf(row.get("valor")));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en parseo de CSV para productos: " + e.getMessage());
            e.printStackTrace();
            System.exit(5);
        }
    }
    @Override
    public void crearProducto(int idProducto, String nombre, Double valor) {
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setString(2, nombre);
            stmt.setDouble(3, valor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Producto getProductoById(int id) {
        final String sql = "SELECT * FROM persons WHERE id = " + id + ";";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("idProducto"),
                            rs.getString("nombre"),
                            rs.getDouble("valor")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT idProducto, nombre, valor FROM producto";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                Double valor = rs.getDouble("valor");
                productos.add(new Producto(idProducto, nombre, valor));
            }
        }
        return productos;
    }


}
