package display;

import business.Anuncios.GeneradorAnuncios;
import business.Usuario.GestorContactos;

public class tests {
    public static void main(String[]args){
        GeneradorAnuncios generadorAnuncios=new GeneradorAnuncios();
        
        int i=generadorAnuncios.CrearAnuncioGeneral(GestorContactos.GetInstance().GetContactById("i92izded@uco.es")).getId();
        System.out.println("El id es "+i);
        return;
    }
}
