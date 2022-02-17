package netconsulting.com.ec.kfcpickup.busquedaEstados;

import java.util.List;

public class Request {
    public String servicio_pickup;
    public String tipo_pickup;
    public String fecha_hora_pickup;
    public int cdn_id;
    public int rst_id;
    public String iDTipoDocumento;
    public String cli_documento;
    public String cli_nombres;
    public String cli_telefono;
    public String cli_direccion;
    public String cli_email;
    public double cfac_subtotal;
    public double cfac_iva;
    public double cfac_total;
    public String tipo_pago;
    public String comentario_general;
    public List<Detalle> detalles;
    public List<Formaspago> formaspago;
    public List<Autorizacione> autorizaciones;
    public String codigo_app;
}
