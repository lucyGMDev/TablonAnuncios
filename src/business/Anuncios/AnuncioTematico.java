package business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import business.Usuario.Contacto;

public class AnuncioTematico extends Anuncio {

    private ArrayList<String> temas;

    public AnuncioTematico(int id, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios,ArrayList<String> temas) {
        
        super(id, TipoAnuncio.Tematico, titulo, cuerpo, fecha_publicacion, propietario, estadoAnuncio, destinatarios);
        this.temas=temas;
    }   
    
    public ArrayList<String> getTemasAnuncio(){
        return this.temas;
    }

    
}
