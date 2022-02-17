package netconsulting.com.ec.kfcpickup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import netconsulting.com.ec.kfcpickup.Activities.LeerQr;
import netconsulting.com.ec.kfcpickup.Activities.ReporteAActivity;
import netconsulting.com.ec.kfcpickup.Activities.SearchActivity;
import netconsulting.com.ec.kfcpickup.Activities.ViewWebActivity;
import netconsulting.com.ec.kfcpickup.Modelo.Busquedas;
import netconsulting.com.ec.kfcpickup.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context mCtx;
    private List<Busquedas> busquedas;
    private String idCodigo;
    private String tlfCliente;
    private String nombreCliente;
    private String horaPedido;
    private String horaPickup;

    public SearchAdapter(Context mCtx, List<Busquedas> busquedas) {
        this.mCtx = mCtx;
        this.busquedas = busquedas;
        this.idCodigo = idCodigo;
        this.tlfCliente = tlfCliente;
        this.nombreCliente = nombreCliente;
        this.horaPedido = horaPedido;
        this.horaPickup = horaPickup;
    }

    public SearchAdapter() {
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.search_list, null);

        return new SearchViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final Busquedas Busquedas = busquedas.get(position);

        holder.nameCliente.setText(Busquedas.getNombreCliente());
        holder.idCodigo.setText(Busquedas.getIdcodigo());
        holder.tlfCliente.setText(Busquedas.getTlfCliente());
        holder.horaPickup.setText(Busquedas.getHoraPickup());
        holder.estdoOrden.setText(Busquedas.getIdEstado());
        holder.pago.setText(Busquedas.getTipoPago());
        if ("preparando".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorPreparando));
        }
        if ("rechazado".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorRechazdo));
        }
        if ("entregado".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorEntregado));
        }
        if ("rechazado-restaurante".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorRechazdo));
        }
        if ("registrado".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorRegistra));
        }
        if ("recibido-restaurante".equals(Busquedas.getIdEstado())) {
            holder.estdoOrden.setTextColor(ContextCompat.getColorStateList(mCtx, R.color.colorRegistra));
        }
        holder.layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirectItems(variedades.getSubCategoria(), variedades.getNombre(), variedades.getTamanio(), variedades.getIdTamanio(), variedades.getIdvariedad(), variedades.getImagenUrl());
                Toast.makeText(mCtx, "Detalles de pedido", Toast.LENGTH_LONG).show();
                Intent i = new Intent(v.getContext(), ReporteAActivity.class);
                i.putExtra(ReporteAActivity.URL, Busquedas.idcodigo);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return busquedas.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView viewMoreOrder;
        TextView nameCliente, idCodigo, tlfCliente, horaPedido, horaPickup, estdoOrden, pago;
        RelativeLayout layoutOrder;

        public SearchViewHolder(View itemView) {
            super(itemView);

            nameCliente = itemView.findViewById(R.id.nameCliente);
            idCodigo = itemView.findViewById(R.id.idCodigo);
            tlfCliente = itemView.findViewById(R.id.tlfCliente);
            horaPickup = itemView.findViewById(R.id.horaPickup);
            estdoOrden = itemView.findViewById(R.id.estdoOrden);
            viewMoreOrder = itemView.findViewById(R.id.viewMoreOrder);
            pago = itemView.findViewById(R.id.idRestaurante);
            layoutOrder = itemView.findViewById(R.id.layoutOrder);

        }
    }
}
