package business.Anuncios;



import business.Usuario.Contacto;

public abstract class GeneradorAnunciosAbstracto {
    public abstract AnuncioTematico CrearAnuncioTematico(Contacto propietario);

    public abstract AnuncioGeneral CrearAnuncioGeneral(Contacto propietario);    
    
    public abstract AnuncioFlash CrearAnuncioFlash(Contacto propietario); 
}
