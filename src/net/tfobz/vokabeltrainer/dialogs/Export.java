package net.tfobz.vokabeltrainer.dialogs;

import java.util.ArrayList;

import javax.swing.*;

public class Export extends JDialog {

    public static void main(String[] args) {
        // Export ex = new Export();
    }

    public Export(ArrayList<String> options) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setTitle("Export");
        setSize(400, 200);
        setResizable(false);
        setLayout(null);

        JTextField txtPaht = new JTextField();
        txtPaht.setText("Enter file path");
        txtPaht.setBounds(50, 30, 300, 25);
        this.getContentPane().add(txtPaht);

        // JList list = new JList(options.toArray());
        // list.setSelectionMode(
        //         ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // int[] select = { 19, 20, 22 };
        // list.setSelectedIndices(select);
        // JOptionPane.showMessageDialog(null, new JScrollPane(list));

        // setVisible(true);
    }
}
