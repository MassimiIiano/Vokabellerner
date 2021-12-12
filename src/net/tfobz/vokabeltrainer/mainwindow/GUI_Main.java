package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import net.tfobz.vokabeltrainer.dialogs.*;
import net.tfobz.vokabeltrainer.model.*;

public class GUI_Main extends JFrame
{

	Lernkartei chosenKartei;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI_Main();
			}
		});
	}

	public GUI_Main() {
		this.setTitle("Vokabeltrainer");
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 375,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 187, 750, 375);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		VokabeltrainerDB.loeschenLernkartei(0);

		try {
			chosenKartei = ChooseKartei.chooseKartei(this);
		} catch (NullPointerException e) {
			this.dispose();
		}

		if (chosenKartei != null) {

			int karteiNummer = chosenKartei.getNummer();

			VokabeltrainerDB.importierenKarten(karteiNummer, "Lernkarteien/" + chosenKartei.getBeschreibung() + ".txt");
			VokabeltrainerDB.aendernFach(new Fach(1, "Fach 1", 0, new Date()));

			JButton btnAddTab = new JButton("+");
			btnAddTab.setBounds(178, 0, 20, 20);
			btnAddTab.setBorder(null);
			btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnAddTab.setToolTipText("Neues Fach hinzufügen");
			btnAddTab.setBackground(new Color(238, 238, 238));

			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 0, 744, 346);
			for (int i = 1; i < 3; i++) {
				VokabeltrainerDB.hinzufuegenFach(karteiNummer, new Fach());
				VokabeltrainerDB.aendernFach(new Fach((i + 1), "Fach " + (i + 1), 0, new Date()));
			}
			ArrayList<Tab> tabs = new ArrayList<Tab>();
			for (int i = 0; i < 3; i++) {
				Tab tab = new Tab(chosenKartei, tabbedPane);
				tab.eingabe.addKeyListener(new KeyAdapter() {
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							//kontrolliere eingabe
						}
						
					}
					
				});
				tabs.add(tab);
				tabbedPane.addTab("Fach " + (i + 1), tabs.get(i));
			}

			tabbedPane.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == 2) {
						int fachNummer = tabbedPane.getTabCount();
						VokabeltrainerDB.loeschenFach(karteiNummer, fachNummer);
						tabbedPane.remove(tabbedPane.getTabCount() - 1);
						btnAddTab.setLocation(btnAddTab.getX() - 59, 0);
					} else if (e.getButton() == 1) {
						if (tabs.get(tabbedPane.getSelectedIndex()).l_Vorgabe.getText() == "") {
							Karte randomKarte = VokabeltrainerDB.getZufaelligeKarte(chosenKartei.getNummer(),
									tabbedPane.getSelectedIndex() + 1);
							if (randomKarte == null) {
								tabs.get(tabbedPane.getSelectedIndex()).l_Falsch.setForeground(Color.RED);
								tabs.get(tabbedPane.getSelectedIndex()).l_Falsch.setText("In diesem Fach sind noch keine Wörter");
							} else {
								tabs.get(tabbedPane.getSelectedIndex()).l_Vorgabe.setText(randomKarte.getWortEins());
							}
						}
					}
				}
			});
			btnAddTab.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (tabbedPane.getTabCount() < 12) {
						int fachNmber = VokabeltrainerDB.getFaecher(karteiNummer).size() + 1;
						VokabeltrainerDB.hinzufuegenFach(karteiNummer, new Fach());
						VokabeltrainerDB.aendernFach(new Fach(fachNmber, "Fach " + fachNmber, 0, new Date()));
						tabs.add(new Tab(chosenKartei, tabbedPane));
						tabbedPane.addTab("Fach " + fachNmber, tabs.get(tabs.size() - 1));
						btnAddTab.setLocation(btnAddTab.getX() + 59, 0);
					}
				}
			});

			getContentPane().add(btnAddTab);
			getContentPane().add(tabbedPane);

			setVisible(true);
		} else {
			this.dispose();
		}
	}
}
