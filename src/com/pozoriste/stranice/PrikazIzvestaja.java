package com.pozoriste.stranice;

import com.pozoriste.model.Korisnik;
import com.pozoriste.model.Predstava;
import com.pozoriste.model.StavkaIzvestaja;
import com.pozoriste.model.TipKorisnika;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PrikazIzvestaja extends JDialog {
    public PrikazIzvestaja(List<StavkaIzvestaja> izvestaj) {
        setSize(300, 500);
        setLocationRelativeTo(null);
        JTable tabela = new JTable();
        setModal(true);

        tabela.setModel(new PrikazIzvestaja.ModelTabele(izvestaj));

        tabela.setColumnSelectionAllowed(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        float ukupno=0;

        for(StavkaIzvestaja s:izvestaj )
            ukupno+=s.getCena();

        JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        jsp.setTopComponent(add(new JScrollPane(tabela)));
        jsp.setBottomComponent(new JLabel("UKUPNO: "+ukupno));
        add(jsp);

    }


    public class ModelTabele extends AbstractTableModel {

        private static final long serialVersionUID = 2710362894062472488L;

        private List<String> kolone = new ArrayList<String>();
        List<StavkaIzvestaja> stavke;

        public ModelTabele(List<StavkaIzvestaja> stavke) {
            kolone.add("ID");
            this.stavke = stavke;
            kolone.add("Cena");
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public int getRowCount() {
            return stavke.size();
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
            if (kolona == 1)
                return String.class;
            return Float.class;
        }

        @Override
        public Object getValueAt(int red, int kolona) {
            StavkaIzvestaja stavka = stavke.get(red);
            switch (kolona) {
                case 0:
                    return stavka.getId();
                case 1:
                    return stavka.getCena();
            }
            return null;
        }

    }
}


