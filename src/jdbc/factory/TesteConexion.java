package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexion {

	public static void main(String[] args) throws Exception {

		try (ConnectionFactory connectionFactory = new ConnectionFactory()) {
			Connection connection = connectionFactory.recuperarConexion();

			System.out.println("Probando conexion!!");

			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Conexión cerrada!!");
	}

}
