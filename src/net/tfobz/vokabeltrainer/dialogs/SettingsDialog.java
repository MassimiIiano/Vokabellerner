package net.tfobz.vokabeltrainer.dialogs;

import net.tfobz.vokabeltrainer.model.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.io.File;
import java.util.InputMismatchException;

public class SettingsDialog extends JDialog
{

	public ErrinerungDialog errinerungsDialog;
	public JButton btnErrinerung = new JButton("Errinerung");

	/**
	 * Initialize the contents of the frame.
	 */
	public SettingsDialog() {
		this.setTitle("Einstellungen");
		this.setResizable(false);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width/2)-250,
				(Toolkit.getDefaultToolkit().getScreenSize().height/2)-90, 500, 180);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JCheckBox chckbxGrosskleinschreibungBeachten = new JCheckBox("Gross-/Kleinschreibung beachten");
		chckbxGrosskleinschreibungBeachten.setBounds(8, 9, 466, 25);
		this.getContentPane().add(chckbxGrosskleinschreibungBeachten);

		JCheckBox chckbxNurLektionenMit = new JCheckBox("Nur Lektionen mit abgelaufener Errinerung");
		chckbxNurLektionenMit.setBounds(8, 39, 466, 25);
		this.getContentPane().add(chckbxNurLektionenMit);

		JLabel lblTheme = new JLabel("Theme:");
		lblTheme.setBounds(12, 83, 56, 16);
		this.getContentPane().add(lblTheme);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Dark", "White" }));
		comboBox.setBounds(80, 80, 108, 22);
		comboBox.setSelectedItem("White");
		this.getContentPane().add(comboBox);

		
		btnErrinerung.setBounds(10, 117, 97, 25);

		this.getContentPane().add(btnErrinerung);
		
		JButton btnImport = new JButton("Import");
		btnImport.setBounds(385, 117, 97, 25);
		btnImport.addActionListener(e-> {
                try {
                    int nummer = VokabeltrainerDB.getLernkarteien().indexOf(ChooseKartei.chooseKartei())+1;
                    if(nummer != 0) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.showOpenDialog(SettingsDialog.this);
                        File file = chooser.getSelectedFile();
                        VokabeltrainerDB.importierenKarten(nummer, file.getAbsolutePath());
                    } else 
                        throw new InputMismatchException("Sie sollen eine Sprache aussuchen");
                    
                }catch(InputMismatchException e1) {
                    JOptionPane.showMessageDialog(SettingsDialog.this, e1.getMessage(), "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }
            
        });
		getContentPane().add(btnImport);

		JButton btnExport = new JButton("Export");
		btnExport.setBounds(276, 117, 97, 25);
		btnExport.addActionListener(e -> {
        	try {
        		int nummer = VokabeltrainerDB.getLernkarteien().indexOf(ChooseKartei.chooseKartei())+1;
        		if (nummer != 0) {
        			JFileChooser chooser = new JFileChooser();
        			chooser.showSaveDialog(SettingsDialog.this);
        			boolean mitFaecher = false;
        			if (JOptionPane.showConfirmDialog(SettingsDialog.this, "Auch die Faecher exportieren?", "FAECHER EXPORTIEREN", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION)
        				mitFaecher = true;
        			System.out.println(nummer);
        			System.out.println(VokabeltrainerDB.exportierenKarten(nummer, chooser.getSelectedFile().getAbsolutePath(), mitFaecher));
        		} else {
        			throw new InputMismatchException("Sie sollen eine Sprache aussuchen");
        		}
        	} catch (InputMismatchException e1) {
        		JOptionPane.showMessageDialog(SettingsDialog.this, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        	}
        });
		getContentPane().add(btnExport);

		setVisible(true);
	}
}
