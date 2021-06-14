package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.GlavniProzor;
import com.pozoriste.model.Korisnik;
import com.pozoriste.model.Predstava;
import com.pozoriste.model.TipKorisnika;

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
    private List<Predstava> svePredstave;

    //korisnik koji je ulogovan
    private Korisnik ulogovanikorisnik;

    public Predstave(Korisnik k) {
        ulogovanikorisnik = k;
        svePredstave = (List<Predstava>) Fajlovi.ProcitajIzFajla("./predstave.p");
        if (svePredstave == null)
            svePredstave = new LinkedList<Predstava>();
        JTable tabela = new JTable();
        tabela.setModel(new ModelTabele(svePredstave, ulogovanikorisnik));

        tabela.setColumnSelectionAllowed(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Action detalji = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selektovanRed = Integer.valueOf(e.getActionCommand());
                new DetaljiPredstave(svePredstave.get(selektovanRed)).setVisible(true);

            }
        };

        //za dugme za detalje koristi se ova klasa sa interneta
        new ButtonColumn(tabela, detalji, 4);


        Action izmena = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selektovanRed = Integer.valueOf(e.getActionCommand());
                new DodavanjePredstave(svePredstave, tabela, svePredstave.get(selektovanRed)).setVisible(true);

            }
        };

        //za dugme za detalje koristi se ova klasa sa interneta
        new ButtonColumn(tabela, izmena, 5);


        //da se vidi zaglavlje i da moze da se skroluje
        JScrollPane pane = new JScrollPane(tabela);
        add(pane);
        JButton dodaj = new JButton("Dodaj");
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DodavanjePredstave(svePredstave, tabela, null).setVisible(true);
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

        public ModelTabele(List<Predstava> svePredstave, Korisnik ulogovan) {
            kolone.add("Ime");
            kolone.add("Datum");
            kolone.add("Cena");
            kolone.add("Rasprodato");
            kolone.add("Detalji");// SHOW DETAILS
            if (ulogovan.getTipkorisnika() == TipKorisnika.ADMINISTRATOR)
                kolone.add("Izmeni");
            this.svePredstave = svePredstave;

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex > 3;
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
                case 5:
                    return "IZMENA";
            }
            return null;
        }

    }

}
