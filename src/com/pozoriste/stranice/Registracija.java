package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.model.Korisnik;
import com.pozoriste.model.TipKorisnika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class Registracija extends JDialog {

    public Registracija(List<Korisnik> korisnici) {
        setModal(true);
        setSize(new Dimension(300, 400));
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Ime"));
        JTextField imeUnos = new JTextField();
        add(imeUnos);

        add(new JLabel("Prezime"));
        JTextField prezimeUnos = new JTextField();
        add(prezimeUnos);

        add(new JLabel("Korisnicko ime"));
        JTextField korisnickoImeUnos = new JTextField();
        add(korisnickoImeUnos);


        add(new JLabel("Lozinka"));
        JPasswordField lozinkaUnos1 = new JPasswordField();
        add(lozinkaUnos1);

        add(new JLabel("Lozinka"));
        JPasswordField lozinkaUnos2 = new JPasswordField();
        add(lozinkaUnos2);

        JButton nazad = new JButton("Nazad");
        add(nazad);
        nazad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JButton dodaj = new JButton("Registruj se");
        add(dodaj);
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ime = imeUnos.getText().trim();
                String prezime = prezimeUnos.getText().trim();
                String korisnickoIme = korisnickoImeUnos.getText().trim();
                String lozinka = lozinkaUnos1.getText();
                String lozinka2 = lozinkaUnos2.getText();
                String greska = "";
                if (ime.equals(""))
                    greska += "Ime nije uneto\n";
                if (prezime.equals(""))
                    greska += "Prezime nije uneto\n";
                if (korisnickoIme.equals(""))
                    greska += "Korisnicko ime nije uneto\n";


                if (lozinka.equals(""))
                    greska += "Lozinka nije uneta";
                if (!lozinka.equals(lozinka2))
                    greska += "Lozinke se ne poklapaju";
                for (Korisnik k : korisnici)
                    if (k.getKorisnickoIme().equals(korisnickoIme))
                        greska += "Korisnicko ime je zauzeto";

                if (!greska.equals("")) {
                    JOptionPane.showMessageDialog(null, greska, "greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    Korisnik korisnik = new Korisnik();
                    korisnik.setKorisnickoIme(korisnickoIme);
                    korisnik.setIme(ime);
                    korisnik.setPrezime(prezime);
                    korisnik.setTipkorisnika(TipKorisnika.KORISNIK);
                    korisnik.setLozinka(lozinka);
                    korisnici.add(korisnik);
                    //cuvanej u fajl:\
                    Fajlovi.SnimiUFajl(korisnici, "./korisnici.k");
                    setVisible(false);

                }

            }
        });
    }
}
