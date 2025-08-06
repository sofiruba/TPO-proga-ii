import java.util.HashSet;
import java.util.Set;

public class Cliente implements Comparable<Cliente> {
    private String nombre;
    private int scoring;
    private int idgrafo;

    private Set<String> siguiendo;
    private Set<String> conexiones;
    private Set<String> amistades;

    private boolean privado; // atributo privacidad

    public Cliente() {
        // Constructor vacío para Gson
        this.siguiendo = new HashSet<>();
        this.conexiones = new HashSet<>();
        this.amistades = new HashSet<>();
        this.privado = false; // por defecto público
    }

    public Cliente(String nombre, int scoring, int idgrafo) {
        this.nombre = nombre;
        this.scoring = scoring;
        this.idgrafo = idgrafo;
        this.siguiendo = new HashSet<>();
        this.conexiones = new HashSet<>();
        this.amistades = new HashSet<>();
        this.privado = false;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getScoring() {
        return scoring;
    }

    public void setScoring(int scoring) {
        this.scoring = scoring;
    }

    public int getIdgrafo() {
        return idgrafo;
    }

    public void setIdgrafo(int idgrafo) {
        this.idgrafo = idgrafo;
    }

    public Set<String> getSiguiendo() {
        if (siguiendo == null) {
            siguiendo = new HashSet<>();
        }
        return siguiendo;
    }

    public void setSiguiendo(Set<String> siguiendo) {
        this.siguiendo = siguiendo;
    }

    public Set<String> getConexiones() {
        if (conexiones == null) {
            conexiones = new HashSet<>();
        }
        return conexiones;
    }

    public void setConexiones(Set<String> conexiones) {
        this.conexiones = conexiones;
    }

    public Set<String> getAmistades() {
        if (amistades == null) {
            amistades = new HashSet<>();
        }
        return amistades;
    }
    public void agregarConexion(Cliente otro) {
        if (!conexiones.contains(otro)) {
            conexiones.add(otro.getNombre());
        }
    }

    public void setAmistades(Set<String> amistades) {
        this.amistades = amistades;
    }

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivacidad(boolean privado) {
        this.privado = privado;
    }

    // Método para corregir null tras deserialización
    private Object readResolve() {
        if (siguiendo == null) {
            siguiendo = new HashSet<>();
        }
        if (conexiones == null) {
            conexiones = new HashSet<>();
        }
        if (amistades == null) {
            amistades = new HashSet<>();
        }
        // Si la privacidad no se leyó, por defecto false
        return this;
    }
    public int getIdGrafo(){
        return idgrafo;
    }
    public void setIdGrafo(int idgrafo) {
        this.idgrafo = idgrafo;
    }
    @Override
    public int compareTo(Cliente o) {
        if (this.scoring < o.scoring) {
            return -1;
        } else if (this.scoring > o.scoring) {
            return 1;
        } else {
            return this.nombre.compareTo(o.nombre);
        }
    }
    @Override
    public String toString() {
        return nombre + " (scoring: " + scoring + ")";
    }
}
