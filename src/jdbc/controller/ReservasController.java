package jdbc.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {
	private ReservaDAO reservaDAO;
	// private ConnectionFactory connectionFactory; // Agrega una instancia de
	// ConnectionFactory.

	public ReservasController(ConnectionFactory connectionFactory) {
		// this.connectionFactory = connectionFactory;
		this.reservaDAO = new ReservaDAO(connectionFactory);
	}

	public void guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
	}

	public List<Reserva> buscar() {
		return this.reservaDAO.buscar();
	}

	public List<Reserva> buscarId(String id) {
		return this.reservaDAO.buscarId(id);
	}

	public void actualizar(Integer huesped_id, Integer habitacion_id, Date fechaE, Date fechaS, String valor,
			Integer formaPago, String numReserva, Integer id) {
		this.reservaDAO.Actualizar(huesped_id, habitacion_id, fechaE, fechaS, valor, formaPago, numReserva, id);
	}

	public void Eliminar(Integer id) {
		this.reservaDAO.Eliminar(id);
	}

	public Map<String, Integer> metodosDePago() {
		return this.reservaDAO.listarMetodosDePago();
	}
}
