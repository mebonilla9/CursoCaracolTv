package co.appreactor.eventos.negocio.fragmentos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.negocio.adaptadores.AdaptadorSala;
import co.appreactor.eventos.persistencia.dto.RespuestaDTO;
import co.appreactor.eventos.persistencia.entidades.Sala;
import co.appreactor.eventos.persistencia.servicio.ServicioHttp;

/**
 * Created by lord_nightmare on 8/11/17.
 */

public class ListarSalaFragment extends Fragment implements Handler.Callback {

    private RecyclerView rvSala;
    private List<Sala> listaSalas;
    private AdaptadorSala adaptador;

    // refencia a un Handler para recibir el resultado de una peticion por HTTP
    private Handler puente;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_listar_sala,container,false);
        this.rvSala = (RecyclerView) vista.findViewById(R.id.rvSala);
        puente = new Handler(this);
        // metodo que dispara la peticion al servidor para obtener la lista de
        // salas disponibles
        consultarSalas();
        return vista;
    }

    private void consultarSalas() {
        Type tipoRespuesta = new TypeToken<RespuestaDTO<List<Sala>>>(){}.getType();
        ServicioHttp.invocar(
                "",
                null,
                "POST",
                tipoRespuesta,
                puente,
                "/sala/consultar"
        );
    }

    @Override
    public boolean handleMessage(Message message) {
        // extraer el objeto de respuesta del mensaje recibido
        RespuestaDTO<List<Sala>> respuesta = (RespuestaDTO<List<Sala>>) message.obj;
        if (respuesta.getCodigo() != 1){
            Toast.makeText(getActivity(), respuesta.getMensaje(), Toast.LENGTH_LONG).show();
            return false;
        }

        // obtener en el atributo listaSalas el resultado de los datos del objeto
        // respuesta
        listaSalas = respuesta.getDatos();

        // Construir el adaptador para el recyclerView con base al atributo
        // listaSalas que funcionara como el dataSet
        adaptador = new AdaptadorSala(listaSalas,getActivity());

        // Asignar el LayoutManager para determinar la estructura en que sera visualizada
        // la lista de salas
        rvSala.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Asignar al recyclerView el adaptador
        rvSala.setAdapter(adaptador);
        return false;
    }
}
