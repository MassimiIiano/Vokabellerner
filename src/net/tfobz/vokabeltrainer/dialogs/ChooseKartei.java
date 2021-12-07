package net.tfobz.vokabeltrainer.dialogs;

import java.util.*;
import javax.swing.*;
import net.tfobz.vokabeltrainer.model.*;

public class ChooseKartei {
    public static Lernkartei chooseKartei(JFrame parent) {
        List<Lernkartei> lernkarteien = VokabeltrainerDB.getLernkarteien(); 
        if(lernkarteien.isEmpty())
        {
        	VokabeltrainerDB.hinzufuegenLernkartei(new Lernkartei("Standard Lernkartei", "Deusch", "Englisch", true, false));
        	lernkarteien = VokabeltrainerDB.getLernkarteien();
        }
        Object[] list = lernkarteien.toArray();
        Object ret = JOptionPane.showInputDialog(
            parent, 
            "Karteien:", 
            "Vokabeltrainer - Lernkartei Auswahl", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            list, 
            "Waehle deine Kartei"
        );
        return (Lernkartei) ret;
    }

}
