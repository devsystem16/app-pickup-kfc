package netconsulting.com.ec.kfcpickup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import netconsulting.com.ec.kfcpickup.Adapter.ProductoAdapter;
import netconsulting.com.ec.kfcpickup.Modelo.Detalle;
import netconsulting.com.ec.kfcpickup.Modelo.DetalleProducto;
import netconsulting.com.ec.kfcpickup.Modelo.Modificadore;
import netconsulting.com.ec.kfcpickup.Modelo.Plu;
import netconsulting.com.ec.kfcpickup.Modelo.Root;
import netconsulting.com.ec.kfcpickup.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResumenPedido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumenPedido extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView lblSubtotal;
    private TextView lblIva;
    private TextView lblTotal;
    private RecyclerView.Adapter mAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Root root;
    private static DecimalFormat df = new DecimalFormat("0.00");


    public ResumenPedido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResumenPedido.
     */
    // TODO: Rename and change types and number of parameters
    public static ResumenPedido newInstance(String param1, String param2) {
        ResumenPedido fragment = new ResumenPedido();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        lblIva = vista.findViewById(R.id.lblIva);
        lblTotal = vista.findViewById(R.id.lblTotal);
        lblSubtotal = vista.findViewById(R.id.lblSubtotal);
        this.lblSubtotal.setText(String.valueOf(df.format(root.getOrden().getRequest().getCfac_subtotal())));
        this.lblIva.setText(String.valueOf(df.format(root.getOrden().getRequest().getCfac_iva())));
        this.lblTotal.setText(String.valueOf(df.format(root.getOrden().getRequest().getCfac_total())));

        RecyclerView recyclerView = vista.findViewById(R.id.lista);
        List<DetalleProducto> listaDetalleProductos = new ArrayList<>();
        List<String> listaModificador = null;
        String agreadosString = "";
        listaModificador = new ArrayList<>();
        for (Detalle det : root.getOrden().getRequest().getDetalles()) {
            DetalleProducto productoDet = new DetalleProducto();
            for (Plu plus : root.getPlus()) {
                if (plus.plu_id == det.getPlu_id()) {
                    productoDet.setProducto(plus.getPlu_descripcion());
                    productoDet.setCantidad(det.getDop_cantidad() + "x");

                    for (Modificadore modificador : det.getModificadores()) {

                        for (Plu plus1 : root.getPlus()) {
                            if (modificador.getPlu_id() == plus1.getPlu_id()) {
                                listaModificador.add(plus1.getPlu_descripcion());
                            }
                        }
                    }
                    for (String agregados : listaModificador) {
                        agreadosString = agreadosString + agregados + "\n";
                    }
                    productoDet.setDescripion(agreadosString);
                    listaDetalleProductos.add(productoDet);
                    listaModificador = new ArrayList<>();
                }

            }
        }

        mAdapter = new ProductoAdapter(vista.getContext(), listaDetalleProductos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(vista.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
        return vista;
    }
}