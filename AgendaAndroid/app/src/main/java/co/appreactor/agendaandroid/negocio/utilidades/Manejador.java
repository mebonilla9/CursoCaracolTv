package co.appreactor.agendaandroid.negocio.utilidades;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 7/11/17.
 */

public class Manejador extends DefaultHandler {

    private List<Persona> listaPersonas;
    private Persona persona;
    private String contenido;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName){
            case "lista":
                listaPersonas = new ArrayList<>();
                break;
            case "persona":
                persona = new Persona();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName){
            case "nombre":
                persona.setNombre(contenido);
                break;
            case "correo":
                persona.setCorreo(contenido);
                break;
            case "telefono":
                persona.setTelefono(contenido);
                break;
            case "persona":
                listaPersonas.add(persona);
                persona = null;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        contenido = new String(ch,start,length);
    }

    public List<Persona> getListaPersonas(){
        return listaPersonas;
    }
}
