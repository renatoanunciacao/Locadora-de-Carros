/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.LocacaoDAO;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Carro;
import modelo.Cliente;
import modelo.Locacao;

/**
 *
 * @author Renato
 */
public class TelaLocacao extends javax.swing.JFrame {

    DefaultTableModel modeloTabela = new DefaultTableModel(new Object[][]{}, new Object[]{"ID", "Data de saida", "Data de chegada", "Cliente", "Carro", "Valor Total", "Oculto"});

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Cliente c = new Cliente();
    Carro car = new Carro();

    /**
     * Creates new form TelaLocacao
     */
    public TelaLocacao() {
        initComponents();
        tblLocados.setModel(modeloTabela);
        tblLocados.removeColumn(tblLocados.getColumnModel().getColumn(6));
       carregarLocacoes();
    }

    public void carregarLocacoes() {
        modeloTabela.setNumRows(0);
        LocacaoDAO dao = new LocacaoDAO();
        for (Locacao l : dao.carregarlocacoes()) {
            String aux = "";
            //System.out.println(""+l.getData_de_chegada().toString());
            if(l.getData_de_chegada() != null){
               aux =  sdf.format(l.getData_de_chegada());
            }
            modeloTabela.addRow(new Object[]{l.getId(), sdf.format(l.getData_de_saida()), aux,l.getCliente().getNome(), l.getCarro().getNome(), l.getValor_total(), l});
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLocados = new javax.swing.JTable();
        btnLocar = new javax.swing.JButton();
        btnDevolver = new javax.swing.JButton();
        btnInformacoes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));

        tblLocados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLocados);

        btnLocar.setText("Locar Carro");
        btnLocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocarActionPerformed(evt);
            }
        });

        btnDevolver.setText("Devolver Carro");
        btnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverActionPerformed(evt);
            }
        });

        btnInformacoes.setText("Informações");
        btnInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacoesActionPerformed(evt);
            }
        });

        jLabel1.setText("Carros alugados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnLocar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDevolver)
                            .addGap(97, 97, 97)
                            .addComponent(btnInformacoes))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocar)
                    .addComponent(btnDevolver)
                    .addComponent(btnInformacoes))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolverActionPerformed

        int indiceLinha = tblLocados.getSelectedRow();
        if (indiceLinha != -1) {
            Locacao selecionado = (Locacao) modeloTabela.getValueAt(indiceLinha, 6);

            LocarCarro tela = new LocarCarro(this, rootPaneCheckingEnabled);
            tela.setL(selecionado);
            tela.setVisible(true);
            tela.devolverVeiculo();
           
           //System.out.println(selecionado.getCarro().getCategoria());
        int diferencaDias = (int) (selecionado.getData_de_chegada().getTime() - selecionado.getData_de_saida().getTime()) / (1000 * 60 * 60 * 24);
       selecionado.setValor_total(diferencaDias * selecionado.getCarro().getCategoria().getValor());
    
            if (selecionado != null) {
                LocacaoDAO dao = new LocacaoDAO();
                dao.devolver(selecionado);
                carregarLocacoes();
                
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma Locação ");
        }
    }//GEN-LAST:event_btnDevolverActionPerformed

    private void btnLocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocarActionPerformed
        LocarCarro tela = new LocarCarro(this, rootPaneCheckingEnabled);
        tela.setVisible(true);
        tela.locarVeiculo();
        Locacao novo = tela.getL();

        if (novo != null) {
            LocacaoDAO dao = new LocacaoDAO();
            dao.locar(novo);
            carregarLocacoes();

        }
    }//GEN-LAST:event_btnLocarActionPerformed

    private void btnInformacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacoesActionPerformed
         int indiceLinha = tblLocados.getSelectedRow();
        if (indiceLinha != -1) {
            Locacao selecionado = (Locacao) modeloTabela.getValueAt(indiceLinha, 6);
            
            JOptionPane.showMessageDialog(this, "Nome do cliente: "+selecionado.getCliente().getNome()
                    +"\nCarro alugado: "+selecionado.getCarro().getNome()
                    +"\nData de saída do carro: "+sdf.format(selecionado.getData_de_saida())
                    +"\nData de chegada do carro: "+sdf.format(selecionado.getData_de_chegada())
                    + "\nValor total do aluguel "+ selecionado.getValor_total());
        }else{
            System.out.println("Selecione uma locação");
        }
        
    }//GEN-LAST:event_btnInformacoesActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLocacao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLocacao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLocacao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLocacao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLocacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDevolver;
    private javax.swing.JButton btnInformacoes;
    private javax.swing.JButton btnLocar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLocados;
    // End of variables declaration//GEN-END:variables
}