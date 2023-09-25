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
import jdbc.modelo.Reserva;

public class ReservaDAO {
	private ConnectionFactory connectionFactory;

	public ReservaDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void guardar(Reserva reserva) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "INSERT INTO reservas (huesped_id, habitacion_id, fecha_entrada, fecha_salida, precio_reserva, forma_pago_id, numero_reservacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setInt(1, reserva.getHuesped_id());
				pstm.setInt(2, reserva.getHabitacion_id());
				pstm.setDate(3, reserva.getFechaE());
				pstm.setDate(4, reserva.getFechaE());
				pstm.setString(5, reserva.getPrecioReserva());
				pstm.setInt(6, reserva.getFormaPago());
				pstm.setString(7, reserva.getNumeroReserva());

				pstm.executeUpdate();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_reservas, huesped_id, habitacion_id, fecha_entrada, fecha_salida, precio_reserva, forma_pago_id, numero_reservacion FROM reservas";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnReserva(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> buscarId(String numReserva) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_reservas, huesped_id, habitacion_id, fecha_entrada, fecha_salida, precio_reserva, forma_pago_id, numero_reservacion FROM reservas WHERE numero_reservacion = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, numReserva);
				pstm.execute();

				transformarResultSetEnReserva(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void Eliminar(Integer id) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM reservas WHERE id_reservas = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void Actualizar(Integer huesped_id, Integer habitacion_id, Date fechaE, Date fechaS, String valor,
			Integer formaPago, String reserva, Integer id) {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (PreparedStatement stm = connection
					.prepareStatement(
							"UPDATE reservas SET huesped_id = ?, habitacion_id = ?, fecha_entrada = ?, fecha_salida = ?, precio_reserva = ?, forma_pago_id = ?, numero_reservacion = ? WHERE id = ?")) {
				stm.setInt(1, huesped_id);
				stm.setInt(2, habitacion_id);
				stm.setDate(3, fechaE);
				stm.setDate(4, fechaS);
				stm.setString(5, valor);
				stm.setInt(6, formaPago);
				stm.setString(7, reserva);
				stm.setInt(8, id);
				stm.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) throws SQLException {
		try (Connection connection = connectionFactory.recuperarConexion()) {
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Reserva produto = new Reserva(rst.getInt(1), rst.getInt(2), rst.getInt(3), rst.getDate(4),
							rst.getDate(5), rst.getString(6), rst.getInt(7), rst.getString(8));

					reservas.add(produto);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Método para obtener todas los metodos de pago desde la base de datos
	public Map<String, Integer> listarMetodosDePago() {
		Map<String, Integer> formasPago = new HashMap<>();
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT id_formas_pago, dsc_forma_pago FROM formas_pago";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						int idFormaPago = rst.getInt("id_formas_pago");
						String formaPago = rst.getString("dsc_forma_pago");
						formasPago.put(formaPago, idFormaPago);
					}
				}
			}
			return formasPago;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
