/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;
import Clases.ExistenciaExportar;
import Clases.Farmacia;
import Clases.Productos;
import Clases.Servicios;
import Clases.TipoCentro;
import Clases.UMedidas;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Teudis
 */
public class Operaciones {
    
    
    Statement stmt = null;
    final ResultSet resultado = null; 
    
     public void insert_cod_provincia(String nombre, int id)
    {
    
        try {
            
            Connection c = DbConexion.getConnection();          
                       
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("insert into config_cod_provincia values(?,?);");
            prep.setString(2, nombre);
            prep.setInt(1,id);             
            prep.execute(); 
            
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
    
    }
     
   
     public int get_total_producto_lote(String codigo)
     {
       int cant =0;
         try {
            
            Connection c = DbConexion.getConnection();              
            ResultSet res;           
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT\n" +
            "sum(farmac_asic_producto_existencia.cantidad_existencia)as total\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = ? and farmac_asic_producto_existencia.lote_id <> 0 and farmac_asic_producto_existencia.servicio_id = 0  and farmac_asic_producto_existencia.semana_estadistica = ?");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);              
            prep.setString(1,codigo); 
            prep.setInt(2,semana);            
            res = prep.executeQuery();
              if (res.next()) {
                  
                  cant = res.getInt("total");
              }
            
           
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }        
         
