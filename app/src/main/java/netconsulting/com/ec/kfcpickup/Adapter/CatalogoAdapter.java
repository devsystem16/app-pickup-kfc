package netconsulting.com.ec.kfcpickup.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import netconsulting.com.ec.kfcpickup.Modelo.Catalogo;
import netconsulting.com.ec.kfcpickup.R;

public class CatalogoAdapter extends ArrayAdapter<Catalogo> {

    private Context context;
    private ArrayList<Catalogo> Catalogo;

    public CatalogoAdapter(Context mCtx, ArrayList<Catalogo> list, int spin) {
        super(mCtx, spin, list);
        this.context = mCtx;
        this.Catalogo = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // Ordinary


        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) { // This view starts when we click the
        // spinner.
        return initView(position, convertView, parent);
    }

    public View initView(int position, View convertView, ViewGroup parent) {


        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(Catalogo.get(position).getDescripcion());

        return label;
    }

}