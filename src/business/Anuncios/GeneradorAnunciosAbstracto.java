package business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import business.Usuario.Contacto;

public abstract class GeneradorAnunciosAbstracto {
    public abstract AnuncioTematico crearAnuncioTematico(int id, String titulo, String cuerpo, Date fecha_publicacion,
    Contacto propietario, EstadosAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios,ArrayList<String> temas);
}
