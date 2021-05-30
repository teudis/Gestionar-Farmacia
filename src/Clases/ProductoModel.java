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
public class ProductoModel extends  AbstractTableModel {
    
    
    //datos
         String[] columnNames = {
                        "No.",
                        "Código",
                        "Descripción",
                        "U/M",
                        "Ficha"
                        };
         Object[][] data ;
         
         //Connection c = null;
         Statement stmt = null;

    public ProductoModel() {
     
        
         try {
            
            Connection c = DbConexion.getConnection(); 
            stmt = c.createStatement();
            ResultSet cantidad_registros = stmt.executeQuery("SELECT\n" +
"count (config_cod_producto.codigo) as cantidad\n" +
"\n" +
"FROM\n" +
"config_cod_producto\n" +
"INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id");
            int cantidad = cantidad_registros.getInt("cantidad");            
            ResultSet res = stmt.executeQuery("SELECT\n" +
"config_cod_producto.codigo,\n" +
"config_cod_producto.descripcion,\n" +
"config_cod_unidad_medida.nombre as medida\n" +
"FROM\n" +
"config_cod_producto\n" +
"INNER JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id");
                
              data = new Object[cantidad][5]; 
             int pos =  0;
             while (res.next()) {  
                
                 data[pos][0] = pos+1;  
                 data[pos][1] = res.getString("codigo" );
                 data[pos][2] = res.getString("descripcion").toUpperCase();
                 data[pos][3] = res.getString("medida").toUpperCase();
                 pos++;
                 
             }
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            
        } 
        
    }
    
     public ProductoModel(ArrayList <Productos> productos) 
     {
          data = new Object[productos.size()][5]; 
          for (int i = 0; i < productos.size(); i++) {
             
            data[i][0] = i+1;  
            data[i][1] = productos.get(i).getCodigo();
            data[i][2] = productos.get(i).getDescripcion().toUpperCase();
            data[i][3] = productos.get(i).getMedida().toUpperCase();
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
    
     public String getColumnName(int col) {
        return columnNames[col];
    }
     
         @Override
       public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
          if ( col==4) {
              
              return true;
          }
          else
              return false;
    }
     
     
    
}
