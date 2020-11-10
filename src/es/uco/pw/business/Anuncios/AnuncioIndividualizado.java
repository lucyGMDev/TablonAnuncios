package es.uco.pw.business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import es.uco.pw.business.Usuario.Contacto;

public class AnuncioIndividualizado extends Anuncio {

    public AnuncioIndividualizado(int id, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        super(id, TipoAnuncio.Individualizado, titulo, cuerpo, fecha_publicacion, propietario, estadoAnuncio, destinatarios);
        
    }
    
}
