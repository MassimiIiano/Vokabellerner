package net.tfobz.vokabeltrainer.dialogs;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class SettingsDialog {

    private JDialog frmEinstellungen;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SettingsDialog window = new SettingsDialog();
                    window.frmEinstellungen.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SettingsDialog() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmEinstellungen = new JDialog();

        frmEinstellungen.setTitle("Einstellungen");
        frmEinstellungen.setResizable(false);
        frmEinstellungen.setBounds(100, 100, 500, 250);
        frmEinstellungen.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frmEinstellungen.getContentPane().setLayout(null);

        JCheckBox chckbxGrosskleinschreibungBeachten = new JCheckBox("Gross-/Kleinschreibung beachten");
        chckbxGrosskleinschreibungBeachten.setBounds(8, 9, 466, 25);
        frmEinstellungen.getContentPane().add(chckbxGrosskleinschreibungBeachten);

        JCheckBox chckbxNurLektionenMit = new JCheckBox("Nur Lektionen mit abgelaufener Errinerung");
        chckbxNurLektionenMit.setBounds(8, 39, 466, 25);
        frmEinstellungen.getContentPane().add(chckbxNurLektionenMit);

        JLabel lblTheme = new JLabel("Theme:");
        lblTheme.setBounds(12, 83, 56, 16);
        frmEinstellungen.getContentPane().add(lblTheme);

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Dark", "White" }));
        comboBox.setBounds(80, 80, 108, 22);
        frmEinstellungen.getContentPane().add(comboBox);

        JButton btnErrinerung = new JButton("Errinern");
        
        JButton btnImport = new JButton("Import");
        btnImport.setBounds(385, 177, 97, 25);
        frmEinstellungen.getContentPane().add(btnImport);

        JButton btnExport = new JButton("Export");
        btnExport.setBounds(276, 177, 97, 25);
        frmEinstellungen.getContentPane().add(btnExport);

        JButton btnAddLanguage = new JButton("Add Language");
        btnAddLanguage.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAddLanguage.setBounds(8, 177, 133, 25);
        frmEinstellungen.getContentPane().add(btnAddLanguage);
        JRootPane rootPane = SwingUtilities.getRootPane(btnAddLanguage);
        rootPane.setDefaultButton(btnAddLanguage);
    }
}
