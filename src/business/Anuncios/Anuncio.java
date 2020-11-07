package business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import business.Usuario.Contacto;

public abstract class Anuncio {
    protected int id;
    protected String tipoAnuncio;
    protected String titulo;
    protected String cuerpo;
    protected java.util.Date fecha_publicacion;
    protected Contacto propietario;
    protected EstadosAnuncio estadoAnuncio;
    protected ArrayList<Contacto> destinatarios;

    public Anuncio(int id, String tipoAnuncio, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadosAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        this.id = id;
        this.tipoAnuncio = tipoAnuncio;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fecha_publicacion = fecha_publicacion;
        this.propietario = propietario;
        this.estadoAnuncio = estadoAnuncio;
        this.destinatarios = destinatarios;
    }

    public int getId(){
        return this.id;
    }

    public String getTipoAnuncio(){
        return this.tipoAnuncio;
    }
    
    public void setTipoAnuncio(String tipoAnuncio){
        //TODO
    
    }

    public String getTituloAnuncio(){
        return this.titulo;
    }

    public void setTituloAnuncio(String titulo){
        this.titulo=titulo;
    }

    
}
