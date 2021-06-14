package com.pozoriste.model;

public class StavkaIzvestaja {
    private int id; //id predstave ili karte za izevestaj
    private float cena;//ukupna cena za kartu ili predstavu

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }
}
