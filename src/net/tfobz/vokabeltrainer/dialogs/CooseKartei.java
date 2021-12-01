package net.tfobz.vokabeltrainer.dialogs;

import java.util.*;
import javax.swing.*;
import net.tfobz.vokabeltrainer.model.*;

public class CooseKartei {
    public static Lernkartei cooseKartei(JFrame parent) {
        List<Lernkartei> lernkarteien = VokabeltrainerDB.getLernkarteien(); 
        Object[] list = lernkarteien.toArray();
        Object ret = JOptionPane.showInputDialog(
            parent, 
            "Karteien:", 
            "Start", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            list, 
            "Waele deine Kartei"
        );
        return (Lernkartei) ret;
    }

}
