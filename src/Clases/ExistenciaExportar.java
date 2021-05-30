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
public class ExistenciaExportar {
    
    private int asic_id;
    private int tipo_centro_id;
    private int servicio_id;
    private int producto_id;
    private int lote_id ;
    private int semana_estadistica;
    private int cantidad_existencia;
    private int farmacia_id;

    public ExistenciaExportar(int asic_id, int tipo_centro_id, int servicio_id, int producto_id, int lote_id, int semana_estadistica, int cantidad_existencia, int farmacia_id) {
        this.asic_id = asic_id;
        this.tipo_centro_id = tipo_centro_id;
        this.servicio_id = servicio_id;
        this.producto_id = producto_id;
        this.lote_id = lote_id;
        this.semana_estadistica = semana_estadistica;
        this.cantidad_existencia = cantidad_existencia;
        this.farmacia_id = farmacia_id;
    }

    /**
     * @return the asic_id
     */
    public int getAsic_id() {
        return asic_id;
    }

    /**
     * @param asic_id the asic_id to set
     */
    public void setAsic_id(int asic_id) {
        this.asic_id = asic_id;
    }

    /**
     * @return the tipo_centro_id
     */
    public int getTipo_centro_id() {
        return tipo_centro_id;
    }

    /**
     * @param tipo_centro_id the tipo_centro_id to set
     */
    public void setTipo_centro_id(int tipo_centro_id) {
        this.tipo_centro_id = tipo_centro_id;
    }

    /**
     * @return the servicio_id
     */
    public int getServicio_id() {
        return servicio_id;
    }

    /**
     * @param servicio_id the servicio_id to set
     */
    public void setServicio_id(int servicio_id) {
        this.servicio_id = servicio_id;
    }

    /**
     * @return the producto_id
     */
    public int getProducto_id() {
        return producto_id;
    }

    /**
     * @param producto_id the producto_id to set
     */
    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    /**
     * @return the lote_id
     */
    public int getLote_id() {
        return lote_id;
    }

    /**
     * @param lote_id the lote_id to set
     */
    public void setLote_id(int lote_id) {
        this.lote_id = lote_id;
    }

    /**
     * @return the semana_estadistica
     */
    public int getSemana_estadistica() {
        return semana_estadistica;
    }

    /**
     * @param semana_estadistica the semana_estadistica to set
     */
    public void setSemana_estadistica(int semana_estadistica) {
        this.semana_estadistica = semana_estadistica;
    }

    /**
     * @return the cantidad_existencia
     */
    public int getCantidad_existencia() {
        return cantidad_existencia;
    }

    /**
     * @param cantidad_existencia the cantidad_existencia to set
     */
    public void setCantidad_existencia(int cantidad_existencia) {
        this.cantidad_existencia = cantidad_existencia;
    }

    /**
     * @return the farmacia_id
     */
    public int getFarmacia_id() {
        return farmacia_id;
    }

    /**
     * @param farmacia_id the farmacia_id to set
     */
    public void setFarmacia_id(int farmacia_id) {
        this.farmacia_id = farmacia_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.asic_id;
        hash = 37 * hash + this.tipo_centro_id;
        hash = 37 * hash + this.servicio_id;
        hash = 37 * hash + this.producto_id;
        hash = 37 * hash + this.lote_id;
        hash = 37 * hash + this.semana_estadistica;
        hash = 37 * hash + this.farmacia_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExistenciaExportar other = (ExistenciaExportar) obj;
        if (this.asic_id != other.asic_id) {
            return false;
        }
        if (this.tipo_centro_id != other.tipo_centro_id) {
            return false;
        }
        if (this.servicio_id != other.servicio_id) {
            return false;
        }
        if (this.producto_id != other.producto_id) {
            return false;
        }
        if (this.lote_id != other.lote_id) {
            return false;
        }
        if (this.semana_estadistica != other.semana_estadistica) {
            return false;
        }
        if (this.farmacia_id != other.farmacia_id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
