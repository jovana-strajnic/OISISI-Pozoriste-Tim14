package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.GlavniProzor;
import com.pozoriste.model.Korisnik;
import com.pozoriste.model.TipKorisnika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Pocetna extends JPanel {
    //svi korisnici
    private List<Korisnik> korisnici ;

    public Pocetna() {
        korisnici= (List<Korisnik>) Fajlovi.ProcitajIzFajla("./korisnici.k");
        //ako nema fajla dodamo samo podrazumvanog admina
        if(korisnici==null){
            korisnici=new ArrayList<Korisnik>();
            Korisnik admin=new Korisnik();
            admin.setTipkorisnika(TipKorisnika.ADMINISTRATOR);
            admin.setLozinka("admin");
            admin.setKorisnickoIme("admin");
            korisnici.add(admin);
            Fajlovi.SnimiUFajl(korisnici,"./korisnici.k");
        }
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
                Prijava p = new Prijava(korisnici);
                p.setVisible(true);
                if (p.getUlogovanKorisnik() != null) {
                    GlavniProzor.getAktivniProzor().promeniStranicu(new Predstave(p.getUlogovanKorisnik()));
                }

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
