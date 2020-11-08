package data.dao.Anuncios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import business.Anuncios.AnuncioGeneral;
import business.Anuncios.AnuncioTematico;
import business.Usuario.Contacto;
import data.dao.common.DAO;

public class AnunciosDAO extends DAO{
   
   
    public int GetMaxID(){
        int maxID=-1;
        Statement stmt=null;
        
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            
            stmt=conect.createStatement();
            ResultSet rs=stmt.executeQuery(sqlProp.getProperty("getMaxID.anuncios"));
            while(rs.next()){
                maxID=rs.getInt(1);
            }

            if(stmt != null)
                stmt.close();
            
        }catch(Exception e){
            System.out.println(e);
        }


        return maxID;
    }

    public int InsertarAnuncioTematico(AnuncioTematico anuncio){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is); 
            PreparedStatement ps= conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioTematico"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2, anuncio.getTituloAnuncio());
            ps.setString(3, anuncio.getCuerpoAnuncio());
            // java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            java.sql.Date fechaPublicacion=java.sql.Date.valueOf(anuncio.getFechaPublicacion().toString());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5, anuncio.getPropietario().getEmail());
            ps.setString(6, anuncio.getEsadoAnuncio().toString());
            
            String intereses="";            
            for(String interes : anuncio.getTemasAnuncio()){
                interes+=interes.toLowerCase()+",";
            }
            if(anuncio.getTemasAnuncio().size()>0){
                intereses=intereses.substring(0,intereses.length()-1);
            }
            ps.setString(7, intereses);

            status=ps.executeUpdate();

            for(Contacto contact:anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, anuncio.getId());
                psDestinatario.setString(2, contact.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return status;
    }

    public int InsertarAnuncioGeneral(AnuncioGeneral anuncio){
        int status=0;

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioGeneral"));
            //TODO terminar 
        }catch(Exception e){
            System.out.println(e);
        }

        return status;
    }
}