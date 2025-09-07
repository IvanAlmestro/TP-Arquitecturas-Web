package factory;


import lombok.Getter;

import java.sql.*;

public final class ConexionMySQL {
    //metodo estatico para obtener la instancia unica (singleton) osea una sola coneccion y no haya problemas al hacer consultas SQL.
    //VOLATILE ES PARA QUE LA INSTANCIA SEA VISIBLE PARA TODOS LOS HILOS , Y NO SE CREE MAS DE UNA VEZ
    @Getter
    private static volatile ConexionMySQL instance;
    private Connection conn;

    private static final String URL = "jdbc:mysql://localhost:3306/tp1_grupo10?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";
        //constructor privado
        private ConexionMySQL(){
            String driver = "com.mysql.cj.jdbc.Driver";
            try {
                // Cargo el driver
                Class.forName(driver).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                // Guardo en el atributo de la clase
                this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
                this.conn.setAutoCommit(true);
                System.out.println("Conexión a base de datos realizada");
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        public static ConexionMySQL getInstance() {
            if (instance == null) {
                synchronized (ConexionMySQL.class) {
                    if (instance == null) {
                        instance = new ConexionMySQL();
                    }
                }
            }
            return instance;
        }
    //metodo para obtener la conexion
        public Connection getConnection(){
            return this.conn;
        }


    public Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stmt;
    }

    public PreparedStatement prepareStatement(String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pstmt;
    }
}