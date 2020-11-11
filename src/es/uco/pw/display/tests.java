package es.uco.pw.display;


import es.uco.pw.business.Anuncios.GestorAnuncios;
import es.uco.pw.business.Usuario.GestorContactos;


public class tests {
    public static void main(String[]args){

        GestorAnuncios gAnuncions = new GestorAnuncios();
        GestorContactos gContactos = new GestorContactos();
        gAnuncions.CrearAnuncio(gContactos.GetContactById("i92izded@uco.es"));
        return;
    }
}
