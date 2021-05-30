/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;



/**
 *
 * @author Annie&Teudis
 */
public class Lotes {
    
    private String lote;
    private String fecha_vence;
    private int existencia;
    private int id_producto ;
    
    
    public Lotes(String lote, String fecha_vence, int existencia) {
        this.lote = lote;
        this.fecha_vence = fecha_vence;
        this.existencia = existencia;
        
    }
    
    public Lotes(String lote, int id_producto , String fecha_vence)
    {
      
        this.lote = lote;
        this.fecha_vence = fecha_vence;
        this.id_producto = id_producto;   
    
    }
            
    
    public Lotes (String lote , String fecha_vence)
    {
        this.fecha_vence = fecha_vence;
        this.lote = lote;    
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the fecha_vence
     */
    public String getFecha_vence() {
        return fecha_vence;
    }

    /**
     * @param fecha_vence the fecha_vence to set
     */
    public void setFecha_vence(String fecha_vence) {
        this.fecha_vence = fecha_vence;
    }

    /**
     * @return the existencia
     */
    public int getExistencia() {
        return existencia;
    }

    /**
     * @param existencia the existencia to set
     */
    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    /**
     * @return the id_producto
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * @param id_producto the id_producto to set
     */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    
    
    
    
}
