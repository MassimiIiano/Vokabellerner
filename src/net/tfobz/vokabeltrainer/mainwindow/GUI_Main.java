package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	Karte currentKarte;
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	ArrayList<Tab> tabs = new ArrayList<Tab>();

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

			tabbedPane.setBounds(0, 0, 744, 346);
			for (int i = 1; i < 3; i++) {
				VokabeltrainerDB.hinzufuegenFach(karteiNummer, new Fach());
				VokabeltrainerDB.aendernFach(new Fach((i + 1), "Fach " + (i + 1), 0, new Date()));
			}
			for (int i = 0; i < 3; i++) {
				Tab tab = new Tab(chosenKartei, tabbedPane);
				tab.btnMinus.addMouseListener(new MouseAdapter() {

					@SuppressWarnings("static-access")
					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == 1) {
							int decision = new JOptionPane().showConfirmDialog(GUI_Main.this,
									"Möchten sie dieses Wort aus der Liste entfernen?", "Wort Löschen", JOptionPane.YES_NO_OPTION);
							if (decision == JOptionPane.YES_OPTION) {

								VokabeltrainerDB.loeschenKarte(currentKarte.getNummer());
								updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
								createRandomKarte("In diesem Fach sind keine weiteren Wörter");
								tabs.get(tabbedPane.getSelectedIndex()).eingabe.setText("");

							} else {
								return;
							}
						}
					}
				});

				tab.btnPlus.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {

						if(e.getButton() == 1) {
							NewWordDialog newWord = new NewWordDialog();
							newWord.labels[0].setText(chosenKartei.getWortEinsBeschreibung());
							newWord.labels[1].setText(chosenKartei.getWortZweiBeschreibung());
							newWord.btn_ok.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseClicked(MouseEvent e) {
									
									if(e.getButton() == 1) {
										if(!newWord.textFields[0].getText().isEmpty() &&
												!newWord.textFields[1].getText().isEmpty()) {
											Karte k = new Karte();
											k.setWortEins(newWord.textFields[0].getText());
											k.setWortZwei(newWord.textFields[1].getText());
											int check = VokabeltrainerDB.hinzufuegenKarte(chosenKartei.getNummer(), k);
											if(check == 0) {
												newWord.dispose();
												JOptionPane.showMessageDialog(GUI_Main.this, "Wort erfolgreich hinzugefügt",
														"Neues Wort", JOptionPane.INFORMATION_MESSAGE);
											} else {
												JOptionPane.showMessageDialog(newWord, "Ein Fehler ist aufgetreten!", "Fehler bei hinzufügen",
														JOptionPane.ERROR_MESSAGE);
											}
										} else {
											JOptionPane.showMessageDialog(newWord, "Keine Wörter eingegeben!", "Fehler bei Eingabe",
													JOptionPane.ERROR_MESSAGE);
										}
									}
									
								}
							});
						}
						
					}
				});

				tab.eingabe.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {

						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							if (!tabs.get(tabbedPane.getSelectedIndex()).eingabe.getText().isEmpty()) {
								if (currentKarte != null) {
									if (currentKarte.getRichtig(tabs.get(tabbedPane.getSelectedIndex()).eingabe.getText())) {

										new Thread(new Runnable() {

											@Override
											public void run() {
												updateCenterDialog(Color.GREEN,
														"Richtig! Die Antwort lautet '" + currentKarte.getWortZwei() + "'");
												synchronized (this) {
													try {
														this.wait(1000);
													} catch (InterruptedException e) {
														e.printStackTrace();
													}
												}
												VokabeltrainerDB.setKarteRichtig(currentKarte);
												updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
												createRandomKarte("In diesem Fach sind keine weiteren Wörter");
												tabs.get(tabbedPane.getSelectedIndex()).eingabe.setText("");
											}
										}).start();
									} else {
										new Thread(new Runnable() {

											@Override
											public void run() {
												updateCenterDialog(Color.RED,
														"Falsch! Die Antwort lautet '" + currentKarte.getWortZwei() + "'");
												synchronized (this) {
													try {
														this.wait(2500);
													} catch (InterruptedException e) {
														e.printStackTrace();
													}
												}
												VokabeltrainerDB.setKarteFalsch(currentKarte);
												updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
												createRandomKarte("In diesem Fach sind keine weiteren Wörter");
												tabs.get(tabbedPane.getSelectedIndex()).eingabe.setText("");
											}
										}).start();
									}
								}
							} else {
								new Thread(new Runnable() {

									@Override
									public void run() {
										updateCenterDialog(Color.RED, "Keine Antwort eingegeben!");
										synchronized (this) {
											try {
												this.wait(1000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
										updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
									}
								}).start();
							}
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
							createRandomKarte("In diesem Fach sind noch keine Wörter");
						} else {
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
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

			updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
			createRandomKarte("In diesem Fach sind keine weiteren Wörter");
			tabs.get(tabbedPane.getSelectedIndex()).eingabe.setText("");

			setVisible(true);
		} else {
			this.dispose();
		}
	}

	public void createRandomKarte(String text) {
		Karte randomKarte = VokabeltrainerDB.getZufaelligeKarte(chosenKartei.getNummer(),
				tabbedPane.getSelectedIndex() + 1);
		if (randomKarte == null) {
			currentKarte = null;
			updateCenterDialog(Color.RED, text);
			tabs.get(tabbedPane.getSelectedIndex()).l_Vorgabe.setText("");
		} else {
			currentKarte = randomKarte;
			tabs.get(tabbedPane.getSelectedIndex()).l_Vorgabe.setText(randomKarte.getWortEins());
			updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
		}
	}

	public boolean updateCenterDialog(Color color, String text) {
		tabs.get(tabbedPane.getSelectedIndex()).l_centerDialog.setForeground(color);
		tabs.get(tabbedPane.getSelectedIndex()).l_centerDialog.setText(text);
		return false;
	}

}
