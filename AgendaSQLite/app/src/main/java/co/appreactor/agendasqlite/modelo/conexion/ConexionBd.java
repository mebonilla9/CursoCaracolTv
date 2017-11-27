package co.appreactor.agendasqlite.modelo.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lord_nightmare on 23/11/17.
 */

public class ConexionBd extends SQLiteOpenHelper {

    // Nombre oficial de la base de datos
    private final static String NOMBRE_BASE_DATOS = "agenda";

    // El numero de la version de SQLite que vamos a usar;
    private final static int VERSION = 3;

    public ConexionBd(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
    }

    /**
     * Metodo que define la creacion de las DDL's de la base de datos
     * @param db representa la base de datos actual que la aplicación va
     *           a manejar
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE persona (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "estado TEXT NOT NULL);";
        // Cargar la DDL o DDL's a la base de datos
        db.execSQL(sql);
    }

    /**
     * Metodo que se utiliza para actualizar la version sqlite que utiliza la
     * aplicacion
     *
     * @param db representa a la base de datos de la aplicacion;
     * @param oldVersion representa la version actual de la base de datos
     * @param newVersion representa la nueva version de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Salvar la informacion de las tablas de la base de datos
        // Ejecutar los DROP's de las tablas
        String sql = "DROP TABLE persona";
        db.execSQL(sql);
        // modificar la version
        db.setVersion(newVersion);
        onCreate(db);
        // Tarea de recuperacion de datos
    }

    protected SQLiteDatabase abrir(){
        // retornar la base de datos modo lectura
        //return getReadableDatabase();
        // retornar la base de datos en modo lectura / escritura
        return getWritableDatabase();
    }

    protected void cerrar(SQLiteDatabase db){
        if (db != null){
            db.close();
        }
    }
}
