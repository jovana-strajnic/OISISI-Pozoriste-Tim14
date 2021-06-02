package com.pozoriste.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Predstava implements Serializable {


    //sva polja koje ima predstava
    private String naziv;
    private String opis;
    private LocalDateTime datum;
    private float cena;
    private Map<Integer, Boolean> sedista = new HashMap<Integer, Boolean>();
    private boolean rasprodato;
    private int sifra;


    public Predstava() {
        //stavimo sva sedista na slobodna
        for (int i = 0; i < 30; i++)
            sedista.put(i, false);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public Map<Integer, Boolean> getSedista() {
        return sedista;
    }

    public void setSedista(Map<Integer, Boolean> sedista) {
        this.sedista = sedista;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public boolean isRasprodato() {
        return rasprodato;
    }

    public void setRasprodato(boolean rasprodato) {
        this.rasprodato = rasprodato;
    }

    public int getSifra() {
        return sifra;
    }

    public void setSifra(int sifra) {
        this.sifra = sifra;
    }

}
