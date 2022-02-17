package netconsulting.com.ec.kfcpickup.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import netconsulting.com.ec.kfcpickup.Contract.ConfiguracionContract;
import netconsulting.com.ec.kfcpickup.Modelo.Configuracion;


public class ConfiguracionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Configuracion.db";
    final String CREAR_TABLA_PDA = "CREATE TABLE " + ConfiguracionContract.ConfiguracionEntry.TABLE_NAME + "("
            + ConfiguracionContract.ConfiguracionEntry.ID_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ConfiguracionContract.ConfiguracionEntry.IP_CONSULTA + " TEXT NOT NULL, "
            + ConfiguracionContract.ConfiguracionEntry.IP_IMPRESION + " TEXT NOT NULL, "
            + ConfiguracionContract.ConfiguracionEntry.CODIGO_TIENDA + " TEXT , "
            + ConfiguracionContract.ConfiguracionEntry.IP_TRADE + " TEXT NOT NULL, "
            + "UNIQUE (" + ConfiguracionContract.ConfiguracionEntry.ID_CODIGO + "))";


    public ConfiguracionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAR_TABLA_PDA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE  IF EXISTS Configuracion");
        db.execSQL(CREAR_TABLA_PDA);

    }

    public long grabarConfiguracion(Configuracion config) {
        eliminarTodo();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ConfiguracionContract.ConfiguracionEntry.TABLE_NAME,
                null,
                config.toContentValues());
    }

    public void eliminarTodo() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(ConfiguracionContract.ConfiguracionEntry.TABLE_NAME, null, null);
    }

    public int contarUsuarios() {
        int cont = consultarTodos().getCount();
        return cont;
    }


    public Cursor consultarTodos() {

        return getReadableDatabase()
                .query(
                        ConfiguracionContract.ConfiguracionEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public ArrayList<Configuracion> consultarTodosValores() {
        ArrayList<Configuracion> lista = new ArrayList<Configuracion>();
        Cursor fila = consultarTodos();
        while (fila.moveToNext()) {
            Configuracion user = new Configuracion();
            user.setId_codigo(fila.getString(fila.getColumnIndex(ConfiguracionContract.ConfiguracionEntry.ID_CODIGO)));
            user.setIp_consulta(fila.getString(fila.getColumnIndex(ConfiguracionContract.ConfiguracionEntry.IP_CONSULTA)));
            user.setTrade(fila.getString(fila.getColumnIndex(ConfiguracionContract.ConfiguracionEntry.IP_TRADE)));
            user.setIp_impresion(fila.getString(fila.getColumnIndex(ConfiguracionContract.ConfiguracionEntry.IP_IMPRESION)));
            user.setCod_tienda(fila.getString(fila.getColumnIndex(ConfiguracionContract.ConfiguracionEntry.CODIGO_TIENDA)));

            lista.add(user);
        }
        return lista;
    }

}
