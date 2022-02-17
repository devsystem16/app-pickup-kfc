package netconsulting.com.ec.kfcpickup.Modelo;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Busquedas {

    public String idcodigo;
    public String tlfCliente;
    public String nombreCliente;
    public String horaPedido;
    public String horaPickup;
    public String idEstado;
    public String tipoPago;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public Busquedas(String idcodigo, String tlfCliente, String nombreCliente, String horaPedido, String horaPickup, String idEstado, String tipoPago) {
        this.idcodigo = idcodigo;
        this.tlfCliente = tlfCliente;
        this.nombreCliente = nombreCliente;
        this.horaPedido = horaPedido;
        this.horaPickup = horaPickup.substring(0, 16);
        this.idEstado = idEstado;
        this.tipoPago = tipoPago;
    }

    public static String formatDateTimeFromDate(String mDateFormat, String fecha) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(DATE_FORMAT).parse(fecha);
            if (date1 == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1.toString();
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getIdcodigo() {
        return idcodigo;
    }

    public void setIdcodigo(String idcodigo) {
        this.idcodigo = idcodigo;
    }

    public String getTlfCliente() {
        return tlfCliente;
    }

    public void setTlfCliente(String tlfCliente) {
        this.tlfCliente = tlfCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getHoraPickup() {
        return horaPickup;
    }

    public void setHoraPickup(String horaPickup) {
        this.horaPickup = horaPickup;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }
}
