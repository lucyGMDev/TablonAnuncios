package es.uco.pw.data.dao.Anuncios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import es.uco.pw.business.Anuncios.AnuncioFlash;
import es.uco.pw.business.Anuncios.AnuncioIndividualizado;
import es.uco.pw.business.Anuncios.AnuncioTematico;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioFlashDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioGeneralDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.data.dao.common.DAO;

public class AnuncioDAO extends DAO{
   //TODO modificar llamadas a las insercciones para usar DTO en vez de clases anuncios
   
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
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
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

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public int InsertarAnuncioGeneral(AnuncioGeneralDTO anuncio){
        int status=0;

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioGeneral"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2,anuncio.getTitulo());
            ps.setString(3,anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5,anuncio.getPropietario().getEmail());
            ps.setString(6,anuncio.getEstadoAnuncio().toString());
            status=ps.executeUpdate();

            
            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            System.out.println(e);
        }

        return status;
    }

    public int InsertarAnuncioFlash(AnuncioFlashDTO anuncio){
        int status=0;

        try{
            
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioFlash"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2, anuncio.getTitulo());
            ps.setString(3, anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            java.sql.Date fecha_fin=new java.sql.Date(anuncio.getFechaFin().getTime());
            ps.setDate(5, fecha_fin);
            ps.setString(6, anuncio.getPropietario().getEmail());
            ps.setString(7, anuncio.getEstadoAnuncio().toString());
            
            status=ps.executeUpdate();

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }


    public int InsertarAnuncioIndividualizado(AnuncioIndividualizado anuncio){
        int status=0;

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioIndividualizado"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2,anuncio.getTituloAnuncio());
            ps.setString(3,anuncio.getCuerpoAnuncio());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5,anuncio.getPropietario().getEmail());
            ps.setString(6,anuncio.getEsadoAnuncio().toString());
            status=ps.executeUpdate();

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            System.out.println(e);
        }

        return status;
    }
}