         return cant;        
     }
      
     
   public int get_existencia_servicio (int farmacia_id, int centro_id, int  servicio_id,int producto_id )
   {
      int cant =0;
         try {
            
            Connection c = DbConexion.getConnection();              
            ResultSet res;           
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT\n" +
            "farmac_asic_producto_existencia.cantidad_existencia as total \n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.tipo_centro_id = ? AND\n" +
            "farmac_asic_producto_existencia.servicio_id = ? AND           \n" +
            "farmac_asic_producto_existencia.farmacia_id = ? AND\n" +
            "farmac_asic_producto_existencia.semana_estadistica = ? \n" +
            "and  farmac_asic_producto_existencia.producto_id = ? ");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);  
            prep.setInt(1,centro_id);
            prep.setInt(2,servicio_id); 
            prep.setInt(3,farmacia_id); 
            prep.setInt(4,semana);   
            prep.setInt(5,producto_id);  
            res = prep.executeQuery();
              if (res.next()) {
                  
                  cant = res.getInt("total");
              }
            
           
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }        
         
         return cant; 
   
   
   }
     
     public int get_total_producto_nolote(String codigo)
     {
       int cant =0;
         try {
            
            Connection c = DbConexion.getConnection();              
            ResultSet res;           
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT\n" +
            "sum(farmac_asic_producto_existencia.cantidad_existencia)as total\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = ? and farmac_asic_producto_existencia.lote_id = 0 and  farmac_asic_producto_existencia.servicio_id = 0 and  farmac_asic_producto_existencia.semana_estadistica = ?");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);             
            prep.setString(1,codigo); 
            prep.setInt(2,semana);             
            res = prep.executeQuery();
              if (res.next()) {
                  
                  cant = res.getInt("total");
              }
            
           
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }        
         
         return cant; 
       
     }
     
     
     public int get_valor(String codigo)
     {
         int cant =0;
          try {
            
            Connection c = DbConexion.getConnection();              
            ResultSet res;           
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("SELECT\n" +
            "farmac_asic_producto_existencia.cantidad_existencia,\n" +
            "config_cod_producto.descripcion\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.semana_estadistica = ? AND\n" +
            "config_cod_producto.codigo = ? "); 
            
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);
            prep.setInt(1,semana); 
            prep.setString(2,codigo);             
            res = prep.executeQuery();
              if (res.next()) {
                  
                  cant = res.getInt("cantidad_existencia");
              }
            
           
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }        
         
         return cant;     
     }
     
     public void generar_lotes_semana(int semana_actual)
     {
        try {
            
           
            Connection c = DbConexion.getConnection();            
            c.setAutoCommit(false);   
            stmt = c.createStatement();            
            Calendar calendar = Calendar.getInstance();
            int year_actual =  calendar.get(Calendar.YEAR);
            
            ResultSet semana_estadistica = stmt.executeQuery("SELECT\n" +
            "max(semana_estadistica) as max\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia where year="+year_actual);
             PreparedStatement prep  = null;
             int max_semana = semana_estadistica.getInt("max");
             if (max_semana==0) {
                
                 int year = year_actual - 1;
                 semana_estadistica = stmt.executeQuery("SELECT\n" +
            "max(semana_estadistica) as max\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia where year="+year);
            
              max_semana = semana_estadistica.getInt("max");
            }
             String query;
             ResultSet res ;
                
            
                           
            
             if (max_semana < semana_actual  ) {
                 
                 query ="SELECT\n" +
                        "*\n" +
                        "FROM\n" +
                        "farmac_asic_producto_existencia\n" +
                        "WHERE\n" +
                        "farmac_asic_producto_existencia.semana_estadistica = ? and year =? ";

                 prep = c.prepareStatement(query);
                 prep.setInt(1 ,max_semana);                 
                 year_actual =  calendar.get(Calendar.YEAR);
                 prep.setInt(2 ,year_actual); 
                 res = prep.executeQuery();
                 int asic_id , tipo_centro_id ,servicio_id ,producto_id,lote_id,cantidad_existencia,farmacia_id;
                                  
                 while (res.next()) { 
                 
                    asic_id = res.getInt("asic_id");
                    tipo_centro_id = res.getInt("tipo_centro_id");
                    servicio_id =  res.getInt("servicio_id");
                    producto_id = res.getInt("producto_id");
                    lote_id = res.getInt("lote_id");                    
                    cantidad_existencia = res.getInt("cantidad_existencia");
                    farmacia_id = res.getInt("farmacia_id");
                    query = "insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id,year) values(?,?,?,?,?,?,?,?,?);";
                    prep = c.prepareStatement(query);
                    prep.setInt(1, asic_id);  
                    prep.setInt(2, tipo_centro_id);  
                    prep.setInt(3, servicio_id);  
                    prep.setInt(4, producto_id);
                    prep.setInt(5, lote_id); 
                    prep.setInt(6, semana_actual); 
                    prep.setInt(7, cantidad_existencia);   
                    prep.setInt(8, farmacia_id);
                    prep.setInt(9, year_actual);
                    prep.executeUpdate();
                     
                 }                
                 c.commit();
             }
             else  // primera semana de anno  -- cambio de anno donde ultima semana no es la 52
                  if ((max_semana== 52 && semana_actual==1)  || (max_semana != semana_actual)) {
                
                       query ="SELECT\n" +
                        "*\n" +
                        "FROM\n" +
                        "farmac_asic_producto_existencia\n" +
                        "WHERE\n" +
                        "farmac_asic_producto_existencia.semana_estadistica = ? and year = ?";

                 prep = c.prepareStatement(query);
                 prep.setInt(1 ,max_semana); 
                 int anno = year_actual - 1;
                 prep.setInt(2 ,anno);
                 res = prep.executeQuery();
                 int asic_id , tipo_centro_id ,servicio_id ,producto_id,lote_id,cantidad_existencia,farmacia_id;
                                  
                 while (res.next()) { 
                 
                    asic_id = res.getInt("asic_id");
                    tipo_centro_id = res.getInt("tipo_centro_id");
                    servicio_id =  res.getInt("servicio_id");
                    producto_id = res.getInt("producto_id");
                    lote_id = res.getInt("lote_id");                    
                    cantidad_existencia = res.getInt("cantidad_existencia");
                    farmacia_id = res.getInt("farmacia_id");
                    query = "insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id, year) values(?,?,?,?,?,?,?,?,?);";
                    prep = c.prepareStatement(query);
                    prep.setInt(1, asic_id);  
                    prep.setInt(2, tipo_centro_id);  
                    prep.setInt(3, servicio_id);  
                    prep.setInt(4, producto_id);
                    prep.setInt(5, lote_id); 
                    prep.setInt(6, semana_actual); 
                    prep.setInt(7, cantidad_existencia);   
                    prep.setInt(8, farmacia_id); 
                    year_actual =  calendar.get(Calendar.YEAR);
                    prep.setInt(9, year_actual);
                    prep.executeUpdate();
                     
                 }                
                 c.commit();
                      
               
                      
             }
             
            
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
     
     }
     
     public void insert_cod_asic (String nombre, int id)
     {
          try {
            
            Connection c = DbConexion.getConnection();                         
            stmt = c.createStatement();
            ResultSet res = stmt.executeQuery("select id from config_cod_provincia");
            int id_provincia = res.getInt("id");
            PreparedStatement prep = c.prepareStatement("insert into config_cod_asic values(?,?,?);");
            prep.setInt(1,id);
            prep.setInt(2,id_provincia);
            prep.setString(3, nombre);            
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
     
     public ArrayList get_necesidad_productos()
     {
          ArrayList<Productos> productos = new ArrayList<Productos>();
       
       
        try {
            
            Connection c = DbConexion.getConnection();  
            stmt = c.createStatement();
            ResultSet res = stmt.executeQuery("SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "upper(config_cod_producto.descripcion) AS descripcion,\n" +
            "upper(config_cod_unidad_medida.nombre ) AS medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo AS cantidad,\n" +
            "upper(config_cod_asic.nombre ) AS asic,\n" +
            "upper (config_cod_provincia.nombre) AS estado,\n" +
            "upper(config_cod_farmacia.nombre) as farmacia\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "INNER JOIN config_cod_asic ON farmac_asic_producto_necesidad.asic_id = config_cod_asic.id\n" +
            "INNER JOIN config_cod_provincia ON farmac_asic_producto_necesidad.provincia_id = config_cod_provincia.id AND config_cod_asic.provincia_id = config_cod_provincia.id\n" +
            "INNER JOIN config_cod_farmacia ON config_cod_farmacia.asic_id = config_cod_asic.id AND config_cod_farmacia.provincia_id = config_cod_provincia.id AND farmac_asic_producto_necesidad.farmacia_id = config_cod_farmacia.id\n" +
            "ORDER BY\n" +
            "config_cod_producto.descripcion ASC");
            
            while (res.next()) {                
                
                String codigo = res.getString("codigo");
                String descripcion = res.getString("descripcion");
                String medida = res.getString("medida");
                int cantidad = res.getInt("cantidad");
                String estado = res.getString("estado");
                String farmacia = res.getString("farmacia");
                String asic =  res.getString("asic");
                Productos p = new Productos(codigo, descripcion, cantidad, medida, estado, asic, farmacia) ;
                productos.add(p);
                
                
            }
           // c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
       return productos;
     
     }
     
     public ArrayList get_farmacias ()
     {
       //Farmacia [] farmacias = null;
       
       ArrayList<Farmacia> farmacias = new ArrayList<Farmacia>();
       
       
        try {
            
            Connection c = DbConexion.getConnection();
            stmt = c.createStatement();
            ResultSet res = stmt.executeQuery("SELECT *\n" +
            "FROM\n" +
            "config_cod_farmacia");
            
            while (res.next()) {                
                
                int id = res.getInt("id");
                String nombre = res.getString("nombre");
                int provincia_id = res.getInt("provincia_id");
                int asic_id = res.getInt("asic_id");
                Farmacia f = new Farmacia(nombre, id, provincia_id, asic_id);
                farmacias.add(f);
                
                
            }
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
       return farmacias;
     }
     
     
     public ArrayList get_tipo_centros(int farmacia_actual)
     {
       ArrayList<TipoCentro> centros = new ArrayList<TipoCentro>();
        try {
            
            Connection c = DbConexion.getConnection(); 
            stmt = c.createStatement();
            ResultSet res = null;
            String query = "SELECT DISTINCT\n" +
            "config_cod_tipo_centro.nombre,\n"+
            "config_cod_tipo_centro.id,\n" +
              "config_cod_tipo_centro.sigla\n"+
            "FROM\n" +
            "farmac_asic_servicio\n" +
            "INNER JOIN config_cod_tipo_centro ON farmac_asic_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "WHERE\n" +
            "farmac_asic_servicio.farmacia_id = ?" ;
            
            PreparedStatement prep = c.prepareStatement(query);
            prep = c.prepareStatement(query);
            prep.setInt(1,farmacia_actual);
            res = prep.executeQuery();
            
            while (res.next()) {                
                
                int id = res.getInt("id");
                String nombre = res.getString("nombre");
                String sigla = res.getString("sigla");
                TipoCentro centro = new TipoCentro(id, nombre, sigla);                
                centros.add(centro);
            }
           
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
       return centros;
     }
     
     public ArrayList get_servicios(int centro_actual)
     {
       ArrayList<Servicios> servicio = new ArrayList<Servicios>();
     
       try {
            
            Connection c = DbConexion.getConnection();                         
            stmt = c.createStatement();
            ResultSet res = null;
            String query = "SELECT DISTINCT\n" +
            "config_cod_servicio.nombre,\n" +
            "config_cod_servicio.id\n" +
            "FROM\n" +
            "config_cod_servicio\n" +
            "INNER JOIN config_tipo_centro_servicio ON config_tipo_centro_servicio.servicio_id = config_cod_servicio.id\n" +
            "INNER JOIN config_cod_tipo_centro ON config_tipo_centro_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "WHERE\n" +
            "config_tipo_centro_servicio.tipo_centro_id = ?" ;
            
            PreparedStatement prep = c.prepareStatement(query);
            prep = c.prepareStatement(query);
            prep.setInt(1,centro_actual);
            res = prep.executeQuery();
            
            while (res.next()) {                
                
                int id = res.getInt("id");
                String nombre = res.getString("nombre");
                Servicios s = new Servicios(nombre,id);                
                servicio.add(s);
            }
           
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
       
       return servicio;
     }
     
     public void insert_tipo_centro(String nombre,String sigla, String descripcion, int id )
     {
         try {
            
            Connection c = DbConexion.getConnection();  
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("insert into config_cod_tipo_centro values(?,?,?,?);");
            
            prep.setInt(1,id);
            prep.setString(2, nombre);
            prep.setString(3, descripcion);
            prep.setString(4, sigla);
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
         
     
     }
     
     
     public void insert_cod_servicio (String nombre,String sigla, String descripcion, int id)
     {
         
         try {
                        
            Connection c = DbConexion.getConnection();                                   
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("insert into config_cod_servicio values(?,?,?,?);");            
            prep.setInt(1,id);
            prep.setString(2, nombre);
            prep.setString(3, descripcion);
            prep.setString(4, sigla);
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
     public void insert_tipo_centro_servicio(int tipo_centro_id,int servicio_id)
     {
     
         try {
            
            
            Connection c = DbConexion.getConnection(); 
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("insert into config_tipo_centro_servicio values(?,?);");
            
            prep.setInt(1,tipo_centro_id);
            prep.setInt(2, servicio_id);           
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
         
     
     }
      
     
     public void insert_cod_farmacia(String nombre , int id)
     {
         
          try {
            
            Connection c = DbConexion.getConnection();   
            stmt = c.createStatement();
            ResultSet res = stmt.executeQuery("select id from config_cod_provincia");
            int id_provincia = res.getInt("id");
            ResultSet res_asic = stmt.executeQuery("select id from config_cod_asic");
            int id_asic = res_asic.getInt("id");
            PreparedStatement prep = c.prepareStatement("insert into config_cod_farmacia values(?,?,?,?);");
            prep.setInt(1,id);
            prep.setInt(2,id_provincia);
            prep.setInt(3,id_asic);
            prep.setString(4, nombre);            
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
     
     public void insert_unidad_medida(String nombre, int id)
     {
        try {
            
            
            Connection c = DbConexion.getConnection();  
            stmt = c.createStatement();
            ResultSet res = stmt.executeQuery("select id from config_cod_unidad_medida Where id="+ id+" " );
              if (res.next()== false)
              { 
                   PreparedStatement prep = c.prepareStatement("insert into config_cod_unidad_medida values(?,?);");
                   prep.setInt(1,id);           
                   prep.setString(2, nombre);            
                   prep.execute(); 
              }
                
          
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
     
     public void insertar_producto (int id,String codigo,String descripcion, int oficial_id,String factor_conversion, int unidad_medida_id, int unidad_medidad_almacen_id)
     {
     
        try {
            
            
            Connection c = DbConexion.getConnection(); 
            stmt = c.createStatement();
            PreparedStatement prep = c.prepareStatement("insert into config_cod_producto values(?,?,?,?,?,?,?);");
            
            prep.setInt(1,id);
            prep.setInt(2, unidad_medida_id); 
            prep.setString(3,codigo); 
            prep.setString(4,factor_conversion);           
            prep.setString(5,descripcion);
            prep.setInt(6,oficial_id);
            prep.setInt(7,unidad_medidad_almacen_id);
            prep.execute(); 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           // System.exit(0);
            
        }
     
     
     }
     
     public void insert_farmac_asic_servicio(JSONArray farmac_asic_servicio)
     {
         
         try {
            
            Connection c = DbConexion.getConnection();            
            c.setAutoCommit(false);  
            JSONObject asic_servicio = null;
            int id ,asic_id , tipo_centro_id, servicio_id, farmacia_id;
            Boolean activo;
            stmt = c.createStatement();
            //stmt.beginTransaction();
            PreparedStatement prep = c.prepareStatement("insert into farmac_asic_servicio values(?,?,?,?,?,?);");
            
            for (int j = 0; j < farmac_asic_servicio.size(); j++) {
                    
                   asic_servicio = (JSONObject) farmac_asic_servicio.get(j);  
                  
                   id =  Integer.parseInt ((String) asic_servicio.get("id"));
                   asic_id =  Integer.parseInt ((String) asic_servicio.get("asic_id"));
                   tipo_centro_id =  Integer.parseInt ((String) asic_servicio.get("tipo_centro_id"));
                   servicio_id =  Integer.parseInt ((String) asic_servicio.get("servicio_id"));
                   farmacia_id =  Integer.parseInt ((String) asic_servicio.get("farmacia_id"));                  
                   activo =  (Boolean) asic_servicio.get("activo");
                   prep.setInt(1, id);
                   prep.setInt(2, asic_id);  
                   prep.setInt(3, tipo_centro_id);  
                   prep.setInt(4, servicio_id);  
                   prep.setBoolean(5, activo);  
                   prep.setInt(6, farmacia_id);                  
                   prep.executeUpdate();
                } 
            
            
            stmt.close();
            c.commit();
           // c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     }
     
     
     public void insert_farmac_asic_producto_necesidad (JSONArray producto_necesidad)
     {
       try {
            
            Connection c = DbConexion.getConnection();             
            c.setAutoCommit(false);  
            JSONObject necesidad = null;
            int id ,asic_id , tipo_centro_id =0, servicio_id=0, farmacia_id , provincia_id ,producto_id ;
            float stock_maximo=0,stock_minimo=0;
            
            stmt = c.createStatement();
            //stmt.beginTransaction();
            PreparedStatement prep = c.prepareStatement("insert into farmac_asic_producto_necesidad values(?,?,?,?,?,?,?,?,?);");
            
            for (int j = 0; j < producto_necesidad.size(); j++) {
                    
                   necesidad = (JSONObject) producto_necesidad.get(j);  
                  
                   id =  Integer.parseInt ((String) necesidad.get("id"));
                   provincia_id = Integer.parseInt ((String) necesidad.get("provincia_id"));
                   producto_id =  Integer.parseInt ((String) necesidad.get("producto_id"));
                   String s_stock_maximo =  necesidad.get("stock_maximo").toString();
                   //System.out.println("maximo:"+s_stock_maximo);
                   if (!"".equals(s_stock_maximo)) {
                    
                       //stock_maximo = Integer.parseInt(s_stock_maximo);
                       stock_maximo = Float.parseFloat(s_stock_maximo);
                }
                   String s_stock_minimo = necesidad.get("stock_minimo").toString();
                   if (!"".equals(s_stock_minimo)) {
                    
                       //stock_minimo =  Integer.parseInt(s_stock_minimo);
                       stock_minimo =  Float.parseFloat(s_stock_minimo);
                       
                }
                   
                   asic_id =  Integer.parseInt ((String) necesidad.get("asic_id"));
                   String s_tipo_centro_id = necesidad.get("tipo_centro_id").toString();
                   if (!"".equals(s_tipo_centro_id)) {
                    
                       tipo_centro_id = Integer.parseInt(s_tipo_centro_id);
                       
                }
                   
                    String s_servicio_id = necesidad.get("servicio_id").toString();
                    if (!"".equals(s_servicio_id)) {
                    
                        servicio_id = Integer.parseInt(s_servicio_id);
                }
                   
                   farmacia_id =  Integer.parseInt ((String) necesidad.get("farmacia_id"));                 
                   
                   prep.setInt(1, id);
                   prep.setInt(2, provincia_id);
                   prep.setInt(3, asic_id);  
                   prep.setInt(4, tipo_centro_id);  
                   prep.setInt(5, servicio_id);  
                   prep.setInt(6, producto_id);
                   prep.setFloat(7, stock_maximo); 
                   prep.setFloat(8, stock_minimo); 
                   prep.setInt(9, farmacia_id);                  
                   prep.executeUpdate();
                   
                   stock_maximo = 0;
                   stock_minimo = 0;
                   producto_id = 0;
                   servicio_id = 0;
                } 
            
            
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.out.println("necesidad");
             System.exit(0);
            
        }
     }
     
     
     public void existencia_no_lotes(int existencia, int semana, String codigo)
     {
        try {
            
            Connection c = DbConexion.getConnection();  
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.codigo\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = ?";
                       
            prep = c.prepareStatement(query);
            prep.setString(1, codigo); 
            //prep.setString(2,codigo); 
            ResultSet res = prep.executeQuery();
            int id_producto = res.getInt("id");
            
            query = "SELECT\n" +
            "farmac_asic_producto_existencia.asic_id,\n" +
            "farmac_asic_producto_existencia.farmacia_id\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.producto_id = ? AND\n" +
            "farmac_asic_producto_existencia.lote_id = ? AND farmac_asic_producto_existencia.semana_estadistica = ? \n" +
            " " ;
            prep = c.prepareStatement(query);
            prep.setInt(1,id_producto); 
            prep.setInt(2,0); 
            prep.setInt(3,semana);             
            res = prep.executeQuery();
             // Actualizar
            
            boolean update = false;
             if (res.next()) {
                 
                 update = true;
             }
              int asic_id ;
              int farmacia_id;
             if (update) {
                 
            asic_id = res.getInt("asic_id");
            farmacia_id = res.getInt("farmacia_id");
            query = "Update farmac_asic_producto_existencia set cantidad_existencia = ? where producto_id = ? and asic_id =? and farmacia_id = ? and semana_estadistica =?  ";
            prep = c.prepareStatement(query);
            prep.setInt(1,existencia); 
            prep.setInt(2,id_producto); 
            prep.setInt(3,asic_id); 
            prep.setInt(4,farmacia_id);             
            prep.setInt(5,semana);
            prep.executeUpdate();
             }
             else                 
             {
                query = "SELECT\n" +
                "config_cod_farmacia.id as farmacia_id,\n" +
                "config_cod_asic.id as asic_id\n" +
                "FROM\n" +
                "config_cod_farmacia\n" +
                "INNER JOIN config_cod_asic ON config_cod_farmacia.asic_id = config_cod_asic.id";
                 prep = c.prepareStatement(query);
                 res = prep.executeQuery();
                 
                 
                 asic_id = res.getInt("asic_id");
                 farmacia_id = res.getInt("farmacia_id");  
                 query = "insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id) values(?,?,?,?,?,?,?,?);";
                 prep = c.prepareStatement(query);
                 prep.setInt(1,asic_id); 
                 prep.setInt(2,0); 
                 prep.setInt(3,0); 
                 prep.setInt(4,id_producto); 
                 prep.setInt(5,0);
                 prep.setInt(6,semana);
                 prep.setInt(7,existencia);
                 prep.setInt(8,farmacia_id);
                 prep.executeUpdate();
             
             }
            
            c.commit();
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }    
     
     
     }
     
     public void existencia_lotes (String nombre_lote,int existencia, int semana, String codigo, String fecha_vence)
     {
         
         try {
            
            Connection c = DbConexion.getConnection();  
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.codigo\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = ?";
                       
            prep = c.prepareStatement(query);
            prep.setString(1, codigo); 
            //prep.setString(2,codigo); 
            ResultSet res = prep.executeQuery();
            int id_producto = res.getInt("id");
            query = "SELECT\n" +
            "config_cod_lote.id\n" +
            "FROM\n" +
            "config_cod_lote\n" +
            "WHERE\n" +
            "config_cod_lote.producto_id = ? and nombre = ? and fecha_vence = ? "; 
            prep = c.prepareStatement(query);
            prep.setInt(1,id_producto); 
            prep.setString(2,nombre_lote); 
            prep.setString(3,fecha_vence); 
            res = prep.executeQuery();
            int lote_id = 0 ;
             while (res.next()) {                 
                lote_id  = res.getInt("id");
                 
             }
            query = "SELECT\n" +
            "farmac_asic_producto_existencia.asic_id,\n" +
            "farmac_asic_producto_existencia.farmacia_id\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.producto_id = ? AND\n" +
            "farmac_asic_producto_existencia.lote_id = ? AND farmac_asic_producto_existencia.semana_estadistica = ? and farmac_asic_producto_existencia.year = ? \n" +
            " " ;
            prep = c.prepareStatement(query);
            prep.setInt(1,id_producto); 
            prep.setInt(2,lote_id); 
            prep.setInt(3,semana);  
            Calendar calendar =  Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            prep.setInt(4,year); 
            res = prep.executeQuery();
             // Actualizar
            
            boolean update = false;
             if (res.next()) {
                 
                 update = true;
             }
              int asic_id ;
              int farmacia_id;
             if (update) {
                 
            asic_id = res.getInt("asic_id");
            farmacia_id = res.getInt("farmacia_id");
            query = "Update farmac_asic_producto_existencia set cantidad_existencia = ? where producto_id = ? and asic_id =? and farmacia_id = ? and lote_id = ?  and semana_estadistica =?  and year = ? ";
            prep = c.prepareStatement(query);
            prep.setInt(1,existencia); 
            prep.setInt(2,id_producto); 
            prep.setInt(3,asic_id); 
            prep.setInt(4,farmacia_id); 
            prep.setInt(5,lote_id);
            prep.setInt(6,semana);
            prep.setInt(7,year);
            prep.executeUpdate();
             }
             else                 
             {
                query = "SELECT\n" +
                "config_cod_farmacia.id as farmacia_id,\n" +
                "config_cod_asic.id as asic_id\n" +
                "FROM\n" +
                "config_cod_farmacia\n" +
                "INNER JOIN config_cod_asic ON config_cod_farmacia.asic_id = config_cod_asic.id";
                 prep = c.prepareStatement(query);
                 res = prep.executeQuery();
                 
                 
                 asic_id = res.getInt("asic_id");
                 farmacia_id = res.getInt("farmacia_id");  
                 query = "insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id, year) values(?,?,?,?,?,?,?,?,?);";
                 prep = c.prepareStatement(query);
                 prep.setInt(1,asic_id); 
                 prep.setInt(2,0); 
                 prep.setInt(3,0); 
                 prep.setInt(4,id_producto); 
                 prep.setInt(5,lote_id);
                 prep.setInt(6,semana);
                 prep.setInt(7,existencia);
                 prep.setInt(8,farmacia_id);
                 prep.setInt(9,year);
                 prep.execute();
             
             }
            
            c.commit();
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }      
     
     }
     
     
     public void guardar_existencia_servicio(String descripcion,  int existencia,int farmacia_id, int centro_id,int servicio_id)
     {
          try {
              
              
            Connection c = DbConexion.getConnection();  
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_farmacia.asic_id AS asic,\n" +
            "config_cod_producto.id AS producto\n" +
            "FROM\n" +
            "config_cod_farmacia ,\n" +
            "config_cod_servicio ,\n" +
            "config_cod_tipo_centro ,\n" +
            "config_cod_producto\n" +
            "INNER JOIN farmac_asic_servicio ON farmac_asic_servicio.servicio_id = config_cod_servicio.id AND farmac_asic_servicio.farmacia_id = config_cod_farmacia.id AND farmac_asic_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "INNER JOIN config_producto_servicio ON config_producto_servicio.servicio_id = config_cod_servicio.id AND config_producto_servicio.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_tipo_centro.id = ? AND\n" +
            "config_cod_servicio.id = ? AND\n" +
            "config_cod_farmacia.id = ? AND\n" +
            "config_cod_producto.descripcion = ?";
                       
            prep = c.prepareStatement(query);
            prep.setInt(1, centro_id);
            prep.setInt(2, servicio_id);
            prep.setInt(3, farmacia_id); 
            prep.setString(4,descripcion);  
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);            
            ResultSet res = prep.executeQuery();
            //valor id de producto
            int id_producto = res.getInt("producto");
            int asic_id = res.getInt("asic");
            query = "SELECT DISTINCT\n" +
            "count(*)as existe\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.asic_id = ? AND\n" +
            "farmac_asic_producto_existencia.tipo_centro_id = ? AND\n" +
            "farmac_asic_producto_existencia.farmacia_id = ? AND\n" +
            "farmac_asic_producto_existencia.servicio_id = ? AND\n" +
            "farmac_asic_producto_existencia.producto_id = ? AND\n" +
            "farmac_asic_producto_existencia.semana_estadistica = ?";
            prep = c.prepareStatement(query);
            prep.setInt(1,asic_id); 
            prep.setInt(2,centro_id); 
            prep.setInt(3,farmacia_id); 
            prep.setInt(4,servicio_id); 
            prep.setInt(5,id_producto);
            prep.setInt(6,semana);
            res = prep.executeQuery();                
            int update = res.getInt("existe");
            stmt.close();
            stmt = c.createStatement();
            int year = calendar.get(Calendar.YEAR);
              if (update > 0) {
                  
                    query = "Update farmac_asic_producto_existencia set cantidad_existencia = ? where producto_id = ? and asic_id =? and farmacia_id = ? and  semana_estadistica = ? and tipo_centro_id = ? and servicio_id = ? and year = ?  ";
                    prep = c.prepareStatement(query);
                    prep.setInt(1,existencia); 
                    prep.setInt(2,id_producto); 
                    prep.setInt(3,asic_id); 
                    prep.setInt(4,farmacia_id); 
                    prep.setInt(5,semana);
                    prep.setInt(6,centro_id);
                    prep.setInt(7,servicio_id);
                    prep.setInt(8,year);
                    prep.execute();
                    c.commit();
              }
              else
              {
                // insertar datos
                query = "insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id, year) values(?,?,?,?,?,?,?,?,?);";
                 prep = c.prepareStatement(query);
                 prep.setInt(1,asic_id); 
                 prep.setInt(2,centro_id); 
                 prep.setInt(3,servicio_id); 
                 prep.setInt(4,id_producto); 
                 prep.setInt(5,0);
                 prep.setInt(6,semana);
                 prep.setInt(7,existencia);
                 prep.setInt(8,farmacia_id);
                 prep.setInt(9,year);
                 prep.execute();
                 c.commit();
              
              }
            
                      
            
            stmt.close();
            //c.close();
            
              
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }  
     
     }
     
     
     public  ArrayList get_medidas()
     {
       ArrayList<UMedidas> listado = new ArrayList<UMedidas>();
         
        try {
          Connection c = DbConexion.getConnection();  
          c.setAutoCommit(false);
          

            stmt = c.createStatement();
            ResultSet medidas = stmt.executeQuery( "SELECT\n" +
            "*\n" +
            "FROM\n" +
            "config_cod_unidad_medida ORDER BY nombre ASC" );
             while ( medidas.next() ) {
                 
                int id = medidas.getInt("id");
                String medida = medidas.getString("nombre");
                UMedidas umedida = new UMedidas(id, medida);
                listado.add(umedida);
             
             }
          
           stmt.close();
           //c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
     
        return listado;
     }
     
     public ArrayList get_semanas()
     {
         ArrayList<Integer> week = new ArrayList<Integer>();
        
              
        
        try {
            Connection c = DbConexion.getConnection();  
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet semanas = stmt.executeQuery( "SELECT DISTINCT\n" +
            "farmac_asic_producto_existencia.semana_estadistica\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia" );
             while ( semanas.next() ) {
                 
                 week.add(semanas.getInt("semana_estadistica"));
             
             }
          
           stmt.close();
           //c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
     
       return week;
     }
     
     public int cant_registros()
     { 
         int cantidad = 0;
            try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            " count(*) as cantidad\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia";
            prep = c.prepareStatement(query);            
            ResultSet res = prep.executeQuery();
            
            while (res.next()) {                
             
                cantidad = res.getInt("cantidad");
              
                
            }
            c.commit();
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }   
     
          return cantidad;
     
     }
     
     
     public ArrayList get_producto_medida_existencia(int medida)
     {
       ArrayList <Productos> productos = new ArrayList<Productos>()  ;
            try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id\n" +
            "FROM\n" +
            "config_cod_producto ,\n" +
            "farmac_asic_servicio\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN config_cod_lote ON config_cod_lote.producto_id = config_cod_producto.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.lote_id = config_cod_lote.id AND farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_producto.unidad_medida_id = ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC ";
            prep = c.prepareStatement(query); 
            prep.setInt(1, medida);
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion;
            int medida_id, producto_id ;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");
                
                Productos pro = new Productos(codigo, descripcion,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }  
     
        return productos;
     
     
     }
     
     
     
     public ArrayList get_producto_medida_necesidad(int medida_id)
     {
        ArrayList <Productos> productos = new ArrayList<Productos>()  ;
            try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.unidad_medida_id = ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1, medida_id);
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion,medida;
            int stock_maximo ;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion = res.getString("descripcion");
                medida = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo, descripcion, medida);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }  
     
        return productos;
         
     
     }
     public ArrayList get_producto_medida(int medida_id)
     {
        ArrayList <Productos> productos = new ArrayList<Productos>()  ;
            try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida\n" +            
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1, medida_id);
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion,medida;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion = res.getString("descripcion");
                medida = res.getString("medida");
                
                Productos pro = new Productos(codigo, descripcion, medida);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }  
     
        return productos;
     }
     
     public ArrayList get_producto_descripcion_existencia (String descripcion)
     {
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_producto.descripcion like ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion_producto;
            int medida_id, producto_id;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");                
                Productos pro = new Productos(codigo, descripcion_producto,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     
     }     
     
     
     public ArrayList get_producto_descripcion_necesidad(String descripcion)
     {
          ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.descripcion like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion_producto,medida;
            int stock_maximo;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo, descripcion_producto, medida);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
          
         return productos;
     }
     
     public ArrayList get_producto_descripcion(String descripcion)
     {
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.descripcion LIKE  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo, descripcion_producto,medida;
            while (res.next())
            {
                codigo = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida = res.getString("medida");
                
                Productos pro = new Productos(codigo, descripcion_producto, medida);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     }
     
     
     public ArrayList get_producto_codigo_existencia(String codigo)
     {
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id as medida \n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_producto.codigo like ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC ";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto;
                    int medida;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida = res.getInt("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;    
     
     }
     
     public ArrayList get_producto_codigo_necesidad(String codigo)
     {
      
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.codigo like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida;
            int stock_maximo ;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;     
     
     }
     
     public ArrayList get_producto_codigo (String codigo)
     {
     
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.codigo LIKE  ?  ORDER BY\n" +
            "config_cod_producto.descripcion ASC ";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida = res.getString("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     
     public ArrayList get_producto_medida_codigo_existencia(int medida, String codigo)
     {
        ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ?\n" +
            "AND\n" +
            "config_cod_producto.codigo like ? ";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto;
            int medida_id, producto_id ;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");                
                Productos pro = new Productos(codigo_producto, descripcion_producto,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     
     }
     
     public ArrayList get_producto_medida_codigo_necesidad(int medida, String codigo)
     {
         ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.unidad_medida_id =  ? AND\n" +
            "config_cod_producto.codigo like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            int stock_maximo;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     }
     
     public ArrayList get_producto_medida_codigo(int medida, String codigo)
     {
        ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS medida,\n" +
            "config_cod_unidad_medida.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ? and\n" +
            "config_cod_producto.codigo LIKE  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC  ";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     public ArrayList get_producto_descripcion_codigo_existencia(String descripcion , String codigo)
     {
       ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.factor_conversion,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.oficial_id,\n" +
            "config_cod_producto.unidad_medida_almacen_id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "where config_cod_producto.codigo like ? \n" +
            " AND \n" +
            "config_cod_producto.descripcion like ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC ";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + codigo + "%");
            prep.setString(2, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            int medida_id,producto_id;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");                
                Productos pro = new Productos(codigo_producto, descripcion_producto,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     
     public ArrayList get_producto_descripcion_codigo_necesidad(String descripcion , String codigo)
     {
         ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.descripcion like ? AND\n" +
            "config_cod_producto.codigo like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + descripcion + "%");
            prep.setString(2, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            int stock_maximo;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     public ArrayList get_producto_descripcion_codigo(String descripcion , String codigo)
     {
         ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS medida,\n" +
            "config_cod_unidad_medida.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.descripcion LIKE  ? and\n" +
            "config_cod_producto.codigo LIKE  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setString(1, "%" + descripcion + "%");
            prep.setString(2, "%" + codigo + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     
     public ArrayList get_producto_medida_descripcion_existencia (int medida, String descripcion)
     {
          ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ?\n" +
            "AND\n" +
            "config_cod_producto.descripcion like ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto;
            int medida_id , producto_id;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");                
                Productos pro = new Productos(codigo_producto, descripcion_producto,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     public ArrayList get_producto_medida_descripcion_necesidad (int medida, String descripcion)
     {
                  ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.unidad_medida_id = ? AND\n" +
            "config_cod_producto.descripcion like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            int  stock_maximo;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     
     }
     
     public  ArrayList get_producto_medida_descripcion(int medida, String descripcion)
     {
               ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS medida,\n" +
            "config_cod_unidad_medida.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ? and\n" +
            "config_cod_producto.descripcion LIKE  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     }
     
  
     public ArrayList get_producto_all_existencia(int medida, String descripcion, String codigo)
     {
        ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT DISTINCT\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.id,\n" +
            "config_cod_producto.unidad_medida_id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ?\n" +
            "And \n" +
            "config_cod_producto.codigo like ? \n" +
            "AND\n" +
            "config_cod_producto.descripcion like  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            prep.setString(3, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto;
            int medida_id, producto_id;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_id = res.getInt("unidad_medida_id");
                producto_id = res.getInt("id");                
                Productos pro = new Productos(codigo_producto, descripcion_producto,medida_id, producto_id);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     public ArrayList get_producto_all_necesidad(int medida, String descripcion, String codigo)
     {
              ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_producto.unidad_medida_id = ? AND\n" +
            "config_cod_producto.codigo like ?  AND \n" +
            "config_cod_producto.descripcion like ? \n" +
            "ORDER BY config_cod_producto.descripcion  ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            prep.setString(3, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            int stock_maximo;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                stock_maximo = res.getInt("stock_maximo");
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                pro.setStock_maximo(stock_maximo);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     public ArrayList get_producto_all(int medida, String descripcion, String codigo)
     {
              ArrayList<Productos>  productos = new ArrayList<Productos>();
          try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query ="SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS medida,\n" +
            "config_cod_unidad_medida.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = ? and\n" +
            "config_cod_producto.codigo LIKE and\n" +        
            "config_cod_producto.descripcion LIKE  ? ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
            prep = c.prepareStatement(query); 
            prep.setInt(1,medida);
            prep.setString(2, "%" + codigo + "%");
            prep.setString(3, "%" + descripcion + "%");
            ResultSet res = prep.executeQuery();  
            String codigo_producto, descripcion_producto,medida_producto;
            while (res.next())
            {
                codigo_producto = res.getString("codigo");
                descripcion_producto = res.getString("descripcion");
                medida_producto = res.getString("medida");
                
                Productos pro = new Productos(codigo_producto, descripcion_producto, medida_producto);
                productos.add(pro);
            }
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
     
         return productos;
     
     }
     
     
     public int cant_datos (String tabla)
     {
        int cantidad = 0;
         try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);            
            ResultSet res = null;            
            PreparedStatement prep = null;
            stmt = c.createStatement();  
            String query ="";
            query = "Select count(*) as cantidad from "+ tabla;
            prep = c.prepareStatement(query);                     
            res = prep.executeQuery();  
            while (res.next()) {
                
                cantidad = res.getInt("cantidad");
                
            }
             } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
        return cantidad;
     
     
     }
     
     public int get_id_farmacia()
     {
        int id_farmacia = 0;
         try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);            
            ResultSet res = null;            
            PreparedStatement prep = null;
            stmt = c.createStatement();  
            String query ="";
            query = "SELECT config_cod_farmacia.id FROM config_cod_farmacia";
            prep = c.prepareStatement(query);                     
            res = prep.executeQuery();  
            while (res.next()) {
                
                id_farmacia = res.getInt("id");
                
            }
             } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
        return id_farmacia;
     
     }
     
     public ArrayList get_existencia_semana(int semana)
     { 
        ArrayList<ExistenciaExportar> existencia = new ArrayList<ExistenciaExportar>();
        try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            int farmacia_id = get_id_farmacia();
            String query ="SELECT DISTINCT\n" +
            "*\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia where semana_estadistica = ? and farmacia_id = ?";
            prep = c.prepareStatement(query);
            prep.setInt(1, semana); 
            prep.setInt(2, farmacia_id); 
            ResultSet res = prep.executeQuery();
            int asic_id , tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia;
            while (res.next()) {                
             
                asic_id = res.getInt("asic_id");
                tipo_centro_id = res.getInt("tipo_centro_id");
                servicio_id = res.getInt("servicio_id");
                producto_id = res.getInt("producto_id");
                lote_id = res.getInt("lote_id");
                semana_estadistica = res.getInt("semana_estadistica");
                cantidad_existencia = res.getInt("cantidad_existencia");
               // farmacia_id = res.getInt("farmacia_id");
                
                ExistenciaExportar ex = new ExistenciaExportar(asic_id, tipo_centro_id, servicio_id, producto_id, lote_id, semana_estadistica, cantidad_existencia, farmacia_id);
                existencia.add(ex);
                
            }
            c.commit();
            stmt.close();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }   
        
        
     
        return existencia;
     }
     
     
     public void insert_farmac_asic_producto_existencia(JSONArray producto_existencia )
     {
       try {
            
            Connection c = DbConexion.getConnection();           
            c.setAutoCommit(false);  
            JSONObject existencia = null;
            int id ,asic_id , tipo_centro_id = 0, servicio_id = 0, farmacia_id , producto_id , lote_id = 0,cantidad_existencia, semana_estadistica;
            
            stmt = c.createStatement();
            //stmt.beginTransaction();
            PreparedStatement prep;
            
            for (int j = 0; j < producto_existencia.size(); j++) {
                    
                   existencia = (JSONObject) producto_existencia.get(j);  
                  
                                     
                   producto_id =  Integer.parseInt ((String) existencia.get("producto_id"));
                   String s_lote_id = existencia.get("lote_id").toString();
                   if (!"".equals(s_lote_id)) {
                    
                       lote_id = Integer.parseInt(s_lote_id); 
                   }
                   String   s_cantidad_existencia =  existencia.get("cantidad_existencia").toString();
                   cantidad_existencia =  Integer.parseInt(s_cantidad_existencia);
                   String s_semana_estadistica = existencia.get("semana_estadistica").toString();
                   semana_estadistica = Integer.parseInt (s_semana_estadistica);
                   asic_id =  Integer.parseInt ((String) existencia.get("asic_id"));
                   String s_tipo_centro_id = existencia.get("tipo_centro_id").toString();
                   if (!"".equals(s_tipo_centro_id)) {
                    
                       tipo_centro_id = Integer.parseInt(s_tipo_centro_id);
                       
                }
                   
                    String s_servicio_id = existencia.get("servicio_id").toString();
                    if (!"".equals(s_servicio_id)) {
                    
                        servicio_id = Integer.parseInt(s_servicio_id);
                }
                   farmacia_id =  Integer.parseInt ((String) existencia.get("farmacia_id"));                 
                   
                   PreparedStatement existe = c.prepareStatement("SELECT\n" +
                   "count(*) as cantidad\n" +
                   "FROM\n" +
                   "farmac_asic_producto_existencia\n" +
                   "WHERE\n" +
                   "main.farmac_asic_producto_existencia.asic_id = ? AND\n" +
                   "main.farmac_asic_producto_existencia.tipo_centro_id = ? AND\n" +
                   "main.farmac_asic_producto_existencia.servicio_id = ? AND\n" +
                   "main.farmac_asic_producto_existencia.producto_id = ? AND\n" +
                   "farmac_asic_producto_existencia.semana_estadistica = ? AND\n" +
                   "farmac_asic_producto_existencia.lote_id = ? AND\n" +
                   "farmac_asic_producto_existencia.farmacia_id = ?");
                   existe.setInt(1, asic_id);  
                   existe.setInt(2, tipo_centro_id);  
                   existe.setInt(3, servicio_id);  
                   existe.setInt(4, producto_id);
                   existe.setInt(5, semana_estadistica);
                   existe.setInt(6, lote_id);
                   existe.setInt(7, farmacia_id);                  
                   ResultSet cantidad_existe = existe.executeQuery();
                   Calendar calendar = Calendar.getInstance();
                   int year = calendar.get(Calendar.YEAR);
                   int insert = cantidad_existe.getInt("cantidad");
                   if (insert == 0 ) {
                    
                        prep = c.prepareStatement("insert into farmac_asic_producto_existencia (asic_id,tipo_centro_id,servicio_id,producto_id,lote_id,semana_estadistica,cantidad_existencia,farmacia_id) values(?,?,?,?,?,?,?,?);");
                        prep.setInt(1, asic_id);  
                        prep.setInt(2, tipo_centro_id);  
                        prep.setInt(3, servicio_id);  
                        prep.setInt(4, producto_id);
                        prep.setInt(5, lote_id); 
                        prep.setInt(6, semana_estadistica); 
                        prep.setInt(7, cantidad_existencia);   
                        prep.setInt(8, farmacia_id); 
                        //prep.setInt(9, year);
                        prep.executeUpdate();
                        System.out.println("insertando");
                       
                       }
                   
                  
                   tipo_centro_id = 0;
                   servicio_id = 0;
                   lote_id = 0;
                } 
            
            
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
     
     public void insert_producto_servicio (JSONArray cod_producto_servicio)
     {
         
          try {
            
            Connection c = DbConexion.getConnection();            
            c.setAutoCommit(false);  
            JSONObject producto_servicio = null;
            String nombre_lote ;
            int servicio_id ;
            int producto_id ;
            
            stmt = c.createStatement();
            //stmt.beginTransaction();
           PreparedStatement prep = c.prepareStatement("insert into config_producto_servicio values(?,?);");
            
            for (int j = 0; j < cod_producto_servicio.size(); j++) {
                    
                   producto_servicio = (JSONObject) cod_producto_servicio.get(j);  
                   servicio_id =  Integer.parseInt ((String) producto_servicio.get("servicio_id"));
                   producto_id =  Integer.parseInt ((String) producto_servicio.get("producto_id"));
                   
                   prep.setInt(1, servicio_id);
                   prep.setInt(2, producto_id);                                   
                   prep.executeUpdate();
                } 
            
            
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     
     }
     
     public void insert_lotes (JSONArray lotes,int producto_id)
     {
       try {
            
            Connection c = DbConexion.getConnection();            
            c.setAutoCommit(false);  
            JSONObject lote = null;
            String nombre_lote ;
            int id_lote ;
            String fecha_vence ;
            String descripcion_lote;
            stmt = c.createStatement();
            //stmt.beginTransaction();
            PreparedStatement prep = c.prepareStatement("insert into config_cod_lote values(?,?,?,?,?);");
            
            for (int j = 0; j < lotes.size(); j++) {
                    
                   lote = (JSONObject) lotes.get(j);  
                   nombre_lote =  (String) lote.get("nombre");
                   id_lote =  Integer.parseInt ((String) lote.get("id"));
                   fecha_vence =  (String) lote.get("fecha_vence");
                   descripcion_lote = (String) lote.get("descripcion");
                   prep.setInt(1, id_lote);
                   prep.setInt(2, producto_id);
                   prep.setString(3,nombre_lote);
                   prep.setString(4,descripcion_lote);
                   prep.setString(5,fecha_vence);
                   prep.executeUpdate();
                } 
            
            
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
     }
     
    
     public int get_cant_servicios(int farmcia, int centro , int servicio)
     {
        int cant =0;
        try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            java.util.Date date = new java.util.Date();
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);
            ResultSet res = null;            
            PreparedStatement prep = null;
            stmt = c.createStatement();  
            String query ="";            
           
             query = "SELECT DISTINCT\n" +
            "config_cod_producto.codigo\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "INNER JOIN config_producto_servicio ON config_producto_servicio.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_servicio ON config_producto_servicio.servicio_id = config_cod_servicio.id ,\n" +
            "config_cod_tipo_centro\n" +
            "INNER JOIN config_tipo_centro_servicio ON config_tipo_centro_servicio.tipo_centro_id = config_cod_tipo_centro.id AND config_tipo_centro_servicio.servicio_id = config_cod_servicio.id ,\n" +
            "config_cod_farmacia\n" +
            "INNER JOIN farmac_asic_servicio ON farmac_asic_servicio.servicio_id = config_cod_servicio.id AND farmac_asic_servicio.farmacia_id = config_cod_farmacia.id AND farmac_asic_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.servicio_id = config_cod_servicio.id AND farmac_asic_producto_existencia.farmacia_id = config_cod_farmacia.id AND farmac_asic_producto_existencia.producto_id = config_cod_producto.id AND farmac_asic_producto_existencia.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN farmac_asic_producto_necesidad ON farmac_asic_producto_necesidad.servicio_id = config_cod_servicio.id AND farmac_asic_producto_necesidad.farmacia_id = config_cod_farmacia.id AND farmac_asic_producto_necesidad.producto_id = config_cod_producto.id AND farmac_asic_producto_necesidad.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_tipo_centro.id = ? AND\n" +
            "config_cod_servicio.id = ? AND\n" +
            "config_cod_farmacia.id = ?";
            prep = c.prepareStatement(query);
            prep.setInt(3,farmcia);
            prep.setInt(1,centro);
            prep.setInt(2,servicio);           
            res = prep.executeQuery();  
            while (res.next()) {
                
                cant++;
            }
           
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
     
        return cant;
     }
     
      public void remove_all(String table)
    {
        try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from " + table ;
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }      
    
    }
      
      
     
      
      public void eliminar_farmacias_duplicadas(int farmacia_id)
      {
      
           try {
            
             Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE  from farmac_asic_producto_existencia where farmac_asic_producto_existencia.farmacia_id <> " + farmacia_id ;
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();         
           
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
      
      }

  
    
    
    
}
