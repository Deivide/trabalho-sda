/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on Sep 28, 2011, 1:07:26 AM
 */
package sda.client.gui.form.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import sda.bo.Archive;
import sda.bo.User;
import sda.client.gui.dialog.login.LoginDialog;
import sda.client.gui.dialog.login.ServerPathDialog;
import sda.client.gui.dialog.user.AddUserDialog;
import sda.client.gui.dialog.user.UsersDialog;
import sda.client.manager.UCHandlerArchiveManager;
import sda.comunication.especification.Especification;
import sda.manager.rmi.RemoteServiceInterface;

public class Main extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private User user = null;
    private boolean authentication = false;
    private DefaultTableModel modelTable = null;
    private JTable tableArquivos = null;
    
    /** Creates new form Main */
    public Main() {
        setLookAndFeel();
        initComponents();
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        setLocation(x, y);
        new ServerPathDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }
    
    private void generateTable() {
        List<Archive> listFiles = null;
                
        //Conexao RMI onde invoca metodo remoto para retornar todos os objetos Archives
        try {
            Registry reg = LocateRegistry.getRegistry(Especification.MANAGER_ADDR, Especification.RMI_PORT);
            RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
            listFiles = stub.retrieveAllArchive();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        if (listFiles != null && (listFiles.get(0).getName() != null)) {
            modelTable = new DefaultTableModel();
            tableArquivos = new JTable(modelTable);
            tableArquivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tableArquivos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (tableArquivos.getSelectedRow() >= 0) {
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                            UCHandlerArchiveManager uc = new UCHandlerArchiveManager();
                            uc.receiveFile(modelTable.getValueAt(tableArquivos.getSelectedRow(), 0).toString(), fileChooser.getSelectedFile().getPath());
                        }
                    }
                }
            });

            modelTable.addColumn("Nome");
            modelTable.addColumn("Tamanho");

            //Preenche a tabela de arquivos com os dados
            for (Archive file : listFiles) {
                modelTable.addRow(new Object[] {file.getName(), file.getSize()});
            }
            if (listFiles != null) {
                labelTotalFiles.setText("Total de Arquivos: " + listFiles.size());
            }
            scrollTable.setViewportView(tableArquivos);
            scrollTable.updateUI();
            
        } else {
            modelTable = new DefaultTableModel();
            tableArquivos = new JTable(modelTable);
        }
    }
    
    private void clearTableFiles() {
        scrollTable.setViewportView(null);
        scrollTable.updateUI();
        modelTable = null;
        tableArquivos = null;
        labelTotalFiles.setText("");
    }
    
    /**
     * Metodo que define se os botoes estao ativos ou nao.
     * @param enabled 
     */
    private void setStateButtons(boolean enabled) {
        btnListarArquivos.setEnabled(enabled);
        btnUpload.setEnabled(enabled);
        menuDesconectar.setEnabled(enabled);
        menuConectar.setEnabled(!enabled);
        if (!enabled) {
            clearTableFiles();
        }
    }
    
     /**
     * Método que define o tema de exibição do frame.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        panelFrame1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnListarArquivos = new javax.swing.JButton();
        btnUpload = new javax.swing.JButton();
        labelTotalFiles = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        labelStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSistema = new javax.swing.JMenu();
        menuConectar = new javax.swing.JMenuItem();
        menuDesconectar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSair = new javax.swing.JMenuItem();
        menuUsuarios = new javax.swing.JMenu();
        menuNovoUsuario = new javax.swing.JMenuItem();
        menuListarUsuários = new javax.swing.JMenuItem();

        fileChooser.setCurrentDirectory(new java.io.File("C:\\Program Files\\NetBeans 7.0.1"));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SDA Cliente");
        setMinimumSize(new java.awt.Dimension(450, 400));

        panelFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder("Arquivos"));
        panelFrame1.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 0));

        btnListarArquivos.setText("Listar");
        btnListarArquivos.setEnabled(false);
        btnListarArquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnListFiles(evt);
            }
        });
        jPanel2.add(btnListarArquivos);

        btnUpload.setText("Upload");
        btnUpload.setEnabled(false);
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnUpload(evt);
            }
        });
        jPanel2.add(btnUpload);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);
        jPanel1.add(labelTotalFiles, java.awt.BorderLayout.CENTER);

        panelFrame1.add(jPanel1, java.awt.BorderLayout.NORTH);
        panelFrame1.add(scrollTable, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelFrame1, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setPreferredSize(new java.awt.Dimension(407, 23));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));
        jPanel3.add(labelStatus);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        menuSistema.setText("Sistema");

        menuConectar.setText("Conectar");
        menuConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnConnect(evt);
            }
        });
        menuSistema.add(menuConectar);

        menuDesconectar.setText("Desconectar");
        menuDesconectar.setEnabled(false);
        menuDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnDisconnect(evt);
            }
        });
        menuSistema.add(menuDesconectar);
        menuSistema.add(jSeparator1);

        menuSair.setText("Sair");
        menuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnExit(evt);
            }
        });
        menuSistema.add(menuSair);

        jMenuBar1.add(menuSistema);

        menuUsuarios.setText("Usuários");

        menuNovoUsuario.setText("Cadastrar");
        menuNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnNewUser(evt);
            }
        });
        menuUsuarios.add(menuNovoUsuario);

        menuListarUsuários.setText("Listar");
        menuListarUsuários.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnListUsers(evt);
            }
        });
        menuUsuarios.add(menuListarUsuários);

        jMenuBar1.add(menuUsuarios);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actionBtnConnect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnConnect
        user = new User();
        LoginDialog loginDialog = new LoginDialog(this, rootPaneCheckingEnabled, rootPaneCheckingEnabled, user, labelStatus);
        
        authentication = loginDialog.getAuthentication();
        
        if (authentication) {
            setStateButtons(true);
            menuDesconectar.setText("Desconectar (" + user.getUser() + ")");
        }
    }//GEN-LAST:event_actionBtnConnect

    private void actionBtnDisconnect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnDisconnect
        authentication = false;
        user = new User();
        menuDesconectar.setText("Desconectar");
        setStateButtons(false);
    }//GEN-LAST:event_actionBtnDisconnect

    private void actionBtnNewUser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnNewUser
        new AddUserDialog(this, true, false);
    }//GEN-LAST:event_actionBtnNewUser

    private void actionBtnListUsers(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnListUsers
        new UsersDialog(this, true);
    }//GEN-LAST:event_actionBtnListUsers

    private void actionBtnExit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnExit
        dispose();
        System.exit(0);
    }//GEN-LAST:event_actionBtnExit

    private void actionBtnUpload(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnUpload
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                new UCHandlerArchiveManager().sendFile(fileChooser.getSelectedFile().getPath());
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_actionBtnUpload

    private void actionBtnListFiles(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnListFiles
        generateTable();
    }//GEN-LAST:event_actionBtnListFiles

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListarArquivos;
    private javax.swing.JButton btnUpload;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelTotalFiles;
    private javax.swing.JMenuItem menuConectar;
    private javax.swing.JMenuItem menuDesconectar;
    private javax.swing.JMenuItem menuListarUsuários;
    private javax.swing.JMenuItem menuNovoUsuario;
    private javax.swing.JMenuItem menuSair;
    private javax.swing.JMenu menuSistema;
    private javax.swing.JMenu menuUsuarios;
    private javax.swing.JPanel panelFrame1;
    private javax.swing.JScrollPane scrollTable;
    // End of variables declaration//GEN-END:variables
}
