package netconsulting.com.ec.kfcpickup.Modelo;

public class Plu {
    public int plu_id;
    public String plu_descripcion;

    public String plu_cantidad;

    public int getPlu_id() {
        return plu_id;
    }

    public void setPlu_id(int plu_id) {
        this.plu_id = plu_id;
    }

    public String getPlu_descripcion() {
        return plu_descripcion;
    }

    public String getPlu_cantidad() {
        return plu_cantidad;
    }

    public void setPlu_cantidad(String plu_cantidad) {
        this.plu_cantidad = plu_cantidad;
    }

    public void setPlu_descripcion(String plu_descripcion) {
        this.plu_descripcion = plu_descripcion;
    }
}
