package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * la clase ConnectionFactory proporciona dos métodos para establecer conexiones
 * con diferentes bases de datos. Cada método devuelve un objeto de conexión
 * (Connection) a la base de datos utilizando las URL de conexión, nombres de
 * usuario y contraseñas proporcionados en los argumentos de los métodos. Estas
 * conexiones se pueden utilizar para ejecutar operaciones SQL en la base de
 * datos correspondiente.
 * 
 */

/*
 * public class ConnectionFactory {
 * 
 * public DataSource dataSource;
 * 
 * public ConnectionFactory() { ComboPooledDataSource comboPooledDataSource =
 * new ComboPooledDataSource(); comboPooledDataSource // .setJdbcUrl(
 * "jdbc:mysql://192.168.152.128:3306/hotelalura?useTimezone=true&serverTimezone=UTC"
 * ); .setJdbcUrl(
 * "jdbc:mysql://172.16.3.129:3306/hotelalura?useTimezone=true&serverTimezone=UTC"
 * ); comboPooledDataSource.setUser("Alura");
 * comboPooledDataSource.setPassword("aluraLatam1234");
 * 
 * this.dataSource = comboPooledDataSource; }
 * 
 * // Recupera una conexión a la base de datos en el servidor MySQL public
 * Connection recuperarConexion() { try { return
 * this.dataSource.getConnection(); } catch (SQLException e) { throw new
 * RuntimeException(e); } } }
 */

public class ConnectionFactory implements AutoCloseable {
    private DataSource dataSource;

    public ConnectionFactory() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource
                .setJdbcUrl("jdbc:mysql://192.168.152.128:3306/hotelalura?useTimezone=true&serverTimezone=UTC");
        // .setJdbcUrl("jdbc:mysql://172.16.3.132:3306/hotelalura?useTimezone=true&serverTimezone=UTC");
        comboPooledDataSource.setUser("Alura");
        comboPooledDataSource.setPassword("aluraLatam1234");

        this.dataSource = comboPooledDataSource;
    }

    // Recupera una conexión a la base de datos en el servidor MySQL
    public Connection recuperarConexion() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public void close() throws Exception {
        // Cierra la fuente de datos cuando se cierra la ConnectionFactory.
        if (dataSource instanceof ComboPooledDataSource) {
            ((ComboPooledDataSource) dataSource).close();
        }
    }
}
