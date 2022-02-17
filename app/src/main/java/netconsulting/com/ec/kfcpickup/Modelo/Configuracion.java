package netconsulting.com.ec.kfcpickup.Modelo;

import android.content.ContentValues;


import netconsulting.com.ec.kfcpickup.Contract.ConfiguracionContract;

/*
 *  Modelo Usuario
 * */
public class Configuracion {

    private String id_codigo;
    private String ip_consulta;
    private String ip_impresion;
    private String cod_tienda;
    private String trade;

    public Configuracion(String id_codigo, String ip_consulta, String cod_tienda, String trade) {
        this.id_codigo = id_codigo;
        this.ip_consulta = ip_consulta;
        this.cod_tienda = cod_tienda;
        this.trade = trade;

    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCod_tienda() {
        return cod_tienda;
    }

    public void setCod_tienda(String cod_tienda) {
        this.cod_tienda = cod_tienda;
    }

    public Configuracion() {


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ConfiguracionContract.ConfiguracionEntry.ID_CODIGO, id_codigo);
        values.put(ConfiguracionContract.ConfiguracionEntry.IP_CONSULTA, ip_consulta);
        values.put(ConfiguracionContract.ConfiguracionEntry.IP_IMPRESION, ip_impresion);
        values.put(ConfiguracionContract.ConfiguracionEntry.CODIGO_TIENDA, cod_tienda);
        values.put(ConfiguracionContract.ConfiguracionEntry.IP_TRADE, trade);
        return values;
    }

    public String getId_codigo() {
        return id_codigo;
    }

    public String getIp_consulta() {
        return ip_consulta;
    }

    public String getIp_impresion() {
        return ip_impresion;
    }

    public void setId_codigo(String id_codigo) {
        this.id_codigo = id_codigo;
    }

    public void setIp_consulta(String ip_consulta) {
        this.ip_consulta = ip_consulta;
    }

    public void setIp_impresion(String ip_impresion) {
        this.ip_impresion = ip_impresion;
    }

}
