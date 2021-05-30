/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import BD.DbConexion;
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
public class NecesidadFarmaciaModel extends  AbstractTableModel {
    
     //datos
         String[] columnNames = {"No.",
                        "Código",
                        "Descripción",
                        "U/M",
                        "Necesidad", 
                        "Lugar"
                        
                        };
         Object[][] data ;
         
         //Connection c = null;
         Statement stmt = null;

         
   public NecesidadFarmaciaModel(ArrayList<Productos> productos)
   {
       data = new Object[productos.size()][7]; 
       for (int i = 0; i < productos.size(); i++) {
           
            data[i][0] = i+1;
            data[i][1] =  productos.get(i).getCodigo();
            data[i][2] =  productos.get(i).getDescripcion().toUpperCase();
            data[i][3] =  productos.get(i).getMedida().toUpperCase();   
            data[i][4] =  productos.get(i).getStock_maximo();
           
       }
       
   
   }
         
    public NecesidadFarmaciaModel() {
        
         try {
            
            Connection c = DbConexion.getConnection(); 
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT\n" +
"count (config_cod_producto.codigo) as cantidad\n" +
"FROM\n" +
"farmac_asic_producto_necesidad\n" +
"INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
"INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id");
            int cantidad = cantidad_registros.getInt("cantidad");            
            ResultSet res = stmt.executeQuery("SELECT\n" +
            "config_cod_producto.codigo,\n" +
            "config_cod_producto.descripcion,\n" +
            "config_cod_unidad_medida.nombre as unidad_medida,\n" +
            "farmac_asic_producto_necesidad.stock_maximo AS necesidad,\n" +
            "config_cod_servicio.nombre as lugar\n" +
            "FROM\n" +
            "farmac_asic_producto_necesidad\n" +
            "INNER JOIN config_cod_producto ON farmac_asic_producto_necesidad.producto_id = config_cod_producto.id\n" +
            "INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "LEFT JOIN config_cod_servicio ON farmac_asic_producto_necesidad.servicio_id = config_cod_servicio.id\n" +
            "ORDER BY config_cod_producto.descripcion ASC");

              data = new Object[cantidad][6]; 
             int pos =  0;
             String valor_necesidad;
             //String []necesidad_mostrar;
             while (res.next()) {                 
                 
                 
                 data[pos][0] = pos+1;
                 data[pos][1] = res.getString("codigo" );
                 data[pos][2] = res.getString("descripcion").toUpperCase();
                 data[pos][3] = res.getString("unidad_medida").toUpperCase();  
                 valor_necesidad = res.getString("necesidad");
                 
                 if ( valor_necesidad.charAt(valor_necesidad.length()-1) != '0') {
                    
                     data[pos][4] = valor_necesidad;
                 }
                 else
                 {
                     String subcadena = valor_necesidad.substring(0, valor_necesidad.length()-2);
                    data[pos][4] = subcadena;
                 }
                 
                  if (res.getString("lugar")== null) {
                    
                      data[pos][5] = "FARMACIA";
                  }
                  else
                  {
                    data[pos][5] = res.getString("lugar");
                  
                  }
                 
                 pos++;
                 
             }
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
        
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
    
}
