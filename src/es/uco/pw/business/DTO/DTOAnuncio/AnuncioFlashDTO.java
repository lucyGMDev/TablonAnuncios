package es.uco.pw.business.DTO.DTOAnuncio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Anuncios.TipoAnuncio;
import es.uco.pw.business.Usuario.Contacto;

public class AnuncioFlashDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private TipoAnuncio tipoAnuncio;
    private String titulo;
    private String cuerpo;
    private Date fechaPublicacion;
    private Date fechaFin;
    private Contacto propietario;
    private EstadoAnuncio estadoAnuncio;
    private ArrayList<Contacto> destinatarios;

    public TipoAnuncio getTipoAnuncio() {
        return tipoAnuncio;
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

    public Contacto getPropietario() {
        return propietario;
    }

    public void setPropietario(Contacto propietario) {
        this.propietario = propietario;
    }

    public EstadoAnuncio getEstadoAnuncio() {
        return estadoAnuncio;
    }

    public void setEstadoAnuncio(EstadoAnuncio estadoAnuncio) {
        this.estadoAnuncio = estadoAnuncio;
    }

    public ArrayList<Contacto> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(ArrayList<Contacto> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public AnuncioFlashDTO(String titulo, String cuerpo, Date fechaPublicacion, Date fechaFin, Contacto propietario,
            EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        this.tipoAnuncio = TipoAnuncio.Flash;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFin = fechaFin;
        this.propietario = propietario;
        this.estadoAnuncio = estadoAnuncio;
        this.destinatarios = destinatarios;
    }
}
