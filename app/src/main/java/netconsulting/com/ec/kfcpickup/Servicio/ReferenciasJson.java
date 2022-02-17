package netconsulting.com.ec.kfcpickup.Servicio;

public class ReferenciasJson {
    public String PICKUP_Consultar_Qr = null;
    public String PICKUP_ImprimirTienda = null;
    public String PICKUP_token = null;
    public String PICKUP_factura = null;
    public String PICKUP_pedidoEntregado = null;
    public String BUSCAR_ORDEN_ESTADO = null;
    public String PICKUP_dispatched = null;
    public String BUSCAR_ORDEN_CLIENTE = null;
    public String BUSCAR_ORDEN_INF_DETALLE = null;

    public ReferenciasJson(String ipconsulta, String ipImpesion, String restaurante, String trade) {
        this.PICKUP_Consultar_Qr =   ipconsulta + "/applocales/buscar-fact";
        this.PICKUP_ImprimirTienda = "http://" + ipImpesion + "/appPickup/public/api/pedidos/imprimir-orden";
        this.PICKUP_token = "https://" + trade + "/api/authentication/login";
        this.PICKUP_dispatched = "https://" + trade + "/api/order/dispatched";
        this.PICKUP_factura = "https://" + trade + "/api/order/dispatched";
        this.PICKUP_pedidoEntregado =  ipconsulta + "/webhooks/pedido-entregado";
        this.BUSCAR_ORDEN_ESTADO =   ipconsulta + "/busca-orden?rst_id=" + restaurante + "&state=";
        this.BUSCAR_ORDEN_CLIENTE =  ipconsulta + "/busca-orden-criterio?rst_id=" + restaurante + "&";
        this.BUSCAR_ORDEN_INF_DETALLE =  ipconsulta + "/info-detail?codigoApp=";

    }


    //final public static String REFERENCES_RESULTADO_1 = "https://app.freshlifefloral.com/servicio/login.php?email=";


    //"https://firebasestorage.googleapis.com/v0/b/orangezoo-972b1.appspot.com/o/kfc.json?alt=media&token=cdf14016-d658-4a16-a301-362b87213cde";
    final public static String REFERENCES_RESULTADO_2 = "https://firebasestorage.googleapis.com/v0/b/orangezoo-972b1.appspot.com/o/json1.json?alt=media&token=b6a63ecc-aac0-4042-a9b2-becf8e4180f2";

    final public static String REFERENCES_BUSQUEDA_CODAPP = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_ESTADO_1 = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_ESTADO_2 = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_ESTADO_3 = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_ESTADO_4 = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_ESTADO_5 = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_NOMBRE = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_APELLIDO = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";
    final public static String REFERENCES_BUSQUEDA_CEDULA = "http://192.168.101.30:9095/info-detail?codigoApp=0000052134-010106";

    final public static String REFERENCES_RESULTADO_3 = "https://www.kfc.com.ec/";


}
