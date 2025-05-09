import java.util.HashSet;
import java.util.Set;

public class Cliente {
    private String nombre;
    private Integer scoring;
    private Set<String> siguiendo;
    private Set<String> conexiones;

    public Cliente(String nombre, Integer scoring, Set<String> siguiendo, Set<String> conexiones) {
        if (nombre == null || scoring == null) {
            throw new IllegalArgumentException("El nombre y el scoring no pueden ser nulos.");
        }
        validarNombre(nombre);
        validarScoring(scoring);
        this.nombre = nombre;
        this.scoring = scoring;
        this.siguiendo = validarLista(nombre, siguiendo);
        this.conexiones = validarLista(nombre, conexiones);
    }

    private void validarNombre(String nombre) {
        if (!nombre.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
        }
    }

    private void validarScoring(Integer scoring) {
        if (scoring < 1 || scoring > 10) {
            throw new IllegalArgumentException("El scoring debe estar entre 1 y 10.");
        }
    }

    private Set<String> validarLista(String nombre, Set<String> lista) {
        if (lista != null) {
            for (String item : lista) { 
                if (item == null || item.isEmpty()) {
                    throw new IllegalArgumentException("La lista contiene un nombre inválido.");
                }
            }
            if (lista.contains(nombre)) {
                throw new IllegalArgumentException("No puedes seguirte ni conectarte a ti mismo.");
            }
        }
        return lista != null ? new HashSet<>(lista) : new HashSet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getScoring() {
        return scoring;
    }

    public Set<String> getSiguiendo() {
        return siguiendo;
    }

    public Set<String> getConexiones() {
        return conexiones;
    }

    @Override
    public String toString() {
        return String.format("Cliente [nombre=%s, scoring=%d, siguiendo=%s, conexiones=%s]", nombre, scoring, siguiendo, conexiones);
    }
}
