package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesDAO {
	private ConnectionFactory connectionFactory;

	public HuespedesDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void guardar(Huespedes huesped) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "INSERT INTO huespedes (nombre_huesped, apellido_huesped, fechaNacimiento_huesped, nacionalidad_id, email_huesped, telefono_huesped) VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, huesped.getNombre());
				pstm.setString(2, huesped.getApellido());
				pstm.setDate(3, huesped.getFechaNacimiento());
				pstm.setInt(4, huesped.getNacionalidad());
				pstm.setString(5, huesped.getEmailHuesped());
				pstm.setString(6, huesped.getTelefono());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						huesped.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> listarHuespedes() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_huesped, nombre_huesped, apellido_huesped, fechaNacimiento_huesped, nacionalidad_id, email_huesped, telefono_huesped FROM huespedes";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnHuesped(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Integer> obtenerHuespedes() {
		Map<String, Integer> huespedes = new HashMap<>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_huesped, nombre_completo FROM vista_nombreCompleto_huesped";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						int idHuesped = rst.getInt("id_huesped");
						String nombreCompleto = rst.getString("nombre_completo");
						huespedes.put(nombreCompleto, idHuesped);
					}
				}
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Huespedes> buscarId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try (Connection connection = connectionFactory.recuperarConexion()) {

			String sql = "SELECT id_huesped, nombre_huesped, apellido_huesped, fechaNacimiento_huesped, nacionalidad_id, email_huesped, telefono_huesped FROM huespedes WHERE id_huesped = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();

				transformarResultSetEnHuesped(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> buscarApellido(String apellido) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try (Connection connection = connectionFactory.recuperarConexion()) {

			String sql = "SELECT id_huesped, nombre_huesped, apellido_huesped, fechaNacimiento_huesped, nacionalidad_id, email_huesped, telefono_huesped FROM huespedes WHERE apellido_huesped LIKE ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, "%" + apellido + "%");
				pstm.execute();

				transformarResultSetEnHuesped(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Huespedes obtenerHuespedPorId(int id) {
		Huespedes huesped = null;
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT nombre_huesped, apellido_huesped, fechaNacimiento_huesped, nacionalidad_id, email_huesped, telefono_huesped FROM huespedes WHERE id_huesped = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, id); // Establece el valor del parámetro ID
				try (ResultSet rs = pstm.executeQuery()) {
					if (rs.next()) {
						// Recupera los datos del huésped de la consulta
						String nombre = rs.getString("nombre_huesped");
						String apellido = rs.getString("apellido_huesped");
						Date fechaNacimiento = rs.getDate("fechaNacimiento_huesped");
						int nacionalidad = rs.getInt("nacionalidad_id");
						String email = rs.getString("email_huesped");
						String telefono = rs.getString("telefono_huesped");

						// Crea un objeto Huespedes con los datos recuperados
						huesped = new Huespedes(id, nombre, apellido, fechaNacimiento, nacionalidad, email, telefono);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return huesped;
	}

	public String obtenerNombreHuesped(int id) {
		String nombre = null;
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT nombre_huesped FROM huespedes WHERE id_huesped = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, id); // Establece el valor del parámetro ID
				try (ResultSet rs = pstm.executeQuery()) {
					if (rs.next()) {
						// Recupera los datos del huésped de la consulta
						nombre = rs.getString("nombre_huesped");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return nombre;
	}

	public void Actualizar(String nombre, String apellido, Date fechaN, Integer nacionalidad, String email,
			String telefono, Integer id) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (PreparedStatement stm = connection
					.prepareStatement(
							"UPDATE huespedes SET nombre_huesped = ?, apellido_huesped = ?, fechaNacimiento_huesped = ?, nacionalidad_id = ?, email_huesped = ?, telefono_huesped = ? WHERE id_huesped = ?")) {
				stm.setString(1, nombre);
				stm.setString(2, apellido);
				stm.setDate(3, fechaN);
				stm.setInt(4, nacionalidad);
				stm.setString(5, email);
				stm.setString(6, telefono);
				stm.setInt(7, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void Eliminar(Integer id) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM huespedes WHERE id_huesped = ?")) {
				stm.setInt(1, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultSetEnHuesped(List<Huespedes> reservas, PreparedStatement pstm) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Huespedes huespedes = new Huespedes(rst.getInt(1), rst.getString(2), rst.getString(3),
							rst.getDate(4),
							rst.getInt(5), rst.getString(6), rst.getString(7));
					reservas.add(huespedes);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existeHuesped(Integer idHuesped) {
		// Realiza una consulta a la base de datos para verificar si existe el huesped
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (PreparedStatement pstm = connection
					.prepareStatement("SELECT COUNT(*) AS total FROM huespedes WHERE id_huesped = ?")) {
				pstm.setInt(1, idHuesped);
				ResultSet rs = pstm.executeQuery();
				rs.next();
				int total = rs.getInt("total");

				// Si el total es mayor que 0, significa que el huesped existe
				return total > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
