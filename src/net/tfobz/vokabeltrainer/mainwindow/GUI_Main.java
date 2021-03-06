package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import net.tfobz.vokabeltrainer.dialogs.*;
import net.tfobz.vokabeltrainer.model.*;

public class GUI_Main extends JFrame
{

	Lernkartei chosenKartei;
	ArrayList<Karte> currentKarte = new ArrayList<Karte>();
	int selectedTab = 0;
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	int karteiNummer = -1;
	SettingsDialog sd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		
		// Zeigt Benutzer Auswahl aller gefundenen Lernkarteien, falls keine vorhanden
		// ist, wird eine Starterkartei erstellt.
		// Die Auswahl des Benutzer wird in einer Variable gespeichert.
		try {
			this.chosenKartei = ChooseKartei.chooseKartei(this);
		} catch (NullPointerException e) {
			this.dispose();
		}

		if (this.chosenKartei != null) {

			this.karteiNummer = this.chosenKartei.getNummer();

			// Importiert die Karten der Lernkartei und gibt sie in das richtige Fach, falls
			// keine angegeben wurde, werden die
			// Karten in eine neues Fach gelegt. Falls bis zum Abschluss jenes Prozesses,
			// weniger als 3 F??cher erstellt wurden,
			// erstellt das Programm automatisch alle fehlenden bis zu insgesamt 3 F??chern.
			importKarten();

			JButton btnAddTab = new JButton("+");
			btnAddTab.setBounds(178, 0, 20, 20);
			btnAddTab.setBorder(null);
			btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnAddTab.setToolTipText("Neues Fach hinzuf??gen");
			btnAddTab.setBackground(new Color(238, 238, 238));

			this.tabbedPane.setBounds(0, 0, 744, 346);

			// Erstell die anf??nglichen Tabs und weist ihnen die Zuh??rer zu, je nachdem wie
			// viele F??cher importiert wurden; mind. 3
			for (int i = 0; i < VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size(); i++) {
				Tab tab = new Tab(this.chosenKartei, this.tabbedPane);

				addTabListener(tab);

				this.tabs.add(tab);
				this.tabbedPane.addTab("Fach " + (i + 1), this.tabs.get(i));
			}

			// Der TabbedPane und dem '+'-Knopf neben den Tabs (graphisch) werden Maush??rer
			// zugewiesen
			this.tabbedPane.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					// Falls Mausrad-Knopf, soll Tab gel??scht werden
					if (e.getButton() == 2) {
						// Damit diese L??schen-Funktion nicht Tab wechselt sobald das Mausrad gedr??ckt
						// wird, wird auf den
						// letzten aufgerufenen Tab gewechselt
						GUI_Main.this.tabbedPane.setSelectedIndex(GUI_Main.this.selectedTab);

						// ??berpr??ft ob innerhalb der Fl??che des letzten Tabs gedr??ckt wurde
						int left = 58 * (GUI_Main.this.tabbedPane.getTabCount() - 1);
						int right = 58 * (GUI_Main.this.tabbedPane.getTabCount() - 1) + 58;
						if (e.getX() >= left && e.getX() <= right && e.getY() > 0 && e.getY() < 20) {

							// Entfernt das Fach aus der Datenbank und der TabbedPane und l??scht die
							// currentKarte dieses Faches
							// ("Dieses Faches" ist relativ, da immer nur das letzte Fach gel??scht werden
							// kann)
							int fachNummer = GUI_Main.this.tabbedPane.getTabCount();
							VokabeltrainerDB.loeschenFach(GUI_Main.this.karteiNummer, fachNummer);
							GUI_Main.this.tabbedPane.remove(GUI_Main.this.tabbedPane.getTabCount() - 1);
							GUI_Main.this.currentKarte.remove(GUI_Main.this.currentKarte.size() - 1);

							// Setzt den Knopf um Tabs hinzuzuf??gen immer hinter den letzten Tab
							btnAddTab.setLocation(btnAddTab.getX() - 58, 0);

							// Probiert aus ob der Index noch stimmt, falls nicht wird er um 1 verringert
							try {
								GUI_Main.this.tabbedPane.setSelectedIndex(GUI_Main.this.selectedTab);
							} catch (IndexOutOfBoundsException e1) {
								GUI_Main.this.selectedTab--;
							}
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == 1) {
						System.out.println(VokabeltrainerDB.getLernkarteien().size());
						System.out.println(VokabeltrainerDB.getFaecher(1).size());
						GUI_Main.this.selectedTab = GUI_Main.this.tabbedPane.getSelectedIndex();
						int left = 58 * GUI_Main.this.tabbedPane.getSelectedIndex();
						int right = (58 * GUI_Main.this.tabbedPane.getSelectedIndex()) + 58;
						if (e.getX() >= left && e.getX() <= right && e.getY() > 0 && e.getY() < 20) {
							if (GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).l_Vorgabe.getText() == "") {
								createRandomKarte("In diesem Fach sind noch keine W??rter");
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
						Tab tab = new Tab(GUI_Main.this.chosenKartei, GUI_Main.this.tabbedPane);
						addTabListener(tab);
						GUI_Main.this.tabs.add(tab);
						GUI_Main.this.tabbedPane.addTab("Fach " + fachNmber, GUI_Main.this.tabs.get(GUI_Main.this.tabs.size() - 1));
						GUI_Main.this.currentKarte.add(new Karte());
						btnAddTab.setLocation(btnAddTab.getX() + 59, 0);
					}
				}
			});

