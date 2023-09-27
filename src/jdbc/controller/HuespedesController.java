package jdbc.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesController {
	private HuespedesDAO huespedDAO;

	public HuespedesController(ConnectionFactory connectionFactory) {
		huespedDAO = new HuespedesDAO(connectionFactory);
	}

	public void guardar(Huespedes huespedes) {
		huespedDAO.guardar(huespedes);
	}

	public List<Huespedes> listarHuespedes() {
		return huespedDAO.listarHuespedes();
	}

	public Map<String, Integer> obtenerHuespedes() {
		return huespedDAO.obtenerHuespedes();
	}

	public List<Huespedes> listarHuespedesId(String id) {
		return huespedDAO.buscarId(id);
	}

	public List<Huespedes> buscarHuespedApellido(String apellido) {
		return huespedDAO.buscarApellido(apellido);
	}

	public Huespedes obtenerHuespedPorId(int id) {
		return huespedDAO.obtenerHuespedPorId(id);
	}

	/*
	 * public String obtenerNombreHuesped(int id) {
	 * return huespedDAO.obtenerNombreHuesped(id);
	 * }
	 */

	public String obtenerNombreHuesped(Integer id) {
		// Utiliza el mapa para obtener el nombre del huésped a partir del ID
		for (Map.Entry<String, Integer> entry : obtenerHuespedes().entrySet()) {
			if (entry.getValue().equals(id)) {
				return entry.getKey();
			}
		}
		// Si el ID no se encuentra, puedes devolver null o algún otro valor
		// predeterminado
		return null;
	}

	public boolean existeHuesped(Integer id) {
		return huespedDAO.existeHuesped(id);
	}

	public void actualizar(String nombre, String apellido, Date fechaN, Integer nacionalidad, String email,
			String telefono, Integer id) {
		huespedDAO.Actualizar(nombre, apellido, fechaN, nacionalidad, email, telefono, id);
	}

	public void Eliminar(Integer id) {
		huespedDAO.Eliminar(id);
	}

}
