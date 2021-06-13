package com.pozoriste.stranice;

import com.pozoriste.model.Korisnik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Pocetna extends JPanel {
    //svi korisnici
    private List<Korisnik> korisnici = new ArrayList<Korisnik>();

    public Pocetna() {
        setBackground(new Color(120, 0, 0));
        // da stoje na srediti, ovaj layout po defaultu to radi
        setLayout(new GridBagLayout());
        JButton prijava = new JButton("Prijava");
        JButton registracija = new JButton("Registracija");

        add(prijava);
        add(registracija);

        prijava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        registracija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    new Registracija(korisnici).setVisible(true);
            }
        });


    }
}
