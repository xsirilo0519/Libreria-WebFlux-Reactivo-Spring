package co.com.sofka.bibliotecawebfluxreactivo.Utils;

public class Mensaje {
    private boolean estado;
    private String mensaje;

    public Mensaje(boolean estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public Mensaje() {

    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getMensaje() {
        return mensaje;
    }
}
