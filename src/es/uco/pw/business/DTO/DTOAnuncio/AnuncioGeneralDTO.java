package es.uco.pw.business.DTO.DTOAnuncio;

import java.io.Serializable;
import java.util.Date;

import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Usuario.Contacto;

public class AnuncioGeneralDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String titulo;
    private String cuerpo;
    private Date fechaPublicacion; 
    private Contacto propietario;
    private EstadoAnuncio estadoAnuncio;



}
