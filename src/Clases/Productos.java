/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Teudis
 */
public class Productos {
    
    
    private String codigo ;
    private String descripcion;
    private String medida;
    private int medida_id;
    private int producto_id;
    private int stock_maximo;  
    private String estado;
    private String asic;
    private String farmacia;

    
    
    public void setStock_maximo(int stock_maximo) {
        this.stock_maximo = stock_maximo;
    }

    public int getStock_maximo() {
        return stock_maximo;
    }

    public Productos(String codigo, String descripcion, String medida) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.medida = medida;
    }
    
     public Productos(String codigo, String descripcion, int  medida_id) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.medida_id = medida_id;
    }

    public Productos(String codigo, String descripcion,  int medida_id, int producto_id) {
        this.codigo = codigo;
        this.descripcion = descripcion;        
        this.medida_id = medida_id;
        this.producto_id = producto_id;
    }

    public Productos(String codigo, String descripcion, int stock_maximo , String medida , String estado, String asic, String farmacia) {
        this.codigo = codigo;
        this.descripcion = descripcion;        
        this.stock_maximo = stock_maximo;  
        this.medida = medida;
        this.estado = estado;
        this.asic = asic;
        this.farmacia = farmacia;
    }
    
    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    
    
    public int getMedida_id() {
        return medida_id;
    }

    public void setMedida_id(int medida_id) {
        this.medida_id = medida_id;
    }

    
    
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the medida
     */
    public String getMedida() {
        return medida;
    }

    /**
     * @param medida the medida to set
     */
    public void setMedida(String medida) {
        this.medida = medida;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the asic
     */
    public String getAsic() {
        return asic;
    }

    /**
     * @param asic the asic to set
     */
    public void setAsic(String asic) {
        this.asic = asic;
    }

    /**
     * @return the farmacia
     */
    public String getFarmacia() {
        return farmacia;
    }

    /**
     * @param farmacia the farmacia to set
     */
    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }
    
    
  
    
}
