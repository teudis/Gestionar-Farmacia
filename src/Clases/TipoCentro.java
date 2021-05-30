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
public class TipoCentro {
    
    private int id;
    private String nombre ;
    private String sigla ;

    public TipoCentro(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoCentro(int id, String nombre, String sigla) {
        this.id = id;
        this.nombre = nombre;
        this.sigla = sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
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
    
    
    
}
