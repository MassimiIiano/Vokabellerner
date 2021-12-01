package net.tfobz.vokabeltrainer.dialogs;

import javax.swing.*;
import java.awt.EventQueue;

public class KalenderDialog {
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KalenderDialog window = new KalenderDialog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KalenderDialog() {
		initialize();
//		Until
//		UtilDateModel model = new UtilDateModel();
//		JDatePanelImpl datePanel = new JDatePanelImpl(model);
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		
//		frame.add(datePicker);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



}
