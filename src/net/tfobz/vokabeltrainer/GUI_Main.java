package net.tfobz.vokabeltrainer;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class GUI_Main extends JFrame
{

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUI_Main window = new GUI_Main();
	}

	public GUI_Main() {
		this.setTitle("Vokabeltrainer");
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 450,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 350, 750, 455);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);	
		
		JButton btnAddTab = new JButton("+");
		btnAddTab.setBounds(178, 0, 20, 20);
		btnAddTab.setBorder(null);
		btnAddTab.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddTab.setToolTipText("Neuen Tab hinzufügen");
		btnAddTab.setBackground(new Color(238,238,238));

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 744, 426);
		tabbedPane.addTab("Fach 1", new Tab());
		tabbedPane.addTab("Fach 2", new Tab());
		tabbedPane.addTab("Fach 3", new Tab());
		
		tabbedPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 2) {
					tabbedPane.remove(tabbedPane.getTabCount()-1);
					btnAddTab.setLocation(tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).getX()+20, 0);
				}
			}
		});
		getContentPane().add(btnAddTab);
		getContentPane().add(tabbedPane);
		//tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).getX()
		
		btnAddTab.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(tabbedPane.getTabCount());
				tabbedPane.addTab("Fach " + (tabbedPane.getTabCount()+1), new Tab());
				int x = tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).getX();
				//btnAddTab.setLocation(tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).getX() + 20, 0);
			}
		});
		

		
		this.setVisible(true);
	}
}
