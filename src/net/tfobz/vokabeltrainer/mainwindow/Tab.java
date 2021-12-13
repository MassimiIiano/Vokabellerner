package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Tab extends JPanel
{

	/**
	 * Create the application.
	 */
	public Tab() {
		this.setBounds(0 , 0, 744, 346);
		this.setLayout(null);
		
		JLabel lblVorgabe = new JLabel("Vorgabe");
		lblVorgabe.setHorizontalAlignment(SwingConstants.CENTER);
		lblVorgabe.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblVorgabe.setBounds(149, 10, 450, 73);
		this.add(lblVorgabe);
		
		JTextField Eingabe = new JTextField();
		Eingabe.setFont(new Font("Tahoma", Font.PLAIN, 40));
		Eingabe.setBounds(149, 209, 450, 50);
		Eingabe.setColumns(10);
		this.add(Eingabe);
		
		JButton btnPlus = new JButton("+");
		btnPlus.setToolTipText("Füge ein Wort der Liste hinzu");
		btnPlus.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPlus.setBorder(null);
		btnPlus.setBackground(new Color(240,240,240));
		btnPlus.setBounds(305, 105, 40, 40);
		this.add(btnPlus);
		
		JButton btnMinus = new JButton("-");
		btnMinus.setToolTipText("Lösche ein Wort aus der Liste");
		btnMinus.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnMinus.setBorder(null);
		btnMinus.setBackground(new Color(240,240,240));
		btnMinus.setBounds(412, 105, 40, 40);
		btnMinus.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				new JOptionPane().showConfirmDialog(Tab.this, "Möchten sie dieses Wort aus der Liste entfernen?", "Wort Löschen", JOptionPane.YES_NO_OPTION);
			}
		});
		this.add(btnMinus);
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.setBounds(584, 274, 130, 30);
		btnEinstellungen.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new net.tfobz.vokabeltrainer.dialogs.SettingsDialog();
			}
		});
		this.add(btnEinstellungen);
		
		JLabel lblFalsch = new JLabel("falsch");
		lblFalsch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFalsch.setHorizontalAlignment(SwingConstants.CENTER);
		lblFalsch.setBounds(0, 162, 734, 30);
		this.add(lblFalsch);
		
		this.setVisible(true);
	}

}
