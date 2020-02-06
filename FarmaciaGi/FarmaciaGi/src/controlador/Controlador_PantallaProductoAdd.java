/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import vista.Pantalla_ProductosAdd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import mail.MailBug;
import modelo.Productos;
import modelo.Proveedor;
import utilerias.Utilerias;

/**
 *
 * @author saube
 */
public class Controlador_PantallaProductoAdd {

    Utilerias util = new Utilerias();
    Pantalla_ProductosAdd productoAgregar;
    Productos productos;
    MailBug mailbug = new MailBug();

    public Controlador_PantallaProductoAdd(String rol, String turno, int idEmpleado) {
        productoAgregar = new Pantalla_ProductosAdd();
        productoAgregar.setLocationRelativeTo(null);
        productoAgregar.setVisible(true);

        productoAgregar.altaMedicamentoGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (productoAgregar.altaMedicamentoCodigo.getText().equals("") || productoAgregar.altaMedicamentoMarcaComercial.getText().equals("")
                        || productoAgregar.altaMedicamentoSustancia.getText().equals("") || productoAgregar.altaMedicamentoPrecio.getText().equals("")
                        || productoAgregar.altaMedicamentoCantidad.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No deje campos en blaco</h1></html>");

                } else {

                    boolean next = validarFormulario(productoAgregar.altaMedicamentoPrecio.getText(), productoAgregar.altaMedicamentoCantidad.getText());
                    if (next) {
                        if (!productoAgregar.altaMedicamentoCodigo.getText().matches("[0-9]*")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un codigo correcto.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            productoAgregar.altaMedicamentoCodigo.requestFocus();
                            return;
                        }
                        if (!productoAgregar.altaMedicamentoCantidad.getText().matches("[0-9]*")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un cantidad correcta.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);

                            productoAgregar.altaMedicamentoCantidad.requestFocus();
                            return;
                        }
                        if (!productoAgregar.altaMedicamentoPrecio.getText().matches("\\d+\\.?\\d?\\d?")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un precio correcto.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);

                            productoAgregar.altaMedicamentoPrecio.requestFocus();
                            return;
                        }

                        long codigo = Long.valueOf(productoAgregar.altaMedicamentoCodigo.getText());
                        String marcaComercia = productoAgregar.altaMedicamentoMarcaComercial.getText();
                        String sustancia = productoAgregar.altaMedicamentoSustancia.getText();
                        double precio = Double.parseDouble(productoAgregar.altaMedicamentoPrecio.getText());
                        String tipoMedicamento = productoAgregar.altaMedicamentoTipoMedicamento.getSelectedItem().toString();
                        String laboratorio = productoAgregar.altaMedicamentoLavoratorio.getSelectedItem().toString();
                        Proveedor proveedor = (Proveedor) productoAgregar.altaMedicamentoProveedor.getSelectedItem();
                        int cantidad = Integer.parseInt(productoAgregar.altaMedicamentoCantidad.getText());

                        productos = new Productos(codigo, marcaComercia.toUpperCase(), sustancia.toUpperCase(), precio, tipoMedicamento, laboratorio, proveedor.getIdproveedor(), cantidad);

                        if (productos.registrarProducto()) {
                            JOptionPane.showMessageDialog(null, "<html><h1>EL PRODUCTO SE HA DADO DE ALTA EN LA BASE DE DATOS</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            productoAgregar.altaMedicamentoCodigo.requestFocus();
                            productoAgregar.altaMedicamentoCodigo.setBackground(Color.WHITE);

                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                           
                        }
                    }
                }

            }
        });

//        productoAgregar.altaMedicamentoRegresar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                productoAgregar.dispose();
//                new Controlador_PantallaProductos(rol, turno ,idEmpleado );
//
//            }
//        });
        productoAgregar.altaMedicamentoCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    long codigo = Long.parseLong(productoAgregar.altaMedicamentoCodigo.getText());
                    productos = new Productos(codigo);
                    boolean next = productos.verificarCodigo();

                    if (next) {
                        productoAgregar.altaMedicamentoCodigo.setBackground(Color.RED);
                        productoAgregar.altaMedicamentoCodigo.setText("");
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> !! EL CODIGO YA ESTA REGISTRADO !! </h1></html>");
                       
                    } else {
                        productoAgregar.altaMedicamentoCodigo.setBackground(Color.GREEN);
                        productoAgregar.altaMedicamentoMarcaComercial.requestFocus();

                    }

                }

            }
        });
    }

    public boolean validarFormulario(String precio, String cantidad) {
        boolean next = false;
        Pattern patPrecio = Pattern.compile("^[0-9]+([.])?([0-9]+)?$");
        Pattern patCantidad = Pattern.compile("[0-9]*");
        Matcher matPrecio = patPrecio.matcher(precio);
        Matcher matCantidad = patCantidad.matcher(cantidad);

        if (matPrecio.matches()) {
            productoAgregar.altaMedicamentoPrecio.setBackground(Color.WHITE);

            if (matCantidad.matches()) {
                productoAgregar.altaMedicamentoCantidad.setBackground(Color.WHITE);
                next = true;
            } else {
                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Numeros </h1></html>");
                productoAgregar.altaMedicamentoCantidad.setBackground(Color.red);
            }
        } else {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Numeros </h1></html>");
            productoAgregar.altaMedicamentoPrecio.setBackground(Color.red);
        }

        return next;
    }

    public void limpiarCampos() {
        productoAgregar.altaMedicamentoCantidad.setText("");
        productoAgregar.altaMedicamentoCodigo.setText("");
        productoAgregar.altaMedicamentoMarcaComercial.setText("");
        productoAgregar.altaMedicamentoPrecio.setText("");
        productoAgregar.altaMedicamentoSustancia.setText("");
        productoAgregar.altaMedicamentoLavoratorio.setSelectedIndex(1);
        productoAgregar.altaMedicamentoTipoMedicamento.setSelectedIndex(1);
        productoAgregar.altaMedicamentoProveedor.setSelectedIndex(1);

    }

}
