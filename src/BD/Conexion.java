/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Annie&Teudis
 */
public class Conexion {
    
   // Connection c = null;
    Statement stmt = null;

    public Conexion() {
        
        try {
      //Class.forName("org.sqlite.JDBC");
      //c = DriverManager.getConnection("jdbc:sqlite:medicamentos.db");
      Connection c = DbConexion.getConnection();
      stmt = c.createStatement();
     
     String sql = "CREATE TABLE IF NOT EXISTS config_cod_provincia " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +                   
                   "nombre varchar(50)  NOT NULL) "  ;
      stmt.executeUpdate(sql);  
      sql = "CREATE TABLE IF NOT EXISTS config_cod_asic " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +
                   " provincia_id         int(8), " + 
                   "nombre varchar(50)  NOT NULL, "             +                
                   "FOREIGN KEY (provincia_id) REFERENCES config_cod_provincia (id) ON DELETE RESTRICT ON UPDATE NO ACTION)";
      stmt.executeUpdate(sql);  
      
       sql = "CREATE TABLE IF NOT EXISTS config_cod_farmacia " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +
                   "provincia_id         int(8), " + 
                   "asic_id int(8) UNIQUE ," + 
                   "nombre varchar(50)  NOT NULL, "             + 
                   "FOREIGN KEY (asic_id) REFERENCES config_cod_asic (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (provincia_id) REFERENCES config_cod_provincia (id) ON DELETE RESTRICT ON UPDATE NO ACTION)";
      stmt.executeUpdate(sql);  
      
       sql = "CREATE TABLE IF NOT EXISTS config_cod_unidad_medida " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +                    
                   "nombre varchar(50)  NOT NULL) "             ;
                                                  
                  
                 
      stmt.executeUpdate(sql);  
      
       sql = "CREATE TABLE IF NOT EXISTS config_cod_tipo_centro " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +                    
                   "nombre varchar(50)  NOT NULL, "             +
                   " descripcion    varchar(50)     NOT NULL, " +  
                   "sigla varchar(50) DEFAULT NULL) "   ;
      stmt.executeUpdate(sql);
      
      sql = "CREATE TABLE IF NOT EXISTS config_cod_servicio " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +                    
                   "nombre varchar(50)  NOT NULL, "             +
                   " descripcion    varchar(50)     NOT NULL, " +  
                   "sigla varchar(50) DEFAULT NULL) ";
      stmt.executeUpdate(sql);
      
        sql = "CREATE TABLE IF NOT EXISTS config_tipo_centro_servicio " +
                   "(tipo_centro_id             int(8)          NOT NULL  ," +                    
                   " servicio_id                int(8)          NOT NULL, "     +
                   "FOREIGN KEY (tipo_centro_id) REFERENCES config_cod_tipo_centro (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION)";
      stmt.executeUpdate(sql);
        sql = "CREATE TABLE IF NOT EXISTS config_cod_producto " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," + 
                   "unidad_medida_id   int(8)          NOT NULL  ," + 
                   "codigo varchar(30)  NOT NULL, "            +
                   "factor_conversion int(8)  , "             +
                   " descripcion    varchar(50)     NOT NULL, " + 
                   "oficial_id   int(8)           ," + 
                   "unidad_medida_almacen_id    int(8)           ," +  
                   "FOREIGN KEY (oficial_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (unidad_medida_id) REFERENCES config_cod_unidad_medida (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (unidad_medida_almacen_id) REFERENCES config_cod_unidad_medida (id) ON DELETE RESTRICT ON UPDATE NO ACTION)"                 ;
      stmt.executeUpdate(sql); 
          sql = "CREATE TABLE IF NOT EXISTS config_cod_lote " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," +   
                   "producto_id      int(8)          NOT NULL  ," +
                   "nombre varchar(50)  NOT NULL, "             +
                   "descripcion    varchar(50)     NOT NULL, " +  
                   "fecha_vence   date NOT NULL, "           +                   
                   "FOREIGN KEY (producto_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION)" ;
      stmt.executeUpdate(sql);
      
         sql = "CREATE TABLE IF NOT EXISTS config_producto_servicio " +
                   "(servicio_id             int(8)          NOT NULL  ," +                    
                   " producto_id                int(8)          NOT NULL, "     +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (producto_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION)";
      stmt.executeUpdate(sql);
       sql = "CREATE TABLE IF NOT EXISTS farmac_asic_servicio " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," + 
                   " asic_id int(8)  ," + 
                   " tipo_centro_id int(8) , "            +
                   " servicio_id int(8)   , "             +
                   " activo       bool    NOT NULL, " + 
                   " farmacia_id int(8) , "            +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (asic_id) REFERENCES config_cod_asic (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (farmacia_id) REFERENCES config_cod_farmacia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (tipo_centro_id) REFERENCES config_cod_tipo_centro (id) ON DELETE RESTRICT ON UPDATE NO ACTION)"                 ;
      stmt.executeUpdate(sql); 
      
       sql = "CREATE TABLE IF NOT EXISTS farmac_asic_producto_necesidad " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," + 
                 " provincia_id int(8)  ," + 
                   " asic_id int(8)  ," + 
                   " tipo_centro_id int(8) , "            +
                   " servicio_id int(8)   , "             +
                   " producto_id     int(8) , " +       
                   " stock_maximo     float(8) , " + 
                   " stock_minimo     float(8) , " +  
                   " farmacia_id int(8) , "            +
                   "FOREIGN KEY (provincia_id) REFERENCES config_cod_provincia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (asic_id) REFERENCES config_cod_asic (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (farmacia_id) REFERENCES config_cod_farmacia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (producto_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (tipo_centro_id) REFERENCES config_cod_tipo_centro (id) ON DELETE RESTRICT ON UPDATE NO ACTION)"                 ;
      stmt.executeUpdate(sql); 
      
      sql = "CREATE TABLE IF NOT EXISTS farmac_asic_producto_existencia " +
                   "(id        INTEGER   PRIMARY KEY AUTOINCREMENT ," + 
                   " asic_id int(8)  ," + 
                   " tipo_centro_id int(8) , "            +
                   " servicio_id int(8)   , "             +
                   " producto_id     int(8) , " +       
                   " lote_id     int(8) , " + 
                   "semana_estadistica     int(8) , " + 
                   " cantidad_existencia     int(8)       NOT NULL, " +                    
                   " farmacia_id int(8) , "            +
                   " year int(8)  , "            +
                   "FOREIGN KEY (lote_id) REFERENCES config_cod_lote (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (asic_id) REFERENCES config_cod_asic (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (farmacia_id) REFERENCES config_cod_farmacia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (producto_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (tipo_centro_id) REFERENCES config_cod_tipo_centro (id) ON DELETE RESTRICT ON UPDATE NO ACTION)"                 ;
      stmt.executeUpdate(sql); 
      
      stmt.close(); 
     
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
        ///System.out.println("database created successfully");
    }
    
   
    public void update_database()
    {
        try {
       Connection c = DbConexion.getConnection();     
       stmt = c.createStatement();
       String sql = "ALTER TABLE farmac_asic_producto_necesidad RENAME TO tmp;";
       stmt.executeUpdate(sql);       
       sql = "CREATE TABLE farmac_asic_producto_necesidad " +
                   "(id              int(8)          NOT NULL PRIMARY KEY ," + 
                 " provincia_id int(8)  ," + 
                   " asic_id int(8)  ," + 
                   " tipo_centro_id int(8) , "            +
                   " servicio_id int(8)   , "             +
                   " producto_id     int(8) , " +       
                   " stock_maximo     float(8) , " + 
                   " stock_minimo     float(8) , " +  
                   " farmacia_id int(8) , "            +
                   "FOREIGN KEY (provincia_id) REFERENCES config_cod_provincia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (servicio_id) REFERENCES config_cod_servicio (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (asic_id) REFERENCES config_cod_asic (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (farmacia_id) REFERENCES config_cod_farmacia (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (producto_id) REFERENCES config_cod_producto (id) ON DELETE RESTRICT ON UPDATE NO ACTION," +
                   "FOREIGN KEY (tipo_centro_id) REFERENCES config_cod_tipo_centro (id) ON DELETE RESTRICT ON UPDATE NO ACTION)"                 ;
       stmt.executeUpdate(sql);       
       
      sql = "INSERT INTO farmac_asic_producto_necesidad (id,provincia_id,asic_id,tipo_centro_id,servicio_id,producto_id,stock_maximo,stock_minimo,farmacia_id)\n" +
        "  SELECT id,provincia_id,asic_id,tipo_centro_id,servicio_id,producto_id,stock_maximo,stock_minimo,farmacia_id\n" +
        "  FROM tmp;";
      stmt.executeUpdate(sql);
      
      sql = " DROP TABLE tmp;";
       stmt.executeUpdate(sql);
       stmt.close(); 
     
        } 
        catch (Exception e ) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    
    
    }
    
    public void delete_old_year()
    {
      try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            Calendar calendar = Calendar.getInstance();
            int year_actual = calendar.get(Calendar.YEAR);
            String sql = "DELETE from farmac_asic_producto_existencia where year <>" + year_actual ;
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }  
    
    
    }
    
