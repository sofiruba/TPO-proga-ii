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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    @Override
    public String toString() {
        String detallesStr = (detalles != null && !detalles.isEmpty()) ?
                " - " + detalles : "";
        return String.format("[%s] %s%s",
                fechayhora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                tipo, detallesStr);
    }

    public void imprimirDetallado() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.printf("│ Tipo: %-49s │%n", tipo);
        System.out.printf("│ Fecha: %-48s │%n",
                fechayhora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        if (detalles != null && !detalles.isEmpty()) {
            System.out.printf("│ Detalles: %-43s │%n", detalles);
        }
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

}
