/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mail.Mail;
import modelo.Productos;
import utilerias.Utilerias;
import vista.Pantalla_Faltantes;

/**
 *
 * @author saube
 */
public class Controller_Faltantes {

    Pantalla_Faltantes pantalla_Faltantes;

    public Controller_Faltantes(String turno, String idEmpleado) {
        pantalla_Faltantes = new Pantalla_Faltantes();
        pantalla_Faltantes.setVisible(true);
        pantalla_Faltantes.setLocationRelativeTo(null);

        pantalla_Faltantes.txtTesto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int valor = 0;
                if (pantalla_Faltantes.RNombre.isSelected()) {
                    valor = 0;
                } else {
                    valor = 1;
                }
                String text = pantalla_Faltantes.txtTesto.getText();
                Clear_Table(pantalla_Faltantes.jTableProductos);
                pantalla_Faltantes.jTableProductos.setModel(new Productos().faltanteTabla(pantalla_Faltantes.jTableProductos, text, valor));
            }
        });

        pantalla_Faltantes.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = pantalla_Faltantes.jTableProductos.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "<html><h1> Seleccione un producto</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String piezas = JOptionPane.showInputDialog(null, "Ingrese la cantidad de piezas que se pediran");
                DefaultTableModel model = (DefaultTableModel) pantalla_Faltantes.jTableFaltantes.getModel();
                model.addRow(new Object[]{pantalla_Faltantes.jTableProductos.getValueAt(filaSeleccionada, 0).toString(),
                    pantalla_Faltantes.jTableProductos.getValueAt(filaSeleccionada, 1).toString(),
                    pantalla_Faltantes.jTableProductos.getValueAt(filaSeleccionada, 2).toString(),
                    piezas
                });
                Clear_Table(pantalla_Faltantes.jTableProductos);
                pantalla_Faltantes.txtTesto.setText("");
                pantalla_Faltantes.txtTesto.requestFocus();
            }
        });

        pantalla_Faltantes.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = pantalla_Faltantes.jTableFaltantes.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "<html><h1> Seleccione un producto para eliminarlo</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DefaultTableModel model = (DefaultTableModel) pantalla_Faltantes.jTableFaltantes.getModel();
                model.removeRow(filaSeleccionada);
            }
        });

        pantalla_Faltantes.btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) pantalla_Faltantes.jTableFaltantes.getModel();
                if (model.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1> no hay datos en la tabla</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    FileOutputStream ficheroPdf = null;
                    // Se crea el documento
                    Document documento = new Document(PageSize.A4.rotate());
// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
                    ficheroPdf = new FileOutputStream("C:/farmacia/faltantes.pdf");
                    // Se asocia el documento al OutputStream y se indica que el espaciado entre
// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                    PdfWriter.getInstance(documento, ficheroPdf);
// Se abre el documento.
                    documento.open();

                    Image foto = Image.getInstance("src/imagenes/faltantes.png");
                    foto.scaleToFit(150, 150);
                    foto.setAlignment(Chunk.ALIGN_LEFT);
                    documento.add(foto);

                    Paragraph titulo = new Paragraph();
                    titulo.setAlignment(Paragraph.ALIGN_CENTER);
                    titulo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.BLACK));
                    titulo.add("PEDIDOS DE FALTANTES");
                    documento.add(titulo);

                    Paragraph titulo2 = new Paragraph();
                    titulo2.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo2.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    titulo2.add(dateFormat.format(date));
                    documento.add(titulo2);

                    Paragraph titulo3 = new Paragraph();
                    titulo3.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo3.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    titulo3.add(Utilerias.SUCURSALE);
                    documento.add(titulo3);

                    Paragraph saltolinea12 = new Paragraph();
                    saltolinea12.add("\n\n");
                    documento.add(saltolinea12);

                    float[] columnWidths = {3, 3, 1, 1};
                    PdfPTable tabla = new PdfPTable(columnWidths);
                    tabla.setWidthPercentage(100);
                    //Añadimos los títulos a la tabla. 
                    Paragraph columna1 = new Paragraph("MARCA COMERCIAL");
                    columna1.getFont().setStyle(Font.BOLD);
                    columna1.getFont().setSize(10);
                    tabla.addCell(columna1);

                    Paragraph columna2 = new Paragraph("SUSTANCIA");
                    columna2.getFont().setStyle(Font.BOLD);
                    columna2.getFont().setSize(10);
                    tabla.addCell(columna2);

                    Paragraph columna3 = new Paragraph("COMENTARIO");
                    columna3.getFont().setStyle(Font.BOLD);
                    columna3.getFont().setSize(10);
                    tabla.addCell(columna3);

                    Paragraph columna4 = new Paragraph("Piezas");
                    columna4.getFont().setStyle(Font.BOLD);
                    columna4.getFont().setSize(10);
                    tabla.addCell(columna4);

                    for (int i = 0; i < model.getRowCount(); i++) {
                        tabla.addCell(model.getValueAt(i, 1).toString());
                        tabla.addCell(model.getValueAt(i, 2).toString());
                        tabla.addCell("");
                        tabla.addCell(model.getValueAt(i, 3).toString());
                    }
                    documento.add(tabla);

                    documento.close();
                    Mail mail = new Mail();
                    mail.send_mail("pedidosgisucursal@gmail.com", "PDF", "Faltantes", 3); //farmaciagi08@gmail.com
                    Clear_Table(pantalla_Faltantes.jTableProductos);
                    Clear_Table(pantalla_Faltantes.jTableFaltantes);
                    pantalla_Faltantes.txtTesto.setText("");
                    pantalla_Faltantes.txtTesto.requestFocus();
                    JOptionPane.showMessageDialog(null, "<html><h1> Exito</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(Controlador_PantallaProductos.class.getName()).log(Level.SEVERE, " " + ex);
                }
            }
        });

    }

    private void Clear_Table(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
