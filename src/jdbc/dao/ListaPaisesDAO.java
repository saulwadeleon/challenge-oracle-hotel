package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.factory.ConnectionFactory;

public class ListaPaisesDAO {
    private ConnectionFactory connectionFactory;

    public ListaPaisesDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Map<String, Integer> listarPaises() {
        Map<String, Integer> nombrePaises = new HashMap<>();
        try (Connection connection = connectionFactory.recuperarConexion()) {
            String sql = "SELECT id_pais, nombre_pais FROM pais";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        int idPais = rst.getInt("id_pais");
                        String nombrePais = rst.getString("nombre_pais");
                        nombrePaises.put(nombrePais, idPais);
                    }
                }
            }
            return nombrePaises;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
