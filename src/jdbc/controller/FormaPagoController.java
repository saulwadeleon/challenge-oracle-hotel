package jdbc.controller;

import java.util.Map;
import jdbc.dao.FormaPagoDAO;
import jdbc.factory.ConnectionFactory;

public class FormaPagoController {
    private FormaPagoDAO formaPagoDAO;

    // Constructor que recibe la conexión a la base de datos
    public FormaPagoController(ConnectionFactory connectionFactory) {
        this.formaPagoDAO = new FormaPagoDAO(connectionFactory);
    }

    public Map<String, Integer> metodosDePago() {
        return formaPagoDAO.obtenerFormasPago();
    }

    public String obtenerNombreFormaPago(int idFormaPago) {
        return formaPagoDAO.obtenerNombreFormaPago(idFormaPago);
    }
}
