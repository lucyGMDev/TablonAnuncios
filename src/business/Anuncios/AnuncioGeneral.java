package business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import business.Usuario.Contacto;

public class AnuncioGeneral extends Anuncio {

    public AnuncioGeneral(int id, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        super(id, TipoAnuncio.General, titulo, cuerpo, fecha_publicacion, propietario, estadoAnuncio, destinatarios);
        
    }
    
}
