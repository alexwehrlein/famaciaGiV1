package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Promociones;
import vista.Pantalla_Promociones;

/**
 *
 * @author alexwehrlein
 */
public class Controlador_Promociones {

    Pantalla_Promociones pp;
    Promociones promociones;

    public Controlador_Promociones() {
        pp = new Pantalla_Promociones();
        pp.setVisible(true);
        pp.setLocationRelativeTo(null);
        pp.codigo.requestFocus();
        for (Component component : pp.container.getComponents()) {
            component.setEnabled(false);
        }
        pp.tablaPromociones.setModel(new Promociones().cargarRegistroPromociones(pp.tablaPromociones, ""));

        pp.tablaPromociones.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int column = pp.tablaPromociones.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pp.tablaPromociones.getRowHeight();
                int filaseleccionada;
                if (row < pp.tablaPromociones.getRowCount() && row >= 0 && column < pp.tablaPromociones.getColumnCount() && column >= 0) {
                    Object value = pp.tablaPromociones.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;
                        if (boton.getName().equals("btnModificar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "¿Modificar Descuento?", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                filaseleccionada = pp.tablaPromociones.getSelectedRow();
                                int id = (int) pp.tablaPromociones.getValueAt(filaseleccionada, 0);
                                String descuento = pp.tablaPromociones.getValueAt(filaseleccionada, 3).toString();
                                String tipo = (String) pp.tablaPromociones.getValueAt(filaseleccionada, 4);
                                System.out.println(id + " " + descuento + " " + tipo);
                                if (!descuento.matches("[0-9]*")) {
                                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad correcta.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                promociones = new Promociones(id, Integer.parseInt(descuento), tipo);
                                boolean next = promociones.modificarPromocion();
                                if (next) {
                                    Clear_Table();
                                    pp.tablaPromociones.setModel(new Promociones().cargarRegistroPromociones(pp.tablaPromociones, ""));
                                    JOptionPane.showMessageDialog(null, "Exito al modificar promocion", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        pp.codigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String codigo = pp.codigo.getText();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (codigo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto", "ERRO", JOptionPane.ERROR_MESSAGE);
                        pp.codigo.requestFocus();
                        return;
                    }
                    promociones = new Promociones(Long.parseLong(codigo));
                    String arr[] = promociones.buscarProducto();
                    if (!arr[2].isEmpty()) {
                        JOptionPane.showMessageDialog(null, arr[2], "ERRO", JOptionPane.ERROR_MESSAGE);
                        pp.codigo.requestFocus();
                        return;
                    }
                    pp.nombre.setText(arr[0]);
                    pp.precio.setText("$ " + arr[1]);
                    pp.descuento.requestFocus();
                    for (Component component : pp.container.getComponents()) {
                        component.setEnabled(true);
                    }
                }

            }
        });

        pp.seach.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigo = pp.seach.getText();
                    if (codigo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto", "ERRO", JOptionPane.ERROR_MESSAGE);
                        pp.seach.requestFocus();
                        return;
                    }
                    Clear_Table();
                    pp.tablaPromociones.setModel(new Promociones().cargarRegistroPromociones(pp.tablaPromociones, codigo));
                }
            }
        });

        pp.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descuento = pp.descuento.getText();
                String codigo = pp.codigo.getText();
                if (descuento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese la cantidad del descuento.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pp.descuento.requestFocus();
                    return;
                }
                if (!descuento.matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad correcta.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pp.descuento.requestFocus();
                    return;
                }
                promociones = new Promociones(Long.parseLong(codigo) , Integer.parseInt(descuento));
                boolean next = promociones.guardesPromocion();
                if (next) {
                    JOptionPane.showMessageDialog(null, "Exito al guardaar promocion", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    for (Component component : pp.container.getComponents()) {
                        component.setEnabled(false);
                    }
                    pp.codigo.setText("");
                    pp.precio.setText("");
                    pp.descuento.setText("");
                    pp.codigo.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pp.Refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear_Table();
                pp.codigo.setText("");
                pp.tablaPromociones.setModel(new Promociones().cargarRegistroPromociones(pp.tablaPromociones, ""));
            }
        });

    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pp.tablaPromociones.getModel();
        int filas = pp.tablaPromociones.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
