package netconsulting.com.ec.kfcpickup.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;
import netconsulting.com.ec.kfcpickup.Modelo.Root;
import netconsulting.com.ec.kfcpickup.Modelo.Token;
import netconsulting.com.ec.kfcpickup.R;
import netconsulting.com.ec.kfcpickup.SQLiteOpenHelper.ConfiguracionDbHelper;
import netconsulting.com.ec.kfcpickup.Servicio.ReferenciasJson;
import pl.droidsonroids.gif.GifImageView;

public class LeerQr extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    ConfiguracionDbHelper configuracionDbHelper;
    ReferenciasJson referenciasJson;
    GifImageView gif;
    ArrayList<Configuracion> lista;
    MaterialButton searchEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_qr);
        gif = findViewById(R.id.imagenFinalizar);
        searchEC = findViewById(R.id.search_EC);

        this.gif.setImageResource(R.drawable.gif_qr);
        configuracionDbHelper = new ConfiguracionDbHelper(this);
        lista = configuracionDbHelper.consultarTodosValores();
        if (lista != null) {
            if (lista.size() > 0) {
                referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion(), lista.get(0).getCod_tienda(), lista.get(0).getTrade());
            }
        }

        searchEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LeerQr.this, SearchActivity.class);
                startActivity(i);

            }
        });

    }

    public void onClick(View v) {
        Intent i = new Intent(LeerQr.this, Home.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta", Toast.LENGTH_SHORT).show();
                String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                if (resultado != null) {
                    Toast.makeText(getApplicationContext(), "No se pudo escanear el código QR", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if (requestCode == REQUEST_CODE_QR_SCAN) {
                if (data != null) {
                    String lectura = data.getStringExtra("data");
                    //obtenerToken();

                    consultarQRHttp(
                            lectura);
                    Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();


                }
            }
        }
    }

    public void sensorActivar() {

        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(1500, 10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1500);
        }

    }


    public void consultarEjemplo() {

        String json = "{\"orden\":{\"_id\":\"5f74f05d433100006d001042\",\"state\":\"recibido-restaurante\",\"stateMessage\":\"Procesando en el restaurante\",\"stateTechnicalMessage\":\"Procesando en el restaurante\",\"codigo_app\":\"0000000826-010111\",\"request\":{\"servicio_pickup\":\"PickUp-LLevar\",\"tipo_pickup\":\"ASAP\",\"fecha_hora_pickup\":\"2020-09-30 16:09:48\",\"cdn_id\":10,\"rst_id\":162,\"IDTipoDocumento\":\"030B9503-85CF-E511-80C6-000D3A3261F3\",\"cli_documento\":\"1713254819\",\"cli_nombres\":\"Carlos Suntaxi\",\"cli_telefono\":\"0984664654\",\"cli_direccion\":\"Ca\\u00f1aris 2 y panzaleo\",\"cli_email\":\"carlossun_26@hotmail.com\",\"cfac_subtotal\":22.7589,\"cfac_iva\":2.7311,\"cfac_total\":25.49,\"tipo_pago\":\"tarjeta\",\"comentario_general\":null,\"detalles\":[{\"plu_id\":30511,\"dop_cantidad\":1,\"comentario\":\"Original\",\"modificadores\":[{\"plu_id\":33617,\"dop_cantidad\":1},{\"plu_id\":296,\"dop_cantidad\":1}]}],\"formaspago\":[{\"bin\":445446,\"fpf_total_pagar\":25.49}],\"autorizaciones\":[{\"Autorizacion\":\"774605\",\"MensajeRespuestaAut\":\"APPROVED\",\"NumeroTarjeta\":\"4049\",\"TarjetaHabiente\":\"Carlos Suntaxi\",\"FechaTransaccion\":\"20200930\",\"HoraTransaccion\":\"125351\",\"TipoTarjeta\":\"visa\",\"IDFormaPago\":\"DA0A9503-85CF-E511-80C6-000D3A3261F3\",\"PasarelaPago\":\"PAYPHONE\"}],\"codigo_app\":\"0000000826-010111\"},\"updated_at\":\"2020-09-30T20:53:50.801000Z\",\"created_at\":\"2020-09-30T20:53:49.645000Z\",\"responseData\":{\"estado\":\"OK\",\"codigo\":\"200\",\"msg\":\"Pedido Pick Up Recibido\",\"data\":{\"idConciliation\":\"5f74f05d433100006d001042\",\"cabeceraCreada\":5644,\"fecha_hora_pickup\":\"2020-09-30 16:09:48\",\"id_transaccion\":\"K026F000912782\"},\"error\":[]},\"clase_css_estado\":\"warning\"},\"plus\":[{\"plu_id\":296,\"plu_descripcion\":\"ENSALADA DE COL GR.\"},{\"plu_id\":30511,\"plu_descripcion\":\"MEGA FESTIN 12 PRE 2 A\\/M 1 PAPA FGR ENS COL GR\"},{\"plu_id\":33617,\"plu_descripcion\":\"Mitad Original\\/Crispy\"}]}";
        Intent intent = new Intent(this, Informacion.class);
        intent.putExtra("json", json);
        intent.putExtra("qr", "K026F000912782");
        startActivity(intent);
        finish();
    }

    public void consultarQR(final String codigoQR) {
        Toast.makeText(getBaseContext(), R.string.waitQR, Toast.LENGTH_LONG).show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("cfac", codigoQR);
        params.put("restaurante", lista.get(0).getCod_tienda());

        JSONObject jsonObj = new JSONObject(params);

        String url = referenciasJson.PICKUP_Consultar_Qr + "/" + codigoQR + "/" + lista.get(0).getCod_tienda();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.length() > 0) {

                                Gson gson = new Gson();
                                Root root;
                                root = gson.fromJson(response.toString(), Root.class);
                                if ("preparando".equals(root.getOrden().getState())) {
                                    cambiarGif(R.drawable.gif_check);
                                    towardsGoConfig(response.toString(), codigoQR);
                                } else {
                                    cambiarGif(R.drawable.falta);
                                    alertarError("Orden no Facturada", 3);

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sensorActivar();
                            alertarVersion("Problemas generados con el JSON. El detalle es : " + e.toString(), 3);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        alertarVersion("Problemas en red : " + error.toString(), 3);

                        sensorActivar();

                    }
                }
        );

        Volley.newRequestQueue(this).add(getRequest);


    }

    public void towardsGoConfig(String data, String qr) {


        Intent intent = new Intent(this, Informacion.class);
        intent.putExtra("json", data);
        intent.putExtra("qr", qr);
        startActivity(intent);
        finish();
    }


    public void alertarVersion(String mensaje, int opc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setIcon(getResources().getDrawable(R.drawable.ic_info, getBaseContext().getTheme()));
        builder.setTitle("ADVERTENCIA");
        switch (opc) {
            case 1:
              /*  builder.setPositiveButton(
                        "Actualizar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wsenruta.correosdelecuador.gob.ec/descargas/enruta-57.apk"));
                                startActivity(browserIntent);
                            }
                        });*/
                break;
            case 2:
              /*  builder.setPositiveButton(
                        "Reintentar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reintentar();
                            }
                        });*/
                break;
            case 3:
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                break;
        }

        AlertDialog alerta = builder.create();
        alerta.show();
    }


    public void consultarQRHttp(String codigo) {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                InputStream xml = null;
                Token token = null;
                try {

                  
                    URL url = new
                            URL(referenciasJson.PICKUP_Consultar_Qr);
                    HttpURLConnection httpConnection = (HttpURLConnection)
                            url.openConnection();
                    httpConnection.setDoInput(true);
                    httpConnection.setDoOutput(true);

                    httpConnection.setRequestMethod("POST");

                    httpConnection.setRequestProperty("Accept",
                            "application/json");



                    OutputStream outStream = httpConnection.getOutputStream();
                    OutputStreamWriter outStreamWriter = new
                            OutputStreamWriter(outStream, "UTF-8");


                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("cfac", codigo)
                            .appendQueryParameter("restaurante", lista.get(0).getCod_tienda());


                    String query = builder.build().getEncodedQuery();
                    outStreamWriter.write(query);
                    outStreamWriter.flush();
                    outStreamWriter.close();
                    outStream.close();
                    BufferedReader br = null;
                    if (httpConnection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            Gson gson = new Gson();
                            Root root;
                            root = gson.fromJson(strCurrentLine.toString(), Root.class);
                            httpConnection.disconnect();

                            if ("preparando".equals(root.getOrden().getState())) {
                                cambiarGif(R.drawable.gif_check);
                                towardsGoConfig(strCurrentLine.toString(), codigo);
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        cambiarGif(R.drawable.falta);
                                        alertarError(root.getOrden().getStateMessage(), 3);

                                    }
                                });


                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), "token de seguridad", Toast.LENGTH_SHORT);
                                }
                            });

                        }
                    } else {
                        br = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {

                        }

                        runOnUiThread(new Runnable() {
                            public void run() {
                                cambiarGif(R.drawable.falta);
                                alertarError("ORDEN NO ENCONTRADA EN LA TIENDA", 3);
                            }
                        });
                    }


                } catch (Exception e) {
                    // crearUsuarioTemporal();
                    runOnUiThread(new Runnable() {
                        public void run() {

                            alertarError("PROBLEMAS EN RED : " + e.getMessage(), 3);

                            sensorActivar();
                        }
                    });


                }
            }

        });


    }


    public void alertarError(String mensaje, int opc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setIcon(getResources().getDrawable(R.drawable.ic_info, getBaseContext().getTheme()));
        builder.setTitle("ADVERTENCIA");
        switch (opc) {
            case 1:
              /*  builder.setPositiveButton(
                        "Actualizar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wsenruta.correosdelecuador.gob.ec/descargas/enruta-57.apk"));
                                startActivity(browserIntent);
                            }
                        });*/
                break;
            case 2:
              /*  builder.setPositiveButton(
                        "Reintentar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reintentar();
                            }
                        });*/
                break;
            case 3:
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cambiarGif(R.drawable.gif_qr);
                            }
                        });
                break;
        }

        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void obtenerToken() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                InputStream xml = null;
                Token token = null;
                try {


                    URL url = new
                            URL(referenciasJson.PICKUP_token);
                    HttpURLConnection httpConnection = (HttpURLConnection)
                            url.openConnection();
                    httpConnection.setDoOutput(true);
                    httpConnection.setRequestMethod("POST");

                    httpConnection.setRequestProperty("Accept",
                            "application/json");

                    httpConnection.setDoOutput(true);

                    OutputStream outStream = httpConnection.getOutputStream();
                    OutputStreamWriter outStreamWriter = new
                            OutputStreamWriter(outStream, "UTF-8");

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("grant_type", "client_credentials")
                            .appendQueryParameter("client_id", "5")
                            .appendQueryParameter("client_secret", "Jh9i9RxEGo5BJvXV7861PU185WJMiHZ45J63MLlj");
                    String query = builder.build().getEncodedQuery();

                    outStreamWriter.write(query);

                    outStreamWriter.flush();
                    outStreamWriter.close();
                    outStream.close();
                    BufferedReader br = null;
                    if (httpConnection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            Gson gson = new Gson();
                            token = gson.fromJson(strCurrentLine, Token.class);
                            EntregarPedido(token, "0000003855-010106", "K045F000693668");
                        }
                    } else {
                        br = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            System.out.println(strCurrentLine);
                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        });

    }


    public void EntregarPedido(Token token, String codigo_app, String id_transaccion) {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                InputStream xml = null;
                String urlTexto = null;

                try {

                    urlTexto = referenciasJson.PICKUP_dispatched + "/?codigo_app=" + codigo_app + "&id_transaccion=" + id_transaccion;
                    URL url = new
                            URL(urlTexto);
                    HttpURLConnection httpConnection = (HttpURLConnection)
                            url.openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());
                    httpConnection.setRequestProperty("Accept",
                            "application/json");

                    int responseCode = 0;
                    try {
                        responseCode = httpConnection.getResponseCode();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.out.println("GET Response Code :: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                httpConnection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }


                    } else {
                        System.out.println("GET request not worked");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        });

    }


    private void cambiarGif(int gif) {
        this.gif.setImageResource(gif);


    }


    @Override
    protected void onPause() {
        super.onPause();
    }

}