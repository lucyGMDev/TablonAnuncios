package es.uco.pw.business.DTO.DTOAnuncio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Anuncios.TipoAnuncio;

public class AnuncioDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private TipoAnuncio tipo;
    private String titulo;
    private String cuerpo;
    private Date fechaPublicacion;
    private Date fechaFin;
    private String emailPropietario;
    private EstadoAnuncio estadoAnuncio;
    private ArrayList<String> temas;
    private ArrayList<String> destinatarios;
    public AnuncioDTO(int id, TipoAnuncio tipo, String titulo, String cuerpo, Date fechaPublicacion, Date fechaFin,
            String emailPropietario, EstadoAnuncio estadoAnuncio, ArrayList<String> temas,ArrayList<String>destinatarios) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFin = fechaFin;
        this.emailPropietario = emailPropietario;
        this.estadoAnuncio = estadoAnuncio;
        this.temas = temas;
        this.destinatarios=destinatarios;
    }

    public int getId() {
        return id;
    }

    public TipoAnuncio getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEmailPropietario() {
        return emailPropietario;
    }

    public void setEmailPropietario(String emailPropietario) {
        this.emailPropietario = emailPropietario;
    }

    public EstadoAnuncio getEstadoAnuncio() {
        return estadoAnuncio;
    }

    public void setEstadoAnuncio(EstadoAnuncio estadoAnuncio) {
        this.estadoAnuncio = estadoAnuncio;
    }

    public ArrayList<String> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<String> temas) {
        this.temas = temas;
    }

    @Override
    public String toString() {
        String cadena="";
        cadena+="Anuncio con id "+this.id+" de tipo "+this.tipo.toString()+"\n";
        cadena+="El titulo del anuncio es: "+this.titulo+"\n";
        cadena+="El cuerpo del anuncio es:\n";
        cadena+=this.cuerpo+"\n";
        cadena+="Fecha publicacion: "+this.fechaPublicacion.toString()+"\n";
        if(this.tipo.equals(TipoAnuncio.Flash)){
            cadena+="Fecha caducidad: "+this.getFechaFin()+"\n";
        }
        cadena+="El email del propietario del anuncio es: "+this.emailPropietario+"\n";
        cadena+="Este estado esta actualmente en: "+this.estadoAnuncio.toString()+"\n";
        if(this.tipo.equals(TipoAnuncio.Tematico)){
            cadena+="Los temas del anunco son: "+"\n";
            for(String tema:this.temas){
                cadena+="\t"+tema+"\n";
            }
        }
        if(!this.destinatarios.isEmpty()){
            cadena+="Los destinatarios del anunco son: "+"\n";
            for(String destinatarios:this.destinatarios){
                cadena+="\t"+destinatarios+"\n";
            }
        }
        return cadena;
    }

    public ArrayList<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(ArrayList<String> destinatarios) {
        this.destinatarios = destinatarios;
    }
    
    
    

}
