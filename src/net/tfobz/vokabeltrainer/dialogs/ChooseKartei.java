package net.tfobz.vokabeltrainer.dialogs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import net.tfobz.vokabeltrainer.model.*;

public class ChooseKartei
{
	public static Lernkartei chooseKartei(JFrame parent) {
		List<Lernkartei> lernkarteien = VokabeltrainerDB.getLernkarteien();
		if (lernkarteien.isEmpty()) {
			VokabeltrainerDB
					.hinzufuegenLernkartei(new Lernkartei("Vokabeltrainer Deutsch Englisch", "Deusch", "Englisch", false, false));
			lernkarteien = VokabeltrainerDB.getLernkarteien();
			if (!createDefaultFile()) {
				JOptionPane.showMessageDialog(parent, "Fehler beim erstellen der Standartdatei", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				parent.dispose();
				return null;
			}
		}
		Object[] list = lernkarteien.toArray();
		Object ret = JOptionPane.showInputDialog(parent, "Karteien:", "Vokabeltrainer - Lernkartei Auswahl",
				JOptionPane.QUESTION_MESSAGE, null, list, "Waehle deine Kartei");
		Lernkartei test = (Lernkartei) ret;
		return (Lernkartei) ret;
	}

	public static boolean createDefaultFile() {

		boolean ret = false;

		File f = new File("Lernkarteien/Vokabeltrainer Deutsch Englisch.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			return false;
		}

		try {

			FileWriter writer = new FileWriter("Lernkarteien/Vokabeltrainer Deutsch Englisch.txt");
			writer.write("Haus; house\nFuchs; fox\nGeschirr; Dishes\nLösung; solution\nerkennen; recognize\n"
					+ "Architekt; architect\nMedizin; medicin\nverteilen; distribute\nVulkan; volcano\n"
					+ "rutschig; slippery\nSkelett; skeleton\nArena; arena\nHorizont; horizon\nTemperatur; temperature\n"
					+ "Hauptstadt; capital\nKlavier; piano\nBatterie; battery\nErlaubnis; permission\n"
					+ "Privatsphäre; privacy\ngenerieren; generate\n");
			writer.close();

		} catch (IOException e1) {
			return false;
		}

		ret = true;

		return ret;
	}

}
