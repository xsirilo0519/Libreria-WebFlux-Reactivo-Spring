package co.com.sofka.bibliotecawebfluxreactivo.Utils;

public class Filtro {
    private String area;
    private String tipo;

    public Filtro(String area, String tipo) {
        this.area = area;
        this.tipo = tipo;
    }

    public String getArea() {
        return area;
    }

    public String getTipo() {
        return tipo;
    }
}
