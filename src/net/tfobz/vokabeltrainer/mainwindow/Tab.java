package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.tfobz.vokabeltrainer.model.*;

public class Tab extends JPanel
{

	JLabel l_Falsch = new JLabel("");
	JLabel l_Vorgabe = new JLabel("");
	JTextField eingabe = new JTextField();
	/**
	 * Create the application.
	 */
	public Tab(Lernkartei chosenKartei, JTabbedPane tabbedPane) {
		this.setBounds(0 , 0, 744, 346);
		this.setLayout(null);
		
		this.l_Vorgabe.setHorizontalAlignment(SwingConstants.CENTER);
		this.l_Vorgabe.setFont(new Font("Tahoma", Font.PLAIN, 60));
		this.l_Vorgabe.setBounds(149, 10, 450, 73);
		
		int selectedIndex = tabbedPane.getSelectedIndex();
		if(selectedIndex == -1) {
			selectedIndex = 0;
		}
		this.add(l_Vorgabe);
		
		eingabe.setFont(new Font("Tahoma", Font.PLAIN, 40));
		eingabe.setBounds(149, 209, 450, 50);
		eingabe.setColumns(10);
		this.add(eingabe);
		
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
		
		this.l_Falsch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.l_Falsch.setHorizontalAlignment(SwingConstants.CENTER);
		this.l_Falsch.setBounds(0, 162, 734, 30);
		this.add(l_Falsch);
		
		this.setVisible(true);
	}

}
