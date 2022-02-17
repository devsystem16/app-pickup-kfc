package netconsulting.com.ec.kfcpickup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import netconsulting.com.ec.kfcpickup.Modelo.Root;
import netconsulting.com.ec.kfcpickup.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallePedido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallePedido extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView lblNombre;
    private TextView lblDireccion;
    private TextView lblCedula;
    private TextView lblTelefono;
    private TextView lblEmail;
    private TextView titlefactura;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Root root;

    public DetallePedido() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallePedido.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallePedido newInstance(String param1, String param2) {
        DetallePedido fragment = new DetallePedido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            root = gson.fromJson(mParam1, Root.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);
        lblNombre = vista.findViewById(R.id.lblCliente);
        lblCedula = vista.findViewById(R.id.lblCedula);
        lblTelefono = vista.findViewById(R.id.lblTelefono);
        lblEmail = vista.findViewById(R.id.lblEmail);
        lblDireccion = vista.findViewById(R.id.lblDireccion);
        titlefactura = vista.findViewById(R.id.titlefactura);
        this.lblNombre.setText(root.getOrden().getRequest().getCli_nombres());
        this.lblCedula.setText(root.getOrden().getRequest().getCli_documento());
        this.lblTelefono.setText(root.getOrden().getRequest().getCli_telefono());
        this.lblDireccion.setText(root.getOrden().getRequest().getCli_direccion());
        this.lblEmail.setText(root.getOrden().getRequest().getCli_email());
        this.titlefactura.setText("FACTURA NRO.: " + root.getOrden().getRequest().getCodigo_app());
        return vista;
    }
}