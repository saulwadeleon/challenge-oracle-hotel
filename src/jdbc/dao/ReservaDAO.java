package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	private ConnectionFactory connectionFactory;

	public ReservaDAO(ConnectionFactory conexion) {
		connectionFactory = conexion;
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

	public List<Reserva> buscarPorNumReserva(String numReserva) {
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

	public Reserva obtenerReservaPorId(Integer idReserva) {
		Reserva reserva = null;
		try (Connection connection = connectionFactory.recuperarConexion()) {
			String sql = "SELECT huesped_id, habitacion_id, fecha_entrada, fecha_salida, precio_reserva, forma_pago_id, numero_reservacion FROM reservas WHERE id_reservas = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, idReserva);
				try (ResultSet rs = pstm.executeQuery()) {
					if (rs.next()) {
						int idHuesped = rs.getInt("huesped_id");
						int idTipoHabitacion = rs.getInt("habitacion_id");
						Date fechaInicio = rs.getDate("fecha_entrada");
						Date fechaTermino = rs.getDate("fecha_salida");
						String precioReserva = rs.getString("precio_reserva");
						int formaPago = rs.getInt("forma_pago_id");
						String numeroReserva = rs.getString("numero_reservacion");

						// Crea un objeto Reservas con los datos recuperados
						reserva = new Reserva(idHuesped, idTipoHabitacion, fechaInicio, fechaTermino, precioReserva,
								formaPago, numeroReserva);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return reserva;
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
							"UPDATE reservas SET huesped_id = ?, habitacion_id = ?, fecha_entrada = ?, fecha_salida = ?, precio_reserva = ?, forma_pago_id = ?, numero_reservacion = ? WHERE id_reservas = ?")) {
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

}
