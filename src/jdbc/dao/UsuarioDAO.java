package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.factory.ConnectionFactory;
import jdbc.security.BcryptUtil; // Asegúrate de tener una clase BcryptUtil para manejar la encriptación y verificación de contraseñas.

public class UsuarioDAO {
    private ConnectionFactory connectionFactory;

    public UsuarioDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Obtén la contraseña encriptada desde la base de datos.
    public String obtenerContraseñaEncriptada(String nombreUsuario) {
        try (Connection connection = connectionFactory.recuperarConexion()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT password_usuario FROM usuarios WHERE username = ?")) {
                preparedStatement.setString(1, nombreUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtén la contraseña encriptada desde la base de datos.
                        String contraseñaEncriptadaDesdeDB = resultSet.getString("password_usuario");
                        return contraseñaEncriptadaDesdeDB;
                    } else {
                        return null; // Si el usuario no existe, retorna null.
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la contraseña encriptada del usuario", e);
        }

    }

    public boolean verificarContraseña(String contraseñaIngresada, String contraseñaEncriptadaDesdeDB) {
        return BcryptUtil.verificarContraseña(contraseñaIngresada, contraseñaEncriptadaDesdeDB);
    }
}
