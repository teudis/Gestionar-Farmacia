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
public class Farmacia {
    
    private String nombre ;
    private int id;
    private int provincia_id;
    private int asic_id;   
    
    public Farmacia(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public Farmacia(String nombre, int id, int provincia_id, int asic_id) {
        this.nombre = nombre;
        this.id = id;
        this.provincia_id = provincia_id;
        this.asic_id = asic_id;
    }

    
    
    public int getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(int provincia_id) {
        this.provincia_id = provincia_id;
    }

    public int getAsic_id() {
        return asic_id;
    }

    public void setAsic_id(int asic_id) {
        this.asic_id = asic_id;
    }
    
    

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
