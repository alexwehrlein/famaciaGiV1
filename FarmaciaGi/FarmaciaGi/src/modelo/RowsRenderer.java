
package modelo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class RowsRenderer extends DefaultTableCellRenderer {
private int columna ;

public RowsRenderer(int Colpatron)
{
    this.columna = Colpatron;
}

@Override
public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
{        
    setBackground(Color.white);
    table.setForeground(Color.black);
    super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    if(table.getValueAt(row,columna).equals("A"))
    {
        this.setForeground(Color.RED);
    }else if(table.getValueAt(row,columna).equals("B")){
        this.setForeground(Color.BLUE);
    }else if(table.getValueAt(row, columna).equals("C")){
        this.setForeground(Color.GREEN);
    }
    return this;
  }
  }