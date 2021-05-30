/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;


import BD.Operaciones;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import Ventanas.Lotes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;




/**
 *
 * @author Teudis
 */
public class ButtonEditor extends DefaultCellEditor {
    
   protected JButton btn;
	 private String lbl;
	 private Boolean clicked;
         private  String codigo ;
         private  Boolean lote ;
         private String descripcion;
         private  Lotes l ;
         private JTable datos;
         private JTextField valor_existencia;
         private Operaciones op ;
         private FarmaciaTableModel existencia_farmacia;
         private int fila ;
         private int columna;
          ExistenciaLotesModel existencia_lotes;
	 
	 public ButtonEditor(JTextField txt,Lotes l , FarmaciaTableModel dtm) {
		super(txt);
		setClickCountToStart(1);
		btn=new JButton();
		btn.setOpaque(true);
                btn.setText("Editar");                
		this.l = l;
                this.existencia_farmacia = dtm;                
                this.fila = 0;
                this.columna = 4;
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
		 lbl="Editar";
		 btn.setText(lbl);
		 clicked=true;                  
                 lote =  (Boolean) table.getValueAt(row, 5);
                 codigo = table.getValueAt(row, 1).toString(); 
                 descripcion = table.getValueAt(row, 2).toString(); 
                 fila = row;                 
		 return btn;
	}
	 
	//IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
	 @Override
	public Object getCellEditorValue() {

		 if(clicked)
			{
			//SHOW US SOME MESSAGE
				//JOptionPane.showMessageDialog(btn, lbl+" Clicked");
                            
                          // producto con lotes
                          if (lote==true) {
                              
                              
                              existencia_lotes = new ExistenciaLotesModel(codigo);
                             
                             datos = new JTable(existencia_lotes);
                             datos.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
                             DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
                            singleclick.setClickCountToStart(1);
                            datos.setDefaultEditor(datos.getColumnClass(3), singleclick);
                            datos.setFont(new Font("Arial", Font.ITALIC, 13));
                            datos.setRowHeight(30);        
                            datos.getColumnModel().getColumn(0).setPreferredWidth(50);       
                            datos.getColumnModel().getColumn(1).setPreferredWidth(250);
                            datos.getColumnModel().getColumn(2).setPreferredWidth(100);
                            datos.getColumnModel().getColumn(3).setPreferredWidth(100);
                            datos.setPreferredScrollableViewportSize(new Dimension(500, 90));
                            JScrollPane contenedor = new JScrollPane(datos);
                            datos.setFillsViewportHeight(true);  
                            
                            JPanel panel = new JPanel(); 
                            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                            JButton b1 = new JButton("Guardar");     
                            b1.addActionListener(new ActionListener() {
 
                                @Override
                                public void actionPerformed(ActionEvent e)
                                {
                                    //Guardar lotes
                                   //int filas =  datos.getRowCount();
                                   //int columnas = datos.getColumnCount();
                                   int existencia = 0;
                                   String nombre_lote ="";
                                   String fecha_vence = "";
                                   op = new Operaciones();
                                   // semana del anno
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setFirstDayOfWeek( Calendar.MONDAY);
                                    calendar.setMinimalDaysInFirstWeek( 4 );
                                    Date date = new Date();
                                    calendar.setTime(date);
                                    int semana  = calendar.get(Calendar.WEEK_OF_YEAR);
       
                                    //Nueva modificacion 
                                    ArrayList <Integer> info_filas = existencia_lotes.getFilas();
                                    //System.out.println(info_filas.size());
                                    for (int i = 0; i <info_filas.size() ; i++) {
                                        
                                        nombre_lote =  datos.getValueAt(info_filas.get(i),1).toString().toLowerCase();
                                        existencia =  Integer.parseInt(datos.getValueAt(info_filas.get(i),3).toString());
                                        fecha_vence = datos.getValueAt(info_filas.get(i),2).toString().toLowerCase();
                                        op.existencia_lotes(nombre_lote,existencia,semana, codigo, fecha_vence);
                                        nombre_lote = "";
                                        existencia = 0;
                                        
                                    }
                                    
                                     
                                    
                                     int total = op.get_total_producto_lote(codigo);
                                   
                                   l.dispose();
                                   //actualizar cambios en la table de existencia en farmacia con lote
                                    existencia_farmacia.setValueAt(total, fila, columna);
                                    JOptionPane.showMessageDialog(btn," Datos guardados"); 
                                   
                                }
                            }); 
                             //panel.add(contenedor);                            
                             panel.add(b1);
                             
                             l = new Lotes(new javax.swing.JFrame(), true);
                             l.setLayout(new BorderLayout());
                             l.add(contenedor,BorderLayout.CENTER);
                             l.add(panel, BorderLayout.SOUTH);
                             l.setVisible(true);
                             
                            }
                            else
                            {
                                // no tiene lote
                                
                                
                                JPanel panel = new JPanel(); 
                                panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                                op = new Operaciones();
                                int valor_producto = op.get_valor(codigo);                               
                                valor_existencia = new JTextField(10);
                                valor_existencia.setText( String.valueOf(valor_producto));
                                JPanel panel_boton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                                JButton b1 = new JButton("Guardar");  
                                JLabel texto_existencia = new JLabel("Cantidad existencia:");
                                b1.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                       
                                      int valor = Integer.parseInt( valor_existencia.getText());                                      
                                      Calendar calendar = Calendar.getInstance();
                                    calendar.setFirstDayOfWeek( Calendar.MONDAY);
                                    calendar.setMinimalDaysInFirstWeek( 4 );
                                    Date date = new Date();
                                    calendar.setTime(date);
                                    int semana_actual  = calendar.get(Calendar.WEEK_OF_YEAR);
                                    op = new Operaciones();
                                    op.existencia_no_lotes(valor, semana_actual, codigo);
                                    int total = op.get_total_producto_nolote(codigo);
                                    l.dispose();
                                    //actualizar cambios en la table de existencia en farmacia con lote
                                    existencia_farmacia.setValueAt(total, fila, columna);
                                    JOptionPane.showMessageDialog(btn," Datos guardados"); 
                                        
                                    }
                                });
                                panel.add(texto_existencia);
                                panel.add(valor_existencia);
                                panel_boton.add(b1);
                                l = new Lotes(new javax.swing.JFrame(), true);
                                l.setSize(300,130);
                                l.setTitle(descripcion);
                                l.add(panel,BorderLayout.NORTH);
                                l.add(panel_boton, BorderLayout.SOUTH);
                                l.setVisible(true);
                                       
                            }
                            
                            
                                
			}
		//SET IT TO FALSE NOW THAT ITS CLICKED
		clicked=false;
	  return new String(lbl);
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
    
    
}
