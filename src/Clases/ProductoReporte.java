/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Teudis
 */
public class ProductoReporte {
    
    private String codigo;
    private String descripcion;
    private String estado;
    private String asic;
    private String farmacia;
    private boolean tratamiento_lote;
    private boolean  oficial;
    private  int total ;
    private ArrayList<Lotes> listado_lotes;

    public ProductoReporte(String codigo, String descripcion, String estado, String asic, String farmacia, boolean tratamiento_lote, boolean oficial, int total) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.asic = asic;
        this.farmacia = farmacia;
        this.tratamiento_lote = tratamiento_lote;
        this.oficial = oficial;
        this.total = total;
        listado_lotes = new ArrayList<Lotes>();
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

    /**
     * @return the tratamiento_lote
     */
    public boolean isTratamiento_lote() {
        return tratamiento_lote;
    }

    /**
     * @param tratamiento_lote the tratamiento_lote to set
     */
    public void setTratamiento_lote(boolean tratamiento_lote) {
        this.tratamiento_lote = tratamiento_lote;
    }

    /**
     * @return the oficial
     */
    public boolean isOficial() {
        return oficial;
    }

    /**
     * @param oficial the oficial to set
     */
    public void setOficial(boolean oficial) {
        this.oficial = oficial;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the listado_lotes
     */
    public ArrayList<Lotes> getListado_lotes() {
        return listado_lotes;
    }

    /**
     * @param listado_lotes the listado_lotes to set
     */
    public void setListado_lotes(ArrayList<Lotes> listado_lotes) {
        this.listado_lotes = listado_lotes;
    }
    
    
    
}
