/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import BD.DbConexion;
import BD.Operaciones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Teudis
 */
public class FarmaciaTableModel extends  AbstractTableModel{

    //datos
         String[] columnNames = {"No.",
                        "Código",
                        "Descripción",
                        "U/M",
                        "Existencia",
                        "Lote",
                        "Operación"
         };
         
         Object[][] data ;
         Operaciones op;
          
         Statement stmt = null;

    public FarmaciaTableModel() {
        
         try {
            
            Connection c = DbConexion.getConnection();    
            //c.setAutoCommit(false);
            stmt = c.createStatement();                     
            //PreparedStatement prep  = null; 
            //String query;
             ResultSet res ;            
           
            int cantidad;            
            res = stmt.executeQuery("SELECT DISTINCT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre AS medida,\n" +
            "(Select count(*) from config_cod_producto) AS cantidad\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "LEFT JOIN farmac_asic_producto_existencia ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
            "LEFT JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN config_cod_servicio ON farmac_asic_producto_existencia.servicio_id = config_cod_servicio.id\n" +
            "LEFT JOIN config_producto_servicio ON config_producto_servicio.servicio_id = config_cod_servicio.id AND config_producto_servicio.producto_id = config_cod_producto.id\n" +
            "LEFT JOIN farmac_asic_servicio ON farmac_asic_servicio.servicio_id = config_cod_servicio.id") ;
            
             cantidad = res.getInt("cantidad");
             
             
              data = new Object[cantidad][7]; 
              boolean lote = false;
              int producto_id;
              int pos =  0;
             while (res.next()) {  
                 
                 data[pos][0] = pos+1;
                 data[pos][1] = res.getString("codigo" );
                 data[pos][2] = res.getString("descripcion").toUpperCase();
                 data[pos][3] = res.getString("medida");
                 
                 String codigo =  res.getString("codigo" );
                 int existencia =0;
                 op = new Operaciones();
                 producto_id =  this.get_producto_id(codigo); 
                 ///int existencia = this.get_existencia(producto_id);
                 
                 //data[pos][4] = existencia;                 
                 lote = this.Lote(producto_id);
                 if (lote) {
                     
                    
                     existencia = op.get_total_producto_lote(codigo);
                     data[pos][4] = existencia;                    
                     data[pos][5] =  true;
                 }
                 else
                 {
                  existencia = op.get_total_producto_nolote(codigo);
                   data[pos][4] = existencia;                   
                   data[pos][5] =  false;
                 }
                 pos++;
                 
             }
            
            
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        }
        
    }

   
    public FarmaciaTableModel(ArrayList <Productos> productos)
    {
    
        data = new Object[productos.size()][7]; 
        boolean lote = false;
        int producto_id = 0;
        String medida_nombre = "";
         for (int i = 0; i < productos.size(); i++) {
             
                 data[i][0] = i+1;
                 data[i][1] = productos.get(i).getCodigo();
                 data[i][2] = productos.get(i).getDescripcion().toUpperCase();
                 // buscar medida
                 int medida = productos.get(i).getMedida_id();
                 medida_nombre = this.get_medida(medida);
                 data[i][3] = medida_nombre.toUpperCase();
                 producto_id = productos.get(i).getProducto_id();
                 int existencia = 0;
                 op = new Operaciones();
                
                 lote = this.Lote(producto_id);
                 if (lote) {
                     
                     existencia = op.get_total_producto_lote(productos.get(i).getCodigo());
                     data[i][4] = existencia;  
                     data[i][5] =  true;
                 }
                 else
                 {
                   existencia = op.get_total_producto_nolote(productos.get(i).getCodigo());
                   data[i][4] = existencia;  
                   data[i][5] =  false;
                 }
                
            }
    
    }
    
   
    
    private int get_existencia(int producto_id)
    {
      int resultado =0;
      
       try {
            
            Connection c = DbConexion.getConnection();    
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT\n" +
            "farmac_asic_producto_existencia.cantidad_existencia\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.producto_id ="+producto_id+" ");
                      
             if (cantidad_registros.next()) {
               
                 resultado = cantidad_registros.getInt("cantidad_existencia");
           }
 
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
    
      return resultado;
    }
     
    private boolean Lote(int producto_id)
    {
       boolean resultado = false;
        
       try {
            
            Connection c = DbConexion.getConnection();     
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT\n" +
            "count (config_cod_lote.producto_id) as lote\n" +
            "FROM\n" +
            "config_cod_lote\n" +
            "WHERE\n" +
            "config_cod_lote.producto_id ="+producto_id+" ");
            int cantidad = cantidad_registros.getInt("lote");            
           if (cantidad>0) {
               
               resultado = true;
           }
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
       return resultado; 
    
    }
         
    
    
    @Override
    public int getRowCount() {
       
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex]; 
    }
    
         @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

   
    
         @Override
      public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
      
     // editar campo necesidad
      public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
          if ( col==6) {
              
              return true;
          }
          else
              return false;
    }
      
      
      // establecer valor
         @Override
       public void setValueAt(Object value, int row, int col) {
         data[row][col] = value;
         fireTableCellUpdated(row, col);
         
    }

    private int get_producto_id(String codigo) {
        
        int producto_id = 0 ;
         try {
            
            Connection c = DbConexion.getConnection();    
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT \n" +
            "config_cod_producto.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = "+codigo+" ");
            
            producto_id = cantidad_registros.getInt("id");
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
       return producto_id; 
        
    }

    private String get_medida(int medida) {
        
        String medida_nombre = null;
         try {
            
            Connection c = DbConexion.getConnection();    
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT\n" +
            "config_cod_unidad_medida.nombre\n" +
            "FROM\n" +
            "config_cod_unidad_medida\n" +
            "WHERE\n" +
            "config_cod_unidad_medida.id = "+medida+" ");
            
            medida_nombre = cantidad_registros.getString("nombre");
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
       return medida_nombre; 
        
        
    }
      
      
    
}
