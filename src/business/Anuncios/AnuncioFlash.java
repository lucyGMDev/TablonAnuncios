package business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import business.Usuario.Contacto;



public class AnuncioFlash extends Anuncio {
    private Date fecha_fin;

    public AnuncioFlash(int id, String titulo, String cuerpo, Date fecha_publicacion,Date fecha_fin,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        super(id, TipoAnuncio.Flash, titulo, cuerpo, fecha_publicacion, propietario, estadoAnuncio, destinatarios);
        this.fecha_fin=fecha_fin;
    }

    public void setFechaFin(Date fecha_fin){
        this.fecha_fin=fecha_fin;
    }

    public Date getFechaFin(){
        return this.fecha_fin;
    }
    
}
