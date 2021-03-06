package es.uco.pw.business.Anuncios;



import es.uco.pw.business.Usuario.Contacto;

public abstract class GeneradorAnunciosAbstracto {
    public abstract AnuncioTematico CrearAnuncioTematico(Contacto propietario);

    public abstract AnuncioGeneral CrearAnuncioGeneral(Contacto propietario);    
    
    public abstract AnuncioFlash CrearAnuncioFlash(Contacto propietario); 

    public abstract AnuncioIndividualizado CrearAnuncioIndividualizado(Contacto propietario); 
}
