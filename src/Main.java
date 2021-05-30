
import BD.Conexion;
import BD.Operaciones;
import Clases.ButtonEditor;
import Clases.ButtonEditorFicha;
import Clases.ButtonRenderer;
import Clases.ButtonRendererFicha;
import Clases.ExistenciaExportar;
import Clases.ExistenciaServicioModel;
import Clases.Farmacia;
import Clases.FarmaciaTableModel;
import Clases.ImportarDatos;
import Clases.NecesidadDataSource;
import Clases.NecesidadFarmaciaModel;
import Clases.ProductoModel;
import Clases.Productos;
import Clases.Servicios;
import Clases.TipoCentro;
import Ventanas.Bprogreso;
import Ventanas.BuscadorExistencia;
import Ventanas.BuscadorNecesidad;
import Ventanas.BuscadorProductos;
import Ventanas.ExportarDatos;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Ventanas.Lotes;
import Ventanas.SeleccionServicio;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;







/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Annie&Teudis
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    
      Lotes l = null ;
      Conexion bd ;
      JTable producto_servicios  = null;
      JTextArea areaDeTexto;
      private JPanel panel1;
      private JPanel necesidad;
      private JPanel existencia_farmacia;
      private JPanel existencia_servicio;
      private JComboBox Jcentro;
      private  JComboBox Jfarmacia;
      private JComboBox Jservicio;
      ArrayList<TipoCentro> centros ;
      ArrayList<Servicios> servicios ;
      String [] nombre_servicio;
      ArrayList<Farmacia> f ;
      int [] seleccionados = new int[3] ;
      Operaciones op_selec ;
      JScrollPane scrollPane_servicios;
      JButton bguardar;
      BuscadorProductos buscador_producto;
      BuscadorExistencia buscador_existencia;
      BuscadorNecesidad buscador_necesidad;
      ExistenciaServicioModel eservicio;
    
    public Main() {
        
        Conexion bd = new Conexion();
        //bd.update_database();
        //bd.update_year();
        initComponents();         
        setIconImage (new ImageIcon(getClass().getResource("/images/icon3.png")).getImage());
        setLocationRelativeTo(null);
        setTitle("FARMACIA COLAVEN");
        jMenu3.setVisible(false);
        jMenu4.setVisible(false);
        Operaciones op = new Operaciones();
        
        int cantidad = op.cant_registros();  
        if(cantidad>0)
        {
        //setResizable(false);
        // Productos
        //int farmcia_actual =op.get_id_farmacia();
        //op.eliminar_farmacias_duplicadas(farmcia_actual);
        createTab1();        
        jTabbedPane3.addTab("Productos", panel1);
        
        // create tab necesidad
          createTab2();      
        jTabbedPane3.addTab("Necesidad Farmacia", necesidad);
        
        // Crear existencia en farmacia
        createTab3();
        jTabbedPane3.addTab("Existencia Farmacia", existencia_farmacia);
        // Existencia en servicio
        createTab4();
       jTabbedPane3.addTab("Existencia Servicio", existencia_servicio);
        
        }
        else
        {
          //mensaje de inserciones
                 Component btn = null;
                  jTabbedPane3.enable(false);
                JOptionPane.showMessageDialog(btn," Debe importar los datos para poder trabajar"); 
        }
        buscador_producto  = new BuscadorProductos(this, true);
        buscador_existencia = new BuscadorExistencia(this, true);
        buscador_necesidad = new BuscadorNecesidad(this, true);
    }
    
  
    
    private void importar_datos()
    {
         String filejson = "" ;
        JFileChooser fileChooser = new JFileChooser();
        //Indicamos lo que podemos seleccionar
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.ZIP", "zip");
 
        //Le indicamos el filtro
        fileChooser.setFileFilter(filtro);
            int seleccion = fileChooser.showOpenDialog(this);
       if (seleccion == JFileChooser.APPROVE_OPTION)
        {
            // Selecionamos el fichero
            File archivoZIP = fileChooser.getSelectedFile();
            try {
                // Creamos el fichero zip
                ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZIP));
                zis.getNextEntry();                 
                Scanner sc = new Scanner(zis);
                while (sc.hasNextLine()) {
                    
                    filejson = sc.nextLine();
                }
                
                zis.close();
                
                //procesamiento json
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(filejson);
                JSONObject jsonObject = (JSONObject) obj;
                // Mostar Ventana con barra de progreso
                Bprogreso bprogreso =new Bprogreso(null, true);
                ImportarDatos importar =  new ImportarDatos(bprogreso,jsonObject);
                importar.execute();
                bprogreso.setVisible(true);
                if (importar.getCorrecto()== false) {
                    
                    JOptionPane.showMessageDialog(null,"ERROR,IMPORTANDO DATOS DE OTRA FARMACIA"); 
                }
                else
                {
                // fin de procesamiento json
                // activar tabs y mostrarlos
                jTabbedPane3.removeAll();
                 // Productos
                createTab1();        
                jTabbedPane3.addTab("Productos", panel1);

                // create tab necesidad
                  createTab2();      
                jTabbedPane3.addTab("Necesidad Farmacia", necesidad);

                // Crear existencia en farmacia
                createTab3();
                jTabbedPane3.addTab("Existencia Farmacia", existencia_farmacia);
                // Existencia en servicio
                createTab4();
               jTabbedPane3.addTab("Existencia Servicio", existencia_servicio);
               
                
                // activar JTAb
                jTabbedPane3.enable(true);
                
                //mensaje de inserciones
                
                JOptionPane.showMessageDialog(null," Datos importados correctamente"); 
                
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        
        
    
    }
    
    private void exportar_datos()
    {
        ExportarDatos exportarmodal = new ExportarDatos(this, true);
        exportarmodal.pack();
        exportarmodal.setLocationRelativeTo(null);
        exportarmodal.setVisible(true);
        
        
         ArrayList<ExistenciaExportar> datos = exportarmodal.get_listado();
         JSONObject obj = new JSONObject();
         JSONArray listado_existencia = new JSONArray();
         
         for (int i = 0; i < datos.size(); i++) {
                
             obj.put("asic_id", datos.get(i).getAsic_id());
             obj.put("tipo_centro_id", datos.get(i).getTipo_centro_id());
             obj.put("servicio_id", datos.get(i).getServicio_id());
             obj.put("producto_id", datos.get(i).getProducto_id());
             obj.put("lote_id", datos.get(i).getLote_id());
             obj.put("semana_estadistica", datos.get(i).getSemana_estadistica());
             obj.put("cantidad_existencia", datos.get(i).getCantidad_existencia());
             obj.put("farmacia_id", datos.get(i).getFarmacia_id());
             listado_existencia.add(obj);
             obj = new JSONObject();
             
            }
         
         Operaciones op = new Operaciones();
         JSONObject mainObj = new JSONObject();
          
         // get asic
         
         // get farmacia
         ArrayList<Farmacia> farmacia = op.get_farmacias();
         JSONObject provincia = new JSONObject();         
         provincia.put("provincia_id", farmacia.get(0).getProvincia_id());
         // asic         
         JSONObject asic = new JSONObject();         
         asic.put("asic_id", farmacia.get(0).getAsic_id());
         // farmacia 
         JSONObject f = new JSONObject();   
         f.put("farmacia_id", farmacia.get(0).getId());  
         
         JSONObject version = new JSONObject();
         mainObj.put("cod_provincia", provincia);
         mainObj.put("cod_asic", asic);
         mainObj.put("cod_farmacia", f);
         version.put("version", "0.1.0");
         mainObj.put("version", version);
         mainObj.put("farmac_asic_producto_existencia", listado_existencia);
         
          try {
              
              // Comprimir fichero
              
              // guardar
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();
            
            if(guarda !=null)
            {
             /*guardamos el archivo y le damos el formato directamente,
              * si queremos que se guarde en formato doc lo definimos como .doc*/
              FileWriter  save=new FileWriter(guarda);
              save.write(mainObj.toString());
              save.close();
              JOptionPane.showMessageDialog(null,
                   "El archivo se a guardado Exitosamente",
                       "Información",JOptionPane.INFORMATION_MESSAGE);
              } 
            
              
          } catch (IOException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
    
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        jMenu1.setText("Archivo");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(755, 580));

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(33, 33));
        jButton1.setMinimumSize(new java.awt.Dimension(33, 33));
        jButton1.setPreferredSize(new java.awt.Dimension(33, 33));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 51));
        jLabel1.setText("V 0.1.0");
        jLabel1.setToolTipText("");

        jMenu2.setText("Archivo");

        jMenuItem2.setText("Importar fichero");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Exportar fichero");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Salir");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Farmacia");

        jMenuItem6.setText("Necesidad Farmacia");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem1.setText("Existencia Farmarcia");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem5.setText("Existencia Servicio");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem7.setText("Servicios Asic");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setText("Productos");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Informes");

        jMenuItem9.setText("Prueba reporte");
        jMenuItem9.setEnabled(false);
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem10.setText("Informe Necesidad ");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
       
       System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
       
        this.importar_datos();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        FarmaciaTableModel dtm;
        dtm = new FarmaciaTableModel();
        JTable table = new JTable(dtm);
        l = new Lotes(this, true);
        //SET CUSTOM RENDERER TO TEAMS COLUMN
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        //SET CUSTOM EDITOR TO TEAMS COLUMN
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(),l,dtm));
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30); 
        table.getColumnModel().getColumn(0).setPreferredWidth(32);       
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(320);
        table.getColumnModel().getColumn(3).setPreferredWidth(75);
        table.getColumnModel().getColumn(4).setPreferredWidth(85);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(90);
      
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jTabbedPane3.addTab("Existencia Farmacia", scrollPane);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        // TODO add your handling code here:
        // Necesidad Farmacia        
        NecesidadFarmaciaModel dtm;
        dtm = new NecesidadFarmaciaModel();
        final  JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(750, 70));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jTabbedPane3.addTab("Necesidad Farmacia", scrollPane);

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        // Listado Productos        
        ProductoModel dtm;
        dtm = new ProductoModel();
        final  JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);       
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jTabbedPane3.addTab("Productos", scrollPane);
        
        
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
         SeleccionServicio serviciomodal = new SeleccionServicio(this, true);
         serviciomodal.pack();
         serviciomodal.setVisible(true);
         int [] valores = serviciomodal.get_datos_seleccionados();
         final int farmacia_id = valores[0];
         final int centro_id  = valores[1];
         final int servicio_id = valores[2];
         
        ExistenciaServicioModel dtm;
        dtm = new ExistenciaServicioModel(farmacia_id,centro_id,servicio_id);
        
        producto_servicios = new JTable(dtm);
        producto_servicios.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
        singleclick.setClickCountToStart(1);
        producto_servicios.setDefaultEditor(producto_servicios.getColumnClass(5), singleclick);
        producto_servicios.setFont(new Font("Arial", Font.ITALIC, 13));
        producto_servicios.setRowHeight(30);        
        producto_servicios.getColumnModel().getColumn(0).setPreferredWidth(40);       
        producto_servicios.getColumnModel().getColumn(1).setPreferredWidth(325);
        producto_servicios.getColumnModel().getColumn(2).setPreferredWidth(55);
        producto_servicios.getColumnModel().getColumn(3).setPreferredWidth(95);
        producto_servicios.getColumnModel().getColumn(4).setPreferredWidth(95);
        producto_servicios.getColumnModel().getColumn(5).setPreferredWidth(90);
        producto_servicios.setPreferredScrollableViewportSize(new Dimension(730, 500));
        JScrollPane scrollPane = new JScrollPane(producto_servicios);
        producto_servicios.setFillsViewportHeight(true);
        
        bguardar = new JButton("Guardar");
        bguardar.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 
                 // guardar datos existencia en servicio
                 int filas =  producto_servicios.getRowCount();
                 int columnas = producto_servicios.getColumnCount();
                 int existencia = 0;
                 String descripcion_producto ="";
                 Operaciones op = new Operaciones();
                 
                 for (int i = 0; i < filas; i++) {
                     
                     for (int j = 0; j < columnas; j++) {
                         
                         descripcion_producto = producto_servicios.getValueAt(i, 1).toString();                         
                         existencia =    Integer.parseInt(producto_servicios.getValueAt(i, 5).toString());
                         
                     }
                     
                     
                     op.guardar_existencia_servicio(descripcion_producto,existencia,farmacia_id,centro_id,servicio_id);
                     
                     
                 }
                 Component btn = null;
                 JOptionPane.showMessageDialog(btn," Datos guardados correctamente"); 
                 
             }
         });
        
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        panel.add(bguardar);
        jTabbedPane3.addTab("Exitencia Servicio", panel);
       
         
         
         
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        
        this.exportar_datos();
       
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         this.importar_datos();
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.exportar_datos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
          
        
        JasperReport reporte = null;
        Connection c = null;
        try {
            try {
                // TODO add your handling code here:
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            try {
                c = DriverManager.getConnection("jdbc:sqlite:medicamentos.db");
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
              URL in = this.getClass().getResource("Reportes/ProductoReporte.jasper"); 
              reporte = (JasperReport) JRLoader.loadObject(in);
              JasperPrint jasperPrint;
              // datos a pasar (reporte, parametro, conexion)
              Map<String,Object> parametros = new HashMap<String,Object>();
              parametros.put("cod",new String("33892495070000"));             
              jasperPrint = JasperFillManager.fillReport(reporte,(Map<String,Object>)parametros, c);      
              //JasperFillManager.fi
              JasperViewer.viewReport(jasperPrint,false);
          } catch (JRException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } 
        
        
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    
    private void InformeNecesidad()
    {
       // informe de necesidad
         JasperReport reporte = null;
         ArrayList<Productos> productos = new ArrayList<Productos>();
         Operaciones op = new Operaciones();
         productos =  op.get_necesidad_productos();
         InputStream inputStream = null;
         JasperPrint jasperPrint= null;
         NecesidadDataSource datasource = new NecesidadDataSource();
         datasource.add(productos);
         String resourceName = "Reportes/ReporteNecesidad.jrxml";
         inputStream = this.getClass().getResourceAsStream(resourceName);
         
                try{
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            
            jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "src/Reportes/necesidad.pdf");
             // excel
            ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();            
            OutputStream outputfile = new FileOutputStream(new File("informe_necesidad.xls"));
            JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
            /* This is Properties of Excel file which is used for configuration setting. */
            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);             
            xlsExporter.exportReport();
            outputfile.write(outputByteArray.toByteArray());
              
          
        }catch (JRException e){
            JOptionPane.showMessageDialog(null,"Error al cargar fichero jrml jasper report "+e.getMessage());
        } catch (FileNotFoundException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
       
    
    
    }
    
    
    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
         try {
              
             
              
              // guardar
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();
            
            if(guarda !=null)
            {
             /*guardamos el archivo y le damos el formato directamente,
              * si queremos que se guarde en formato doc lo definimos como .doc*/
              FileWriter  save=new FileWriter(guarda);
              //save.write(mainObj.toString());
              save.close();
              JOptionPane.showMessageDialog(null,
                   "El archivo se a guardado Exitosamente",
                       "Información",JOptionPane.INFORMATION_MESSAGE);
              } 
            
              
          } catch (IOException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
       
           
         
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    /**
     * @param args the cmmand line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    String fichero = "medicamentos.db";
                    File f =new File(fichero);
                    if (f.exists()) {
                        
                        Operaciones op = new Operaciones();
                        Calendar calendario = Calendar.getInstance();
                        calendario.setFirstDayOfWeek( Calendar.MONDAY);
                        calendario.setMinimalDaysInFirstWeek( 4 );
                        Date date = new Date();
                        calendario.setTime(date);                
                        int semana  = calendario.get(Calendar.WEEK_OF_YEAR);
                        Conexion bd = new Conexion();
                        //bd.update_database();
                        // update anno en bd
                        bd.update_year();
                        // copiar existencias semana anterior
                        op.generar_lotes_semana(semana);
                        //eliminar year old
                        bd.delete_old_year();
                        //eliminar repetidos
                        ArrayList<Integer> repetidos = bd.listado_repetidos_existencia();
                        for (int i = 0; i < repetidos.size(); i++) {
                            
                            bd.listado_eliminar(repetidos.get(i)) ;
                            
                        }
                         
                    }
                    
                    Thread.sleep(2000);
                 } catch (InterruptedException ex) { }
                
               
                
                
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    private void createTab1() {
       
        
        panel1 = new JPanel();
      
       // TODO add your handling code here:
        // Necesidad Farmacia        
        ProductoModel dtm;
        dtm = new ProductoModel();
        final  JTable table = new JTable(dtm);
        l = new Lotes(this, true);
        //SET CUSTOM RENDERER TO TEAMS COLUMN       
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRendererFicha());
        //SET CUSTOM EDITOR TO TEAMS COLUMN
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditorFicha(new JTextField()));
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(100); 
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);        
        table.setFillsViewportHeight(true);
        JPanel pbuscador = new JPanel();
        JButton buscar  = new JButton("Buscar");
         buscar.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
               
                buscador_producto.setVisible(true);
                ArrayList<Productos> productos  = buscador_producto.get_resultados();
                CreateTabSearchProductos(productos);
                
            }
        });
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar);
        panel1.setLayout (new BorderLayout ());
        panel1.add (scrollPane, BorderLayout.CENTER);
        panel1.add(pbuscador,BorderLayout.NORTH);
        //panel1.add(scrollPane);
        //jTabbedPane3.addTab("Productos", scrollPane);
	
        
        
        
    }

    private void createTab2() {
       
        // TODO add your handling code here:
        // Necesidad Farmacia        
        NecesidadFarmaciaModel dtm;
        dtm = new NecesidadFarmaciaModel();
        final  JTable table = new JTable(dtm);        
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);       
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(90);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
         necesidad = new JPanel();
        JPanel pbuscador = new JPanel();
        JButton buscar_necesidad  = new JButton("Buscar");
        buscar_necesidad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           
                //System.out.println("Buscar necesidad");
                
                 buscador_necesidad.setVisible(true);
                ArrayList<Productos> productos  = buscador_necesidad.get_resultados();
                CreateTabSearchNecesidad(productos);
            }
        });
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar_necesidad);
        necesidad.setLayout (new BorderLayout ());
        necesidad.add (scrollPane, BorderLayout.CENTER);
        necesidad.add(pbuscador,BorderLayout.NORTH);
        
    }

    private void createTab3() {
        
        FarmaciaTableModel dtm;
        dtm = new FarmaciaTableModel();
        JTable table = new JTable(dtm);
        l = new Lotes(this, true);
        //SET CUSTOM RENDERER TO TEAMS COLUMN       
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        //SET CUSTOM EDITOR TO TEAMS COLUMN
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(),l,dtm));
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30); 
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(125);
        table.getColumnModel().getColumn(2).setPreferredWidth(320);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(85);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(90);
        //table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        existencia_farmacia = new JPanel();
        JPanel pbuscador = new JPanel();
        JButton buscar_existencia  = new JButton("Buscar");
        buscar_existencia.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 
                 buscador_existencia.setVisible(true);
                ArrayList<Productos> productos  = buscador_existencia.get_resultados();
                CreateTabSearchExistencia(productos);
                 
                 
             }
         });
        
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar_existencia);
        existencia_farmacia.setLayout (new BorderLayout ());
        existencia_farmacia.add (scrollPane, BorderLayout.CENTER);
        existencia_farmacia.add(pbuscador,BorderLayout.NORTH);
        
        
    }

    private void createTab4() {
        
        
        existencia_servicio = new JPanel();
        JPanel pbuscador = new JPanel();
        JPanel pbuscador_servicio = new JPanel();
        pbuscador_servicio.setLayout(new FlowLayout(FlowLayout.LEFT));
        pbuscador.setPreferredSize(new Dimension(750, 500));
        JLabel lfarmacia = new JLabel("Farmacia");
        Jfarmacia = new JComboBox();        
        JLabel lcentro = new JLabel("Centro");       
        Jcentro = new JComboBox();       
         JLabel lservicio = new JLabel("Servicio");
        Jservicio = new JComboBox();
        //llenar combox
        Jcentro.removeAllItems();
        Jservicio.removeAllItems();
        Jfarmacia.removeAllItems();
          //Buscar datos en Base de datos
        op_selec = new Operaciones();
        f = op_selec.get_farmacias();
       String [] nombre_farmacias = new String[f.size()];
       int [] farmacia_id = new int[f.size()];
       
        for (int i = 0; i < f.size(); i++) {
            
            nombre_farmacias[i] = f.get(i).getNombre();
            farmacia_id[i] = f.get(i).getId();
        }
        
         for (int i = 0; i < nombre_farmacias.length; i++) {
                
              Jfarmacia.addItem(nombre_farmacias[i]);
              
            }
         
         // Llenar  combo centro
          int farmacia_actual = farmacia_id[0];
          centros = op_selec.get_tipo_centros(farmacia_actual);
          int []centros_id = new int[centros.size()];
          String []nombre_centros = new String[centros.size()];
          for (int i = 0; i < centros.size(); i++) {
            
              centros_id[i] = centros.get(i).getId();
              nombre_centros[i] = centros.get(i).getSigla();
              
        }
          
         Jcentro.addItem("Seleccione");
        for (int i = 0; i < nombre_centros.length; i++) {
            
            Jcentro.addItem(nombre_centros[i]);
        }
        //llenar combo servicios
        Jservicio.addItem("Seleccione");
        
  
       Jcentro.addItemListener( new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                
                if ( e.getStateChange() == ItemEvent.SELECTED )
               {
                   int pos = Jcentro.getSelectedIndex();
                   
                   if (pos != 0) {
                       
                       //selecciono centro
                       servicios = op_selec.get_servicios(centros.get(pos-1).getId());
                       nombre_servicio =new String[servicios.size()];
                    for (int i = 0; i < servicios.size(); i++) {

                        nombre_servicio[i] = servicios.get(i).getNombre();
                    }
                    
                    Jservicio.removeAllItems();
                    Jservicio.addItem("Seleccione");
                    for (int i = 0; i < nombre_servicio.length; i++) {

                        Jservicio.addItem(nombre_servicio[i]);

                     }
                       
                   }
               }
                
            }
           
       
       });  
        
        // fin llenar combox
        JButton buscar_servicios = new JButton("Buscar");
        buscar_servicios.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
                
                int farmacia_id = Jfarmacia.getSelectedIndex();
                int centro_id  = Jcentro.getSelectedIndex();
                int servicio_id = Jservicio.getSelectedIndex();    
                if (centro_id==0 && servicio_id==0) {
                        
                    Component btn = null;
                  JOptionPane.showMessageDialog(btn," Debe seleccinar tipo de centro y servicio");
                    
                }
                else if(servicio_id==0){
                
                   Component btn = null;
                  JOptionPane.showMessageDialog(btn," Debe seleccionar  servicio");
                }
                else
                {
                     int resultados ;
                     int pos_farmacia = farmacia_id;
                     int pos_centro = centro_id;
                     int pos_servicio = servicio_id;
                     farmacia_id = f.get(farmacia_id).getId();
                     centro_id = centros.get(centro_id-1).getId();
                     servicio_id = servicios.get(servicio_id-1).getId(); 
                    
                    
                    resultados = op_selec.get_cant_servicios(farmacia_id,centro_id,servicio_id);
                    if (resultados==0) {
                        
                        Component btn = null;
                      JOptionPane.showMessageDialog(btn," No existen productos");
                    }
                    else
                    {
                       AddServicios(farmacia_id, centro_id,servicio_id, pos_farmacia, pos_centro,pos_servicio);
                    }
                }
            }
        });
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar_servicios);
        pbuscador_servicio.add(lfarmacia);
        pbuscador_servicio.add(Jfarmacia);
        pbuscador_servicio.add(lcentro);
        pbuscador_servicio.add(Jcentro);
        pbuscador_servicio.add(lservicio);
        pbuscador_servicio.add(Jservicio);
        pbuscador_servicio.add(buscar_servicios);
        
        existencia_servicio.setLayout (new BorderLayout ());        
        existencia_servicio.add(pbuscador_servicio,BorderLayout.NORTH);
        //existencia_servicio.add(pbuscador,BorderLayout.NORTH);
        
    }
    
    public void AddServicios(final int farmacia, final int centro, final int servicio , int pos_farmacia, int pos_centro, int pos_servicio)
    {
        
       // Eliminar tab
        
        jTabbedPane3.remove(3);        
        createTab4();
        
        eservicio = new ExistenciaServicioModel(farmacia,centro,servicio);
        producto_servicios = new JTable(eservicio);
        producto_servicios.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
        singleclick.setClickCountToStart(1);
        producto_servicios.setDefaultEditor(producto_servicios.getColumnClass(5), singleclick);
        producto_servicios.setFont(new Font("Arial", Font.ITALIC, 13));
        producto_servicios.setRowHeight(30);        
        producto_servicios.getColumnModel().getColumn(0).setPreferredWidth(50);       
        producto_servicios.getColumnModel().getColumn(1).setPreferredWidth(325);
        producto_servicios.getColumnModel().getColumn(2).setPreferredWidth(55);
        producto_servicios.getColumnModel().getColumn(3).setPreferredWidth(95);
        producto_servicios.getColumnModel().getColumn(4).setPreferredWidth(95);
        producto_servicios.getColumnModel().getColumn(5).setPreferredWidth(90);
        producto_servicios.setAutoCreateRowSorter(true);
        producto_servicios.setPreferredScrollableViewportSize(new Dimension(750, 500));
        JScrollPane scrollPane = new JScrollPane(producto_servicios);
        producto_servicios.setFillsViewportHeight(true); 
        existencia_servicio.remove(scrollPane);
        existencia_servicio.add (scrollPane, BorderLayout.CENTER);
        // boton guardar existencia en servicio
        bguardar = new JButton("Guardar");
                
        Jfarmacia.setSelectedIndex(pos_farmacia);
        Jcentro.setSelectedIndex(pos_centro);
        Jservicio.setSelectedIndex(pos_servicio);
        JPanel pbguardar =  new JPanel();
        pbguardar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbguardar.add(bguardar);
        existencia_servicio.add(pbguardar,BorderLayout.SOUTH);
        bguardar.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 
                 // guardar datos existencia en servicio
                 //int filas =  producto_servicios.getRowCount();
                 //int columnas = producto_servicios.getColumnCount();
                 int existencia = -1;
                 String descripcion_producto ="";
                 Operaciones op = new Operaciones();
                 
                 //Nueva modificacion
                  ArrayList<Integer> info_filas = eservicio.getFilas();
                  for (int i = 0; i < info_filas.size(); i++) {
                     
                  descripcion_producto = producto_servicios.getValueAt(info_filas.get(i), 1).toString().toLowerCase();
                   if (!producto_servicios.getValueAt(info_filas.get(i), 5).toString().equals("")) {
                             
                             existencia =    Integer.parseInt(producto_servicios.getValueAt(info_filas.get(i), 5).toString());
                         }
                   
                    if (existencia != -1) {
                         
                         op.guardar_existencia_servicio(descripcion_producto,existencia,farmacia,centro,servicio);
                         existencia = -1;
                     }
                  
                 }
                 
                 /*
                 for (int i = 0; i < filas; i++) {
                     
                     for (int j = 0; j < columnas; j++) {
                         
                         descripcion_producto = producto_servicios.getValueAt(i, 1).toString().toLowerCase();  
                         
                         if (!producto_servicios.getValueAt(i, 5).toString().equals("")) {
                             
                             existencia =    Integer.parseInt(producto_servicios.getValueAt(i, 5).toString());
                         }
                         
                         
                     }
                     
                     if (existencia != -1) {
                         
                         op.guardar_existencia_servicio(descripcion_producto,existencia,farmacia,centro,servicio);
                         existencia = -1;
                     }
                     
                     
                     
                 }  */
                 Component btn = null;
                 JOptionPane.showMessageDialog(btn," Datos guardados correctamente"); 
                 
             }
         });
        
        //jTabbedPane3.repaint();
        jTabbedPane3.addTab("Existencia Servicio", existencia_servicio);
        jTabbedPane3.setSelectedIndex(3);
        
    }
    
   public void CreateTabSearchProductos(ArrayList<Productos> p)
    {
        if (p.size()==0) {
             JOptionPane.showMessageDialog(null,"No existen productos con la unidad de medida selecionada"); 
        }
        else
        {
           // crear tab y mostrar 
             panel1 = new JPanel();
      
       // TODO add your handling code here:
        // Necesidad Farmacia        
        ProductoModel dtm;
        dtm = new ProductoModel(p);
        final  JTable table = new JTable(dtm);
        //SET CUSTOM RENDERER TO TEAMS COLUMN       
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRendererFicha());
        //SET CUSTOM EDITOR TO TEAMS COLUMN
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditorFicha(new JTextField()));
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table. setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);        
        table.setFillsViewportHeight(true);
        JPanel pbuscador = new JPanel();
        JButton buscar  = new JButton("Buscar");
         buscar.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
               
                buscador_producto.setVisible(true);
                ArrayList<Productos> productos  = buscador_producto.get_resultados();
                 CreateTabSearchProductos(productos);
                
            }
        });
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar);
        panel1.setLayout (new BorderLayout ());
        panel1.add (scrollPane, BorderLayout.CENTER);
        panel1.add(pbuscador,BorderLayout.NORTH);
        //jTabbedPane3.addTab("Productos1", panel1);
        jTabbedPane3.remove(0);
        jTabbedPane3.insertTab("Productos", null, panel1, null, 0);
        jTabbedPane3.setSelectedIndex(0);
        
        
        }
    
    }
   
   public void CreateTabSearchExistencia(ArrayList<Productos> p)
   {
   
     if (p.size()==0) {
             JOptionPane.showMessageDialog(null,"No existen productos con los parametros selecionados"); 
        }
     else
     {
         
         FarmaciaTableModel dtm;
        dtm = new FarmaciaTableModel(p);
        JTable table = new JTable(dtm);
        l = new Lotes(this, true);
        //SET CUSTOM RENDERER TO TEAMS COLUMN
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        //SET CUSTOM EDITOR TO TEAMS COLUMN
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(),l,dtm));
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        
        // preferencias jtable
       
       
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30); 
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(125);
        table.getColumnModel().getColumn(2).setPreferredWidth(320);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(85);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(90);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        existencia_farmacia = new JPanel();
        JPanel pbuscador = new JPanel();
        JButton buscar_existencia  = new JButton("Buscar");
        buscar_existencia.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 
                 buscador_existencia.setVisible(true);
                 ArrayList<Productos> productos  = buscador_existencia.get_resultados();
                 CreateTabSearchExistencia(productos);
             }
         });
        
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar_existencia);
        existencia_farmacia.setLayout (new BorderLayout ());
        existencia_farmacia.add (scrollPane, BorderLayout.CENTER);
        existencia_farmacia.add(pbuscador,BorderLayout.NORTH);
       // jTabbedPane3.addTab("Existencia Farmacia3", existencia_farmacia);
        jTabbedPane3.remove(2);
        jTabbedPane3.insertTab("Existencia Farmacia", null, existencia_farmacia, null, 2);
        jTabbedPane3.setSelectedIndex(2);
     
     
     }
   
   }
   
   
   public void CreateTabSearchNecesidad(ArrayList<Productos>p)
   {
       if (p.size()==0) {
             JOptionPane.showMessageDialog(null,"No existen productos con los parametros selecionados"); 
        }
     else
           
       {
           
           NecesidadFarmaciaModel dtm;
        dtm = new NecesidadFarmaciaModel(p);
        final  JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(750, 500));
        // preferencias jtable
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFont(new Font("Arial", Font.ITALIC, 13));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);       
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(380);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        necesidad = new JPanel();
        JPanel pbuscador = new JPanel();
        JButton buscar_necesidad  = new JButton("Buscar");
        buscar_necesidad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           
                //System.out.println("Buscar necesidad");
                
                 buscador_necesidad.setVisible(true);
                ArrayList<Productos> productos  = buscador_necesidad.get_resultados();
                CreateTabSearchNecesidad(productos);
            }
        });
        pbuscador.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pbuscador.add(buscar_necesidad);
        necesidad.setLayout (new BorderLayout ());
        necesidad.add (scrollPane, BorderLayout.CENTER);
        necesidad.add(pbuscador,BorderLayout.NORTH);
        // tab
        jTabbedPane3.remove(1);
        jTabbedPane3.insertTab("Necesidad Farmacia", null, necesidad, null, 1);
        jTabbedPane3.setSelectedIndex(1);
        
       }
   
   
   }
    
}
