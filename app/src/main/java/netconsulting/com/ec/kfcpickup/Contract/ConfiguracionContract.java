package netconsulting.com.ec.kfcpickup.Contract;

import android.provider.BaseColumns;

/*
 *  Entidad Usuario
 * */
public class ConfiguracionContract {

    public static abstract class ConfiguracionEntry implements BaseColumns {
        public static final String TABLE_NAME = "Configuracion";

        public static final String ID_CODIGO = "id_codigo";
        public static final String IP_CONSULTA = "ip_consulta";
        public static final String IP_IMPRESION = "ip_impresion";
        public static final String CODIGO_TIENDA = "cod_tienda";
        public static final String IP_TRADE = "trade";
    }

}
