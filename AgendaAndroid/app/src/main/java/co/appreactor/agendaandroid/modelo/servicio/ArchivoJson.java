package co.appreactor.agendaandroid.modelo.servicio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 2/11/17.
 */

public class ArchivoJson implements Archivo {

    private final String nombreArchivo = "/datos_json.txt";

    public ArchivoJson(){
        File carpeta = new File(RUTA_ARCHIVO);
        if (!carpeta.exists()){
            carpeta.mkdir();
        }
    }

    @Override
    public void escribir(List<Persona> listaPersonas) {
        try {
            PrintStream escritor = new PrintStream(RUTA_ARCHIVO+nombreArchivo);
            // Conversion de la lista de objetos a un documento JSON
            escritor.print(new Gson().toJson(listaPersonas));
            escritor.flush();
            escritor.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Persona> leer() {
        List<Persona> listaPersonas = new ArrayList<>();
        try{
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(RUTA_ARCHIVO+nombreArchivo)
                    )
            );
            StringBuilder contenido = new StringBuilder();
            while (lector.ready()){
                contenido.append(lector.readLine());
            }
            // Aplicacion de reflexion
            Type tipoDato = new TypeToken<List<Persona>>(){}.getType();
            listaPersonas = new Gson().fromJson(contenido.toString(),tipoDato);
        } catch(IOException e){
            e.printStackTrace();
        }
        return listaPersonas;
    }
}
