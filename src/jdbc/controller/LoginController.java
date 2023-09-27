package jdbc.controller;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.dao.UsuarioDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.security.BcryptUtil;

/*
 * public class LoginController {
 * 
 * private UsuarioDAO usuarioDAO; // Debes tener una clase UsuarioDAO para
 * interactuar con la base de datos.
 * 
 * public LoginController() { // Inicializa la instancia de UsuarioDAO aquí.
 * usuarioDAO = new UsuarioDAO(); }
 * 
 * public boolean autenticarUsuario(String nombreUsuario, String contraseña) {
 * // Lógica para autenticar al usuario. String contraseñaEncriptadaDesdeDB =
 * usuarioDAO.obtenerContraseñaEncriptada(nombreUsuario);
 * 
 * if (contraseñaEncriptadaDesdeDB != null &&
 * BcryptUtil.verificarContraseña(contraseña, contraseñaEncriptadaDesdeDB)) {
 * return true; // Autenticación exitosa. } else { return false; //
 * Autenticación fallida. } } }
 */

public class LoginController {
    private UsuarioDAO usuarioDAO;
    private ConnectionFactory connectionFactory;

    public LoginController(ConnectionFactory conexion) {
        connectionFactory = conexion;
        usuarioDAO = new UsuarioDAO(connectionFactory);
    }

    public boolean autenticarUsuario(String nombreUsuario, String contraseña) {
        try (Connection connection = connectionFactory.recuperarConexion()) {
            String contraseñaEncriptadaDesdeDB = usuarioDAO.obtenerContraseñaEncriptada(nombreUsuario);

            if (contraseñaEncriptadaDesdeDB != null
                    && BcryptUtil.verificarContraseña(contraseña, contraseñaEncriptadaDesdeDB)) {
                return true; // Autenticación exitosa.
            } else {
                return false; // Autenticación fallida.
            }
        } catch (SQLException e) {
            // Manejo de excepciones si es necesario.
            return false; // Autenticación fallida en caso de excepción.
        }
        // La conexión se cerrará automáticamente al salir del bloque try.
    }
}
