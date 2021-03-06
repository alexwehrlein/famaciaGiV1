/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador_PantallaPrincipal;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelo.Productos;
import modelo.Proveedor;

/**
 *
 * @author saube
 */
public class Pantalla_Productos extends javax.swing.JFrame {

    Pantalla_Productos pantalla_Productos;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    DefaultComboBoxModel<Proveedor> modeloCategorias;
    Productos productos = new Productos();

    /**
     * Creates new form Pantalla_Productos
     */
    public Pantalla_Productos() {
        modeloCategorias = new DefaultComboBoxModel<Proveedor>();
        cargarModeloCat();
        initComponents();
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        this.setResizable(false);
        this.getContentPane().setBackground(new java.awt.Color(153,153,255));
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/logo22.png"));

        return retValue;
    }

    public void cargarModeloCat() {

        ArrayList<Proveedor> listaCategorias;
        listaCategorias = productos.octenerProveedores();

        for (Proveedor categoria : listaCategorias) {
            modeloCategorias.addElement(categoria);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jDialogAddProducto = new javax.swing.JDialog(this,true);
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        altaMedicamentoCodigo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        altaMedicamentoMarcaComercial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        altaMedicamentoSustancia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        altaMedicamentoPrecio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        altaMedicamentoTipoMedicamento = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        altaMedicamentoLavoratorio = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        altaMedicamentoProveedor = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        altaMedicamentoCantidad = new javax.swing.JTextField();
        altaMedicamentoGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        tablaProductos.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting() && (tablaProductos.getSelectedRow()>= 0)) {//This line prevents double events
                        int filaSeleccionada = tablaProductos.getSelectedRow();
                        long codi = (long)tablaProductos.getValueAt(filaSeleccionada, 0);
                        String medicamento = (String)tablaProductos.getValueAt(filaSeleccionada, 1);
                        //int cantidad = (int)tablaProductos.getValueAt(filaSeleccionada, 6);
                        String codiB = Long.toString(codi);
                        //String cantidadE = Integer.toString(cantidad);
                        codigo.setText(codiB);
                        nombre.setText(medicamento);
                        //existenciasM.setText(cantidadE);
                        Productos productos = new Productos(codi);

                    }
                }
            }
        );
        buscarProductos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        productoAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        existenciasM = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        campoAgregarExistencia = new javax.swing.JTextField();
        agregarInventario = new javax.swing.JButton();
        busquedaCodigo = new javax.swing.JRadioButton();
        busquedaNombre = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        agregarInventarioTikect = new javax.swing.JButton();
        actuslizartabla = new javax.swing.JButton();

        jDialogAddProducto.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialogAddProductoWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 112, 192));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ALTA DE MEDICAMENTO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 36))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Codigo:");

        altaMedicamentoCodigo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Marca Comercial: ");

        altaMedicamentoMarcaComercial.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Sustancia: ");

        altaMedicamentoSustancia.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Precio: ");

        altaMedicamentoPrecio.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Tipo de Medicamento: ");

        altaMedicamentoTipoMedicamento.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        altaMedicamentoTipoMedicamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PATENTE", "CONSULTA", "GENÉRICO", "ABARROTES", "PERFUMERIA" }));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("Laboratorio: ");

        altaMedicamentoLavoratorio.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        altaMedicamentoLavoratorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GI", "LEVIC", "AMSA", "REDER", "OFASA", "NADRO", "FLOR DE LIZ", "MONTECORSO" }));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("Proveedor: ");

        altaMedicamentoProveedor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        altaMedicamentoProveedor.setModel(modeloCategorias);

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Cantidad: ");

        altaMedicamentoCantidad.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        altaMedicamentoGuardar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        altaMedicamentoGuardar.setText("Guardar");
        altaMedicamentoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altaMedicamentoGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(altaMedicamentoMarcaComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(altaMedicamentoSustancia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(altaMedicamentoLavoratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(altaMedicamentoTipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(18, 18, 18)
                                    .addComponent(altaMedicamentoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(18, 18, 18)
                                    .addComponent(altaMedicamentoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(altaMedicamentoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(152, 152, 152))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(altaMedicamentoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)))))
                .addGap(0, 58, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(altaMedicamentoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(altaMedicamentoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(altaMedicamentoMarcaComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(altaMedicamentoSustancia, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(altaMedicamentoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(altaMedicamentoTipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(altaMedicamentoLavoratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(altaMedicamentoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(altaMedicamentoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(altaMedicamentoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogAddProductoLayout = new javax.swing.GroupLayout(jDialogAddProducto.getContentPane());
        jDialogAddProducto.getContentPane().setLayout(jDialogAddProductoLayout);
        jDialogAddProductoLayout.setHorizontalGroup(
            jDialogAddProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogAddProductoLayout.setVerticalGroup(
            jDialogAddProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogAddProductoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("INGRESAR PRODUCTOS A SUCURSAL ");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Marca Comercial", "Sustancia", "Precio", "Tipo de Medicamento", "Laboratorio", "Modificar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setGridColor(new java.awt.Color(51, 204, 255));
        jScrollPane1.setViewportView(tablaProductos);

        buscarProductos.setBackground(new java.awt.Color(153, 255, 153));
        buscarProductos.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        buscarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProductosActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find2.png"))); // NOI18N

        productoAgregar.setBackground(new java.awt.Color(255, 255, 255));
        productoAgregar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        productoAgregar.setText(" ALTA DE NUEVO MEDICAMENTO");
        productoAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        productoAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productoAgregarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel3.setText("CODIGO O NOMBRE:");

        codigo.setEditable(false);
        codigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel4.setText("DESCRIPCION:");

        nombre.setEditable(false);
        nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        existenciasM.setEditable(false);
        existenciasM.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        existenciasM.setForeground(new java.awt.Color(255, 0, 0));

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setText("PIEZAS A INGRESAR");

        campoAgregarExistencia.setBackground(new java.awt.Color(153, 255, 153));
        campoAgregarExistencia.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        campoAgregarExistencia.setForeground(new java.awt.Color(255, 0, 0));

        agregarInventario.setBackground(new java.awt.Color(255, 255, 255));
        agregarInventario.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        agregarInventario.setText("INGRESAR");
        agregarInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        busquedaCodigo.setBackground(new java.awt.Color(153, 153, 255));
        buttonGroup1.add(busquedaCodigo);
        busquedaCodigo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        busquedaCodigo.setSelected(true);
        busquedaCodigo.setText("CODIGO ");

        busquedaNombre.setBackground(new java.awt.Color(153, 153, 255));
        buttonGroup1.add(busquedaNombre);
        busquedaNombre.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        busquedaNombre.setText("NOMBRE");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel7.setText("BUSCAR MEDICAMENTO: ");

        agregarInventarioTikect.setBackground(new java.awt.Color(255, 255, 255));
        agregarInventarioTikect.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        agregarInventarioTikect.setText("TIKECT INVENTARIO");
        agregarInventarioTikect.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        actuslizartabla.setBackground(new java.awt.Color(255, 255, 255));
        actuslizartabla.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        actuslizartabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        actuslizartabla.setText("Refrescar");
        actuslizartabla.setToolTipText("Refrescar Tabla.");
        actuslizartabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(busquedaCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(busquedaNombre)
                                .addGap(28, 28, 28)
                                .addComponent(actuslizartabla)
                                .addGap(29, 29, 29)
                                .addComponent(productoAgregar)
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(170, 170, 170)
                                .addComponent(existenciasM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel3)
                                        .addGap(50, 50, 50))
                                    .addComponent(buscarProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoAgregarExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addComponent(agregarInventario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agregarInventarioTikect))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(existenciasM, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(busquedaCodigo)
                        .addComponent(busquedaNombre)
                        .addComponent(actuslizartabla)
                        .addComponent(productoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoAgregarExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(agregarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(agregarInventarioTikect, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarProductosActionPerformed

    private void productoAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productoAgregarActionPerformed

    }//GEN-LAST:event_productoAgregarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Controlador_PantallaPrincipal.ventanaControl2 = false;       
    }//GEN-LAST:event_formWindowClosing

    private void altaMedicamentoGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altaMedicamentoGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_altaMedicamentoGuardarActionPerformed

    private void jDialogAddProductoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogAddProductoWindowClosing
//        pantalla_Productos = new Pantalla_Productos();
//        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
    }//GEN-LAST:event_jDialogAddProductoWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton actuslizartabla;
    public javax.swing.JButton agregarInventario;
    public javax.swing.JButton agregarInventarioTikect;
    public javax.swing.JTextField altaMedicamentoCantidad;
    public javax.swing.JTextField altaMedicamentoCodigo;
    public javax.swing.JButton altaMedicamentoGuardar;
    public javax.swing.JComboBox<String> altaMedicamentoLavoratorio;
    public javax.swing.JTextField altaMedicamentoMarcaComercial;
    public javax.swing.JTextField altaMedicamentoPrecio;
    public javax.swing.JComboBox<Proveedor> altaMedicamentoProveedor;
    public javax.swing.JTextField altaMedicamentoSustancia;
    public javax.swing.JComboBox<String> altaMedicamentoTipoMedicamento;
    public javax.swing.JTextField buscarProductos;
    public javax.swing.JRadioButton busquedaCodigo;
    public javax.swing.JRadioButton busquedaNombre;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JTextField campoAgregarExistencia;
    public javax.swing.JTextField codigo;
    public javax.swing.JTextField existenciasM;
    public javax.swing.JDialog jDialogAddProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField nombre;
    public javax.swing.JButton productoAgregar;
    public javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
