package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import jdbc.factory.ConnectionFactory;

public class FormaPagoDAO {
    private ConnectionFactory connectionFactory;

    // Constructor que recibe la conexión a la base de datos
    public FormaPagoDAO(ConnectionFactory conexion) {
        connectionFactory = conexion;
    }

    // Método para obtener todas las formas de pago desde la base de datos
    public Map<String, Integer> obtenerFormasPago() {
        Map<String, Integer> formasPago = new HashMap<>();
        try (Connection connection = connectionFactory.recuperarConexion()) {
            String sql = "SELECT id_formas_pago, dsc_forma_pago FROM formas_pago ORDER BY dsc_forma_pago ASC";
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

    public String obtenerNombreFormaPago(int idFormaPago) {
        try (Connection connection = connectionFactory.recuperarConexion()) {
            String sql = "SELECT dsc_forma_pago FROM formas_pago WHERE id_formas_pago = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, idFormaPago);
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        return rst.getString("dsc_forma_pago");
                    } else {
                        // Manejar el caso en el que no se encuentre la forma de pago con el ID
                        // especificado.
                        throw new RuntimeException("Forma de pago no encontrada");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
