package netconsulting.com.ec.kfcpickup.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import netconsulting.com.ec.kfcpickup.Adapter.CatalogoAdapter;
import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;
import netconsulting.com.ec.kfcpickup.Modelo.Catalogo;
import netconsulting.com.ec.kfcpickup.Modelo.Mensaje;
import netconsulting.com.ec.kfcpickup.Modelo.MensajeError;
import netconsulting.com.ec.kfcpickup.Modelo.Token;
import netconsulting.com.ec.kfcpickup.R;
import netconsulting.com.ec.kfcpickup.SQLiteOpenHelper.ConfiguracionDbHelper;
import netconsulting.com.ec.kfcpickup.Servicio.ReferenciasJson;

public class MainActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 1000;
    ConfiguracionDbHelper configuracionDbHelper;
    ReferenciasJson referenciasJson;
    ArrayList<Catalogo> listaPais;
    ArrayList<Catalogo> listaCadena;
    ArrayList<Catalogo> listaRestaurante;
    Spinner pais;
    Spinner cadena;
    Spinner restaurante;
    Token token = new Token();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        configuracionDbHelper = new ConfiguracionDbHelper(this);
        pais = new SearchableSpinner(this);
        cadena = new SearchableSpinner(this);
        restaurante = new SearchableSpinner(this);
        setContentView(R.layout.activity_main);
        ArrayList<Configuracion> lista = configuracionDbHelper.consultarTodosValores();
        if (lista != null) {
            if (lista.size() > 0) {
                referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion(), lista.get(0).getCod_tienda(), lista.get(0).getTrade());
            }
        }
        if (lista.size() > 0) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LeerQr.class);
                    startActivity(intent);
                    finish();
                }

                ;
            }, DURACION_SPLASH);
        } else {
            Toast.makeText(getApplicationContext(), "No hay configuración", Toast.LENGTH_SHORT).show();

        }
