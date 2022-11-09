package doisouum;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DoisOuUm extends javax.swing.JFrame {
    Server server;

    public DoisOuUm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        spnNumJogadores = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        spnNumVencedores = new javax.swing.JSpinner();
        btnIniciarServidor = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnFecharServidor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPlayers = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dois ou Um(Servidor)");

        jLabel1.setText("Número de jogadores:");

        spnNumJogadores.setValue(2);

        jLabel2.setText("Número de vencedores: ");

        spnNumVencedores.setValue(1);

        btnIniciarServidor.setText("Iniciar Servidor");
        btnIniciarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarServidorActionPerformed(evt);
            }
        });

        jLabel3.setText("(2 a 10)");

        jLabel4.setText("(1 ou 2)");

        btnFecharServidor.setText("Fechar Servidor");
        btnFecharServidor.setEnabled(false);
        btnFecharServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharServidorActionPerformed(evt);
            }
        });

        tbPlayers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Jogadores"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbPlayers);
        if (tbPlayers.getColumnModel().getColumnCount() > 0) {
            tbPlayers.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnFecharServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciarServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnNumVencedores, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnNumJogadores, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spnNumJogadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnNumVencedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIniciarServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFecharServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 128, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        spnNumJogadores.getAccessibleContext().setAccessibleName("spnNumJogadores");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarServidorActionPerformed
        int numJogadores = (Integer)spnNumJogadores.getValue();
        int numVencedores = (Integer)spnNumVencedores.getValue();
        if(numJogadores < 2 || numJogadores > 10){
            JOptionPane.showMessageDialog(null, "Número de jogadores inválido.", "Informações", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        else if(numVencedores != 1 && numVencedores != 2){
            JOptionPane.showMessageDialog(null, "Número de vencedores inválido.", "Informações", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        spnNumJogadores.setEnabled(false);
        spnNumVencedores.setEnabled(false);
        btnIniciarServidor.setEnabled(false);
        btnFecharServidor.setEnabled(true);
        
        this.server = new Server(numJogadores, numVencedores);
        
        Thread thread = new Thread(server);
        thread.start();
        
        System.out.println("Servidor iniciado");
    }//GEN-LAST:event_btnIniciarServidorActionPerformed

    private void btnFecharServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharServidorActionPerformed
        spnNumJogadores.setEnabled(true);
        spnNumVencedores.setEnabled(true);
        btnIniciarServidor.setEnabled(true);
        btnFecharServidor.setEnabled(false);
        
        server.close();
        
        System.out.println("Servidor fechado");
    }//GEN-LAST:event_btnFecharServidorActionPerformed

    public static void atualizaTabela(){
        for(PlayerHandler ph : PlayerHandler.playerHandlersList){
            String tbData[] = {ph.getPlayerUserName()};
            DefaultTableModel tblModel = (DefaultTableModel)tbPlayers.getModel();
            
            tblModel.addRow(tbData);
        }
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoisOuUm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFecharServidor;
    private javax.swing.JButton btnIniciarServidor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnNumJogadores;
    private javax.swing.JSpinner spnNumVencedores;
    private static javax.swing.JTable tbPlayers;
    // End of variables declaration//GEN-END:variables
}
