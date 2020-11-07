package display;

import business.Usuario.GestorContactos;

public class tests {
    public static void main(String[]args){
        GestorContactos.GetInstance().GetContactById("i92izded@uco.es");
        return;
    }
}
