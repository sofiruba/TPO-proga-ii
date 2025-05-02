import java.util.*;

public class ColaDeSeguimientos {
    private Queue<Seguimiento> cola;
    private List<String> registroDeAcciones;

    public ColaDeSeguimientos() {
        this.cola = new LinkedList<>();
        this.registroDeAcciones = new ArrayList<>();
    }

    public void agregarSolicitud(Seguimiento solicitud) {
        cola.offer(solicitud);
        System.out.println("Solicitud agregada: " + solicitud);
    }

    public void procesarProxima() {
        if (cola.isEmpty()) {
            System.out.println("No hay solicitudes para procesar.");
            return;
        }
        Seguimiento solicitud = cola.poll();
        String origen = solicitud.getOrigen();
        String destino = solicitud.getDestino();

        String log = origen + " ahora sigue a " + destino;
        registroDeAcciones.add(log);
    }
}
