import java.time.LocalDateTime;
import java.util.Date;

public class Accion {
    String tipo;
    String detalles;
    LocalDateTime fechayhora;


    public Accion(String tipo){
        this.tipo = tipo;
        this.fechayhora = LocalDateTime.now();
    }

    public Accion(String tipo, String detalles){
        this.tipo = tipo;
        this.detalles = detalles;
        this.fechayhora = LocalDateTime.now();

    }

    public String getTipo() {
        return tipo;
    }
    public String getDetalles() {
        return detalles;
    }
    public LocalDateTime getFechayhora() {
        return fechayhora;
    }

}
