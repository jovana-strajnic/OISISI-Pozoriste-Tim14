package com.pozoriste.stranice;

import com.pozoriste.GlavniProzor;
import com.pozoriste.model.Korisnik;
import com.pozoriste.model.Predstava;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Predstave extends JPanel {
    //sve koje postoje u sisitemu
    private List<Predstava> svePredstave = new LinkedList<Predstava>();

    //korisnik koji je ulogovan
    private Korisnik ulogovanikorisnik;

    public Predstave(Korisnik k) {
        ulogovanikorisnik = k;
        //neke predstave za testiranje, izbrisacemo posle
        Predstava p = new Predstava();
        p.setNaziv("TEST");
        p.setCena(282);
        p.setDatum(LocalDateTime.now());
        p.setOpis("opis predstae");
        svePredstave.add(p);

        p = new Predstava();
        p.setNaziv("TEST 2");
        p.setCena(282);
        p.setDatum(LocalDateTime.now());
        p.setOpis("opis 2");
        p.setRasprodato(true);

        svePredstave.add(p);

        JTable tabela = new JTable();
        tabela.setModel(new ModelTabele(svePredstave));

        //da se vidi zaglavlje i da moze da se skroluje
        JScrollPane pane = new JScrollPane(tabela);
        add(pane);
        JButton dodaj = new JButton("Dodaj");
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DodavanjePredstave(svePredstave, tabela).setVisible(true);
            }
        });
        add(dodaj);

        //odjava
        JButton odjava = new JButton("Odjava");
        odjava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlavniProzor.getAktivniProzor().promeniStranicu(new Pocetna());
            }
        });
        add(odjava);
    }


    public class ModelTabele extends AbstractTableModel {

        private static final long serialVersionUID = 2710362894062472488L;

        private List<String> kolone = new ArrayList<String>();
        private List<Predstava> svePredstave;

        public ModelTabele(List<Predstava> svePredstave) {
            kolone.add("Ime");
            kolone.add("Datum");
            kolone.add("Cena");
            kolone.add("Rasprodato");
            kolone.add("Detalji");// SHOW DETAILS
            this.svePredstave = svePredstave;

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public int getRowCount() {
            return svePredstave.size();
        }

        @Override
        public int getColumnCount() {
            return kolone.size();
        }

        @Override
        public String getColumnName(int kolona) {
            return kolone.get(kolona);
        }

        @Override
        public Class<?> getColumnClass(int kolona) {
            if (kolona == 2)
                return Float.class;
            return String.class;
        }

        @Override
        public Object getValueAt(int red, int kolona) {
            Predstava predstava = svePredstave.get(red);
            switch (kolona) {
                case 0:
                    return predstava.getNaziv();
                case 1:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formatirano = predstava.getDatum().format(formatter);
                    return formatirano;
                case 2:
                    return predstava.getCena();
                case 3:
                    if (predstava.isRasprodato())
                        return "RASPORODATO";
                    else
                        return "";
                case 4:
                    return "VISE INFORMACIJA";
            }
            return null;
        }

    }

}
