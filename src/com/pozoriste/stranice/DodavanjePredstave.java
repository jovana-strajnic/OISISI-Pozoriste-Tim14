package com.pozoriste.stranice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DodavanjePredstave extends JDialog {

    public DodavanjePredstave() {

        setModal(true);
        setSize(new Dimension(300,400));
        setLayout(new GridLayout(5,2));

        add(new JLabel("Naziv"));
        add(new JTextField());

        add(new JLabel("Opis"));
        add(new JTextField());

        add(new JLabel("Datum"));
        add(new JTextField());

        add(new JLabel("Cena"));
        add(new JTextField());

        JButton nazad=new JButton("Nazad");
        nazad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(nazad);
        add(new JButton("Dodaj"));

    }
}
