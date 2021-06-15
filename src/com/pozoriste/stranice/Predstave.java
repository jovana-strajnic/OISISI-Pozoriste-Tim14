package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.GlavniProzor;
import com.pozoriste.model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
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

    //sve koje postoje u sisitemu
    private List<Karta> sveKarte;


    public Predstave(Korisnik k, List<Korisnik> sviKorisnici) {
        ulogovanikorisnik = k;
        svePredstave = (List<Predstava>) Fajlovi.ProcitajIzFajla("./predstave.p");
        if (svePredstave == null)
            svePredstave = new LinkedList<Predstava>();

        sveKarte = (List<Karta>) Fajlovi.ProcitajIzFajla("./karte.k");
        if (sveKarte == null)
            sveKarte = new LinkedList<Karta>();
        JTable tabela = new JTable();
        tabela.setModel(new ModelTabele(svePredstave, ulogovanikorisnik));

        //sortiranje
        tabela.setAutoCreateRowSorter(true);

        tabela.setColumnSelectionAllowed(false);
        tabela.setRowHeight(40);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Action detalji = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selektovanRed = Integer.valueOf(e.getActionCommand());
                new DetaljiPredstave(svePredstave.get(selektovanRed), ulogovanikorisnik, sveKarte, svePredstave, sviKorisnici).setVisible(true);
                ((ModelTabele) tabela.getModel()).fireTableDataChanged();
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

        //za dugme za izmene koristi se ova klasa sa interneta
        if (ulogovanikorisnik.getTipkorisnika() == TipKorisnika.ADMINISTRATOR)
            new ButtonColumn(tabela, izmena, 5);


        //da se vidi zaglavlje i da moze da se skroluje
        JScrollPane pane = new JScrollPane(tabela);
        JButton dodaj = new JButton("Dodaj");
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DodavanjePredstave(svePredstave, tabela, null).setVisible(true);
            }
        });


        JButton izvestajZaSve = new JButton("Izvestaj");
        izvestajZaSve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StavkaIzvestaja> izvestajlist = new LinkedList<>();

                for (Predstava pr : svePredstave) {
                    StavkaIzvestaja si = new StavkaIzvestaja();

                    si.setId(pr.getSifra());
                    float cena = 0;
                    for (Karta kar : sveKarte) {
                        if (kar.getPredstava().getSifra() == pr.getSifra())
                            cena += kar.getCena();
                    }
                    si.setCena(cena);
                    izvestajlist.add(si);
                }
                new PrikazIzvestaja(izvestajlist).setVisible(true);
            }
        });


        //odjava
        JButton odjava = new JButton("Odjava");
        odjava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlavniProzor.getAktivniProzor().promeniStranicu(new Pocetna());
            }
        });


        //pretrage
        JButton pretraga = new JButton("Pretraga");
        pretraga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IzborFiltera(tabela).setVisible(true);
            }
        });

        JPanel dugmici = new JPanel();
        dugmici.setLayout(new BoxLayout(dugmici, BoxLayout.Y_AXIS));


        dugmici.add(pretraga);
        //samo za admina dajemo da ima dodavanje
        if (ulogovanikorisnik.getTipkorisnika() == TipKorisnika.ADMINISTRATOR)
            dugmici.add(dodaj);
        if (ulogovanikorisnik.getTipkorisnika() == TipKorisnika.ADMINISTRATOR)
            dugmici.add(izvestajZaSve);
        dugmici.add(odjava);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setLeftComponent(pane);
        split.setRightComponent(dugmici);
        split.setDividerLocation(700);
        setLayout(new BorderLayout());
        add(split, BorderLayout.CENTER);

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
            kolone.add("Detalji");
            //samo admin vidi izmenu
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
