package es.uco.pw.data.dao.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;




public class DAO {
    protected Connection getConection(){
        Connection conect = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            conect = DriverManager.getConnection(sqlProp.getProperty("servidor.url"), 
                                                 sqlProp.getProperty("servidor.user"), 
                                                 sqlProp.getProperty("servidor.password"));

        }catch(Exception e){
            System.out.println(e);
        }

        return conect;
    }

    
}

