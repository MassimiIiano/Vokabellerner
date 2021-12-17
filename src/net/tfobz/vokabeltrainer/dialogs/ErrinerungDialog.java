package net.tfobz.vokabeltrainer.dialogs;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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

public class ErrinerungDialog extends JFrame
{

	JFrame frame = new JFrame();
	JLabel l_mitDatum;

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
		frame.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 275,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 91, 550, 182);
		frame.setResizable(false);
		frame.setTitle("Errinerung einstellen");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		FlowLayout layout = new FlowLayout();
		frame.getContentPane().setLayout(layout);
		layout.setAlignment(FlowLayout.TRAILING);
		layout.setVgap(15);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		Calendar calendar = Calendar.getInstance();
		p.put("date.day", String.valueOf(calendar.get(Calendar.DATE)));
		p.put("date.month", String.valueOf(calendar.get(Calendar.MONTH)));
		p.put("date.year", String.valueOf(calendar.get(Calendar.YEAR)));
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormat());

		ChooseDate cal = new ChooseDate(l_mitDatum, datePicker);

		JPanel panelPeriodisch = new JPanel();
		panelPeriodisch.setLayout(new FlowLayout());
		Font basic = new Font("Tahoma", Font.PLAIN, 23);
		JLabel l_title1 = new JLabel("Errinern alle:");
		l_title1.setFont(basic);
		JTextField tf_values[] = new JTextField[3];

		JLabel l_labels[] = new JLabel[3];
		l_labels[0] = new JLabel("Wochen");
		l_labels[1] = new JLabel("Tage");
		l_labels[2] = new JLabel("Stunden");

		for (int i = 0; i < 3; i++) {
			tf_values[i] = new JTextField("");
			tf_values[i].setPreferredSize(new Dimension(50, 25));
			tf_values[i].addKeyListener(new TextFieldKeyHoerer());
			l_labels[i].setFont(basic);
			l_labels[i].setSize(20, 25);
		}

		panelPeriodisch.add(l_title1);
		System.out.println(tf_values[0].getText());
		for (int i = 0; i < 3; i++) {
			panelPeriodisch.add(tf_values[i]);
			panelPeriodisch.add(l_labels[i]);
		}

		l_mitDatum = new JLabel("Oder am dd-MM-yyyy errinern:");
		l_mitDatum.setFont(basic);

		JButton btn_ok = new JButton("Errinerung setzten");
		btn_ok.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (tf_values[0].getText().isEmpty() && tf_values[1].getText().isEmpty() && tf_values[2].getText().isEmpty()) {

					try {
						final String calDatum = cal.getDateAsString();
						l_mitDatum.setText("Oder am " + calDatum + " errinern:");
					} catch (NullPointerException f) {
						JOptionPane.showMessageDialog(ErrinerungDialog.this, "Es wurde keine Zeit oder Datum festgelegt!",
								"Errinerung setzten", JOptionPane.ERROR_MESSAGE);
					}
				} else {

					try {
						cal.getDateAsString();
					} catch (NullPointerException f) {
						System.out.println("in Catch");

						// Setzte Errinerung mit Werten aus TextFeldern

						return;
					}
					JOptionPane.showMessageDialog(ErrinerungDialog.this,
							"Es kann nicht eine Zeit und ein Datum gleichzeitig festgelegt werden", "Errinerung setzten",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btn_cancel = new JButton("Abbrechen");
		btn_cancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});

		frame.getContentPane().add(panelPeriodisch);
		frame.getContentPane().add(l_mitDatum);
		frame.getContentPane().add(cal);
		frame.getContentPane().add(btn_ok);
		frame.getContentPane().add(btn_cancel);
		frame.setVisible(true);
	}

	private class TextFieldKeyHoerer extends KeyAdapter
	{

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {

				JTextField tf = (JTextField) e.getSource();

				String charEntered = String.valueOf(e.getKeyChar());

				if (tf.getText().length() == 1) {

					tf.setText("");
					if (charEntered.matches("\\d")) {
						tf.setText(String.valueOf(e.getKeyChar()));
					}

				} else if (tf.getText().length() == 2) {

					String before = tf.getText();
					
					if (before.matches("\\d{2}")) {
						tf.setText(before);
					} else if (before.matches("\\d{1}[a-zA-Z]{1}")) {
						tf.setText(String.valueOf(before.charAt(0)));
					}

				} else {

					String before = tf.getText();
					if(before.matches("\\d{2}\\w*")) {
						tf.setText(before.substring(0, 2));
					} else if(before.matches("\\d{1}[a-zA-Z]*")) {
						tf.setText(String.valueOf(before.charAt(0)));
					}
				}
			}
		}
	}

	public void waitBeforeChange(long timeout) {
		synchronized (this) {
			try {
				wait(timeout);
			} catch (InterruptedException e1) {
			}
		}
	}
}
