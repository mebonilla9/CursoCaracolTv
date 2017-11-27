package co.appreactor.eventos.persistencia.servicio;

import android.os.Handler;
import android.os.Message;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import co.appreactor.eventos.persistencia.dto.RespuestaDTO;

/**
 * Created by lord_nightmare on 8/11/17.
 */

public final class ServicioHttp {

    private static final String URL_SERVIDOR = "http://appreactor.co:80/eventos";

    public static void invocar(final Object parametros,
                               final String nombreParametros,
                               final String metodo,
                               final Type tipoRespuesta,
                               final Handler puente,
                               final String rutaServidor){
        // Hilo generador de la peticion
        Thread hiloServidor = new Thread(new Runnable() {
            @Override
            public void run() {
                // aqui va la logica para conectarnos al servidor

                // Instancia del objeto que representa el mensaje que almacena
                // el resultado de la peticion para enviarla al fragmento a
                // traves del Handler
                Message mensaje = new Message();
                // Objeto que representa el formato de la respuesta del servidor
                RespuestaDTO respuesta = new RespuestaDTO();
                // convertir los parametros a un json serializado
                String data = new GsonBuilder()
                        .setDateFormat("MMM d, yyyy HH:mm:ss a")
                        .create()
                        .toJson(parametros);
                try{
                    // creacion de los objetos de clases que permiten la
                    // comunicacion con el servidor
                    URL ruta = new URL(URL_SERVIDOR+rutaServidor);
                    // Objeto que representa la conexion con el servidor
                    HttpURLConnection conexion = (HttpURLConnection) ruta.openConnection();
                    // asignar el metodo del protocolo http de la peticion
                    conexion.setRequestMethod(metodo);
                    // asignar la propiedad a la conexion para que reciba objetos json
                    conexion.setRequestProperty("Content-Type","application/json");
                    // facultar a la conexion para que pueda enviar parametros
                    conexion.setDoOutput(true);
                    // facultar a la conexion para que pueda recibir valores en la respuesta
                    conexion.setDoInput(true);

                    // Enviar parametros de salida al servidor
                    OutputStreamWriter salida = new OutputStreamWriter(
                            conexion.getOutputStream()
                    );

                    // dilema de metodo de envio de la peticion
                    if (metodo.equals("GET")){
                        salida.write(nombreParametros+"="+parametros);
                    } else {
                        salida.write(data);
                    }
                    salida.flush();
                    salida.close();

                    // procesar el resultado de la peticion
                    // Abrir el canal de entrada para procesar el response
                    BufferedReader entrada = new BufferedReader(
                            new InputStreamReader(
                                    conexion.getInputStream()
                            )
                    );
                    // IMPOTANTE leer la primera linea de la respuesta y luego verificar
                    // su integridad

                    String linea = entrada.readLine();
                    StringBuilder contenido = new StringBuilder();
                    while (linea != null){
                        contenido.append(linea);
                        linea = entrada.readLine();
                    }
                    entrada.close();

                    // Transformar la respuesta en objetos de clase java
                    respuesta = new GsonBuilder()
                            .setDateFormat("MMM d, yyyy HH:mm:ss a")
                            .create()
                            .fromJson(contenido.toString(),tipoRespuesta);
                    respuesta.setRuta(rutaServidor);

                    // asignar la respuesta al objeto de mensaje
                    mensaje.obj = respuesta;
                    // desconexion del servidor
                    conexion.disconnect();
                } catch(IOException e){
                    e.printStackTrace();
                    respuesta.setCodigo(-1);
                    respuesta.setMensaje("Error fatal: "+e.getMessage());
                }
                // Externalizar el mensaje del hilo y enviarlo a la actividad o fragmento que realiza
                // la peticion
                puente.sendMessage(mensaje);
            }
        });
        hiloServidor.start();
    }
}
