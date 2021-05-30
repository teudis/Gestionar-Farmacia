/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Annie&Teudis
 */
public class DbConexion {
    
     private static Connection con=null;
    
      public static Connection getConnection(){
      try{
         if( con == null ){
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:medicamentos.db");
            System.out.println("Opened database successfully");
         }
     }
        catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);     
       }
      
      return con;      
   }     
}
