package com.pozoriste.stranice;

import com.pozoriste.model.Predstava;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DetaljiPredstave extends JDialog {
    public DetaljiPredstave(Predstava p) {
        setModal(true);
        setSize(new Dimension(700, 500));

        JPanel informacije = new JPanel();
        informacije.setLayout(new GridLayout(4, 2));

        informacije.add(new JLabel("Naziv"));
        informacije.add(new JLabel(p.getNaziv()));

        informacije.add(new JLabel("Opis"));
        informacije.add(new JLabel(p.getOpis()));

        informacije.add(new JLabel("Cena"));
        informacije.add(new JLabel("" + p.getCena()));

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        informacije.add(new JLabel("Datum"));
        informacije.add(new JLabel(p.getDatum().format(formater)));

        JPanel sedista = new JPanel();
        sedista.setLayout(new GridLayout(6, 5));
        for (int i = 0; i < 30; i++) {
            String red = "" + (1 + (int) i / 5);
            String kolona = "" + (1 + i % 6);
            JCheckBox sediste = new JCheckBox("R: " + red + "\nK: " + kolona);
            if (p.getSedista().get(i)) {
                sediste.setSelected(true);
                sediste.setEnabled(false);
            }
            sedista.add(sediste);
        }
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(informacije);
        split.setRightComponent(sedista);
        add(split);

        informacije.setBackground(new Color(130, 72, 57));
        sedista.setBackground(new Color(175, 122, 110));

    }
}
