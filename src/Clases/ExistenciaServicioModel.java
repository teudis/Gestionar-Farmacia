/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import BD.DbConexion;
import BD.Operaciones;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Teudis
 */
public class ExistenciaServicioModel extends  AbstractTableModel {
    
    
    //datos
         String[] columnNames = {"No.",
                        "Producto",
                        "U/M",
                        "Stock Máximo",
                        "Stock Mínimo",
                        "Existencia"
                        
                        };
         Object[][] data ;
         
         private ArrayList<Integer> filas ;
         //Connection c = null;
         Statement stmt = null;
         Operaciones op;

    public ExistenciaServicioModel(int farmacia_id,int centro_id,int servicio_id) {
        
        try {
            
            Connection c = DbConexion.getConnection(); 
            c.setAutoCommit(false);
            ResultSet res = null; 
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            Date date = new Date();
            calendar.setTime(date);   
            PreparedStatement prep = null;
            stmt = c.createStatement();  
            
            String query =""; 
             
             query = "SELECT DISTINCT\n" +
            "config_cod_producto.descripcion as producto,\n" +
            "farmac_asic_producto_necesidad.stock_maximo,\n" +
            "farmac_asic_producto_necesidad.stock_minimo,\n" +
            "config_cod_unidad_medida.nombre as medida ,\n" +
            "config_cod_producto.id \n" +
            "FROM\n" +
            "config_cod_producto ,\n" +
            "config_cod_tipo_centro ,\n" +
            "config_cod_servicio\n" +
            "INNER JOIN config_tipo_centro_servicio ON config_tipo_centro_servicio.tipo_centro_id = config_cod_tipo_centro.id AND config_tipo_centro_servicio.servicio_id = config_cod_servicio.id\n" +
            "INNER JOIN config_producto_servicio ON config_producto_servicio.servicio_id = config_cod_servicio.id AND config_producto_servicio.producto_id = config_cod_producto.id ,\n" +
            "config_cod_farmacia\n" +
            "INNER JOIN farmac_asic_servicio ON farmac_asic_servicio.servicio_id = config_cod_servicio.id AND farmac_asic_servicio.farmacia_id = config_cod_farmacia.id AND farmac_asic_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN farmac_asic_producto_necesidad ON farmac_asic_producto_necesidad.servicio_id = config_cod_servicio.id AND farmac_asic_producto_necesidad.farmacia_id = config_cod_farmacia.id AND farmac_asic_producto_necesidad.producto_id = config_cod_producto.id AND farmac_asic_producto_necesidad.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_tipo_centro.id = ? AND\n" +
            "config_cod_servicio.id = ? AND           \n" +
            "config_cod_farmacia.id = ? \n" +
            "ORDER BY\n" +
            "config_cod_producto.descripcion ASC";
           
            prep = c.prepareStatement(query);
            //prep.setInt(3,semana);
            prep.setInt(3,farmacia_id);
            prep.setInt(1,centro_id);
            prep.setInt(2,servicio_id);           
            res = prep.executeQuery();
            int cantidad = 0;
            while (res.next()) {
              cantidad++;// not rs.first() because the rs.next() below will move on, missing the first element
            }          
            
             query = "SELECT DISTINCT\n" +
            "config_cod_producto.descripcion as producto,\n" +
            "farmac_asic_producto_necesidad.stock_maximo,\n" +
            "farmac_asic_producto_necesidad.stock_minimo,\n" +
            "config_cod_unidad_medida.nombre as medida ,\n" +
            "config_cod_producto.id \n" +
            "FROM\n" +
            "config_cod_producto ,\n" +
            "config_cod_tipo_centro ,\n" +
            "config_cod_servicio\n" +
            "INNER JOIN config_tipo_centro_servicio ON config_tipo_centro_servicio.tipo_centro_id = config_cod_tipo_centro.id AND config_tipo_centro_servicio.servicio_id = config_cod_servicio.id\n" +
            "INNER JOIN config_producto_servicio ON config_producto_servicio.servicio_id = config_cod_servicio.id AND config_producto_servicio.producto_id = config_cod_producto.id ,\n" +
            "config_cod_farmacia\n" +
            "INNER JOIN farmac_asic_servicio ON farmac_asic_servicio.servicio_id = config_cod_servicio.id AND farmac_asic_servicio.farmacia_id = config_cod_farmacia.id AND farmac_asic_servicio.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN farmac_asic_producto_necesidad ON farmac_asic_producto_necesidad.servicio_id = config_cod_servicio.id AND farmac_asic_producto_necesidad.farmacia_id = config_cod_farmacia.id AND farmac_asic_producto_necesidad.producto_id = config_cod_producto.id AND farmac_asic_producto_necesidad.tipo_centro_id = config_cod_tipo_centro.id\n" +
            "LEFT JOIN config_cod_unidad_medida ON config_cod_producto.unidad_medida_id = config_cod_unidad_medida.id AND config_cod_producto.unidad_medida_almacen_id = config_cod_unidad_medida.id\n" +
            "WHERE\n" +
            "config_cod_tipo_centro.id = ? AND\n" +
            "config_cod_servicio.id = ? AND           \n" +
            "config_cod_farmacia.id = ? \n" +
            "ORDER BY\n" +
            "config_cod_producto.descripcion ASC" ;
            
            prep = c.prepareStatement(query);           
            //prep.setInt(3,semana);
            prep.setInt(3,farmacia_id);
            prep.setInt(1,centro_id);
            prep.setInt(2,servicio_id);            
            res = prep.executeQuery();
            data = new Object[cantidad][6];
            int pos =  0;
            String stock_maximo = "";
            String stock_minimo = "";
            int existencia = 0;
            String medida = "par";
            while (res.next()) {  
                 
                 data[pos][0] = pos+1;
                 data[pos][1] = res.getString("producto").toUpperCase();
                 if (res.getString("medida")== null) {
                    
                      data[pos][2] = medida.toUpperCase();
                }
                 else
                 {
                   data[pos][2] = res.getString("medida").toUpperCase();
                 }
                
                 
                 if (res.getString("stock_maximo") != null) {
                    
                     stock_maximo = res.getString("stock_maximo");
                     if (stock_maximo.charAt(stock_maximo.length()-1)=='0') {
                         
                         stock_maximo = stock_maximo.substring(0, stock_maximo.length()-2);
                     } 
                }
                 data[pos][3] = stock_maximo;
                 if (res.getString("stock_minimo")!= null) {
                    
                     stock_minimo = res.getString("stock_minimo");
                     if (stock_minimo.charAt(stock_minimo.length()-1)=='0') {
                         
                         stock_minimo = stock_minimo.substring(0, stock_minimo.length()-2);
                     }
                }
                 data[pos][4] = stock_minimo;
                 //buscar existencia
                 op = new Operaciones();
                 int producto_id  = res.getInt("id");
                 existencia = op.get_existencia_servicio(farmacia_id,centro_id, servicio_id,producto_id );
                
                
                 data[pos][5] = existencia;
                 //System.out.println(pos);
                 pos++;
            }
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
      public boolean isCellEditable( int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
          
          if ( col==5) {
              
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
         filas.add(row);
         
    }

    /**
     * @return the filas
     */
    public ArrayList<Integer> getFilas() {
        return filas;
    }
     
    
    
}
