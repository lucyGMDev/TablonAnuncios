package es.uco.pw.display;

import es.uco.pw.business.Anuncios.GeneradorAnuncios;
import es.uco.pw.business.Usuario.GestorContactos;


public class tests {
    public static void main(String[]args){

        GeneradorAnuncios generadorAnuncios= new GeneradorAnuncios();
        generadorAnuncios.CrearAnuncioTematico(GestorContactos.GetInstance().GetContactById("i92izded@uco.es"));

        return;
    }
}
