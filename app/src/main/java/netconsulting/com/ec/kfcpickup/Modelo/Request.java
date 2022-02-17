package netconsulting.com.ec.kfcpickup.Modelo;

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
    public Object comentario_general;
    public java.util.List<Detalle> detalles;
    public java.util.List<Formaspago> formaspago;
    public java.util.List<Autorizacione> autorizaciones;
    public String codigo_app;

    public String getServicio_pickup() {
        return servicio_pickup;
    }

    public void setServicio_pickup(String servicio_pickup) {
        this.servicio_pickup = servicio_pickup;
    }

    public String getTipo_pickup() {
        return tipo_pickup;
    }

    public void setTipo_pickup(String tipo_pickup) {
        this.tipo_pickup = tipo_pickup;
    }

    public String getFecha_hora_pickup() {
        return fecha_hora_pickup;
    }

    public void setFecha_hora_pickup(String fecha_hora_pickup) {
        this.fecha_hora_pickup = fecha_hora_pickup;
    }

    public int getCdn_id() {
        return cdn_id;
    }

    public void setCdn_id(int cdn_id) {
        this.cdn_id = cdn_id;
    }

    public int getRst_id() {
        return rst_id;
    }

    public void setRst_id(int rst_id) {
        this.rst_id = rst_id;
    }

    public String getiDTipoDocumento() {
        return iDTipoDocumento;
    }

    public void setiDTipoDocumento(String iDTipoDocumento) {
        this.iDTipoDocumento = iDTipoDocumento;
    }

    public String getCli_documento() {
        return cli_documento;
    }

    public void setCli_documento(String cli_documento) {
        this.cli_documento = cli_documento;
    }

    public String getCli_nombres() {
        return cli_nombres;
    }

    public void setCli_nombres(String cli_nombres) {
        this.cli_nombres = cli_nombres;
    }

    public String getCli_telefono() {
        return cli_telefono;
    }

    public void setCli_telefono(String cli_telefono) {
        this.cli_telefono = cli_telefono;
    }

    public String getCli_direccion() {
        return cli_direccion;
    }

    public void setCli_direccion(String cli_direccion) {
        this.cli_direccion = cli_direccion;
    }

    public String getCli_email() {
        return cli_email;
    }

    public void setCli_email(String cli_email) {
        this.cli_email = cli_email;
    }

    public double getCfac_subtotal() {
        return cfac_subtotal;
    }

    public void setCfac_subtotal(double cfac_subtotal) {
        this.cfac_subtotal = cfac_subtotal;
    }

    public double getCfac_iva() {
        return cfac_iva;
    }

    public void setCfac_iva(double cfac_iva) {
        this.cfac_iva = cfac_iva;
    }

    public double getCfac_total() {
        return cfac_total;
    }

    public void setCfac_total(double cfac_total) {
        this.cfac_total = cfac_total;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public Object getComentario_general() {
        return comentario_general;
    }

    public void setComentario_general(Object comentario_general) {
        this.comentario_general = comentario_general;
    }

    public java.util.List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(java.util.List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public java.util.List<Formaspago> getFormaspago() {
        return formaspago;
    }

    public void setFormaspago(java.util.List<Formaspago> formaspago) {
        this.formaspago = formaspago;
    }

    public java.util.List<Autorizacione> getAutorizaciones() {
        return autorizaciones;
    }

    public void setAutorizaciones(java.util.List<Autorizacione> autorizaciones) {
        this.autorizaciones = autorizaciones;
    }

    public String getCodigo_app() {
        return codigo_app;
    }

    public void setCodigo_app(String codigo_app) {
        this.codigo_app = codigo_app;
    }
}
