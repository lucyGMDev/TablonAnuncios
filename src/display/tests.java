package display;

import business.Anuncios.GeneradorAnuncios;
import business.Usuario.GestorContactos;

public class tests {
    public static void main(String[]args){
        GeneradorAnuncios generadorAnuncios=new GeneradorAnuncios();
        
        generadorAnuncios.CrearAnuncioTematico(GestorContactos.GetInstance().GetContactById("i92izded@uco.es"));
        
        return;
    }
}
