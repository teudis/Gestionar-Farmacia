/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Teudis
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

  //CONSTRUCTOR
	public ButtonRenderer() {
		//SET BUTTON PROPERTIES
		setOpaque(true);
               
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean selected, boolean focused, int row, int col) {
		
		//SET PASSED OBJECT AS BUTTON TEXT
			setText("Editar");
				
		return this;
	}
}
