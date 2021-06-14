package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.model.Predstava;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DodavanjePredstave extends JDialog {

    public DodavanjePredstave(List<Predstava> svePredstave, JTable tabela, Predstava predstava) {

        setModal(true);
        setSize(new Dimension(300, 400));
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Naziv"));
        JTextField nazivUnos = new JTextField();
        add(nazivUnos);

        add(new JLabel("Opis"));
        JTextArea opisUnos = new JTextArea();
        add(opisUnos);

        add(new JLabel("Datum"));
        Date datum = new Date();
        JSpinner spiner = new JSpinner(new SpinnerDateModel(datum, null, null, Calendar.SECOND));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spiner, "yyyy/MM/dd hh:mm");
        spiner.setEditor(editor);
        add(spiner);

        add(new JLabel("Cena"));
        JTextField cenaUnos = new JTextField();
        add(cenaUnos);

        JButton nazad = new JButton("Nazad");
        nazad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(nazad);

        if (predstava != null) {
            nazivUnos.setText(predstava.getNaziv());
            opisUnos.setText(predstava.getOpis());
            cenaUnos.setText("" + predstava.getCena());
            spiner.setValue(Date.from(predstava.getDatum().atZone(ZoneId.systemDefault()).toInstant()));
        }

        JButton dodaj = new JButton("Dodaj");

        add(dodaj);
        //sta se radi kad se klikne na dodaj dugme:
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //preuzme se sve uneto
                String naziv = nazivUnos.getText().trim();
                String opis = opisUnos.getText().trim();
                String cena = cenaUnos.getText().trim();
                Date datum = (Date) spiner.getValue();
                float parsiranaCena = 0;
                String greska = "";
                if (naziv.equals(""))
                    greska += "Naziv nije unet\n";
                if (opis.equals(""))
                    greska += "Opis nije unet\n";
                if (cena.equals(""))
                    greska += "Cena nije uneta\n";

                else {
                    try {
                        parsiranaCena = Float.parseFloat(cena);
                    } catch (Exception ex) {
                        greska += "Cena nije validna\n";

                    }
                }
                if (datum.before(new Date()))
                    greska += "Datum je u proslosti";
                if (!greska.equals("")) {
                    JOptionPane.showMessageDialog(null, greska, "greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    Predstava nova = new Predstava();
                    //editovbanje
                    if (predstava != null)
                        nova = predstava;
                    nova.setSifra(svePredstave.size());
                    nova.setOpis(opis);
                    nova.setDatum(datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    nova.setNaziv(naziv);
                    nova.setCena(parsiranaCena);
                    if (predstava == null)
                        svePredstave.add(nova);
                    //cuvanje
                    Fajlovi.SnimiUFajl(svePredstave, "./predstave.p");
                    //da se rerenderuje tabela
                    ((AbstractTableModel) tabela.getModel()).fireTableDataChanged();
                    setVisible(false);
                }
            }
        });

    }
}