			getContentPane().add(btnAddTab);
			getContentPane().add(this.tabbedPane);

			updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
			createRandomKarte("In diesem Fach sind keine weiteren W??rter");
			this.tabs.get(this.tabbedPane.getSelectedIndex()).eingabe.setText("");
			
			
			setVisible(true);
		} else {
			this.dispose();
		}
	}

	public void createRandomKarte(String text) {
		System.out.println(chosenKartei.toString());
		Karte randomKarte = VokabeltrainerDB.getZufaelligeKarte(this.chosenKartei.getNummer(),
				this.tabbedPane.getSelectedIndex() + 1);
		if (randomKarte == null) {
			this.currentKarte.set(this.selectedTab, null);
			updateCenterDialog(Color.RED, text);
			this.tabs.get(this.tabbedPane.getSelectedIndex()).l_Vorgabe.setText("");
		} else {

			this.currentKarte.set(this.selectedTab, randomKarte);
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

				System.out.println(this.selectedTab);
				System.out.println(this.currentKarte.get(this.selectedTab));
				if (this.currentKarte.get(selectedTab)
						.getRichtig(this.tabs.get(this.tabbedPane.getSelectedIndex()).eingabe.getText())) {

					new Thread(new Runnable() {

						@Override
						public void run() {
							updateCenterDialog(Color.GREEN, "Richtig! Die Antwort lautet '"
									+ GUI_Main.this.currentKarte.get(GUI_Main.this.selectedTab).getWortZwei() + "'");
							synchronized (this) {
								try {
									this.wait(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							VokabeltrainerDB.setKarteRichtig(GUI_Main.this.currentKarte.get(GUI_Main.this.selectedTab));
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							createRandomKarte("In diesem Fach sind keine weiteren W??rter");
							GUI_Main.this.tabs.get(GUI_Main.this.tabbedPane.getSelectedIndex()).eingabe.setText("");
						}
					}).start();
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							updateCenterDialog(Color.RED, "Falsch! Die Antwort lautet '"
									+ GUI_Main.this.currentKarte.get(GUI_Main.this.selectedTab).getWortZwei() + "'");
							synchronized (this) {
								try {
									this.wait(2000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							System.out
									.println(VokabeltrainerDB.setKarteFalsch(GUI_Main.this.currentKarte.get(GUI_Main.this.selectedTab)));
							updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
							createRandomKarte("In diesem Fach sind keine weiteren W??rter");
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
		VokabeltrainerDB.importierenKarten(this.karteiNummer,
				"Lernkarteien/" + this.chosenKartei.getBeschreibung() + ".txt");
		for (int i = 1; i <= VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size(); i++) {
			VokabeltrainerDB.aendernFach(new Fach(i, "Fach " + i, 0, new Date()));
			this.currentKarte.add(new Karte());
		}

		if (VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size() < 3) {
			int ersteFachNummer = VokabeltrainerDB.getFaecher(this.chosenKartei.getNummer()).size() + 1;
			for (int i = ersteFachNummer; i <= 3; i++) {
				VokabeltrainerDB.hinzufuegenFach(this.chosenKartei.getNummer(), new Fach());
				VokabeltrainerDB.aendernFach(new Fach(i, "Fach " + i, 0, new Date()));
				this.currentKarte.add(new Karte());
			}
		}
	}

	public void addTabListener(Tab tab) {
		tab.btnMinus.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == 1) {
					int decision = new JOptionPane().showConfirmDialog(GUI_Main.this,
							"M??chten sie dieses Wort aus der Liste entfernen?", "Wort L??schen", JOptionPane.YES_NO_OPTION);
					if (decision == JOptionPane.YES_OPTION) {

						VokabeltrainerDB.loeschenKarte(GUI_Main.this.currentKarte.get(GUI_Main.this.selectedTab).getNummer());
						updateCenterDialog(Color.BLACK, "Geben Sie Ihre Antwort ein: ");
						createRandomKarte("In diesem Fach sind keine weiteren W??rter");
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
										JOptionPane.showMessageDialog(GUI_Main.this, "Wort erfolgreich hinzugef??gt", "Neues Wort",
												JOptionPane.INFORMATION_MESSAGE);
										newWord.dispose();
									} else {
										JOptionPane.showMessageDialog(newWord, "Ein Fehler ist aufgetreten!", "Fehler bei hinzuf??gen",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(newWord, "Keine W??rter eingegeben!", "Fehler bei Eingabe",
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

		tab.btnEinstellungen.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				GUI_Main.this.sd = new SettingsDialog();
				
				GUI_Main.this.sd.chckbxGrosskleinschreibungBeachten.addActionListener(f -> {

					VokabeltrainerDB.exportierenKarten(GUI_Main.this.chosenKartei.getNummer(), "Lernkarteien/" + 
							GUI_Main.this.chosenKartei.getBeschreibung() + ".txt", true);
					VokabeltrainerDB.aendernLernkartei(new Lernkartei(GUI_Main.this.chosenKartei.getNummer(),
							GUI_Main.this.chosenKartei.getBeschreibung(), GUI_Main.this.chosenKartei.getWortEinsBeschreibung(),
							GUI_Main.this.chosenKartei.getWortZweiBeschreibung(), GUI_Main.this.chosenKartei.getRichtung(),
							!GUI_Main.this.chosenKartei.getGrossKleinschreibung()));
					importKarten();
				});
				
				GUI_Main.this.sd.btnErrinerung.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						GUI_Main.this.sd.errinerungsDialog = new ErrinerungDialog();
						GUI_Main.this.sd.errinerungsDialog.btn_ok.addMouseListener(new MouseAdapter() {

							@Override
							public void mouseClicked(MouseEvent e) {
								JTextField tf_value = GUI_Main.this.sd.errinerungsDialog.tf_value;
								ChooseDate cal = GUI_Main.this.sd.errinerungsDialog.cal;
								JLabel l_mitDatum = GUI_Main.this.sd.errinerungsDialog.l_mitDatum;

								if (tf_value.getText().isEmpty()) {

									try {
										final String calDatum = cal.getDateAsString();
										String date[] = calDatum.split("-");
										Calendar calendar = Calendar.getInstance();
										calendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
										Calendar c = Calendar.getInstance();

										int errinernIn = (int) TimeUnit.MILLISECONDS
												.toDays(calendar.getTimeInMillis() - c.getTimeInMillis());

										Fach fach = VokabeltrainerDB.getFach(GUI_Main.this.chosenKartei.getNummer(),
												GUI_Main.this.selectedTab + 1);

										VokabeltrainerDB.aendernFach(
												new Fach(fach.getNummer(), fach.getBeschreibung(), errinernIn, fach.getGelerntAm()));

										l_mitDatum
												.setText("Am " + date[0] + "-" + (Integer.valueOf(date[1]) + 1) + "-" + date[2] + " errinern:");

									} catch (NullPointerException f) {
										JOptionPane.showMessageDialog(e.getComponent().getParent(),
												"Es wurde keine Zeit oder Datum festgelegt!", "Errinerung setzten", JOptionPane.ERROR_MESSAGE);
									}
								} else {

									try {
										cal.getDateAsString();
									} catch (NullPointerException f) {

										Fach fach = VokabeltrainerDB.getFach(GUI_Main.this.chosenKartei.getNummer(),
												GUI_Main.this.selectedTab + 1);

										int check = VokabeltrainerDB.aendernFach(new Fach(fach.getNummer(), fach.getBeschreibung(),
												Integer.parseInt(tf_value.getText()), fach.getGelerntAm()));
										if (check == 0) {
											GUI_Main.this.sd.errinerungsDialog.frame.dispose();
											JOptionPane.showMessageDialog(GUI_Main.this.sd, "Errinerung Erfolgreich gesetzt! Sie werden in "
													+ tf_value.getText() + " Tagen an dieses Fach errinert", "Errinerung",
													JOptionPane.INFORMATION_MESSAGE);
										} else {
											JOptionPane.showMessageDialog(GUI_Main.this.sd, "Ein Fehler ist aufgetreten!", "Fehler",
													JOptionPane.ERROR_MESSAGE);
										}

										return;
									}
									JOptionPane.showMessageDialog(e.getComponent().getParent(),
											"Es kann nicht eine Zeit und ein Datum gleichzeitig festgelegt werden", "Errinerung setzten",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						});

					}
				});

			}
		});

	}
}
