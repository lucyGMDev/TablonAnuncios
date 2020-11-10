package es.uco.pw.display;

import es.uco.pw.business.Anuncios.GeneradorAnuncios;
import es.uco.pw.business.Usuario.GestorContactos;

public class tests {
    public static void main(String[]args){
        GeneradorAnuncios generadorAnuncios=new GeneradorAnuncios();
        
        
        

        int i=generadorAnuncios.CrearAnuncioIndividualizado(GestorContactos.GetInstance().GetContactById("i92izded@uco.es")).getId();
        System.out.println("El id es "+i);
        return;
    }
}
