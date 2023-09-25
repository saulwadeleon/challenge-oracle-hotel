package jdbc.controller;

import java.util.Map;

import jdbc.dao.TipoHabitacionDAO;
import jdbc.factory.ConnectionFactory;

public class TipoHabitacionController {
  private TipoHabitacionDAO tipoHabitacionDAO;
  // private ConnectionFactory connectionFactory; // Agrega una instancia de
  // ConnectionFactory.

  public TipoHabitacionController(ConnectionFactory connectionFactory) {
    // this.connectionFactory = connectionFactory;
    this.tipoHabitacionDAO = new TipoHabitacionDAO(connectionFactory);
  }

  // Método para obtener la lista de tipos de habitaciones
  public Map<String, Integer> listarTipoHabitacion() {
    return tipoHabitacionDAO.listarTipoHabitacion();
  }

  // Método para obtener el precio de una habitación
  public double obtenerPrecioHabitacion(int idTipoHabitacion) {
    return tipoHabitacionDAO.obtenerPrecioHabitacion(idTipoHabitacion);
  }

  /*
   * // Método para agregar tipos de habitaciones
   * public void agregarTipoHabitacion(String tipoHabitacion) {
   * tipoHabitacionDAO.agregarTipoHabitacion(tipoHabitacion);
   * }
   */

}
