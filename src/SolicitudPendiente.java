
public class SolicitudPendiente {
    private String origenId;
    private String destinoId;
    private boolean esAmistad;

    public SolicitudPendiente(String origenId, String destinoId, boolean esAmistad) {
        if (origenId == null || origenId.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen no puede ser nulo o vacío");
        }
        if (destinoId == null || destinoId.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede ser nulo o vacío");
        }
        this.origenId = origenId.trim();
        this.destinoId = destinoId.trim();
        this.esAmistad = esAmistad;
    }


    public String getOrigen() {
        return origenId;
    }

    public String getDestino() {
        return destinoId;
    }

    public boolean esAmistad() {
        return esAmistad;
    }

    @Override
    public String toString() {
        return "Solicitud de " + origenId + " para " + (esAmistad ? "conectar" : "seguir") + " a " + destinoId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudPendiente)) return false;
        SolicitudPendiente that = (SolicitudPendiente) o;
        return origenId.equals(that.origenId) && destinoId.equals(that.destinoId);
    }

    @Override
    public int hashCode() {
        return origenId.hashCode() * 31 + destinoId.hashCode();
    }
}
