package net.tfobz.vokabeltrainer.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ChooseDate extends JPanel
{
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ChooseDate();
			}
		});
	}
	
	public ChooseDate() {
		//super(parent);
		setBounds(500, 500, 500, 50);
		setLayout(new BorderLayout());
			
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		Calendar calendar = Calendar.getInstance();
		p.put("date.day", String.valueOf(calendar.get(Calendar.DATE)));
		p.put("date.month", String.valueOf(calendar.get(Calendar.MONTH)));
		p.put("date.year", String.valueOf(calendar.get(Calendar.YEAR)));
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormat());
		datePicker.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(1,2));
		JButton btn_ok = new JButton("Ok");
		btn_ok.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				UtilDateModel test = (UtilDateModel) datePicker.getModel();
			}
			
		});
		/*JButton btn_cancel = new JButton("Abbrechen");
		btn_cancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		*/
		panelButtons.add(btn_ok);
		//panelButtons.add(btn_cancel);
		

		
		add(panelButtons, BorderLayout.NORTH);
		add(datePicker, BorderLayout.SOUTH);
		setVisible(true);
	}
}
