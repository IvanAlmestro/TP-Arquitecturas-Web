package factory;



import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.ProductoDAO;
import repository.mysql.MySQLDAOFactory;

import java.sql.SQLException;

import static factory.DBType.MYSQL;


public  abstract  class DAOFactory {

    public static volatile DAOFactory instance;

    /*volatile en java garantiza visibildiad y orden de memoria entre hilos para esa variable
    * visibilidad: cuando hilo escribe instance otro hilo la lee actualizada
    * orden: una escritura a una variable volatile ocurre antes que cualquier lectura posterior de esa misma variable*/
    public static DAOFactory getInstance(DBType type){
        if(instance == null){
            synchronized (DAOFactory.class){
                if(instance == null){
                    switch (type){
                        case MYSQL:
                            instance = new MySQLDAOFactory();
                            break;
                            //case DERBY:
                            //    instance = new DerbyDAOFactory();
                            //    break;
                        default:
                            throw new IllegalArgumentException("No es valido el DBTYPE:" + type);
                    }
                }
            }
        }
        return instance;
    }
    //FACTORY methods to create DAO objects
    public abstract ClienteDAO crearClienteDAO() throws SQLException;

    public abstract FacturaDAO crearFacturaDAO() throws SQLException;

    public abstract ProductoDAO crearProductoDAO() throws SQLException;
}
