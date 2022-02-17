package netconsulting.com.ec.kfcpickup.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import netconsulting.com.ec.kfcpickup.Modelo.DetalleProducto;
import netconsulting.com.ec.kfcpickup.Modelo.Plu;
import netconsulting.com.ec.kfcpickup.R;

/*
 *  Adapter Areas por Restaurante
 * */
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context mCtx;
    private List<DetalleProducto> plus;
    private ProductoAdapter productoAdapter;

    public ProductoAdapter(Context mCtx, List<DetalleProducto> plus) {
        this.mCtx = mCtx;
        this.plus = plus;
        this.productoAdapter = this;
    }


    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_productos, null);

        return new ProductoViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final ProductoAdapter.ProductoViewHolder holder, int position) {

        final DetalleProducto plus1 = plus.get(position);

        holder.plu_producto.setText(plus1.getProducto());
        holder.plu_cantidad.setText(plus1.getCantidad());
        holder.plu_descripcion.setText(plus1.getDescripion());

    }

    @Override
    public int getItemCount() {

        return plus.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView plu_descripcion, plu_cantidad, plu_producto;


        private ProductoViewHolder(View itemView) {
            super(itemView);
            plu_producto = (TextView) itemView.findViewById(R.id.plu_producto);
            plu_descripcion = (TextView) itemView.findViewById(R.id.plu_descripcion);

            plu_cantidad = (TextView) itemView.findViewById(R.id.plu_cantidad);

        }
    }

}
