package net.tfobz.vokabeltrainer.dialogs;

import java.awt.FlowLayout;
import java.util.Calendar;
import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ChooseDate extends JPanel {

	JDatePickerImpl datePicker;

	public ChooseDate(JLabel l_date, JDatePickerImpl datePicker) {
		this.datePicker = datePicker;
		setLayout(new FlowLayout());

		add(datePicker);
		setVisible(true);
	}

	public String getDateAsString() {

		UtilDateModel test = (UtilDateModel) datePicker.getModel();
		Calendar cal = Calendar.getInstance();
		cal.setTime(test.getValue());
		return String.valueOf(cal.get(Calendar.DATE)) + "-" + String.valueOf(cal.get(Calendar.MONTH)) + "-" +
				String.valueOf(cal.get(Calendar.YEAR));
	}
}
