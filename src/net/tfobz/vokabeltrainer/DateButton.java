package net.tfobz.vokabeltrainer;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DateButton extends JButton {
    JTextField dt;
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
        dt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this;                
            }
        });
        return dt;
    }


}
