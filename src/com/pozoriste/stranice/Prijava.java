package com.pozoriste.stranice;

import com.pozoriste.model.Korisnik;
import com.pozoriste.model.TipKorisnika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Prijava extends JDialog {
    //kad se uloguje ovde ce se postaviti koji je
    private Korisnik ulogovanKorisnik=null;
    public Prijava(List<Korisnik> korisnici) {
        setModal(true);
        setSize(new Dimension(300, 200));
        setLayout(new GridLayout(3, 2));


        add(new JLabel("Korisnicko ime"));
        JTextField korisnickoImeUnos = new JTextField();
        add(korisnickoImeUnos);


        add(new JLabel("Lozinka"));
        JPasswordField lozinkaUnos1 = new JPasswordField();
        add(lozinkaUnos1);



        JButton nazad = new JButton("Nazad");
        add(nazad);
        nazad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JButton dodaj = new JButton("Prijavi se");
        add(dodaj);
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String korisnickoIme = korisnickoImeUnos.getText().trim();
                String lozinka = lozinkaUnos1.getText();

                for(Korisnik k:korisnici)if(k.getKorisnickoIme().equals(korisnickoIme)&&k.getLozinka().equals(lozinka)){
                    ulogovanKorisnik=k;
                    break;
                }
                if(ulogovanKorisnik==null){
                    JOptionPane.showMessageDialog(null, "Pogresni podaci", "greska", JOptionPane.ERROR_MESSAGE);
                }else{
                    setVisible(false);
                }



            }
        });
    }

    public Korisnik getUlogovanKorisnik() {
        return ulogovanKorisnik;
    }
}
