package es.uco.pw.business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import es.uco.pw.business.Usuario.Contacto;


public abstract class Anuncio {
    protected int id;
    protected TipoAnuncio tipoAnuncio;
    protected String titulo;
    protected String cuerpo;
    protected Date fecha_publicacion;
    protected Contacto propietario;
    protected EstadoAnuncio estadoAnuncio;
    protected ArrayList<Contacto> destinatarios;

    public Anuncio(int id, TipoAnuncio tipoAnuncio, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        this.id = id;
        this.tipoAnuncio = tipoAnuncio;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fecha_publicacion = fecha_publicacion;
        this.propietario = propietario;
        this.estadoAnuncio = estadoAnuncio;
        this.destinatarios = destinatarios;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public TipoAnuncio getTipoAnuncio(){
        return this.tipoAnuncio;
    }
    
   

    public String getTituloAnuncio(){
        return this.titulo;
    }

    public void setTituloAnuncio(String titulo){
        this.titulo=titulo;
    }

    public String getCuerpoAnuncio(){
        return this.cuerpo;
    }

    public void setCuerpoAnuncio(String cuerpo){
        this.cuerpo=cuerpo;
    }

    public Date getFechaPublicacion(){
        return this.fecha_publicacion;
    }
    
    public void setFechaPublicacion(Date fechaPublicacion){
        this.fecha_publicacion=fechaPublicacion;
    }

    public Contacto getPropietario(){
        return this.propietario;
    }

    public void setPropietario(Contacto propietario){
        this.propietario=propietario;
    }

    public EstadoAnuncio getEsadoAnuncio(){
        return this.estadoAnuncio;
    }

    public void setEstadoAnuncio(EstadoAnuncio estadoAnuncio){
        this.estadoAnuncio=estadoAnuncio;
    }

    public ArrayList<Contacto> getDestinatarios(){
        return this.destinatarios;
    }

    public void setDestinatarios(ArrayList<Contacto> destinatarios){
        this.destinatarios=destinatarios;
    }


    
}
