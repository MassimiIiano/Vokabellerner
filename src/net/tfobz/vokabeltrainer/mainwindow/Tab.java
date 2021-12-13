package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.tfobz.vokabeltrainer.model.*;

public class Tab extends JPanel
{

	JLabel l_centerDialog = new JLabel("");
	JLabel l_Vorgabe = new JLabel("");
	JTextField eingabe = new JTextField();
	JButton btnMinus = new JButton("-");
	JButton btnPlus = new JButton("+");
	JButton btnOk = new JButton("Ok");
	JButton btnSwitch = new JButton("↕");
	/**
	 * Create the application.
	 */
	public Tab(Lernkartei chosenKartei, JTabbedPane tabbedPane) {
		this.setBounds(0 , 0, 744, 346);
		this.setLayout(null);
		
		Font basic = new Font("Tahoma", Font.BOLD, 30);
		
		this.l_Vorgabe.setHorizontalAlignment(SwingConstants.CENTER);
		this.l_Vorgabe.setFont(new Font("Tahoma", Font.PLAIN, 60));
		this.l_Vorgabe.setBounds(149, 10, 450, 73);
		
		int selectedIndex = tabbedPane.getSelectedIndex();
		if(selectedIndex == -1) {
			selectedIndex = 0;
		}
		this.add(l_Vorgabe);
		
		this.eingabe.setFont(new Font("Tahoma", Font.PLAIN, 40));
		this.eingabe.setBounds(149, 209, 370, 50);
		this.eingabe.setColumns(10);
		this.add(eingabe);
		
		this.btnPlus.setToolTipText("Füge ein Wort der Liste hinzu");
		this.btnPlus.setFont(basic);
		this.btnPlus.setBorder(BorderFactory.createBevelBorder(0));
		this.btnPlus.setBackground(new Color(240,240,240));
		this.btnPlus.setBounds(243, 105, 40, 40);
		this.add(btnPlus);
		
		this.btnMinus.setToolTipText("Lösche ein Wort aus der Liste");
		this.btnMinus.setFont(basic);
		this.btnMinus.setBorder(BorderFactory.createBevelBorder(0));
		this.btnMinus.setBackground(new Color(240,240,240));
		this.btnMinus.setBounds(432, 105, 40, 40);
		this.add(btnMinus);
		
		this.btnOk.setToolTipText("Überprüfe deine Eingabe");
		this.btnOk.setFont(basic);
		this.btnOk.setBorder(BorderFactory.createBevelBorder(0));
		this.btnOk.setBackground(new Color(240,240,240));
		this.btnOk.setBounds(519, 208, 50, 50);
		this.add(btnOk);
		
		this.btnSwitch.setToolTipText("Tausche die Sprachen");
		this.btnSwitch.setFont(basic);
		this.btnSwitch.setBorder(null);
		this.btnSwitch.setBackground(new Color(240,240,240));
		this.btnSwitch.setBounds(338, 105, 40, 40);
		this.add(btnSwitch);
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.setBounds(584, 274, 130, 30);
		btnEinstellungen.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new net.tfobz.vokabeltrainer.dialogs.SettingsDialog();
			}
		});
		this.add(btnEinstellungen);
		
		this.l_centerDialog.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.l_centerDialog.setHorizontalAlignment(SwingConstants.CENTER);
		this.l_centerDialog.setBounds(0, 162, 734, 30);
		this.add(l_centerDialog);
		
		this.setVisible(true);
	}

}
