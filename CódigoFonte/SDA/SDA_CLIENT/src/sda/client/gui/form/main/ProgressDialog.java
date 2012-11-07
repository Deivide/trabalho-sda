package sda.client.gui.form.main;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ProgressDialog extends javax.swing.JDialog {
	private JButton btnCancel;
	private JProgressBar progress;
	private JLabel labelFile;
	private JLabel jLabel3;
	private JLabel labelDest;
	private JLabel labelSource;
	private JLabel jLabel2;
	private JLabel jLabel1;

	public ProgressDialog(JFrame frame, boolean modal) {
		super(frame, modal);
		setLookAndFeel();
		initGUI();
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
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.setTitle("Uploading...");
				this.setResizable(false);
				this.setName("Progress Operation");
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setText("Cancelar");
					btnCancel.setBounds(250, 106, 74, 23);
				}
				{
					progress = new JProgressBar();
					getContentPane().add(progress);
					progress.setBounds(10, 58, 314, 17);
				}
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("De:");
					jLabel1.setBounds(21, 11, 22, 14);
					jLabel1.setFont(new java.awt.Font("Tahoma",1,11));
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("Para:");
					jLabel2.setBounds(10, 33, 33, 14);
					jLabel2.setFont(new java.awt.Font("Tahoma",1,11));
				}
				{
					labelSource = new JLabel();
					getContentPane().add(labelSource);
					labelSource.setText("");
					labelSource.setBounds(48, 11, 276, 14);
				}
				{
					labelDest = new JLabel();
					getContentPane().add(labelDest);
					labelDest.setText("");
					labelDest.setBounds(47, 33, 277, 14);
				}
				{
					jLabel3 = new JLabel();
					getContentPane().add(jLabel3);
					jLabel3.setText("Copiando");
					jLabel3.setBounds(10, 81, 45, 14);
				}
				{
					labelFile = new JLabel();
					getContentPane().add(labelFile);
					labelFile.setText("");
					labelFile.setBounds(65, 81, 259, 14);
				}
			}
			this.setSize(340, 165);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