    public void listado_eliminar(int producto_id)
    {
    
         try {
            
            Connection c = DbConexion.getConnection(); 
            ResultSet res;
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT\n" +
            "farmac_asic_producto_existencia.id as identificador\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_lote ON config_cod_lote.producto_id = config_cod_producto.id AND farmac_asic_producto_existencia.lote_id = config_cod_lote.id\n" +
            "WHERE\n" +
            "config_cod_producto.id = ? and semana_estadistica = ? and servicio_id = 0 \n" +
            "ORDER BY farmac_asic_producto_existencia.id DESC");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);             
            prep.setInt(1,producto_id);
            prep.setInt(2,semana);                        
            res = prep.executeQuery();
            int pos = 0;
            ArrayList<Integer>eliminar = new ArrayList<>();
            while(res.next())
            {
                if (pos!= 0) {
                    int id = res.getInt("identificador");
                    eliminar.add(id);
                    //System.out.println(id);
                 
                }
                else
                {
                 pos++;
                }                
            
            }  
            
             for (int i = 0; i < eliminar.size(); i++) {
                 
               remove_producto_id(eliminar.get(i));
             }
            
            
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
    
    }
    
    
     public void remove_producto_id(int id)
      {
      
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from farmac_asic_producto_existencia where farmac_asic_producto_existencia.id= " + id ;
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
      
      }
    
    
    public ArrayList<Integer> listado_repetidos_existencia()
    {
        ArrayList<Integer> listado = new ArrayList<>();
     try {
            
            Connection c = DbConexion.getConnection(); 
            ResultSet res;
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT asic_id, producto_id, lote_id, semana_estadistica,count(id) as cantidad\n" +
            "FROM farmac_asic_producto_existencia \n" +
            "where semana_estadistica = ? and tipo_centro_id = 0\n" +
            "group by asic_id, producto_id, lote_id, semana_estadistica\n" +
            "having count(id) > 1\n" +
            "order by count(id) desc");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);             
            prep.setInt(1,semana);                         
            res = prep.executeQuery();
            while(res.next())
            {
                 int id = res.getInt("producto_id");
                 listado.add(id);
            
            }
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
        
    return listado; 
    
    }
    
    public void update_year()
    {
    
         try {
       Connection c = DbConexion.getConnection();     
       stmt = c.createStatement();       
       ResultSet res = stmt.executeQuery("PRAGMA table_info('farmac_asic_producto_existencia')");
       Boolean insertar = true;  
        while(res.next())
        {
            
             if( res.getString(2).equals("year"))
             {
                 
                 insertar = false;


             }


        }
       
             if (insertar==true) {
                 
                 String sql = "ALTER TABLE farmac_asic_producto_existencia  ADD COLUMN year int(8) ;";
                 stmt.executeUpdate(sql);  
             }
            
         //update campo year
            Calendar calendar = Calendar.getInstance();
            int year =  calendar.get(Calendar.YEAR);
            ResultSet semana_estadistica = stmt.executeQuery("SELECT\n" +
            "max(semana_estadistica) as max\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia ");
             PreparedStatement prep  = null;
             int max_semana = semana_estadistica.getInt("max");  
             if (max_semana > 50) {
                 
                 year = 2016;
                 String query = "Update farmac_asic_producto_existencia set year = ? where semana_estadistica >= 50  ";
                 prep = c.prepareStatement(query);
                 prep.setInt(1,year);             
                 prep.executeUpdate();
             }
             else
             {
              String query = "Update farmac_asic_producto_existencia set year = ? where  semana_estadistica < 50   ";
              prep = c.prepareStatement(query);
              prep.setInt(1,year);             
              prep.executeUpdate();
             
             }
            
             
       stmt.close(); 
     
        } 
        catch (Exception e ) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
    
    
    }
    
   
    
   
    
}
