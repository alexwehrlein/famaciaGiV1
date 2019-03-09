/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador_PantallaPrincipal;
import java.awt.Image;
import java.awt.Toolkit;



/**
 *
 * @author saube
 */
public class Pantalla_Empleados extends javax.swing.JFrame {

    /**
     * Creates new form Pantalla_Empleados
     */
    public Pantalla_Empleados() {
        
        initComponents();
        tablaEmpleados.getTableHeader().setReorderingAllowed(false) ;
        this.setResizable(false);
    }
    
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/logo22.png"));


        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        agregarEmpleado = new javax.swing.JButton();

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Telefono", "Correo", "Eliminar", "Modificar"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setBackground(new java.awt.Color(153, 153, 255));
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("EMPLEADOS");

        tablaEmpleados.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Nombre", "Telefono", "Correo", "Direccion", "Puesto", "Turno", "Modificar", "Eliminar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEmpleados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tablaEmpleados);
        if (tablaEmpleados.getColumnModel().getColumnCount() > 0) {
            tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(200);
            tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaEmpleados.getColumnModel().getColumn(5).setPreferredWidth(120);
            tablaEmpleados.getColumnModel().getColumn(6).setPreferredWidth(100);
            tablaEmpleados.getColumnModel().getColumn(7).setPreferredWidth(130);
            tablaEmpleados.getColumnModel().getColumn(8).setPreferredWidth(130);
        }

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empleado.png"))); // NOI18N

        agregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/more.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(agregarEmpleado)
                .addGap(339, 339, 339)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(agregarEmpleado))
                    .addComponent(jLabel9)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(Pantalla_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_Empleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton agregarEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables
}