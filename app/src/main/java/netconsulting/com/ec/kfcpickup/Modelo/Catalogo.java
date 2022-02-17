package netconsulting.com.ec.kfcpickup.Modelo;

public class Catalogo {
    private String id;
    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getDescripcion(); // You can add anything else like maybe getDrinkType()
    }
}
