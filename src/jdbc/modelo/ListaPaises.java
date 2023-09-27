package jdbc.modelo;

import java.util.Map;
import java.util.Set;

public class ListaPaises {

    private Map<String, Integer> paisesMap;

    public ListaPaises(Map<String, Integer> paisesMap) {
        this.paisesMap = paisesMap;
    }

    public Integer obtenerIdPais(String nombrePais) {
        return paisesMap.get(nombrePais);
    }

    public String obtenerNombrePais(Integer idPais) {
        // Aquí obtenemos el nombre del país a partir del ID
        for (Map.Entry<String, Integer> entry : paisesMap.entrySet()) {
            if (entry.getValue().equals(idPais)) {
                return entry.getKey();
            }
        }
        // Si no se encuentra el ID, puedes devolver null o un valor predeterminado
        return null;
    }

    public Set<String> obtenerNombresPaises() {
        return paisesMap.keySet();
    }

}
