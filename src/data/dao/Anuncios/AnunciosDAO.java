package data.dao.Anuncios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


import data.dao.common.DAO;

public class AnunciosDAO extends DAO{
   
   
    public int GetMaxID(){
        int maxID=0;
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
}
