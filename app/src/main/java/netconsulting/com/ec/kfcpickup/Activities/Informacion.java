package netconsulting.com.ec.kfcpickup.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
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

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import netconsulting.com.ec.kfcpickup.Adapter.PagerAdapter;
import netconsulting.com.ec.kfcpickup.Adapter.ProductoAdapter;
import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;
import netconsulting.com.ec.kfcpickup.Modelo.Mensaje;
import netconsulting.com.ec.kfcpickup.Modelo.MensajeError;
import netconsulting.com.ec.kfcpickup.Modelo.Root;
import netconsulting.com.ec.kfcpickup.Modelo.Token;
import netconsulting.com.ec.kfcpickup.R;
import netconsulting.com.ec.kfcpickup.SQLiteOpenHelper.ConfiguracionDbHelper;
import netconsulting.com.ec.kfcpickup.Servicio.ReferenciasJson;

public class Informacion extends AppCompatActivity {

    private Root root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView lblNombre;
    private TextView lblDireccion;
    private TextView lblVarios;
    private TextView lblTelefono;
    private TextView lblSubtotal;
    private TextView lblIva;
    private TextView lblTotal;
    private TextView titlefactura;
    private RecyclerView.Adapter mAdapter;
    private String qr;
    private Bundle parametros;
    private Token token = null;
    private ReferenciasJson referenciasJson;
    private ConfiguracionDbHelper configuracionDbHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_dashboard:
                    towardGoImprimir();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        String urlFactura;
        parametros = this.getIntent().getExtras();
        configuracionDbHelper = new ConfiguracionDbHelper(this);
        ArrayList<Configuracion> lista = configuracionDbHelper.consultarTodosValores();
        if (lista != null) {
            if (lista.size() > 0) {
                referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion(), lista.get(0).getCod_tienda(), lista.get(0).getTrade());
            }
        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        titlefactura = findViewById(R.id.titlefactura);
        lblNombre = findViewById(R.id.lblNombre);
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        Gson gson = new Gson();
        root = gson.fromJson(parametros.getString("json"), Root.class);
        urlFactura = "http://" + lista.get(0).getIp_impresion() + "/pos/facturacion/impresion/impresion_factura.php?cfac_id=" + root.getOrden().getResponseData().getData().getId_transaccion() + "&tipo_comprobante=F&";
        view.loadUrl(urlFactura);
        this.titlefactura.setText("ORDEN APP.: " + root.getOrden().getRequest().getCodigo_app());
        this.lblNombre.setText(root.getOrden().getRequest().getCli_nombres());
        //Adding toolbar to the activity

       /* TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("DETALLE PEDIDO"));
        tabLayout.addTab(tabLayout.newTab().setText("RESUMEN PEDIDO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        configuracionDbHelper=new ConfiguracionDbHelper(this);
        ArrayList<Configuracion> lista= configuracionDbHelper.consultarTodosValores();
        if (lista!=null) {
            if (lista.size()>0) {
                referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion());
            }
        } */

