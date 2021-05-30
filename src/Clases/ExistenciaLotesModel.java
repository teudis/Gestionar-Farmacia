/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import BD.DbConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Teudis
 */
public class ExistenciaLotesModel extends  AbstractTableModel {
    
    //datos
         String[] columnNames = {"No.",
                        "Lote",
                        "Fecha Vence",                      
                        "Existencia"
                        
         };
         
         Object[][] data ;
         private ArrayList<Integer> filas ;
         
         
          //Connection c = null;
         Statement stmt = null;

    public ExistenciaLotesModel(String codigo) {
        
         try {
            
            Connection c = DbConexion.getConnection();  
            stmt = c.createStatement();
             // semana del anno
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            Date date = new Date(); 
            calendar.setTime(date);                
            int semana  = calendar.get(Calendar.WEEK_OF_YEAR);            
            calendar.add(Calendar.YEAR, -1);
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");                         
            String fecha_lote = formateador.format(calendar.getTime()).toString();
            ResultSet producto_id = stmt.executeQuery("SELECT \n" +
            "config_cod_producto.id\n" +
            "FROM\n" +
            "config_cod_producto\n" +
            "WHERE\n" +
            "config_cod_producto.codigo = "+ codigo+ " ");
            int id =   Integer.parseInt( producto_id.getString("id"));
            
            ArrayList<Lotes> listado_lotes = new ArrayList<>();
          
            String query = "SELECT\n" +
            "config_cod_lote.nombre,\n" +
            "config_cod_lote.fecha_vence,\n" +
            "config_cod_lote.id\n" +
            "FROM\n" +
            "config_cod_lote\n" +
            "WHERE\n" +
            "config_cod_lote.producto_id = ? \n" +
            "ORDER BY config_cod_lote.fecha_vence DESC";    
             PreparedStatement prep  = null;
             prep = c.prepareStatement(query);
             prep.setInt(1, id);
             ResultSet res = prep.executeQuery();             
             while(res.next())
             {
              
                 String nombre = res.getString("nombre" );
                 String fecha_vence = res.getString("fecha_vence");
                 int id_lote =  res.getInt("id");
                 int existencia = this.get_existencia(id, semana, id_lote, fecha_lote);
                 Lotes lote_actual;
                 lote_actual = new Lotes( nombre, fecha_vence , existencia);
                 listado_lotes.add(lote_actual);
             }
             data = new Object[listado_lotes.size()][4];  
             for (int i = 0; i < listado_lotes.size(); i++) {
                 
                 data[i][0] = i+1;
                 data[i][1] = listado_lotes.get(i).getLote().toUpperCase();
                 data[i][2] = listado_lotes.get(i).getFecha_vence();
                 data[i][3] = listado_lotes.get(i).getExistencia();
             }
             //
             filas = new ArrayList<>();
              
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

    
         @Override
      public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
      
      
      // editar campo necesidad
      public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
          if (col ==3) {
              
              return true;
          }
          else
              return false;
    }
      
      
      // establecer valor
       public void setValueAt(Object value, int row, int col) {
         data[row][col] = value;
         fireTableCellUpdated(row, col);
         //System.out.println(row+"c:"+col);
         filas.add(row);
         
    }


    private int  get_existencia(int id_producto, int semana, int id_lote,String fecha_lote ) {
        
         int cant = 0 ;
         try {
            
            Connection c = DbConexion.getConnection();  
            stmt = c.createStatement();
            PreparedStatement prep  = null;
            String query = "SELECT\n" +
            "farmac_asic_producto_existencia.cantidad_existencia\n" +
            "FROM\n" +
            "farmac_asic_producto_existencia\n" +
            "INNER JOIN config_cod_lote ON farmac_asic_producto_existencia.lote_id = config_cod_lote.id\n" +
            "WHERE\n" +
            "farmac_asic_producto_existencia.semana_estadistica = ? AND\n" +
            "config_cod_lote.fecha_vence >= ? AND\n" +
            "farmac_asic_producto_existencia.producto_id = ?   AND\n" +
            "farmac_asic_producto_existencia.lote_id = ? OR\n" +
            "farmac_asic_producto_existencia.semana_estadistica = ? AND\n" +
            "config_cod_lote.fecha_vence < ?  AND\n" +
            "farmac_asic_producto_existencia.producto_id = ? AND\n" +
            "farmac_asic_producto_existencia.cantidad_existencia > 0 AND\n" +
            "farmac_asic_producto_existencia.lote_id = ?";             
             prep = c.prepareStatement(query);
             prep.setInt(1, semana);
             prep.setString(2, fecha_lote);
             prep.setInt(3, id_producto); 
             prep.setInt(4,id_lote);
             prep.setInt(5, semana);
             prep.setString(6, fecha_lote);
             prep.setInt(7, id_producto); 
             prep.setInt(8,id_lote); 
             ResultSet existencia = prep.executeQuery();
            
             if (existencia.next()) {
                 
                 cant = existencia.getInt("cantidad_existencia");
             }
            
                
            //c.close();
            
        } catch (Exception e) {
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
       return cant; 
        
        
    }

    /**
     * @return the filas
     */
    public ArrayList<Integer> getFilas() {
        return filas;
    }
    
}
