/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import modelo.Empleado;
import modelo.Usuarios;
import modelo.Ventas;
import vista.Pantalla_principal;

import utilerias.Utilerias;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_PantallaPrincipal {
    //pones esto
    Utilerias n = new Utilerias();

    Pantalla_principal pantalla_Principal;
    Ventas ventas;
    String idEmpleado, nombreEmpleado, turno, rol, pc;
    String id = "";
    int folio;

    public static boolean ventanaControl1 = false;
    public static boolean ventanaControl2 = false;//productos
    public static boolean ventanaControl3 = false;
    public static boolean ventanaControl4 = false;
    public static boolean ventanaControl5 = false;
    public static boolean ventanaControl6 = false;
    public static boolean ventanaControl7 = false;
    public static boolean ventanaControl8 = false;
    public static boolean ventanaControl9 = false;
    public static boolean ventanaControl10 = false;
    public static boolean ventanaControl11 = false;
    public static boolean ventanaControl12 = false;
    public static boolean ventanaControl13 = false;
    public static boolean ventanaControl14 = false;
    public static boolean ventanaControl15 = false;

    public class Imagen extends javax.swing.JPanel {

        private String file;

        public Imagen(String file, Dimension dim) {
            this.setSize(dim); //se selecciona el tamaño del panel
            this.file = file;
        }
//Se crea un método cuyo parámetro debe ser un objeto Graphics

        public void paint(Graphics grafico) {
            Dimension height = getSize();
//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource(file));
//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }

    public Controlador_PantallaPrincipal() {
        Dimension dim;
        pantalla_Principal = new Pantalla_principal();
        dim = pantalla_Principal.getToolkit().getScreenSize();
        pantalla_Principal.setSize(dim);
        // pantalla_Principal.setResizable(false);
        Imagen imagen = new Imagen("/imagenes/farmacia.png", dim);
        pantalla_Principal.jPanelIma.add(imagen);
        pantalla_Principal.setVisible(true);
        pantalla_Principal.setExtendedState(MAXIMIZED_BOTH);
        inicioP();
        UIManager.put("OptionPane.minimumSize", new Dimension(300, 200));//cambiar al tamño de los JoptionPane
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
//abre ventana de login
        pantalla_Principal.jMenuItemIniciarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Principal.jMenuItemIniciarSesion.getText().equals("Iniciar Sesion")) {
                    pantalla_Principal.jDialogLogin.setTitle("Farmacia GI");
                    pantalla_Principal.jDialogLogin.setBounds(249, 154, 598, 344);
                    pantalla_Principal.jDialogLogin.setResizable(false);
                    pantalla_Principal.jDialogLogin.setVisible(true);
                    pantalla_Principal.jTextFieldUsuarioLogin.requestFocus();

                } else {
                    int m = JOptionPane.showConfirmDialog(pantalla_Principal, "<html><h1 align='center'>Desea cerrar sesion </h1></html>", "Confirmar salida", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (m == 0) {
                        inicioP();
                        pantalla_Principal.jMenuItemIniciarSesion.setText("Iniciar Sesion");
                        //  pantalla_Principal.panel.removeAll();
                        //  pantalla_Principal.panel.repaint();
                    } else {

                    }
                    //     JOptionPane.showMessageDialog(null, "Administrador a cerrado sesion");
                    // new Controlador_Mensaje(pantalla_Principal, "Administrador a cerrado sesion");
                }
            }
        });

//ingresa al login Aceptar
        pantalla_Principal.jTextFieldPasswordLogin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (!camposVacios()) {
                        pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.white);
                        pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.white);
                        String[] arr = new Empleado().obtenerContraUsuario(pantalla_Principal.jTextFieldUsuarioLogin.getText());
                        System.out.println(pantalla_Principal.jTextFieldUsuarioLogin.getText());
                        if (!arr[0].equals("")) {
                            if (arr[0].equals(pantalla_Principal.jTextFieldPasswordLogin.getText())) {
                                pantalla_Principal.jTextFieldUsuarioLogin.setText("");
                                pantalla_Principal.jTextFieldPasswordLogin.setText("");
                                idEmpleado = arr[1];
                                nombreEmpleado = arr[3];
                                rol = arr[2];
                                turno = arr[4];
                                pc = arr[5];
                                if (arr[2].equals("Administrador")) {
                                    activarAdministrador();
                                    pantalla_Principal.jDialogLogin.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Bienvenido: " + arr[3] + "</h1></html>");
                                } else if (arr[2].equals("Cajero")) {
                                    activarCajero();
                                    pantalla_Principal.jDialogLogin.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Bienvenido: " + arr[3] + "</h1></html>");
                                    Usuarios obj = new Usuarios();
                                    boolean next = obj.validarRecargaTurno(turno);
                                    if (!next) {
                                        pantalla_Principal.jDialogRecarga.setBounds(249, 154, 409, 150);
                                        pantalla_Principal.jDialogRecarga.setUndecorated(true);
                                        pantalla_Principal.jDialogRecarga.setLocationRelativeTo(null);
                                        pantalla_Principal.jDialogRecarga.setResizable(false);
                                        pantalla_Principal.jDialogRecarga.setVisible(true);
                                        pantalla_Principal.recargaMonto.requestFocus();
                                        pantalla_Principal.recargaMonto.setText("");
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "Error" , "<html><h1 align='center'>Error</h1></html>" , JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.red);
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Contraseña Incorrecta </h1></html>");
                            }
                        } else {
                            pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.red);
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Usuario Incorrecto </h1></html>");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Campos Vacios </h1></html>");
                    }
                }

            }
        });

        pantalla_Principal.jButtonIngresarLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!camposVacios()) {
                    pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.white);
                    pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.white);
                    String[] arr = new Empleado().obtenerContraUsuario(pantalla_Principal.jTextFieldUsuarioLogin.getText());
                    if (!arr[0].equals("")) {
                        if (arr[0].equals(pantalla_Principal.jTextFieldPasswordLogin.getText())) {
                            pantalla_Principal.jTextFieldUsuarioLogin.setText("");
                            pantalla_Principal.jTextFieldPasswordLogin.setText("");
                            idEmpleado = arr[1];
                            nombreEmpleado = arr[3];
                            turno = arr[4];
                            pc = arr[5];
                            if (arr[2].equals("Administrador")) {
                                activarAdministrador();
                                pantalla_Principal.jDialogLogin.setVisible(false);
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Bienvenido: " + arr[3] + "</h1></html>");

                            } else if (arr[2].equals("Cajero")) {
                                activarCajero();
                                pantalla_Principal.jDialogLogin.setVisible(false);
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Bienvenido: " + arr[3] + "</h1></html>");
                                Usuarios obj = new Usuarios();
                                    boolean next = obj.validarRecargaTurno(turno);
                                    if (!next) {
                                        pantalla_Principal.jDialogRecarga.setBounds(249, 154, 409, 150);
                                        pantalla_Principal.jDialogRecarga.setUndecorated(true);
                                        pantalla_Principal.jDialogRecarga.setLocationRelativeTo(null);
                                        pantalla_Principal.jDialogRecarga.setResizable(false);
                                        pantalla_Principal.jDialogRecarga.setVisible(true);
                                        pantalla_Principal.recargaMonto.requestFocus();
                                        pantalla_Principal.recargaMonto.setText("");
                                    }
                            }else{
                                 JOptionPane.showMessageDialog(null, "Error" , "<html><h1 align='center'>Error</h1></html>" , JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.red);
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Contraseña Incorrecta </h1></html>");
                        }
                    } else {
                        pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.red);
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Usuario Incorrecto </h1></html>");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Campos Vacios </h1></html>");
                }

            }
        });
        pantalla_Principal.btnRecarga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String respuesta = pantalla_Principal.recargaMonto.getText();
                if (respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingresar una cantidad </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_Principal.recargaMonto.requestFocus();
                    return;
                }
                if (respuesta.matches("^[0-9]+([.])?([0-9]+)?$")) {
                    System.out.println(respuesta);
                    Usuarios obj = new Usuarios();
                    obj.registrarSaldoRecarga(turno, respuesta);
                    pantalla_Principal.jDialogRecarga.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingresar una cantidad correcta </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_Principal.recargaMonto.requestFocus();
                    return;
                }
            }
        });

        pantalla_Principal.jButtonSalirLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Principal.jDialogLogin.setVisible(false);
            }
        });

        pantalla_Principal.jMenuItemGestionEmp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl1 == false) {
                    ventanaControl1 = true;
                    new Controlador_PantallaEmpleado();
                }
            }
        });

        pantalla_Principal.jMenuItemGestionProducto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (ventanaControl2 == false) {
                    ventanaControl2 = true;
                    new Controlador_PantallaProductos(rol, turno, Integer.parseInt(idEmpleado), pc);
                }

            }
        });

        pantalla_Principal.jMenuItemProductosAdmin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl2 == false) {
                    ventanaControl2 = true;
                    new Controlador_PantallaProductos(rol, turno, Integer.parseInt(idEmpleado), pc);
                }
            }
        });

        pantalla_Principal.jMenuItemGestionProveedor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl3 == false) {
                    ventanaControl3 = true;
                    new Controlador_PantallaProveedor();
                }

            }
        });
        pantalla_Principal.jMenuItemRealizarVenta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl9 == false) {
                    ventanaControl9 = true;
                    new Controlador_Pantalla_Ventas(idEmpleado, nombreEmpleado, turno, rol, pc);
                }

            }
        });

        pantalla_Principal.jMenuItemGestionCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl4 == false) {
                    ventanaControl4 = true;
                    new Controlador_PantallaCliente();
                }

            }
        });

        pantalla_Principal.jMenuItemDevoluciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
                }
                if (ventanaControl5 == false) {
                    ventanaControl5 = true;
                    new Controlador_PantallaDevoluciones(idEmpleado, nombreEmpleado, turno, pc);
                }
            }
        });

        pantalla_Principal.jMenuItemUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl6 == false) {
                    ventanaControl6 = true;
                    new Controlador_PantallaUsuarios();
                }

            }
        });

        pantalla_Principal.jMenuItemGrstionGastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl7 == false) {
                    ventanaControl7 = true;
                    new Controlador_PantallaGstos(turno, pc);
                }
            }
        });

        pantalla_Principal.jMenuItemRealizarCorteCaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl8 == false) {
                    ventanaControl8 = true;
                    new Pantalla_Corte(turno, pc);
                }
            }
        });

        pantalla_Principal.jMenuItemRetiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl10 == false) {
                    ventanaControl10 = true;
                    new Controlador_Pantalla_Retiros(turno);
                }
            }
        });

        pantalla_Principal.jMenuItemInformacionSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl12 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl11 == false) {
                    ventanaControl11 = true;
                    new Controlador_Pantalla_Informacion();
                }
            }
        });

        pantalla_Principal.jMenuItemBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl13 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl12 == false) {
                    ventanaControl12 = true;
                    new Controlador_Pantalla_Bajas(Integer.parseInt(idEmpleado));
                }
            }
        });

        pantalla_Principal.jMenuItemPromociones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Promociones();
                //JOptionPane.showMessageDialog(null, "<html><h1 align='center'>En reparacion. </h1></html>", "Pronto", JOptionPane.WARNING_MESSAGE);
            }
        });

        pantalla_Principal.jMenuItemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl14) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl13 == false) {
                    ventanaControl13 = true;
                    new Controlador_PantallaConfing(pc);
                }
            }
        });

        pantalla_Principal.jMenuItemCompras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl14 == false) {
                    ventanaControl14 = true;
                    new Controlador_PantallaCompras();
                }
            }
        });
        
        pantalla_Principal.jMenuItemBajasTemporales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaControl1 || ventanaControl3 || ventanaControl4 || ventanaControl5 || ventanaControl6 || ventanaControl7 || ventanaControl8
                        || ventanaControl9 || ventanaControl10 || ventanaControl11 || ventanaControl12 || ventanaControl13 || ventanaControl14  ) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Deberás cerrar ventana actual para abrir otra ventana. </h1></html>", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventanaControl15 == false) {
                    ventanaControl15 = true;
                    new Controlador_Pantalla_bajas_temporal();
                }
            }
        });

        pantalla_Principal.jMenuItemRealizarVenta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        pantalla_Principal.jMenuItemGrstionGastos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        pantalla_Principal.jMenuItemDevoluciones.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        pantalla_Principal.jMenuItemGestionProducto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        pantalla_Principal.jMenuItemRetiro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
    }

    public boolean camposVacios() {
        return pantalla_Principal.jTextFieldUsuarioLogin.getText().isEmpty() || pantalla_Principal.jTextFieldPasswordLogin.getText().isEmpty();
    }

    public void cerrar() {
        inicioP();
    }

    private void inicioP() {
        pantalla_Principal.jMenuItemGestionEmp.setEnabled(false);
        pantalla_Principal.jMenuItemGestionProducto.setEnabled(false);
        pantalla_Principal.jMenuItemGestionProveedor.setEnabled(false);
        pantalla_Principal.jMenuItemRealizarVenta.setEnabled(false);
        pantalla_Principal.jMenuItemGestionCliente.setEnabled(false);
        pantalla_Principal.jMenuItemGrstionGastos.setEnabled(false);
        pantalla_Principal.jMenuItemRealizarCorteCaja.setEnabled(false);
        pantalla_Principal.jMenuItemDevoluciones.setEnabled(false);
        pantalla_Principal.jMenuItemUsuarios.setEnabled(false);
        pantalla_Principal.jMenuItemProductosAdmin.setEnabled(false);
        pantalla_Principal.jMenuItemInformacionSucursal.setEnabled(false);
        pantalla_Principal.jMenuItemRetiro.setEnabled(false);
        pantalla_Principal.jMenuItemBajas.setEnabled(false);
        pantalla_Principal.jMenuItemPromociones.setEnabled(false);
        pantalla_Principal.jMenuItemSettings.setEnabled(false);
        pantalla_Principal.jMenuItemCompras.setEnabled(false);
        pantalla_Principal.jMenuItemBajasTemporales.setEnabled(false);
        pantalla_Principal.jMenuAdmon.setEnabled(true);
        pantalla_Principal.jMenuCajero.setEnabled(true);
    }

    private void activarAdministrador() {
        pantalla_Principal.jMenuItemGestionEmp.setEnabled(true);
        pantalla_Principal.jMenuItemGestionProveedor.setEnabled(true);
        pantalla_Principal.jMenuItemGestionCliente.setEnabled(true);
        pantalla_Principal.jMenuItemUsuarios.setEnabled(true);
        pantalla_Principal.jMenuCajero.setEnabled(false);
        pantalla_Principal.jMenuItemProductosAdmin.setEnabled(true);
        pantalla_Principal.jMenuItemInformacionSucursal.setEnabled(true);
        pantalla_Principal.jMenuItemBajas.setEnabled(true);
        pantalla_Principal.jMenuItemSettings.setEnabled(true);
        pantalla_Principal.jMenuItemPromociones.setEnabled(true);
        pantalla_Principal.jMenuItemCompras.setEnabled(true);
        pantalla_Principal.jMenuItemIniciarSesion.setText("Cerrar Sesion");

    }

    private void activarCajero() {
        pantalla_Principal.jMenuItemGrstionGastos.setEnabled(true);
        pantalla_Principal.jMenuItemRealizarCorteCaja.setEnabled(true);
        pantalla_Principal.jMenuItemRealizarVenta.setEnabled(true);
        pantalla_Principal.jMenuAdmon.setEnabled(false);
        pantalla_Principal.jMenuItemDevoluciones.setEnabled(true);
        pantalla_Principal.jMenuItemGestionProducto.setEnabled(true);
        pantalla_Principal.jMenuItemInformacionSucursal.setEnabled(false);
        pantalla_Principal.jMenuItemRetiro.setEnabled(true);
        pantalla_Principal.jMenuItemBajasTemporales.setEnabled(true);
        pantalla_Principal.jMenuItemIniciarSesion.setText("Cerrar Sesion");

    }

    public static void main(String[] args) {
        Controlador_PantallaPrincipal controlador_PantallaPrincipal = new Controlador_PantallaPrincipal();
    }
}
