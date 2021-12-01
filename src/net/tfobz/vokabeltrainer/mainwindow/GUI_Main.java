package net.tfobz.vokabeltrainer.mainwindow;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class GUI_Main extends JFrame
{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUI_Main window = new GUI_Main();
	}

	public GUI_Main() {
		this.setTitle("Vokabeltrainer");
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 375,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 187, 750, 375);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		JButton btnAddTab = new JButton("+");
		btnAddTab.setBounds(178, 0, 20, 20);
		btnAddTab.setBorder(null);
		btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddTab.setToolTipText("Neuen Tab hinzufügen");
		btnAddTab.setBackground(new Color(238, 238, 238));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 744, 346);
		tabbedPane.addTab("Fach 1", new Tab());
		tabbedPane.addTab("Fach 2", new Tab());
		tabbedPane.addTab("Fach 3", new Tab());

		tabbedPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 2) {
					tabbedPane.remove(tabbedPane.getTabCount() - 1);
					btnAddTab.setLocation(btnAddTab.getX() - 59, 0);
				}
			}
		});
		getContentPane().add(btnAddTab);
		getContentPane().add(tabbedPane);
		// tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).getX()

		btnAddTab.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabbedPane.getTabCount() < 12) {
					tabbedPane.addTab("Fach " + (tabbedPane.getTabCount() + 1), new Tab());
					btnAddTab.setLocation(btnAddTab.getX() + 59, 0);
				}
			}
		});

		this.setVisible(true);
	}
}
