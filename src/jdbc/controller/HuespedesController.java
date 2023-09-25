package jdbc.controller;

import java.sql.Date;
import java.util.List;
import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesController {
	private HuespedesDAO huespedDAO;
	private ConnectionFactory connectionFactory; // Agrega una instancia de ConnectionFactory.

	public HuespedesController(ConnectionFactory connectionFactory) {
		// Connection connection = new ConnectionFactory().recuperarConexion();
		this.connectionFactory = connectionFactory;
		this.huespedDAO = new HuespedesDAO(this.connectionFactory);
	}

	public void guardar(Huespedes huespedes) {
		this.huespedDAO.guardar(huespedes);
	}

	public List<Huespedes> listarHuespedes() {
		return this.huespedDAO.listarHuespedes();
	}

	public List<Huespedes> listarHuespedesId(String id) {
		return this.huespedDAO.buscarId(id);
	}

	public boolean existeHuesped(Integer idHuesped) {
		return this.huespedDAO.existeHuesped(idHuesped);
	}

	public void actualizar(String nombre, String apellido, Date fechaN, Integer nacionalidad, String email,
			String telefono, Integer id) {
		this.huespedDAO.Actualizar(nombre, apellido, fechaN, nacionalidad, email, telefono, id);
	}

	public void Eliminar(Integer id) {
		this.huespedDAO.Eliminar(id);
	}

}
