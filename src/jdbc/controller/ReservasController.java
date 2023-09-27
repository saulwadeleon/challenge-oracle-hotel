package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {
	private ReservaDAO reservaDAO;

	public ReservasController(ConnectionFactory connectionFactory) {
		reservaDAO = new ReservaDAO(connectionFactory);
	}

	public void guardar(Reserva reserva) {
		reservaDAO.guardar(reserva);
	}

	public List<Reserva> buscar() {
		return reservaDAO.buscar();
	}

	public List<Reserva> buscarPorNumReserva(String numReserva) {
		return reservaDAO.buscarPorNumReserva(numReserva);
	}

	public Reserva obtenerReservaPorId(Integer idReserva) {
		return reservaDAO.obtenerReservaPorId(idReserva);
	}

	public void actualizar(Integer huesped_id, Integer habitacion_id, Date fechaE, Date fechaS, String valor,
			Integer formaPago, String numReserva, Integer id) {
		reservaDAO.Actualizar(huesped_id, habitacion_id, fechaE, fechaS, valor, formaPago, numReserva, id);
	}

	public void Eliminar(Integer id) {
		reservaDAO.Eliminar(id);
	}

}
