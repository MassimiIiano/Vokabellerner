package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.tfobz.vokabeltrainer.Sprachen;

public class Tab extends JPanel {

	/**
	 * Create the application.
	 */
	public Tab() {
		this.setBounds(0, 0, 744, 426);
		this.setLayout(null);

		JLabel lblVon = new JLabel("Von:");
		lblVon.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblVon.setBounds(20, 35, 62, 37);
		this.add(lblVon);

		JLabel lblZu = new JLabel("Zu:");
		lblZu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblZu.setBounds(450, 35, 45, 37);
		this.add(lblZu);

		JComboBox comboBoxLinks = new JComboBox();
		lblVon.setLabelFor(comboBoxLinks);
		comboBoxLinks.setBounds(92, 42, 209, 30);
		for (Sprachen sprache : Sprachen.values())
			comboBoxLinks.addItem(sprache.toString());
		
		comboBoxLinks.setSelectedIndex(-1);
		this.add(comboBoxLinks);

		JComboBox comboBoxRechts = new JComboBox();
		lblZu.setLabelFor(comboBoxRechts);
		comboBoxRechts.setBounds(505, 42, 209, 30);
		for (Sprachen sprache : Sprachen.values())
			comboBoxRechts.addItem(sprache.toString());

		comboBoxRechts.setSelectedIndex(-1);
		this.add(comboBoxRechts);

		JLabel lblVorgabe = new JLabel("Vorgabe");
		lblVorgabe.setHorizontalAlignment(SwingConstants.CENTER);
		lblVorgabe.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblVorgabe.setBounds(149, 90, 450, 73);
		this.add(lblVorgabe);

		JTextField Eingabe = new JTextField();
		Eingabe.setFont(new Font("Tahoma", Font.PLAIN, 40));
		Eingabe.setBounds(149, 289, 450, 50);
		Eingabe.setColumns(10);
		this.add(Eingabe);

		JButton btnPlus = new JButton("+");
		btnPlus.setToolTipText("Füge ein Wort der Liste hinzu");
		btnPlus.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPlus.setBorder(null);
		btnPlus.setBackground(new Color(240, 240, 240));
		btnPlus.setBounds(305, 185, 40, 40);
		this.add(btnPlus);

		JButton btnMinus = new JButton("-");
		btnMinus.setToolTipText("Lösche ein Wort aus der Liste");
		btnMinus.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnMinus.setBorder(null);
		btnMinus.setBackground(new Color(240, 240, 240));
		btnMinus.setBounds(412, 185, 40, 40);
		btnMinus.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				new JOptionPane().showConfirmDialog(Tab.this, "Möchten sie dieses Wort aus der Liste entfernen?",
						"Wort Löschen", JOptionPane.YES_NO_OPTION);
			}
		});
		this.add(btnMinus);

		JButton btnErrinerung = new JButton("Errinerung");
		btnErrinerung.setBounds(444, 354, 130, 30);
		this.add(btnErrinerung);

		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.setBounds(584, 354, 130, 30);
		this.add(btnEinstellungen);

		JLabel lblFalsch = new JLabel("falsch");
		lblFalsch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFalsch.setHorizontalAlignment(SwingConstants.CENTER);
		lblFalsch.setBounds(0, 242, 734, 30);
		this.add(lblFalsch);

		this.setVisible(true);
	}

}
