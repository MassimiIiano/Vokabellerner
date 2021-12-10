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

public class ErrinerungDialog
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

		l_mitDatum = new JLabel("");
		
		l_mitDatum.setText("Oder am dd-MM-yyyy errinern:");
		l_mitDatum.setFont(basic);

		JButton btn_ok = new JButton("Errinerung setzten");
		btn_ok.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				final String calDatum = cal.getDateAsString();
				l_mitDatum.setText("Oder am " + calDatum + " errinern:");
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

			JTextField tf = (JTextField) e.getSource();
			char charBefore;
			if (tf.getText().length() == 0) {

				if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {

					synchronized (this) {
						try {
							wait(1);
						} catch (InterruptedException e1) {
						}
					}
					tf.setText(Character.toString(e.getKeyChar()));
				} else

					synchronized (this) {
						try {
							wait(1);
						} catch (InterruptedException e1) {
						}
					}
				tf.setText("");
			} else if (tf.getText().length() == 1) {

				charBefore = tf.getText().charAt(0);
				if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {

					synchronized (this) {
						try {
							wait(1);
						} catch (InterruptedException e1) {
						}
					}
					tf.setText(charBefore + Character.toString(e.getKeyChar()));
				} else
					synchronized (this) {
						try {
							wait(1);
						} catch (InterruptedException e1) {
						}
					}
				tf.setText(charBefore + "");
			} else if (tf.getText().length() >= 2) {

				if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {

					String textBefore = tf.getText().substring(0, 2);
					synchronized (this) {
						try {
							wait(100);
						} catch (InterruptedException e1) {
						}
						tf.setText(textBefore);
						System.out.println("");
					}
				}
			}
		}
	}
}
