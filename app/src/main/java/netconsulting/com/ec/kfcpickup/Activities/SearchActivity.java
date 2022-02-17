package netconsulting.com.ec.kfcpickup.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import netconsulting.com.ec.kfcpickup.Adapter.SearchAdapter;
import netconsulting.com.ec.kfcpickup.Modelo.Busquedas;
import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;
import netconsulting.com.ec.kfcpickup.Modelo.Root;
import netconsulting.com.ec.kfcpickup.Modelo.Token;
import netconsulting.com.ec.kfcpickup.R;
import netconsulting.com.ec.kfcpickup.SQLiteOpenHelper.ConfiguracionDbHelper;
import netconsulting.com.ec.kfcpickup.Servicio.ReferenciasJson;
import pl.droidsonroids.gif.GifImageView;

public class SearchActivity extends AppCompatActivity {

    MaterialButton btnEstado, btnCiente;
    RelativeLayout cliente_cero, cliente_uno, cliente_dos, cliente_tres, cabeMidlee;
    TextView choose_op;
    MaterialTextView idCardCliente;
    Button btnSearch;
    LinearLayout cliente, estado;
    RecyclerView recyclerView;
    public static List<Busquedas> busquedas;
    ImageView ImgNotify;
    TextView NoOrderLabel, NoorderLabel2;
    SearchAdapter adapter;
    SwitchCompat idE0, idE1, idE2, idE3, idE4, idE5;
    com.google.android.material.textfield.TextInputEditText nameCliente, nameCardCliente, nameCodigoApp;
    ProgressBar progressBar;
    ConfiguracionDbHelper configuracionDbHelper;
    ReferenciasJson referenciasJson;
    ArrayList<Configuracion> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnCiente = findViewById(R.id.btnCliente);
        btnEstado = findViewById(R.id.btnEstado);

        cliente_uno = findViewById(R.id.cliente_uno);
        cliente_dos = findViewById(R.id.cliente_dos);
        cliente_tres = findViewById(R.id.cliente_tres);
        cabeMidlee = findViewById(R.id.cabeMidlee);
        cliente = findViewById(R.id.cliente);
        estado = findViewById(R.id.estado);
        btnSearch = findViewById(R.id.btnSearch);

        choose_op = findViewById(R.id.choose_op);

        idCardCliente = findViewById(R.id.idCardCliente);

        nameCardCliente = findViewById(R.id.nameCardCliente);
        nameCliente = findViewById(R.id.nameCliente);
        nameCodigoApp = findViewById(R.id.nameCodigoApp);

        recyclerView = findViewById(R.id.resultSearch);

        ImgNotify = findViewById(R.id.ImgNotify);
        NoOrderLabel = findViewById(R.id.NoOrderLabel);
        idE0 = findViewById(R.id.idE0);
        idE1 = findViewById(R.id.idE1);
        idE2 = findViewById(R.id.idE2);
        idE3 = findViewById(R.id.idE3);
        idE4 = findViewById(R.id.idE4);
        idE5 = findViewById(R.id.idE5);

        progressBar = findViewById(R.id.progressBar);
        busquedas = new ArrayList<>();


        recyclerView.setVisibility(View.GONE);
        choose_op.setVisibility(View.VISIBLE);
        cabeMidlee.setVisibility(View.VISIBLE);
        estado.setVisibility(View.GONE);

        btnEstado.setBackgroundTintList(ContextCompat.getColorStateList(SearchActivity.this, R.color.colortextRed));
        btnCiente.setBackgroundTintList(ContextCompat.getColorStateList(SearchActivity.this, R.color.colortextRed));

        configuracionDbHelper = new ConfiguracionDbHelper(this);
        lista = configuracionDbHelper.consultarTodosValores();
        if (lista != null) {
            if (lista.size() > 0) {
                referenciasJson = new ReferenciasJson(lista.get(0).getIp_consulta(), lista.get(0).getIp_impresion(), lista.get(0).getCod_tienda(), lista.get(0).getTrade());
            }
        }

        nameCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (charSequence.length() >= 4) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadPedidosPorCiente(charSequence.toString(), "nombreCliente");
                } else {

                    if (charSequence.length() == 0) {
                        busquedas.clear();
                        ImgNotify.setVisibility(View.VISIBLE);
                        NoOrderLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        nameCardCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (charSequence.length() >= 4) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadPedidosPorCiente(charSequence.toString(), "identificacionCliente");
                } else {

                    if (charSequence.length() == 0) {
                        busquedas.clear();
                        ImgNotify.setVisibility(View.VISIBLE);
                        NoOrderLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nameCodigoApp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (charSequence.length() >= 4) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadPedidosPorCiente(charSequence.toString(), "codigoApp");
                } else {

                    if (charSequence.length() == 0) {
                        busquedas.clear();
                        ImgNotify.setVisibility(View.VISIBLE);
                        NoOrderLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        idE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE2.setChecked(false);
                idE3.setChecked(false);
                idE4.setChecked(false);
                idE5.setChecked(false);
                idE0.setChecked(false);
                String text = "preparando";
                loadPedidos(text, "estado");
            }
        });

        idE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE1.setChecked(false);
                idE3.setChecked(false);
                idE4.setChecked(false);
                idE5.setChecked(false);
                idE0.setChecked(false);

                String text = "rechazado";
                loadPedidos(text, "estado");

            }
        });

        idE0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE1.setChecked(false);
                idE3.setChecked(false);
                idE4.setChecked(false);
                idE5.setChecked(false);

                String text = "entregado";
                loadPedidos(text, "estado");

            }
        });

        idE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE1.setChecked(false);
                idE2.setChecked(false);
                idE4.setChecked(false);
                idE5.setChecked(false);
                idE0.setChecked(false);
                String text = "rechazado-restaurante";
                loadPedidos(text, "estado");

            }
        });

        idE4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE1.setChecked(false);
                idE2.setChecked(false);
                idE3.setChecked(false);
                idE5.setChecked(false);
                idE0.setChecked(false);
                String text = "registrado";
                loadPedidos(text, "estado");
            }
        });

        idE5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idE1.setChecked(false);
                idE2.setChecked(false);
                idE3.setChecked(false);
                idE4.setChecked(false);
                idE0.setChecked(false);
                String text = "recibido-restaurante";
                loadPedidos(text, "estado");
            }
        });

        btnCiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cliente_uno.setVisibility(View.VISIBLE);
                cliente_tres.setVisibility(View.VISIBLE);
                choose_op.setVisibility(View.GONE);
                idCardCliente.setVisibility(View.VISIBLE);
                nameCardCliente.setVisibility(View.VISIBLE);
                cliente.setVisibility(View.VISIBLE);
                cabeMidlee.setVisibility(View.VISIBLE);
                estado.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.GONE);
                idE1.setChecked(false);

                busquedas.clear();
                recyclerView.removeAllViews();
                limpiarLista();


            }
        });

        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.INVISIBLE);
                cliente_uno.setVisibility(View.GONE);
                cliente_tres.setVisibility(View.GONE);
                choose_op.setVisibility(View.GONE);
                idCardCliente.setVisibility(View.GONE);
                nameCardCliente.setVisibility(View.GONE);
                cliente.setVisibility(View.GONE);
                cabeMidlee.setVisibility(View.VISIBLE);
                estado.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                busquedas.clear();
                recyclerView.removeAllViews();
                limpiarLista();


            }
        });


    }


    public void loadPedidosPorCiente(String searchtext, String condicion) {

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (condicion.equals("nombre")) {
            nameCardCliente.setText("");
            nameCodigoApp.setText("");
        } else {

            if (condicion.equals("identificacion")) {
                nameCliente.setText("");
                nameCodigoApp.setText("");
            } else {
                if (condicion.equals("codigo")) {
                    nameCardCliente.setText("");
                    nameCliente.setText("");
                }
            }

        }

        //  recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());


        ImgNotify.setVisibility(View.VISIBLE);
        NoOrderLabel.setVisibility(View.VISIBLE);


        //  String fileJSON = ReferenciasJson.REFERENCES_RESULTADO_1 + searchtext;

        String fileJSON = referenciasJson.BUSCAR_ORDEN_CLIENTE + condicion + "=" + searchtext;
        StringRequest stringRequest_a = new StringRequest(Request.Method.GET, fileJSON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String json = response.toString();
                    json = "{\"orden\":" + json + "}";


                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray array = jsonObject.getJSONArray("orden");


                    if (array.length() <= 0) {


                        ImgNotify.setVisibility(View.VISIBLE);
                        NoOrderLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {


                        ImgNotify.setVisibility(View.GONE);
                        NoOrderLabel.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        busquedas = new ArrayList<>();
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {


                            JSONObject items1 = array.getJSONObject(i);
                            String codApp = items1.getString("codigo_app");
                            String fecha_creacion = items1.getString("fecha_creacion");
                            String state = items1.getString("state");

                            JSONObject items = items1.getJSONObject("request");

                            String tipoP = items.getString("tipo_pago");

                            busquedas.add(new Busquedas(
                                    items1.getString("codigo_app"),
                                    items.getString("cli_telefono"),
                                    items.getString("cli_nombres"),
                                    items1.getString("fecha_creacion"),
                                    items.getString("fecha_hora_pickup"),
                                    items1.getString("state"),
                                    items.getString("tipo_pago")
                            ));

                        }

                        // public varitiesAdapter(Context mCtx, List<Varieties> varieties, String idOrder, String idBuyer, String titleOrder, String codOrder)
                        adapter = new SearchAdapter(getApplicationContext(), busquedas);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


    public void loadPedidos(String searchtext, String condicion) {

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (condicion.equals("nombre")) {
            nameCardCliente.setText("");
            nameCodigoApp.setText("");
        } else {

            if (condicion.equals("identificacion")) {
                nameCliente.setText("");
                nameCodigoApp.setText("");
            } else {
                if (condicion.equals("codigo")) {
                    nameCardCliente.setText("");
                    nameCliente.setText("");
                }
            }

        }

        //  recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());


        ImgNotify.setVisibility(View.VISIBLE);
        NoOrderLabel.setVisibility(View.VISIBLE);


        //  String fileJSON = ReferenciasJson.REFERENCES_RESULTADO_1 + searchtext;

        String fileJSON = referenciasJson.BUSCAR_ORDEN_ESTADO + searchtext;
        StringRequest stringRequest_a = new StringRequest(Request.Method.GET, fileJSON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String json = response.toString();
                    json = "{\"orden\":" + json + "}";


                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray array = jsonObject.getJSONArray("orden");


                    if (array.length() <= 0) {


                        ImgNotify.setVisibility(View.VISIBLE);
                        NoOrderLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {


                        ImgNotify.setVisibility(View.GONE);
                        NoOrderLabel.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        busquedas = new ArrayList<>();
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {


                            JSONObject items1 = array.getJSONObject(i);
                            String codApp = items1.getString("codigo_app");
                            String fecha_creacion = items1.getString("fecha_creacion");
                            String state = items1.getString("state");

                            JSONObject items = items1.getJSONObject("request");

                            String tipoP = items.getString("tipo_pago");

                            busquedas.add(new Busquedas(
                                    items1.getString("codigo_app"),
                                    items.getString("cli_telefono"),
                                    items.getString("cli_nombres"),
                                    items1.getString("fecha_creacion"),
                                    items.getString("fecha_hora_pickup"),
                                    items1.getString("state"),
                                    items.getString("tipo_pago")
                            ));

                        }

                        // public varitiesAdapter(Context mCtx, List<Varieties> varieties, String idOrder, String idBuyer, String titleOrder, String codOrder)
                        adapter = new SearchAdapter(getApplicationContext(), busquedas);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    void limpiarLista() {
        List<Busquedas> lista = new ArrayList<>();
        adapter = new SearchAdapter(getApplicationContext(), lista);
        recyclerView.setAdapter(adapter);
    }

}




