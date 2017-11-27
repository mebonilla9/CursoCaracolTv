package co.appreactor.agendaandroid.modelo.servicio;

import android.util.Xml;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import co.appreactor.agendaandroid.modelo.entidades.Persona;
import co.appreactor.agendaandroid.negocio.utilidades.Manejador;

/**
 * Created by lord_nightmare on 7/11/17.
 */

public class ArchivoSax implements Archivo {

    private final String nombreArchivo = "/datos_sax.txt";

    public ArchivoSax(){
        File carpeta = new File(RUTA_ARCHIVO);
        if (!carpeta.exists()){
            carpeta.mkdir();
        }
    }

    @Override
    public void escribir(List<Persona> listaPersonas) {
        try{
            XmlSerializer serializador = Xml.newSerializer();
            // Generar ruta de salida a la información del
            // serializador
            serializador.setOutput(new FileOutputStream(
                    new File(RUTA_ARCHIVO+nombreArchivo)
            ),"utf-8");

            // Inicio de la escritura del archivo
            serializador.startDocument("utf-8",true);
            serializador.startTag("","lista");
            for (Persona iterador : listaPersonas){
                // crear el nodo persona
                serializador.startTag("","persona");

                // Etiqueta atributo nombre
                serializador.startTag("","nombre");
                serializador.text(iterador.getNombre());
                serializador.endTag("","nombre");

                // Etiqueta atributo correo
                serializador.startTag("","correo");
                serializador.text(iterador.getCorreo());
                serializador.endTag("","correo");

                // Etiqueta atributo telefono
                serializador.startTag("","telefono");
                serializador.text(iterador.getTelefono());
                serializador.endTag("","telefono");

                // cierre de la etiqueta persona
                serializador.endTag("","persona");
            }

            // cierre de la etiqueta global del documento
            serializador.endTag("","lista");
            // cierre del documento
            serializador.endDocument();

        } catch(IOException e){

        }
    }

    @Override
    public List<Persona> leer() {
        List<Persona> listaPersonas = new ArrayList<>();
        try{
            SAXParserFactory fabrica = SAXParserFactory.newInstance();
            SAXParser convertidor = fabrica.newSAXParser();

            // instancia del manejador
            Manejador manejador = new Manejador();

            // Invocar al manejador usando el metodo parse del convertidor
            convertidor.parse(
                    new File(RUTA_ARCHIVO+nombreArchivo),
                    manejador
            );

            // obtener la lista de personas generada por el manejador
            listaPersonas = manejador.getListaPersonas();
        } catch (ParserConfigurationException | SAXException | IOException e){
            //e instanceof SAXException
            e.printStackTrace();
        }
        return listaPersonas;
    }
}