//token();


    }


    public void entregarPedidoCentral() {
        String url = "https://api.multimarca.app-kfc.com/api/order/dispatched";
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


    public void token() {
        String url = "https://api.multimarca.app-kfc.com/api/authentication/login";
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
                    token.setAccess_token(response.getString("accessToken"));
                    entregarPedidoCentral();
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


    public void obtenerToken() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                InputStream xml = null;
                Token token = null;
                try {


                    URL url = new
                            URL("https://api.multimarca.app-kfc.com/api/authentication/login");
                    HttpURLConnection httpConnection = (HttpURLConnection)
                            url.openConnection();

                    httpConnection.setDoInput(true);
                    httpConnection.setDoOutput(true);
                    httpConnection.connect();

                    httpConnection.setRequestProperty("Accept",
                            "application/x-www-form-urlencoded");

                    httpConnection.setRequestMethod("POST");


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

                            //    EntregarPedido(token, root.getOrden().getCodigo_app(), root.getOrden().getResponseData().getData().getId_transaccion());
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
                            //         alertarRegresar("Servicio no disponible", 3);
                        }
                    });

                    //    sensorActivar();

                }
            }

        });

    }

    public void onClick(View v) {
        final EditText ipConsulta = new EditText(this);
        final EditText ipImpresion = new EditText(this);
        final EditText tienda = new EditText(this);
        final EditText trade = new EditText(this);
        ipConsulta.setHint("Ip Consulta");
        ipImpresion.setHint("Ip Impresón");
        tienda.setHint("Codigo Tienda");
        trade.setHint("Dominio Trade");
        ipConsulta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                consultarPais(ipConsulta.getText().toString());
            }
        });

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(ipConsulta);
        layout.addView(pais);
        layout.addView(ipImpresion);
        layout.addView(cadena);
        layout.addView(restaurante);
        layout.addView(trade);

        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                consultarCadena(
                        ipConsulta.getText().toString(),
                        listaPais.get(position).getId()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cadena.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                consultarRestaurante(
                        ipConsulta.getText().toString(),
                        listaCadena.get(position).getId()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        restaurante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tienda.setText(listaRestaurante.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Configuración  IP en Tienda")
                .setView(layout)
                //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ipConsulta.getText().toString().length() > 0 && ipImpresion.getText().toString().length() > 0 && (tienda.getText().toString().length() < 5)) {
                            Configuracion configuracion = new Configuracion();
                            configuracion.setIp_consulta(ipConsulta.getText().toString());
                            configuracion.setIp_impresion(ipImpresion.getText().toString());
                            configuracion.setCod_tienda(tienda.getText().toString());
                            configuracion.setTrade(trade.getText().toString());
                            configuracionDbHelper.grabarConfiguracion(configuracion);
                            Intent intent = new Intent(MainActivity.this, LeerQr.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), R.string.configuracion, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        builder.create().show();

    }


    public void consultarPais(final String ipExtrar) {
        Toast.makeText(getBaseContext(), R.string.waitQR, Toast.LENGTH_LONG).show();
        Map<String, String> params = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject(params);
     //   alertarError("ORDEN NO ENCONTRADA EN LA TIENDA", 3);


        //String url = "HTTP://" + ipExtrar + "/obtener-paises";
        String url =  ipExtrar + "/obtener-paises";
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        if (response.length() > 0) {
                            listaPais = new ArrayList<>();
                            Catalogo select = new Catalogo();
                            select.setId(null);
                            select.setDescripcion("Seleccionar");
                            listaPais.add(select);
                            for (int i = 0; i < response.length(); i++) {
                                Catalogo catalogo = new Catalogo();
                                try {
                                    JSONObject items1 = response.getJSONObject(i);
                                    catalogo.setId(items1.getString("country_id"));
                                    catalogo.setDescripcion(items1.getString("country_detail"));
                                    listaPais.add(catalogo);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            CatalogoAdapter dataAdapter = new CatalogoAdapter(MainActivity.this, listaPais, android.R.layout.simple_expandable_list_item_1);
                            pais.setAdapter(dataAdapter);


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // alertarVersion("Problemas en red : "+error.toString(), 3);


                    }
                }
        );

        Volley.newRequestQueue(this).add(getRequest);


    }


    public void consultarCadena(final String ipExtrar, String pais) {
        Toast.makeText(getBaseContext(), R.string.waitQR, Toast.LENGTH_LONG).show();
        Map<String, String> params = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject(params);

        //String url = "HTTP://" + ipExtrar + "/obtener-cadenas?country_id=" + pais;
        String url =   ipExtrar + "/obtener-cadenas?country_id=" + pais;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        if (response.length() > 0) {
                            listaCadena = new ArrayList<>();
                            Catalogo select = new Catalogo();
                            select.setId(null);
                            select.setDescripcion("Seleccionar");
                            listaCadena.add(select);
                            for (int i = 0; i < response.length(); i++) {
                                Catalogo catalogo = new Catalogo();
                                try {
                                    JSONObject items1 = response.getJSONObject(i);
                                    catalogo.setId(items1.getString("id"));
                                    catalogo.setDescripcion(items1.getString("detail"));
                                    listaCadena.add(catalogo);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            CatalogoAdapter dataAdapter = new CatalogoAdapter(MainActivity.this, listaCadena, android.R.layout.simple_expandable_list_item_1);
                            cadena.setAdapter(dataAdapter);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // alertarVersion("Problemas en red : "+error.toString(), 3);


                    }
                }
        );

        Volley.newRequestQueue(this).add(getRequest);


    }

    public void consultarRestaurante(final String ipExtrar, String cadena) {
        Toast.makeText(getBaseContext(), R.string.waitQR, Toast.LENGTH_LONG).show();
        Map<String, String> params = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject(params);

        //String url = "HTTP://" + ipExtrar + "/obtener-restaurantes?idCadena=" + cadena;
        String url =  ipExtrar + "/obtener-restaurantes?idCadena=" + cadena;


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        if (response.length() > 0) {
                            listaRestaurante = new ArrayList<>();
                            Catalogo select = new Catalogo();
                            select.setId(null);
                            select.setDescripcion("Seleccionar");
                            listaRestaurante.add(select);
                            for (int i = 0; i < response.length(); i++) {
                                Catalogo catalogo = new Catalogo();
                                try {
                                    JSONObject items1 = response.getJSONObject(i);
                                    catalogo.setId(items1.getString("store_id"));
                                    catalogo.setDescripcion(items1.getString("store") + " " + items1.getString("address"));
                                    listaRestaurante.add(catalogo);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            CatalogoAdapter dataAdapter = new CatalogoAdapter(MainActivity.this, listaRestaurante, android.R.layout.simple_expandable_list_item_1);
                            restaurante.setAdapter(dataAdapter);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // alertarVersion("Problemas en red : "+error.toString(), 3);


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


    @Override
    protected void onPause() {
        super.onPause();
    }
}