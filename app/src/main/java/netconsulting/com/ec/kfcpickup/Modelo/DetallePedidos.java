package netconsulting.com.ec.kfcpickup.Modelo;

import netconsulting.com.ec.kfcpickup.fragment.DetallePedido;

public class DetallePedidos {

    public String plu_valor;
    public String modif_valor;
    public String nombre_valor;
    public String cantidad_valor;


    public DetallePedidos(String plu_valor, String modif_valor, String nombre_valor, String cantidad_valor) {
        this.plu_valor = plu_valor;
        this.modif_valor = modif_valor;
        this.nombre_valor = nombre_valor;
        this.cantidad_valor = cantidad_valor;
    }

    public DetallePedidos() {
    }

    public String getPlu_valor() {
        return plu_valor;
    }

    public void setPlu_valor(String plu_valor) {
        this.plu_valor = plu_valor;
    }

    public String getModif_valor() {
        return modif_valor;
    }

    public void setModif_valor(String modif_valor) {
        this.modif_valor = modif_valor;
    }

    public String getNombre_valor() {
        return nombre_valor;
    }

    public void setNombre_valor(String nombre_valor) {
        this.nombre_valor = nombre_valor;
    }

    public String getCantidad_valor() {
        return cantidad_valor;
    }

    public void setCantidad_valor(String cantidad_valor) {
        this.cantidad_valor = cantidad_valor;
    }
}
