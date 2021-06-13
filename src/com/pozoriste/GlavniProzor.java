package com.pozoriste;

import com.pozoriste.stranice.Pocetna;

import javax.swing.*;

public class GlavniProzor extends JFrame {
    private static GlavniProzor aktivniProzor;

    public GlavniProzor() {
        aktivniProzor = this;
        setSize(700, 400);
        add(new Pocetna());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void promeniStranicu(JPanel stranica) {
        getContentPane().removeAll();
        getContentPane().add(stranica);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static GlavniProzor getAktivniProzor() {
        return aktivniProzor;
    }
}
