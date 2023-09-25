package jdbc.security;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptUtil {
    
    // Método para encriptar una contraseña
    public static String encriptarContraseña(String contraseña) {
        return BCrypt.hashpw(contraseña, BCrypt.gensalt());
    }
    
    // Método para verificar una contraseña encriptada
    public static boolean verificarContraseña(String contraseña, String contraseñaEncriptada) {
        return BCrypt.checkpw(contraseña, contraseñaEncriptada);
    }
}