      /*  final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), parametros.getString("json"));
        viewPager.setAdapter(adapter);
        this.qr= parametros.getString("qr");
        Gson gson = new Gson();
      root = gson.fromJson(parametros.getString("json"), Root.class);
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        }); */
    }

    public void towardsGoHome() {
        Intent intent = new Intent(this, LeerQr.class);
        startActivity(intent);
        finish();
    }

    public void sensorActivar() {

        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(1500, 10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1500);
        }

    }

    public void imprimir(final String codigoQR) {

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, referenciasJson.PICKUP_ImprimirTienda + "/" + codigoQR, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            towardsGoHome();


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

                        // crearUsuarioTemporal();
                        alertarVersion("Problemas en red  : " + error.toString(), 3);
                        sensorActivar();

                    }
                }
        );
        Volley.newRequestQueue(this).add(getRequest);


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


    public void alertarRegresar(String mensaje, int opc) {
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
                                Intent intent = new Intent(getApplicationContext(), LeerQr.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                break;
        }

        AlertDialog alerta = builder.create();
        alerta.show();
    }


    public void alertarExitosa(String mensaje, int opc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialoginfoStyle);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setIcon(getResources().getDrawable(R.drawable.ic_info, getBaseContext().getTheme()));
        builder.setTitle("INFORMACION..");
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
                                Intent intent = new Intent(getApplicationContext(), LeerQr.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                break;
        }

        AlertDialog alerta = builder.create();
        alerta.show();
    }


    public void towardGoImprimir() {
        entregarPedidoCentral(root.getOrden().getResponseData().getData().getId_transaccion());
        obtenerTokenV2();
    }

    public void obtenerTokenV2() {
        String url = referenciasJson.PICKUP_token;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        StringEntity entity = null;
        params.put("grant_type", "client_credentials");
        params.put("client_id", "5");
        params.put("client_secret", "Jh9i9RxEGo5BJvXV7861PU185WJMiHZ45J63MLlj");
        try {
            entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                try {
                    token = new Token();
                    token.setAccess_token(response.getString("accessToken"));
                    EntregarPedidoV2(token, root.getOrden().getCodigo_app(), root.getOrden().getResponseData().getData().getId_transaccion());

                    super.onSuccess(statusCode, headers, response);
                } catch (Exception e) {
                    alertarRegresar("Servicio no disponible", 3);

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                alertarRegresar("Servicio no disponible", 3);

                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                alertarRegresar("Servicio no disponible", 3);
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                alertarRegresar("Servicio no disponible", 3);

//                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


    public void EntregarPedidoV2(Token token, String codigo_app, String id_transaccion) {
        String url = referenciasJson.PICKUP_dispatched;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + token.getAccess_token());
        RequestParams params = new RequestParams();
        StringEntity entity = null;
        params.put("codigo_app", codigo_app);
        params.put("id_transaccion", id_transaccion);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                entregarPedidoCentral(id_transaccion);
                Mensaje mensaje = new Mensaje();
                try {
                    mensaje.setMessage(response.getString("order_status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertarExitosa(mensaje.getMessage(), 3);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                alertarRegresar("No se puede actualizar el estado del Pedido en la APP \n Servicio no disponible", 3);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                alertarRegresar("No se puede actualizar el estado del Pedido en la APP \n Servicio no disponible", 3);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                alertarRegresar("No se puede actualizar el estado del Pedido en la APP \n Servicio no disponible", 3);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
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
                    httpConnection.setDoInput(true);
                    httpConnection.setDoOutput(true);

                    httpConnection.setRequestMethod("POST");

                    httpConnection.setRequestProperty("Accept",
                            "application/x-www-form-urlencoded");


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
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), "token de seguridad", Toast.LENGTH_SHORT);
                                }
                            });

                            EntregarPedido(token, root.getOrden().getCodigo_app(), root.getOrden().getResponseData().getData().getId_transaccion());
                        }
                    } else {
                        br = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            System.out.println(strCurrentLine);
                        }
                    }


                } catch (Exception e) {
                    // crearUsuarioTemporal();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            alertarRegresar("Servicio no disponible", 3);
                        }
                    });

                    sensorActivar();

                }
            }

        });

    }


    public void EntregarPedido(Token token, String codigo_app, String id_transaccion) {

        InputStream xml = null;
        String urlTexto = null;
        Mensaje mensaje = new Mensaje();
        MensajeError mensajeError = new MensajeError();
        try {

            urlTexto = referenciasJson.PICKUP_dispatched;
            URL url = new
                    URL(urlTexto);
            HttpURLConnection httpConnection = (HttpURLConnection)
                    url.openConnection();
            httpConnection.setDoInput(true);

            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());
            httpConnection.setRequestProperty("Accept",
                    "application/json");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("codigo_app", codigo_app)
                    .appendQueryParameter("id_transaccion", id_transaccion);

            String query = builder.build().getEncodedQuery();
            OutputStream outStream = httpConnection.getOutputStream();
            OutputStreamWriter outStreamWriter = new
                    OutputStreamWriter(outStream, "UTF-8");
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
                    entregarPedidoCentral(id_transaccion);
                    mensaje = gson.fromJson(strCurrentLine, Mensaje.class);
                    Mensaje finalMensaje = mensaje;
                    runOnUiThread(new Runnable() {


                        public void run() {
                            alertarExitosa(finalMensaje.getMessage(), 3);

                        }
                    });

                }
            } else {
                br = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
                String strCurrentLine;

                while ((strCurrentLine = br.readLine()) != null) {

                    Gson gson = new Gson();

                    mensajeError = gson.fromJson(strCurrentLine, MensajeError.class);
                    MensajeError mensaje1 = mensajeError;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            alertarRegresar(mensaje1.getMessage(), 3);

                        }
                    });

                }
            }


        } catch (Exception e) {
            // crearUsuarioTemporal();
            runOnUiThread(new Runnable() {
                public void run() {

                    alertarRegresar("No se puede actualizar el estado del Pedido en la APP \n Servicio no disponible", 3);
                }
            });
            sensorActivar();

        }
    }


    public void entregarPedidoCentralV2() {
        String url = "referenciasJson.PICKUP_pedidoEntregado";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + token.getAccess_token());
        RequestParams params = new RequestParams();
        StringEntity entity = null;
        params.put("codigo_app", "0000076497-010103");
        params.put("id_transaccion", "K131F000366840");
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                try {

                    Token token = new Token();
                    token.setAccess_token(response.getString("accessToken"));
                    super.onSuccess(statusCode, headers, response);
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println(errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


    public void entregarPedidoCentral(String id_transaccion) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                InputStream xml = null;
                Token token = null;
                try {


                    String urlTexto = null;
                    Mensaje mensaje = new Mensaje();
                    MensajeError mensajeError = new MensajeError();
                    try {

                        urlTexto = referenciasJson.PICKUP_pedidoEntregado;
                        URL url = new
                                URL(urlTexto);
                        HttpURLConnection httpConnection = (HttpURLConnection)
                                url.openConnection();
                        httpConnection.setDoInput(true);

                        httpConnection.setRequestMethod("POST");
                        httpConnection.setRequestProperty("Accept",
                                "application/json");

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("cfac_id", id_transaccion);

                        String query = builder.build().getEncodedQuery();
                        OutputStream outStream = httpConnection.getOutputStream();
                        OutputStreamWriter outStreamWriter = new
                                OutputStreamWriter(outStream, "UTF-8");
                        outStreamWriter.write(query);
                        outStreamWriter.flush();
                        outStreamWriter.close();
                        outStream.close();
                        BufferedReader br = null;
                        if (httpConnection.getResponseCode() == 201) {
                            br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                            String strCurrentLine;
                            while ((strCurrentLine = br.readLine()) != null) {
                                runOnUiThread(new Runnable() {


                                    public void run() {

                                    }
                                });

                            }
                        } else {
                            br = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
                            String strCurrentLine;

                            while ((strCurrentLine = br.readLine()) != null) {


                            }
                        }


                    } catch (Exception e) {
                        // crearUsuarioTemporal();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                alertarRegresar("No se puede actualizar el estado del Pedido en la APP \n Servicio no disponible", 3);
                            }
                        });
                        sensorActivar();

                    }

                } catch (Exception e) {
                    // crearUsuarioTemporal();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            alertarRegresar("Servicio no disponible", 3);
                        }
                    });

                    sensorActivar();

                }
            }

        });
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}