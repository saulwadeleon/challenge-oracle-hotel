package jdbc.controller;

import java.util.Map;

import jdbc.dao.ListaPaisesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.ListaPaises;

public class ListaPaisesController {
    private ListaPaisesDAO listaPaisesDAO;

    public ListaPaisesController(ConnectionFactory connectionFactory) {
        listaPaisesDAO = new ListaPaisesDAO(connectionFactory);
    }

    public ListaPaises obtenerListaPaises() {
        Map<String, Integer> paisesMap = listaPaisesDAO.listarPaises();
        return new ListaPaises(paisesMap);
    }

    public String obtenerNombrePais(int idPais) {
        return listaPaisesDAO.obtenerNombrePais(idPais);
    }

}
