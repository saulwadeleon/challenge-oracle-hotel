package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.factory.ConnectionFactory;

public class TipoHabitacionDAO {
	private ConnectionFactory connectionFactory;

	public TipoHabitacionDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	// Método para obtener todos los tipos de habitaciones desde la base de datos
	public Map<String, Integer> listarTipoHabitacion() {
		Map<String, Integer> tiposHabitacion = new HashMap<>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_tipo_habitacion, nombre_tipo_habitacion, precio_habitacion FROM tipos_habitaciones ORDER BY id_tipo_habitacion";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						int idTipoHabitacion = rst.getInt("id_tipo_habitacion");
						String nombreHabitacion = rst.getString("nombre_tipo_habitacion");
						tiposHabitacion.put(nombreHabitacion, idTipoHabitacion);
					}
				}
			}
			return tiposHabitacion;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public double obtenerPrecioHabitacion(int idTipoHabitacion) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT precio_habitacion FROM tipos_habitaciones WHERE id_tipo_habitacion = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, idTipoHabitacion);
				pstm.execute();
				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return rst.getDouble("precio_habitacion");
					} else {
						// Manejar el caso en el que no se encuentre el tipo de habitación con el ID
						// especificado.
						throw new RuntimeException("Tipo de habitación no encontrado");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String obtenerNombreTipoHabitacion(int idTipoHabitacion) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT nombre_tipo_habitacion FROM tipos_habitaciones WHERE id_tipo_habitacion = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, idTipoHabitacion);
				pstm.execute();
				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return rst.getString("nombre_tipo_habitacion");
					} else {
						// Manejar el caso en el que no se encuentre el tipo de habitación con el ID
						// especificado.
						throw new RuntimeException("Tipo de habitación no encontrado");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
