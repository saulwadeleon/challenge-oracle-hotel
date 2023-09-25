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

    public Set<String> obtenerNombresPaises() {
        return paisesMap.keySet();
    }

}
