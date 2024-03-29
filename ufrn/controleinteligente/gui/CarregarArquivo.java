/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CarregarArquivo.java
 *
 * Created on 20/09/2011, 21:33:12
 */

package br.ufrn.controleinteligente.gui;

import br.ufrn.controleinteligente.estruturas.No;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Andre
 */
public class CarregarArquivo extends javax.swing.JFrame {

    private Interface Interface;

    private java.util.Vector dados = new java.util.Vector();

    /** Creates new form CarregarArquivo */
    public CarregarArquivo(java.awt.Frame parent, boolean modal, Interface Interface) {
        initComponents();
        this.Interface = Interface;
        String linha_str;

        try {
            if(escolhaArquivo.showOpenDialog(escolhaArquivo) == JFileChooser.APPROVE_OPTION){
                File file = escolhaArquivo.getSelectedFile();

                String textoEntrada = "";

                FileInputStream isTwo = new FileInputStream(""+file.getPath());

                BufferedReader dsTwo = new BufferedReader(new InputStreamReader(isTwo));

                dados.removeAllElements();

                //Cada linha do arquivo será atribuído a linha_str
                while((linha_str = dsTwo.readLine()) != null) {
                    dados.add(linha_str + " ");
                    textoEntrada += linha_str+"\n";
                }

                dsTwo.close();
                calcula();
                //this.dispose();
            }
        }catch(Exception e){

        }
        
    }

    private void calcula(){
        String auxString = "";
        String capturaLetra = "";
        String resultado = "";
        int tamanhoString = 0;
        int tipoM = 0;
        int variacaoPixel = 0;

        for(int j=0;j<dados.size();j++) {
            auxString = dados.get(j).toString();
            tamanhoString = auxString.length();
            for(int i=0;i<tamanhoString;i++){
                capturaLetra = auxString.substring(i, i+1);

                if(!capturaLetra.equals(""))
                    resultado += capturaLetra;
            }
            resultado+="\n";
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        escolhaArquivo = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecione o arquivo");

        escolhaArquivo.setCurrentDirectory(new java.io.File("E:\\Windows\\Documentos\\Programação\\Java\\ControleInteligente\\Labirinto\\arquivos"));
        escolhaArquivo.setDialogTitle("Selecione o arquivo");
        escolhaArquivo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(escolhaArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(escolhaArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser escolhaArquivo;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
