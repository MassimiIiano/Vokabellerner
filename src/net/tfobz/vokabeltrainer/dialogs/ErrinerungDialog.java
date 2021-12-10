package net.tfobz.vokabeltrainer.dialogs;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
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
		for(JTextField value : tf_values) {
			value = new JTextField("0");
			value.setSize(10, 25);
		}
		JLabel l_labels[] = new JLabel[3];
		l_labels[0] = new JLabel("Wochen");
		l_labels[1] = new JLabel("Tage");
		l_labels[2] = new JLabel("Stunden");
		for(JLabel label : l_labels) {
			label.setFont(basic);
			label.setSize(20, 25);
		}
		for(int i = 0; i < 3; i++) {
			
		}
		
		String datum = "dd-MM-yyyy";
		JLabel l_mitDatum = new JLabel();
		
		
		getContentPane().add(new ChooseDate());
		setVisible(true);
	}




}
