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

public class ErrinerungDialog extends JFrame {

	public JFrame frame = new JFrame();
	public JLabel l_mitDatum;
	public JButton btn_ok = new JButton("Errinerung setzten");
	public JTextField tf_value = new JTextField();
	public ChooseDate cal;

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
		frame.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 280,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 62, 560, 124);
		frame.setResizable(false);
		frame.setTitle("Errinerung einstellen");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		FlowLayout layout = new FlowLayout();
		frame.getContentPane().setLayout(layout);
		layout.setAlignment(FlowLayout.CENTER);
		layout.setVgap(10);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		Calendar calendar = Calendar.getInstance();
		p.put("date.day", String.valueOf(calendar.get(Calendar.DATE)));
		p.put("date.month", String.valueOf(calendar.get(Calendar.MONTH)));
		p.put("date.year", String.valueOf(calendar.get(Calendar.YEAR)));
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormat());

		cal = new ChooseDate(datePicker);

		JPanel panelPeriodisch = new JPanel();
		panelPeriodisch.setLayout(new FlowLayout());
		Font basic = new Font("Tahoma", Font.PLAIN, 23);
		JLabel l_title1 = new JLabel("oder errinern in:");
		l_title1.setFont(basic);

		JLabel l_days = new JLabel();
		l_days = new JLabel("Tagen");

		tf_value = new JTextField("");
		tf_value.setPreferredSize(new Dimension(50, 25));
		tf_value.addKeyListener(new TextFieldKeyHoerer());
		
		l_days.setFont(basic);
		l_days.setSize(20, 25);

		panelPeriodisch.add(l_title1);
		panelPeriodisch.add(tf_value);
		panelPeriodisch.add(l_days);

		l_mitDatum = new JLabel("Am dd-MM-yyyy errinern:");
		l_mitDatum.setFont(basic);


		JButton btn_cancel = new JButton("Abbrechen");
		btn_cancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});

		frame.getContentPane().add(l_mitDatum);
		frame.getContentPane().add(cal);
		frame.getContentPane().add(panelPeriodisch);
		frame.getContentPane().add(btn_ok);
		frame.getContentPane().add(btn_cancel);
		frame.setVisible(true);
	}

	private class TextFieldKeyHoerer extends KeyAdapter {

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
					if (before.matches("\\d{2}\\w*")) {
						tf.setText(before.substring(0, 2));
					} else if (before.matches("\\d{1}[a-zA-Z]*")) {
						tf.setText(String.valueOf(before.charAt(0)));
					}
				}
			}
		}
	}

}
