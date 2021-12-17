package net.tfobz.vokabeltrainer.dialogs;


import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		this.getContentPane().add(btnImport);

		JButton btnExport = new JButton("Export");
		btnExport.setBounds(276, 117, 97, 25);
		this.getContentPane().add(btnExport);
		
		this.setVisible(true);
	}
}
