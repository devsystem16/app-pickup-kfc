package netconsulting.com.ec.kfcpickup.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import netconsulting.com.ec.kfcpickup.Adapter.SearchAdapter;
import netconsulting.com.ec.kfcpickup.Adapter.detallePedido;
import netconsulting.com.ec.kfcpickup.Modelo.Busquedas;
import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;
import netconsulting.com.ec.kfcpickup.Modelo.DetallePedidos;
import netconsulting.com.ec.kfcpickup.R;
import netconsulting.com.ec.kfcpickup.SQLiteOpenHelper.ConfiguracionDbHelper;
import netconsulting.com.ec.kfcpickup.Servicio.ReferenciasJson;
import netconsulting.com.ec.kfcpickup.fragment.DetallePedido;

public class ReporteAActivity extends AppCompatActivity {

    public static final String URL = "URL";
    public String url;
    Button BtnCliente, BtnEstado;
    RelativeLayout cabeMidlee, cabeBottom, cabeMidlee1, cabeBottom_1;
    ImageView imgPF, imgOF;
    TextView numFac1, numSubTotal1, numIva1, numTotal1, noSeEncontroF, nombreE2, nombreE1, codigoRest_P, tipoServicio_P, tipoPickUp, nombreCPE, direccionCPE, emailCPE, cellCPE, pagoVE, binVE, codigo_p_respuesta, mensaje_p_respuesta, tarjetah_p_respuesta, tipotarjeta_p_respuesta, pasarela_p_respuesta, fechahora_p_respuesta, titleItems;
    TableLayout tabla5;
    TextView numeroFacturaId, FechaFacturaId;
    TextView fecha_impresion_p, estacion_factura_p, estado_factura_p, impresora_factura_p;
    TextView fecha_orden_p, estacion_orden_p, estado_orden_p, impresora_orden_p;
    public static List<DetallePedidos> detallepedidos;
    RecyclerView ReciclyerDetallePedidoC;
    detallePedido adapter;
    ReferenciasJson referenciasJson;
    ArrayList<Configuracion> lista;
    ConfiguracionDbHelper configuracionDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_a);

        BtnCliente = findViewById(R.id.btnCliente);
        BtnEstado = findViewById(R.id.btnEstado);
        cabeMidlee = findViewById(R.id.cabeMidlee);
        cabeBottom = findViewById(R.id.cabeBottom);
        cabeMidlee1 = findViewById(R.id.cabeMidlee1);
        cabeBottom_1 = findViewById(R.id.cabeBottom_1);
        noSeEncontroF = findViewById(R.id.noSeEncontroFac);
        numTotal1 = findViewById(R.id.numTotal1);
        numIva1 = findViewById(R.id.numIva1);
        numSubTotal1 = findViewById(R.id.numSubTotal1);
        numFac1 = findViewById(R.id.numFac1);
        imgPF = findViewById(R.id.imgPF);
        imgOF = findViewById(R.id.imgOF);

        //   codigoApp_P = findViewById(R.id.codigoApp_P);
        codigoRest_P = findViewById(R.id.codigoRest_P);
        tipoServicio_P = findViewById(R.id.tipoServicio_P);
        tipoPickUp = findViewById(R.id.tipoPickUp);
        nombreCPE = findViewById(R.id.nombreCPE);
        direccionCPE = findViewById(R.id.direccionCPE);
        emailCPE = findViewById(R.id.emailCPE);
        cellCPE = findViewById(R.id.cellCPE);
        pagoVE = findViewById(R.id.pagoVE);
        binVE = findViewById(R.id.binVE);
        codigo_p_respuesta = findViewById(R.id.codigo_p_respuesta);
        mensaje_p_respuesta = findViewById(R.id.mensaje_p_respuesta);
        tarjetah_p_respuesta = findViewById(R.id.tarjetah_p_respuesta);
        tipotarjeta_p_respuesta = findViewById(R.id.tipotarjeta_p_respuesta);
        pasarela_p_respuesta = findViewById(R.id.pasarela_p_respuesta);
        fechahora_p_respuesta = findViewById(R.id.fechahora_p_respuesta);
        titleItems = findViewById(R.id.titleItems);
        tabla5 = findViewById(R.id.tabla5);
        numeroFacturaId = findViewById(R.id.nombreE1_11);
        nombreE1 = findViewById(R.id.nombreE1);
        FechaFacturaId = findViewById(R.id.nombreE2_22);
        nombreE2 = findViewById(R.id.nombreE2);

        fecha_impresion_p = findViewById(R.id.fecha_impresion_p);
        estacion_factura_p = findViewById(R.id.estacion_factura_p);
        estado_factura_p = findViewById(R.id.estado_factura_p);
        impresora_factura_p = findViewById(R.id.impresora_factura_p);

        fecha_orden_p = findViewById(R.id.fecha_orden_p);
        estacion_orden_p = findViewById(R.id.estacion_orden_p);
        estado_orden_p = findViewById(R.id.estado_orden_p);
        impresora_orden_p = findViewById(R.id.impresora_orden_p);

        detallepedidos = new ArrayList<>();
        ReciclyerDetallePedidoC = findViewById(R.id.idDetallePedidoC);

        cabeMidlee.setVisibility(View.VISIBLE);
        cabeBottom.setVisibility(View.VISIBLE);
        tabla5.setVisibility(View.VISIBLE);
        cabeMidlee1.setVisibility(View.GONE);
        cabeBottom_1.setVisibility(View.GONE);
        noSeEncontroF.setVisibility(View.INVISIBLE);
        numFac1.setVisibility(View.INVISIBLE);
        numIva1.setVisibility(View.INVISIBLE);
        numSubTotal1.setVisibility(View.INVISIBLE);
        numTotal1.setVisibility(View.INVISIBLE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(URL)) {
            url = bundle.getString(URL);


            //  loadDetallePedido(url);
            configuracionDbHelper = new ConfiguracionDbHelper(this);
            lista = configuracionDbHelper.consultarTodosValores();
            if (lista != null) {
                if (lista.size() > 0) {
                    referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion(), lista.get(0).getCod_tienda(), lista.get(0).getTrade());
                }
            }

            loadDetailCliente(url);
            BtnEstado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cabeMidlee.setVisibility(View.GONE);
                    cabeBottom.setVisibility(View.GONE);
                    cabeMidlee1.setVisibility(View.VISIBLE);
                    cabeBottom_1.setVisibility(View.VISIBLE);
                    tabla5.setVisibility(View.GONE);

                    loadDetailFactura(url);
                }
            });

            BtnCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cabeMidlee.setVisibility(View.VISIBLE);
                    cabeBottom.setVisibility(View.VISIBLE);
                    cabeMidlee1.setVisibility(View.GONE);
                    cabeBottom_1.setVisibility(View.GONE);
                    tabla5.setVisibility(View.VISIBLE);

                    loadDetailCliente(url);
                    //  loadDetallePedido(url);
                }
            });

        }

    }

    String roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d)).toString();
    }

    public void loadDetailFactura(String codApp) {

        //String fileJSON = ReferenciasJson.REFERENCES_RESULTADO_2  + codApp;
        String fileJSON = referenciasJson.BUSCAR_ORDEN_INF_DETALLE + codApp;
        StringRequest stringRequest_a = new StringRequest(Request.Method.GET, fileJSON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String jsonText = "{\"orden\":[" + response.toString() + "]}";
                    JSONObject jsonObject = new JSONObject(jsonText);
                    JSONArray array = jsonObject.getJSONArray("orden");


                    if (array.length() <= 0) {
                        Toast.makeText(getApplicationContext(), R.string.erroNoData, Toast.LENGTH_LONG).show();
                    } else {

                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject items1 = array.getJSONObject(i);

                            JSONArray datosKioskoCabeceraPedido = items1.getJSONArray("datosKioskoCabeceraPedido");

                            for (int j = 0; j < datosKioskoCabeceraPedido.length(); j++) {
                                JSONObject items2 = datosKioskoCabeceraPedido.getJSONObject(j);
                                String numero_factura_id = items2.getString("cfac_id");
                                numeroFacturaId.setText(numero_factura_id);
                            }

                            JSONObject conciliacion = items1.getJSONObject("conciliacion");
                            JSONObject request = conciliacion.getJSONObject("request");
                            String fecha_hora_pickup = request.getString("fecha_hora_pickup");
                            FechaFacturaId.setText(fecha_hora_pickup.substring(0, 16));

                            JSONArray datosCabeceraFactura = items1.getJSONArray("datosCabeceraFactura");
                            if (datosCabeceraFactura.length() == 0) {
                                noSeEncontroF.setVisibility(View.VISIBLE);
                                noSeEncontroF.setText("NO SE ENCONTRO CABECERA DE FACTURA");

                            }

                            for (int h = 0; h < datosCabeceraFactura.length(); h++) {

                                numFac1.setVisibility(View.VISIBLE);
                                numIva1.setVisibility(View.VISIBLE);
                                numSubTotal1.setVisibility(View.VISIBLE);
                                numTotal1.setVisibility(View.VISIBLE);
                                JSONObject itemsF = datosCabeceraFactura.getJSONObject(h);
                                String numfac = itemsF.getString("cfac_numero_factura");
                                String sub_total = itemsF.getString("cfac_subtotal");
                                String iva = itemsF.getString("cfac_iva");
                                String total = itemsF.getString("cfac_total");
                                DecimalFormat df = new DecimalFormat("0.00");
                                numFac1.setText(numfac);
                                numSubTotal1.setText((roundTwoDecimals(Double.parseDouble(sub_total))));
                                numIva1.setText((roundTwoDecimals(Double.parseDouble(iva))));
                                numTotal1.setText((roundTwoDecimals(Double.parseDouble(total))));
                            }

                            JSONArray datosImpresionFactura = items1.getJSONArray("datosImpresionFactura");

                            for (int h = 0; h < datosImpresionFactura.length(); h++) {
                                JSONObject items4 = datosImpresionFactura.getJSONObject(h);

                                String imp_fecha = items4.getString("imp_fecha");
                                String imp_ip_estacion = items4.getString("imp_ip_estacion");
                                String tca_codigo1 = items4.getString("tca_codigo");
                                String imp_impresora = items4.getString("imp_impresora");
                                String imp_url = items4.getString("imp_url");

                                fecha_impresion_p.setText(imp_fecha.substring(0, 16));
                                estacion_factura_p.setText(imp_ip_estacion);
                                estado_factura_p.setText(tca_codigo1);
                                impresora_factura_p.setText(imp_impresora);

                                imgPF.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String urlFact = "http://" + imp_url;
                                        Intent intent = new Intent(getApplicationContext(), ViewWebActivity.class);
                                        intent.putExtra(ViewWebActivity.URL, urlFact);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });


                            }

                            JSONArray datosImpresionOrdenPedido = items1.getJSONArray("datosImpresionOrdenPedido");

                            for (int l = 0; l < datosImpresionOrdenPedido.length(); l++) {
                                // JSONObject datosKioskoCabeceraPedido = items2.getJSONObject(j);
                                JSONObject items5 = datosImpresionOrdenPedido.getJSONObject(l);

                                String imp_fecha = items5.getString("imp_fecha");
                                String imp_ip_estacion = items5.getString("imp_ip_estacion");
                                String tca_codigo = items5.getString("tca_codigo");
                                String imp_impresora = items5.getString("imp_impresora");
                                String imp_url = items5.getString("imp_url");

                                fecha_orden_p.setText(imp_fecha.substring(0, 16));
                                estacion_orden_p.setText(imp_ip_estacion);
                                estado_orden_p.setText(tca_codigo);
                                impresora_orden_p.setText(imp_impresora);


                                imgOF.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String urlFact = "http://" + imp_url;
                                        Intent intent = new Intent(getApplicationContext(), ViewWebActivity.class);
                                        intent.putExtra(ViewWebActivity.URL, urlFact);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });


                            }
                        }


                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest_a);

    }

    public void loadDetailCliente(String codApp) {

        detallepedidos.clear();

        ReciclyerDetallePedidoC.setVisibility(View.VISIBLE);
        ReciclyerDetallePedidoC.setHasFixedSize(false);
        ReciclyerDetallePedidoC.setLayoutManager(new LinearLayoutManager(this));


        String fileJSON = referenciasJson.BUSCAR_ORDEN_INF_DETALLE + codApp;
        StringRequest stringRequest_a = new StringRequest(Request.Method.GET, fileJSON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String jsonText = "{\"orden\":[" + response.toString() + "]}";
                    JSONObject jsonObject = new JSONObject(jsonText);
                    JSONArray array = jsonObject.getJSONArray("orden");


                    if (array.length() <= 0) {
                        Toast.makeText(getApplicationContext(), R.string.erroNoData, Toast.LENGTH_LONG).show();
                    } else {

                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject items1 = array.getJSONObject(i);
                            //   String codApp = items1.getString("codigo_app");

                            //traversing through all the object

                            JSONArray datosKioskoCabeceraPedido = items1.getJSONArray("datosKioskoCabeceraPedido");

                            for (int j = 0; j < datosKioskoCabeceraPedido.length(); j++) {
                                JSONObject items2 = datosKioskoCabeceraPedido.getJSONObject(j);

                                String cli_nombres = items2.getString("cli_nombres");
                                String tipo_servicio = items2.getString("tipo_servicio");
                                String cli_direccion = items2.getString("cli_direccion");
                                String cli_email = items2.getString("cli_email");
                                String cli_telefono = items2.getString("cli_telefono");
                                String numero_factura_id = items2.getString("cfac_id");

                                //codigoApp_P.setText(codApp);
                                tipoServicio_P.setText(tipo_servicio);
                                nombreCPE.setText(cli_nombres);
                                direccionCPE.setText(cli_direccion);
                                emailCPE.setText(cli_email);
                                cellCPE.setText(cli_telefono);
                            }


                            JSONObject conciliacion = items1.getJSONObject("conciliacion");
                            String idPedido = conciliacion.getString("_id");
                            String stateMessage = conciliacion.getString("stateMessage");
                            String stateTechnicalMessage = conciliacion.getString("stateTechnicalMessage");
                            titleItems.setText("Pedido : ".concat(codApp));
                            JSONObject request = conciliacion.getJSONObject("request");
                            String servicio_pickup = request.getString("servicio_pickup");
                            String rst_id = request.getString("rst_id");
                            String fecha_hora_pickup = request.getString("fecha_hora_pickup").substring(0, 16);

                            codigoRest_P.setText(rst_id);
                            tipoPickUp.setText(servicio_pickup);
                            nombreE1.setText(stateMessage);
                            nombreE2.setText(stateTechnicalMessage);

                            JSONArray formaspago = request.getJSONArray("formaspago");

                            for (int h = 0; h < formaspago.length(); h++) {
                                // JSONObject datosKioskoCabeceraPedido = items2.getJSONObject(j);
                                JSONObject items4 = formaspago.getJSONObject(h);

                                String bin = items4.getString("bin");
                                String fpf_total_pagar = items4.getString("fpf_total_pagar");

                                pagoVE.setText(fpf_total_pagar);
                                binVE.setText(bin);

                            }

                            JSONArray autorizaciones = request.getJSONArray("autorizaciones");

                            for (int l = 0; l < autorizaciones.length(); l++) {
                                // JSONObject datosKioskoCabeceraPedido = items2.getJSONObject(j);
                                JSONObject items5 = autorizaciones.getJSONObject(l);

                                String autorizacionCodigo = items5.getString("Autorizacion");
                                String MensajeRespuestaAut = items5.getString("MensajeRespuestaAut");
                                String TarjetaHabiente = items5.getString("TarjetaHabiente");
                                String PasarelaPago = items5.getString("PasarelaPago");
                                String TipoTarjeta = items5.getString("TipoTarjeta");
                                String FechaHoraTarjeta = items5.getString("FechaTransaccion").concat(" / ").concat(items5.getString("HoraTransaccion"));


                                codigo_p_respuesta.setText(autorizacionCodigo);
                                mensaje_p_respuesta.setText(MensajeRespuestaAut);
                                tarjetah_p_respuesta.setText(TarjetaHabiente);
                                tipotarjeta_p_respuesta.setText(TipoTarjeta);
                                pasarela_p_respuesta.setText(PasarelaPago);
                                fechahora_p_respuesta.setText(FechaHoraTarjeta);

                            }

                            JSONArray datosDetalleFactura = items1.getJSONArray("datosDetalleFactura");

                            for (int u = 0; u < datosDetalleFactura.length(); u++) {
                                JSONObject items6 = datosDetalleFactura.getJSONObject(u);

                                String plu_valor = items6.getString("plu_id");
                                String modif_valor = items6.getString("plu_id");
                                String nombre_valor = items6.getString("plu_descripcion");
                                String cantidad_valor = items6.getString("dop_cantidad");

                                detallepedidos.add(new DetallePedidos(
                                        plu_valor,
                                        modif_valor,
                                        nombre_valor,
                                        cantidad_valor
                                ));


                            }
                        }

                        // public varitiesAdapter(Context mCtx, List<Varieties> varieties, String idOrder, String idBuyer, String titleOrder, String codOrder)
                        adapter = new detallePedido(getApplicationContext(), detallepedidos);
                        ReciclyerDetallePedidoC.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest_a);


    }

    public void loadDetallePedido(String codApp) {

        ReciclyerDetallePedidoC.setVisibility(View.VISIBLE);
        ReciclyerDetallePedidoC.setHasFixedSize(false);
        ReciclyerDetallePedidoC.setLayoutManager(new LinearLayoutManager(this));

        //String fileJSON = ReferenciasJson.REFERENCES_RESULTADO_2 + codApp;
        String fileJSON = ReferenciasJson.REFERENCES_RESULTADO_2; //  + codApp;
        StringRequest stringRequest_a = new StringRequest(Request.Method.GET, fileJSON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("orden");


                    if (array.length() <= 0) {
                        ReciclyerDetallePedidoC.setVisibility(View.GONE);
                    } else {

                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject items1 = array.getJSONObject(i);
                            //traversing through all the object

                            JSONArray datosDetalleFactura = items1.getJSONArray("datosDetalleFactura");

                            for (int j = 0; j < datosDetalleFactura.length(); j++) {
                                JSONObject items2 = datosDetalleFactura.getJSONObject(j);

                                String plu_valor = items2.getString("plu_id");
                                String modif_valor = items2.getString("plu_id");
                                String nombre_valor = items2.getString("plu_descripcion");
                                String cantidad_valor = items2.getString("dop_cantidad");

                                detallepedidos.add(new DetallePedidos(
                                        plu_valor,
                                        modif_valor,
                                        nombre_valor,
                                        cantidad_valor
                                ));
                            }
                            // public varitiesAdapter(Context mCtx, List<Varieties> varieties, String idOrder, String idBuyer, String titleOrder, String codOrder)
                            adapter = new detallePedido(getApplicationContext(), detallepedidos);
                            ReciclyerDetallePedidoC.setAdapter(adapter);

                        }


                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest_a);

    }
}