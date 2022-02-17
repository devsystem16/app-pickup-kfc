package netconsulting.com.ec.kfcpickup.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import netconsulting.com.ec.kfcpickup.fragment.DetallePedido;
import netconsulting.com.ec.kfcpickup.fragment.ResumenPedido;

public class PagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;
    String json;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, String joson) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
        this.json = joson;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DetallePedido tab1 = DetallePedido.newInstance(this.json, null);
                return tab1;
            case 1:
                ResumenPedido tab2 = ResumenPedido.newInstance(this.json, null);
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}