package net.tfobz.vokabeltrainer.dialogs;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Properties;

public class ErrinerungDialog extends JFrame{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ErrinerungDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ErrinerungDialog() {
		setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 350,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250, 700, 500);
		setResizable(false);
		setTitle("Errinerung einstellen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new CardLayout());
		
		JPanel panelPeriodisch = new JPanel();
		panelPeriodisch.setLayout(new FlowLayout());
		Font basic = new Font("Tahoma", Font.PLAIN, 25);
		JLabel l_title1 = new JLabel("Errinern alle:");
		l_title1.setFont(basic);
		JTextField tf_values[] = new JTextField[3];
		
		
		JLabel l_labels[] = new JLabel[3];
		l_labels[0] = new JLabel("Wochen");
		l_labels[1] = new JLabel("Tage");
		l_labels[2] = new JLabel("Stunden");
		
		
		for(int i = 0; i < 3; i++) {
			tf_values[i] = new JTextField("0");
			tf_values[i].setPreferredSize(new Dimension(50,25));
			l_labels[i].setFont(basic);
			l_labels[i].setSize(20, 25);
		}
		
		
		panelPeriodisch.add(l_title1);
		System.out.println(tf_values[0].getText());
		for(int i = 0; i < 3; i++) {
			panelPeriodisch.add(tf_values[i]);
			panelPeriodisch.add(l_labels[i]);
		}
		
		String datum = "dd-MM-yyyy";
		JLabel l_mitDatum = new JLabel();
		
		getContentPane().add(panelPeriodisch);
		getContentPane().add(new ChooseDate());
		setVisible(true);
	}

	private class TextFieldHoerer extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
	}

}
