package net.tfobz.vokabeltrainer.dialogs;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewWordDialog extends JFrame
{

	public JTextField textFields[] = new JTextField[2];
	public JLabel labels[] = new JLabel[2];
	public JButton btn_ok = new JButton("Ok");
	public JButton btn_cancel = new JButton("Abbrechen");
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new NewWordDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public NewWordDialog(){
		
		setBounds((Toolkit.getDefaultToolkit().getScreenSize().width/2)-150,
				(Toolkit.getDefaultToolkit().getScreenSize().height/2)-55, 300, 110);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Neues Wort");
		setResizable(false);
		FlowLayout layout = new FlowLayout();
		getContentPane().setLayout(layout);
		layout.setAlignment(FlowLayout.CENTER);
		layout.setVgap(10);
		layout.setHgap(10);
		
		for(int i = 0; i < 2; i++) {
			this.labels[i] = new JLabel();
			this.labels[i].setPreferredSize(new Dimension(50, 25));
			this.textFields[i] = new JTextField();
			this.textFields[i].setPreferredSize(new Dimension(65, 25));
			getContentPane().add(this.labels[i]);
			getContentPane().add(this.textFields[i]);
		}
		
		getContentPane().add(this.btn_ok);
		getContentPane().add(this.btn_cancel);
		setVisible(true);
	}
	
}
