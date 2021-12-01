package net.tfobz.vokabeltrainer;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.*;

public class DateButton extends JButton {
    JTextField dt;
    Date date;
    String dateFormat = "dd.MM.yyyy";

    public DateButton() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateText();
            }
        });
    }

    public JTextField getDateText() {
        setVisible(false);
        dt = new JTextField();
        dt.setBounds(new Rectangle(new Point(getX(), getY()), getSize()));
        dt.setVisible(true);
        dt.setText("dd.mm.yyyy");
        dt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(dt.getText().trim());
                    dt.setVisible(false);
                    DateButton.this.setVisible(true);
                } catch (ParseException e1) {
                    dt.setText("Invalid date");
                    return;
                }
            }
        });
        return dt;
    }

    public Date getDate() {
        return date;
    }
}
