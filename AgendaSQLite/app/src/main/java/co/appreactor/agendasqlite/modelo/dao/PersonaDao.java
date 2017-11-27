package co.appreactor.agendasqlite.modelo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendasqlite.modelo.conexion.ConexionBd;
import co.appreactor.agendasqlite.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 23/11/17.
 */

public class PersonaDao extends ConexionBd {

    public PersonaDao(Context context) {
        super(context);
    }

    // metodo para insertar
    public void insertar(Persona entidad){
        SQLiteDatabase cnn = null;
        try{
            cnn = abrir();
            // Asignacion de valores a un nuevo ContentValues
            ContentValues valores = new ContentValues();
            valores.put("nombre",entidad.getNombre());
            valores.put("correo",entidad.getCorreo());
            valores.put("telefono",entidad.getTelefono());
            valores.put("estado",entidad.getEstado().toString());
            // registrar y obtener el valor de la llave primaria
            // del nuevo registro insertado
            Long id = cnn.insert("persona",null,valores);
            // asignar la llave primara al objeto recibido como parametro
            entidad.setId(id);
        } finally {
            cerrar(cnn);
        }
    }

    public void actualizar(Persona entidad){
        SQLiteDatabase cnn = null;
        try{
            cnn = abrir();
            // Asignacion de valores a un nuevo ContentValues
            ContentValues valores = new ContentValues();
            valores.put("nombre",entidad.getNombre());
            valores.put("correo",entidad.getCorreo());
            valores.put("telefono",entidad.getTelefono());
            valores.put("estado",entidad.getEstado().toString());
            // registrar y obtener el valor de la llave primaria
            // del nuevo registro insertado
            cnn.update(
                    "persona",
                    valores,
                    "id = ?",
                    new String[]{entidad.getId().toString()}
                    );
        } finally {
            cerrar(cnn);
        }
    }

    public List<Persona> consultar(){
        SQLiteDatabase cnn = null;
        List<Persona> listaPersonas = new ArrayList<>();
        String[] columnas = new String[]{"id","nombre","correo","telefono","estado"};
        try{
            cnn = abrir();
            // cursor que contendra los registros que cumplen con los parametros de
            // la consulta
            Cursor resultados = cnn.query(
                    "persona",
                    columnas,
                    "estado = ?",
                    new String[]{"true"},
                    null,
                    null,
                    null
            );
            // validar que el cursor contenga datos
            if (!resultados.moveToFirst()){
                return listaPersonas;
            }
            do{
                Persona gPersona = new Persona();
                gPersona.setId(
                        resultados.getLong(
                                resultados.getColumnIndex("id")
                        )
                );
                gPersona.setNombre(
                        resultados.getString(
                                resultados.getColumnIndex("nombre")
                        )
                );
                gPersona.setCorreo(
                        resultados.getString(
                                resultados.getColumnIndex("correo")
                        )
                );
                gPersona.setTelefono(
                        resultados.getString(
                                resultados.getColumnIndex("telefono")
                        )
                );
                gPersona.setEstado(
                        Boolean.parseBoolean(
                                resultados.getString(
                                        resultados.getColumnIndex("estado")
                                )
                        )
                );
                // asignar objeto de persona cargada a la lista
                listaPersonas.add(gPersona);
            } while (resultados.moveToNext());
        } finally {
            cerrar(cnn);
        }
        return listaPersonas;
    }
}
