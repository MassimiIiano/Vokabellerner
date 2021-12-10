package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JFrame;
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

		chosenKartei = ChooseKartei.chooseKartei(this);
		
		JButton btnAddTab = new JButton("+");
		btnAddTab.setBounds(178, 0, 20, 20);
		btnAddTab.setBorder(null);
		btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddTab.setToolTipText("Neuen Tab hinzufügen");
		btnAddTab.setBackground(new Color(238, 238, 238));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 744, 346);
		tabbedPane.addTab("Fach 1", new Tab());
		VokabeltrainerDB.hinzufuegenFach(chosenKartei.getNummer(), new Fach(0, "Fach 1", 0, new Date()));
		tabbedPane.addTab("Fach 2", new Tab());
		VokabeltrainerDB.hinzufuegenFach(chosenKartei.getNummer(), new Fach(1, "Fach 2", 0, new Date()));
		tabbedPane.addTab("Fach 3", new Tab());
		VokabeltrainerDB.hinzufuegenFach(chosenKartei.getNummer(), new Fach(2, "Fach 3", 0, new Date()));
		tabbedPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 2) {
					tabbedPane.remove(tabbedPane.getTabCount() - 1);
					btnAddTab.setLocation(btnAddTab.getX() - 59, 0);
					int a = tabbedPane.getTabCount()-1;
					VokabeltrainerDB.loeschenFach(chosenKartei.getNummer(), a);
				}
			}
		});
		getContentPane().add(btnAddTab);
		getContentPane().add(tabbedPane);

		btnAddTab.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabbedPane.getTabCount() < 12) {
					int fachNmber = VokabeltrainerDB.getFaecher(chosenKartei.getNummer()).size()+1;
					VokabeltrainerDB.hinzufuegenFach(chosenKartei.getNummer(), new Fach(fachNmber, "Fach " + fachNmber, 0, new Date()));
					tabbedPane.addTab("Fach " + (tabbedPane.getTabCount() + 1), new Tab());
					btnAddTab.setLocation(btnAddTab.getX() + 59, 0);
				}
			}
		});
		setVisible(true);
	}
}
