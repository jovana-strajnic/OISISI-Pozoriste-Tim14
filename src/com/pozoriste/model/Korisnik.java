package com.pozoriste.model;

import java.util.ArrayList;
import java.util.List;

public class Korisnik {
    private String korisnickoIme;
    private String ime;
    private String prezime;
    private List<Karta> kupljeneKarte = new ArrayList<Karta>();
    private String lozinka;
    private TipKorisnika tipkorisnika;

    public Korisnik() {
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public List<Karta> getKupljeneKarte() {
        return kupljeneKarte;
    }

    public void setKupljeneKarte(List<Karta> kupljeneKarte) {
        this.kupljeneKarte = kupljeneKarte;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public TipKorisnika getTipkorisnika() {
        return tipkorisnika;
    }

    public void setTipkorisnika(TipKorisnika tipkorisnika) {
        this.tipkorisnika = tipkorisnika;
    }
}

//enumeraciaj za tipove korisnika
enum TipKorisnika {
    ADMINISTRATOR, KORISNIK
};
