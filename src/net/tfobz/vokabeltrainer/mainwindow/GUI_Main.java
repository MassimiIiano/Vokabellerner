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
	int selectedTab;
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	int karteiNummer = -1;

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
			this.chosenKartei = ChooseKartei.chooseKartei(this);
		} catch (NullPointerException e) {
			this.dispose();
		}

		if (this.chosenKartei != null) {

			this.karteiNummer = this.chosenKartei.getNummer();

			importKarten();
			
			JButton btnAddTab = new JButton("+");
			btnAddTab.setBounds(178, 0, 20, 20);
			btnAddTab.setBorder(null);
			btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnAddTab.setToolTipText("Neues Fach hinzufügen");
			btnAddTab.setBackground(new Color(238, 238, 238));

			this.tabbedPane.setBounds(0, 0, 744, 346);
			for (int i = 0; i < 3; i++) {
				Tab tab = new Tab(this.chosenKartei, this.tabbedPane);
				tab.btnMinus.addMouseListener(new MouseAdapter() {

					@SuppressWarnings("static-access")
					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == 1) {
							int decision = new JOptionPane().showConfirmDialog(GUI_Main.this,
									"Möchten sie dieses Wort aus der Liste entfernen?", "Wort Löschen", JOptionPane.YES_NO_OPTION);
							if (decision == JOptionPane.YES_OPTION) {

								VokabeltrainerDB.loeschenKarte(GUI_Main.this.currentKarte.getNummer());
								updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
								createRandomKarte("In diesem Fach sind keine weiteren Wörter");
								GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).eingabe.setText("");

							} else {
								return;
							}
						}
					}
				});

				tab.btnPlus.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == 1) {
							NewWordDialog newWord = new NewWordDialog();
							newWord.labels[0].setText(GUI_Main.this.chosenKartei.getWortEinsBeschreibung());
							newWord.labels[1].setText(GUI_Main.this.chosenKartei.getWortZweiBeschreibung());
							newWord.btn_ok.addMouseListener(new MouseAdapter() {

								@Override
								public void mouseClicked(MouseEvent e) {

									if (e.getButton() == 1) {
										if (!newWord.textFields[0].getText().isEmpty() && !newWord.textFields[1].getText().isEmpty()) {
											Karte k = new Karte();
											k.setWortEins(newWord.textFields[0].getText());
											k.setWortZwei(newWord.textFields[1].getText());
											int check = VokabeltrainerDB.hinzufuegenKarte(GUI_Main.this.chosenKartei.getNummer(), k);
											if (check == 0) {
												JOptionPane.showMessageDialog(GUI_Main.this, "Wort erfolgreich hinzugefügt", "Neues Wort",
														JOptionPane.INFORMATION_MESSAGE);
												newWord.dispose();
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
							newWord.btn_cancel.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseClicked(MouseEvent e) {
									newWord.dispose();
								}
								
							});
						}

					}
				});

				tab.eingabe.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {

						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							checkEntry();
						}
					}

				});

				tab.btnOk.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == 1) {
							checkEntry();
						}

					}
				});

				tab.btnSwitch.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == 1) {
							VokabeltrainerDB.exportierenKarten(GUI_Main.this.chosenKartei.getNummer(),
									"Lernkarteien/" + GUI_Main.this.chosenKartei.getBeschreibung() + ".txt", true);
							VokabeltrainerDB.aendernLernkartei(new Lernkartei(GUI_Main.this.chosenKartei.getNummer(),
									GUI_Main.this.chosenKartei.getBeschreibung(), GUI_Main.this.chosenKartei.getWortEinsBeschreibung(),
									GUI_Main.this.chosenKartei.getWortZweiBeschreibung(), !GUI_Main.this.chosenKartei.getRichtung(),
									GUI_Main.this.chosenKartei.getGrossKleinschreibung()));
							VokabeltrainerDB.loeschenAlleFaecher(GUI_Main.this.chosenKartei.getNummer());
							importKarten();
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							createRandomKarte("In diesem Fach sind keine weiteren Wörter");
							GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).eingabe.setText("");
						}

					}
				});
				this.tabs.add(tab);
				this.tabbedPane.addTab("Fach " + (i + 1), this.tabs.get(i));
			}

			this.tabbedPane.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == 2) {
						GUI_Main.this.tabbedPane.setSelectedIndex(GUI_Main.this.selectedTab);
						int left = 58 * (GUI_Main.this.tabbedPane.getTabCount() - 1);
						int right = 58 * (GUI_Main.this.tabbedPane.getTabCount() - 1) + 58;
						if (e.getX() >= left && e.getX() <= right && e.getY() > 0 && e.getY() < 20) {
							int fachNummer = GUI_Main.this.tabbedPane.getTabCount();
							VokabeltrainerDB.loeschenFach(GUI_Main.this.karteiNummer, fachNummer);
							GUI_Main.this.tabbedPane.remove(GUI_Main.this.tabbedPane.getTabCount() - 1);
							btnAddTab.setLocation(btnAddTab.getX() - 58, 0);
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == 1) {
						GUI_Main.this.selectedTab = GUI_Main.this.tabbedPane.getSelectedIndex();
						int left = 58 * GUI_Main.this.tabbedPane.getSelectedIndex();
						int right = (58 * GUI_Main.this.tabbedPane.getSelectedIndex()) + 58;
						if (e.getX() >= left && e.getX() <= right && e.getY() > 0 && e.getY() < 20) {
							if (GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).l_Vorgabe.getText() == "" &&
									VokabeltrainerDB.getZufaelligeKarte(GUI_Main.this.chosenKartei.getNummer(),
											GUI_Main.this.tabbedPane.getSelectedIndex() + 1) == null) {
								createRandomKarte("In diesem Fach sind noch keine Wörter");
							} else {
								updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							}
						}
					}
				}
			});
			btnAddTab.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (GUI_Main.this.tabbedPane.getTabCount() < 12) {
						int fachNmber = VokabeltrainerDB.getFaecher(GUI_Main.this.karteiNummer).size() + 1;
						VokabeltrainerDB.hinzufuegenFach(GUI_Main.this.karteiNummer, new Fach());
						VokabeltrainerDB.aendernFach(new Fach(fachNmber, "Fach " + fachNmber, 0, new Date()));
						GUI_Main.this.tabs.add(new Tab(GUI_Main.this.chosenKartei, GUI_Main.this.tabbedPane));
						GUI_Main.this.tabbedPane.addTab("Fach " + fachNmber, GUI_Main.this.tabs.get(GUI_Main.this.tabs.size() - 1));
						btnAddTab.setLocation(btnAddTab.getX() + 59, 0);
					}
				}
			});

			getContentPane().add(btnAddTab);
			getContentPane().add(this.tabbedPane);

			updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
			createRandomKarte("In diesem Fach sind keine weiteren Wörter");
			this.tabs.get(this.tabbedPane.getSelectedIndex()).eingabe.setText("");

			setVisible(true);
		} else {
			this.dispose();
		}
	}

	public void createRandomKarte(String text) {
		Karte randomKarte = VokabeltrainerDB.getZufaelligeKarte(this.chosenKartei.getNummer(),
				this.tabbedPane.getSelectedIndex() + 1);
		if (randomKarte == null) {
			this.currentKarte = null;
			updateCenterDialog(Color.RED, text);
			this.tabs.get(this.tabbedPane.getSelectedIndex()).l_Vorgabe.setText("");
		} else {
			this.currentKarte = randomKarte;
			if (VokabeltrainerDB.getLernkartei(this.chosenKartei.getNummer()).getRichtung()) {
				this.tabs.get(this.tabbedPane.getSelectedIndex()).l_Vorgabe.setText(randomKarte.getWortEins());
			} else {
				this.tabs.get(this.tabbedPane.getSelectedIndex()).l_Vorgabe.setText(randomKarte.getWortZwei());
			}
			updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
		}
	}

	public boolean updateCenterDialog(Color color, String text) {
		tabs.get(tabbedPane.getSelectedIndex()).l_centerDialog.setForeground(color);
		tabs.get(tabbedPane.getSelectedIndex()).l_centerDialog.setText(text);
		return false;
	}

	public void checkEntry() {
		if (!this.tabs.get(this.tabbedPane.getSelectedIndex()).eingabe.getText().isEmpty()) {

			if (this.currentKarte != null) {

				if (this.currentKarte.getRichtig(this.tabs.get(this.tabbedPane.getSelectedIndex()).eingabe.getText())) {

					new Thread(new Runnable() {

						@Override
						public void run() {
							updateCenterDialog(Color.GREEN, "Richtig! Die Antwort lautet '" + GUI_Main.this.currentKarte.getWortZwei() + "'");
							synchronized (this) {
								try {
									this.wait(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							VokabeltrainerDB.setKarteRichtig(GUI_Main.this.currentKarte);
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							createRandomKarte("In diesem Fach sind keine weiteren Wörter");
							GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).eingabe.setText("");
						}
					}).start();
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							updateCenterDialog(Color.RED, "Falsch! Die Antwort lautet '" + GUI_Main.this.currentKarte.getWortZwei() + "'");
							synchronized (this) {
								try {
									this.wait(2500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							VokabeltrainerDB.setKarteFalsch(GUI_Main.this.currentKarte);
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							createRandomKarte("In diesem Fach sind keine weiteren Wörter");
							GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).eingabe.setText("");
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

	public void importKarten() {
		VokabeltrainerDB.importierenKarten(this.karteiNummer, "Lernkarteien/" + this.chosenKartei.getBeschreibung() + ".txt");
		for (int i = 1; i <= VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size(); i++) {
			VokabeltrainerDB.aendernFach(new Fach(i, "Fach " + i, 0, new Date()));
		}

		if (VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size() < 3) {
			int ersteFachNummer = VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size() + 1;
			for (int i = ersteFachNummer; i <= 3; i++) {
				VokabeltrainerDB.hinzufuegenFach(this.chosenKartei.getNummer(), new Fach());
				VokabeltrainerDB.aendernFach(new Fach(i, "Fach " + i, 0, new Date()));
			}
		}
	}
	
}
