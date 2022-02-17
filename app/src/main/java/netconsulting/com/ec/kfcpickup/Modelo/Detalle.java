package netconsulting.com.ec.kfcpickup.Modelo;


public class Detalle {
    public int plu_id;
    public int dop_cantidad;
    public String comentario;
    public java.util.List<Modificadore> modificadores;

    public int getPlu_id() {
        return plu_id;
    }

    public void setPlu_id(int plu_id) {
        this.plu_id = plu_id;
    }

    public int getDop_cantidad() {
        return dop_cantidad;
    }

    public void setDop_cantidad(int dop_cantidad) {
        this.dop_cantidad = dop_cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public java.util.List<Modificadore> getModificadores() {
        return modificadores;
    }

    public void setModificadores(java.util.List<Modificadore> modificadores) {
        this.modificadores = modificadores;
    }
}
