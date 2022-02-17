package netconsulting.com.ec.kfcpickup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import netconsulting.com.ec.kfcpickup.Modelo.DetallePedidos;
import netconsulting.com.ec.kfcpickup.R;

public class detallePedido extends RecyclerView.Adapter<detallePedido.detallePedidoViewHolder> {

    private Context mCtx;
    private List<DetallePedidos> detallePedidos;

    public detallePedido(Context mCtx, List<DetallePedidos> detallePedidos) {
        this.mCtx = mCtx;
        this.detallePedidos = detallePedidos;
    }


    @Override
    public detallePedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.detalle_pedido, null);
        return new detallePedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detallePedidoViewHolder holder, int position) {
        final DetallePedidos DetallePedidos = detallePedidos.get(position);

        holder.plu_valor.setText(DetallePedidos.getPlu_valor());
        holder.modif_valor.setText(DetallePedidos.getModif_valor());
        holder.nombre_valor.setText(DetallePedidos.getNombre_valor());
        holder.cantidad_valor.setText(DetallePedidos.getCantidad_valor());
    }

    @Override
    public int getItemCount() {
        return detallePedidos.size();
    }


    static class detallePedidoViewHolder extends RecyclerView.ViewHolder {


        TextView plu_valor, modif_valor, nombre_valor, cantidad_valor;
        LinearLayout container;

        public detallePedidoViewHolder(View itemView) {
            super(itemView);

            plu_valor = itemView.findViewById(R.id.plu_valor);
            modif_valor = itemView.findViewById(R.id.modif_valor);
            nombre_valor = itemView.findViewById(R.id.nombre_valor);
            cantidad_valor = itemView.findViewById(R.id.cantidad_valor);
            container = itemView.findViewById(R.id.container);


        }
    }

}
