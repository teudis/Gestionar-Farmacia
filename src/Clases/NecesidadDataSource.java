/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Annie&Teudis
 */
public class NecesidadDataSource implements JRDataSource {
    
    private  ArrayList<Productos> productos = new ArrayList<Productos>();
    private int indiceParticipanteActual = -1;
    
    public void add(ArrayList<Productos> p){

        this.productos = p;

    }

    @Override
    public boolean next() throws JRException {
        
         return ++indiceParticipanteActual < productos.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
       
        Object valor = null;
        if ("codigo".equals(jrf.getName())) {
            
              valor = productos.get(indiceParticipanteActual).getCodigo();
        }
        else
            if ("descripcion".equals(jrf.getName())) {
            
                valor = productos.get(indiceParticipanteActual).getDescripcion();
          }
        else
                if ("medida".equals(jrf.getName())) {
            
             valor = productos.get(indiceParticipanteActual).getMedida();
        }
        else
                    if ("stock_maximo".equals(jrf.getName())) {
            valor = productos.get(indiceParticipanteActual).getStock_maximo();
        }
        else
              if ("estado".equals(jrf.getName())) {
             valor = productos.get(indiceParticipanteActual).getEstado();
        }
        else
                  if ("asic".equals(jrf.getName())) {
            
             valor = productos.get(indiceParticipanteActual).getAsic();
        }
        else     
            {
                valor = productos.get(indiceParticipanteActual).getFarmacia();

            }
          
        
        return valor;
    }
    
}
