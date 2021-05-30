/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Annie&Teudis
 */
public class ButtonRendererFicha extends JButton implements TableCellRenderer  {

    public ButtonRendererFicha() {
        
        setOpaque(true);
    }
    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        //SET PASSED OBJECT AS BUTTON TEXT
	       //setText("Ficha");
               Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/images/pdf.gif"));
            setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            Logger.getLogger(ButtonRendererFicha.class.getName()).log(Level.SEVERE, null, ex);
        }
               
	       return this;
        
    }
    
}
