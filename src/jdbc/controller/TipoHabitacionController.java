package jdbc.controller;

import java.util.Map;

import jdbc.dao.TipoHabitacionDAO;
import jdbc.factory.ConnectionFactory;

public class TipoHabitacionController {
  private TipoHabitacionDAO tipoHabitacionDAO;

  public TipoHabitacionController(ConnectionFactory connectionFactory) {
    tipoHabitacionDAO = new TipoHabitacionDAO(connectionFactory);
  }

  // Método para obtener la lista de tipos de habitaciones
  public Map<String, Integer> listarTipoHabitacion() {
    return tipoHabitacionDAO.listarTipoHabitacion();
  }

  // Método para obtener el precio de una habitación
  public double obtenerPrecioHabitacion(int idTipoHabitacion) {
    return tipoHabitacionDAO.obtenerPrecioHabitacion(idTipoHabitacion);
  }

  public String obtenerNombreTipoHabitacion(int idTipoHabitacion) {
    return tipoHabitacionDAO.obtenerNombreTipoHabitacion(idTipoHabitacion);
  }

}
