/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import BD.Conexion;
import BD.Operaciones;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.SwingWorker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Annie&Teudis
 */
public class ImportarDatos extends SwingWorker<Void, Void>{
    
    private JDialog ventana;
    private JSONObject jsonObject;
    private Boolean correcto ;

    public Boolean getCorrecto() {
        return correcto;
    }

    public ImportarDatos(JDialog ventana) {
        this.ventana = ventana;
        this.correcto = true;
    }

    public ImportarDatos(JDialog ventana, JSONObject jsonObject) {
        this.ventana = ventana;
        this.jsonObject = jsonObject;
        this.correcto = true;
    }

    
    
    public JDialog getVentana() {
        return ventana;
    }

    public void setVentana(JDialog ventana) {
        this.ventana = ventana;
    }

    @Override
    protected Void doInBackground() throws Exception {
       longProcessingTask();
        Thread.sleep(2000);
       return null;
    }
    
    @Override
 protected void done() {
    ventana.dispose();//close the modal dialog
 }

    private void longProcessingTask() {
        
                Operaciones op = new Operaciones();
        
                 // config_cod_farmacia
                 String tabla = "config_cod_farmacia";                  
                 JSONObject cod_farmacia = new JSONObject((Map) jsonObject.get("cod_farmacia"));                  
                  String cod_nombre_farmacia =  (String) cod_farmacia.get("nombre");                  
                  String id_farmacia_cod =   cod_farmacia.get("id").toString();
                  int id_farmacia = Integer.parseInt(id_farmacia_cod);                  
                  int farmacia_actual = op.get_id_farmacia();
                  if (farmacia_actual != id_farmacia && farmacia_actual != 0 ) {
            
                      correcto = false;
                  }
          
                  else
                  {
                // cod_provincia
                JSONObject cod_provincia = new JSONObject((Map) jsonObject.get("cod_provincia"));
                String nombre_provincia =  (String) cod_provincia.get("nombre");
                int id_provincia =  Integer.parseInt ((String) cod_provincia.get("id"));
                
                
                 tabla = "config_cod_provincia";
                 // op.remove_all(tabla);
                 int tabla_provincia = op.cant_datos(tabla);
                // insert provincia BD 
                      if (tabla_provincia==0) {
                          
                       op.insert_cod_provincia(nombre_provincia,id_provincia);
                      
                      }
                
                // cod_asic
                JSONObject cod_asic = new JSONObject((Map) jsonObject.get("cod_asic"));
                String nombre_asic =  (String) cod_asic.get("nombre");
                int id_asic =  Integer.parseInt ((String) cod_asic.get("id"));
                
                // insert cod_asic
                tabla = "config_cod_asic";
                int tabla_asic = op.cant_datos(tabla);
                if(tabla_asic==0)
                {
                  op.insert_cod_asic(nombre_asic,id_asic);
                 
                }  
                // insertar farmarcia 
                tabla = "config_cod_farmacia";
                op.remove_all(tabla);
                op.insert_cod_farmacia(cod_nombre_farmacia, id_farmacia);
                
                
                //  tipo de centro
                
                  tabla = "config_cod_tipo_centro";
                  op.remove_all(tabla);
                  JSONArray cod_tipo_centro = (JSONArray) jsonObject.get("cod_tipo_centro");
                  JSONObject tipo_centro = null;
                  for (int i = 0; i < cod_tipo_centro.size(); i++) {
                    
                      tipo_centro = (JSONObject) cod_tipo_centro.get(i);
                      int id_tipo_centro =  Integer.parseInt ((String) tipo_centro.get("id"));
                      String sigla_tipo_centro =  (String) tipo_centro.get("sigla");
                      String descripcion_tipo_centro =  (String) tipo_centro.get("descripcion");
                      String nombre_tipo_centro =  (String) tipo_centro.get("nombre");                      
                      op.insert_tipo_centro(nombre_tipo_centro,sigla_tipo_centro , descripcion_tipo_centro,id_tipo_centro);
                      
                }
                  // cod_servicio
                  tabla = "config_cod_servicio";
                  op.remove_all(tabla);
                 JSONArray cod_servicio = (JSONArray) jsonObject.get("cod_servicio");
                  JSONObject servicio = null;
                  for (int i = 0; i < cod_servicio.size(); i++) {
                    
                      servicio = (JSONObject) cod_servicio.get(i);
                      int id_servicio =  Integer.parseInt ((String) servicio.get("id"));
                      String sigla_servicio =  (String) servicio.get("sigla");
                      String descripcion_servicio =  (String) servicio.get("descripcion");
                      String nombre_servicio =  (String) servicio.get("nombre");                      
                      op.insert_cod_servicio(nombre_servicio,sigla_servicio , descripcion_servicio,id_servicio);
                      
                }
                
                  // cod_tipo_centro_servicio
                 tabla = "config_tipo_centro_servicio";
                 op.remove_all(tabla);
                 JSONArray cod_tipo_centro_servicio = (JSONArray) jsonObject.get("tipo_centro_servicio");
                 JSONObject centro_servicio = null;
                 
                  for (int i = 0; i < cod_tipo_centro_servicio.size(); i++) {
                    
                      centro_servicio = (JSONObject) cod_tipo_centro_servicio.get(i);
                      int tipo_centro_id =  Integer.parseInt ((String) centro_servicio.get("tipo_centro_id"));
                      int servicio_id =  Integer.parseInt ((String) centro_servicio.get("servicio_id"));                      
                      op.insert_tipo_centro_servicio(tipo_centro_id,servicio_id);
                      
                     
                } 
                  
                
                  
                // cod_producto
                  
                tabla = "config_cod_producto";
                op.remove_all(tabla);
                tabla = "config_cod_lote";
                op.remove_all(tabla);
                JSONArray  cod_producto;    
                JSONObject producto = null;
                JSONObject lotes = null ;
                cod_producto = (JSONArray) jsonObject.get("cod_producto");   
                
                for (int i = 0; i < cod_producto.size(); i++) {
                    
                   producto = (JSONObject) cod_producto.get(i);
                   String codigo = (String) producto.get("codigo");
                   int producto_id =  Integer.parseInt ((String) producto.get("id")); 
                   String producto_descripcion = (String) producto.get("descripcion");
                   String producto_factor_conversion =  producto.get("factor_conversion").toString();
                   
                   int producto_oficial_id =  0; // producto.get("oficial_id").toString();
                    if (producto.get("oficial_id")!= null) {
                        
                        producto_oficial_id =  Integer.parseInt(producto.get("oficial_id").toString());
                    }
                   String producto_unidad_medida = (String) producto.get("unidad_medida");
                   int producto_unidad_medida_id =  Integer.parseInt ((String) producto.get("unidad_medida_id"));
                  // String producto_unidad_medida_almacen = (String) producto.get("unidad_medida_almacen");
                   int unidad_medida_almacen_id =  Integer.parseInt ((String) producto.get("unidad_medida_almacen_id"));
                   
                    
                   // insertar unidad de medida
                    op.insert_unidad_medida(producto_unidad_medida, producto_unidad_medida_id);
                    
                    //  Insertar Producto
                    op.insertar_producto(producto_id, codigo, producto_descripcion, producto_oficial_id, producto_factor_conversion, producto_unidad_medida_id, unidad_medida_almacen_id);
                    
                    
                   // insertar lotes
                
                JSONArray cod_lotes = (JSONArray) producto.get("cod_lote");                
                op.insert_lotes(cod_lotes,producto_id);
                
              
                    
                }
                
                //farmac_asic_servicio
                tabla = "farmac_asic_servicio";
                op.remove_all(tabla);
                JSONArray farmac_asic_servicio = (JSONArray) jsonObject.get("farmac_asic_servicio");
                op. insert_farmac_asic_servicio(farmac_asic_servicio);
                
                // farmac_asic_producto_necesidad
                tabla = "farmac_asic_producto_necesidad";
                op.remove_all(tabla);
                JSONArray farmac_asic_producto_necesidad = (JSONArray) jsonObject.get("farmac_asic_producto_necesidad");
                op. insert_farmac_asic_producto_necesidad(farmac_asic_producto_necesidad);
                
                // farmac_asic_producto_existencia
                 
                JSONArray farmac_asic_producto_existencia = (JSONArray) jsonObject.get("farmac_asic_producto_existencia");
                op. insert_farmac_asic_producto_existencia(farmac_asic_producto_existencia);
                
                // producto_servicio
                
                tabla = "config_producto_servicio";
                op.remove_all(tabla);
                JSONArray producto_servicio = (JSONArray) jsonObject.get("producto_servicio");
                op. insert_producto_servicio(producto_servicio);
                //generar lotes de la semana 
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek( Calendar.MONDAY);
                calendar.setMinimalDaysInFirstWeek( 4 );
                Date date = new Date();
                calendar.setTime(date);     
                
                int semana  = calendar.get(Calendar.WEEK_OF_YEAR);
                Conexion bd = new Conexion();
                bd.update_year();
                op.generar_lotes_semana(semana);
                bd.delete_old_year();
                  }
    }
    
    
    
}
