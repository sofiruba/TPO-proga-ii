public class Seguimiento {
    private String origen;
    private String destino;

    public Seguimiento(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;

    }
    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return origen + " → " + destino;
    }

    public void imprimirDetallado() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.printf("│ Solicitud de Seguimiento                │%n");
        System.out.printf("│ De: %-32s │%n", origen);
        System.out.printf("│ Para: %-30s │%n", destino);
        System.out.println("└─────────────────────────────────────────┘");
    }

}
