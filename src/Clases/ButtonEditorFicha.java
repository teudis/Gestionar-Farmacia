/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import BD.Operaciones;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Teudis
 */
public class ButtonEditorFicha extends DefaultCellEditor {
    
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private  String codigo ;
    Statement stmt = null;

    public ButtonEditorFicha(JTextField textField) {
                super(textField);
                setClickCountToStart(1);
		btn=new JButton();
		btn.setOpaque(true);
                //btn.setText("Ficha");               
		
		//WHEN BUTTON IS CLICKED
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fireEditingStopped();
			}
		});
        
    }
    
     //OVERRIDE A COUPLE OF METHODS
	 @Override
	public Component getTableCellEditorComponent(JTable table, Object obj,
			boolean selected, int row, int col) {

			//SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
		 lbl="Ficha";
		// btn.setText(lbl);
                try {
                    Image img = ImageIO.read(getClass().getResource("/images/pdf.gif"));
                     btn.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    Logger.getLogger(ButtonEditorFicha.class.getName()).log(Level.SEVERE, null, ex);
                }
		 clicked=true; 
                 codigo = table.getValueAt(row, 1).toString(); 
		 return btn;                
                 
	}
        
         @Override
	public boolean stopCellEditing() {

	       //SET CLICKED TO FALSE FIRST
			clicked=false;
		return super.stopCellEditing();
	}
	 
	 @Override
	protected void fireEditingStopped() {
		// TODO Auto-generated method stub
		super.fireEditingStopped();
	}
        
   @Override
    public void setClickCountToStart(int cantidad)
    {
      super.setClickCountToStart(cantidad);
    }
    
    
     @Override
	public Object getCellEditorValue() {

		 if(clicked)
			{
                             JasperReport reporte = null;
                             Connection c = null;
                            try {
                                 Class.forName("org.sqlite.JDBC");
                                 c = DriverManager.getConnection("jdbc:sqlite:medicamentos.db");
                                 stmt = c.createStatement();
                                 String query = "SELECT DISTINCT\n" +
                                "config_cod_producto.oficial_id,\n" +
                                "(SELECT\n" +
                                " count(config_cod_producto.codigo)\n" +
                                "FROM\n" +
                                "config_cod_lote\n" +
                                "INNER JOIN config_cod_producto ON config_cod_lote.producto_id = config_cod_producto.id\n" +
                                "WHERE\n" +
                                "config_cod_producto.codigo = ?) AS tratamiento_lote,\n" +
                                "(SELECT\n" +
                                "Sum(farmac_asic_producto_existencia.cantidad_existencia) AS suma\n" +
                                "FROM\n" +
                                "farmac_asic_producto_existencia\n" +
                                "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
                                "WHERE config_cod_producto.codigo = ?\n" +
                                "GROUP BY\n" +
                                "farmac_asic_producto_existencia.semana_estadistica\n" +
                                "ORDER BY\n" +
                                "farmac_asic_producto_existencia.semana_estadistica DESC\n" +
                                "LIMIT 1\n" +
                                ") as total\n" +
                                "FROM\n" +
                                "farmac_asic_producto_existencia\n" +
                                "INNER JOIN config_cod_producto ON farmac_asic_producto_existencia.producto_id = config_cod_producto.id\n" +
                                "where config_cod_producto.codigo = ?";
                                 
                                 ResultSet res ;
                                 PreparedStatement prep  = null;
                                 prep = c.prepareStatement(query);
                                 prep.setString(1 ,codigo); 
                                 prep.setString(2 ,codigo); 
                                 prep.setString(3 ,codigo); 
                                 res = prep.executeQuery();
                                 int oficial_id = 0;
                                 int tratamento_lote = 0;
                                 int cantidad_total =0;
                                 
                                 while (res.next()) {                                    
                                    
                                     oficial_id = res.getInt("oficial_id");
                                     tratamento_lote = res.getInt("tratamiento_lote");
                                     cantidad_total = res.getInt("total");
                                }
                                 
                                
                                // datos a pasar (reporte, parametro, conexion)
                                Calendar calendario = Calendar.getInstance();
                                calendario.setFirstDayOfWeek( Calendar.MONDAY);
                                calendario.setMinimalDaysInFirstWeek( 4 );
                                Date date = new Date();
                                calendario.setTime(date);                
                                int semana  = calendario.get(Calendar.WEEK_OF_YEAR);
                                String semana_actual =  Integer.toString(semana);
                                Map<String,Object> parametros = new HashMap<String,Object>();
                               
                                if (oficial_id ==0) {
                                    
                                    parametros.put("oficial",new String("No"));
                                }
                                else
                                {
                                
                                    parametros.put("oficial",new String("Si"));
                                
                                }
                                
                                if (tratamento_lote > 0) {
                                    
                                    parametros.put("tratamiento",new String("Si"));
                                }
                                else
                                {
                                
                                 parametros.put("tratamiento",new String("No"));
                                
                                }
                                
                                //System.out.println(codigo);
                                parametros.put("cod_producto",codigo);   
                                parametros.put("semana_actual",semana_actual);  
                                URL in = null;
                                if (cantidad_total==0)
                                {
                                    // reporte sin datos
                                  in = getClass().getResource("/Reportes/ProductoReporte_no_existencia.jasper"); 
                                }
                                else
                                if (tratamento_lote > 0 && cantidad_total > 0 ) {
                                    in = getClass().getResource("/Reportes/ProductoReporte.jasper"); 
                                }
                                else
                                {
                                    String total =  Integer.toString( cantidad_total);
                                    parametros.put("cantidad_total",total );  
                                    in = getClass().getResource("/Reportes/ProductoReporte_no_Lotes.jasper"); 
                                }
                                
                                
                                reporte = (JasperReport) JRLoader.loadObject(in);
                                JasperPrint jasperPrint;
                                jasperPrint = JasperFillManager.fillReport(reporte,(Map<String,Object>)parametros, c);      
                                //JasperFillManager.fi
                                JasperViewer.viewReport(jasperPrint,false);
                                
                            } catch (Exception e) {
                                
                                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                                JOptionPane.showMessageDialog(null,  e.getClass().getName() + ": " + e.getMessage(), "Report Error",JOptionPane.ERROR_MESSAGE);
                                //System.exit(0);
                            }
                            
                            
			//SHOW US SOME MESSAGE
		        // JOptionPane.showMessageDialog(btn, lbl+" Clicked");
                                
			}
		//SET IT TO FALSE NOW THAT ITS CLICKED
		clicked=false;
	      return new String(lbl);
	}
    
}
