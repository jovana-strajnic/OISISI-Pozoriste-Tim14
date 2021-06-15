package com.pozoriste.stranice;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IzborFiltera extends JDialog {
    public IzborFiltera(JTable tabela) {
        setModal(true);
        setSize(new Dimension(300, 380));

        final ButtonGroup grupa = new ButtonGroup();
        final JRadioButton naziv = new JRadioButton("Naziv", true);
        final JRadioButton cena = new JRadioButton("Cena");
        final JRadioButton datum = new JRadioButton("Datum");


        grupa.add(naziv);
        grupa.add(cena);
        grupa.add(datum);

        JTextField unos = new JTextField();
        JTextField cena1 = new JTextField();
        JTextField cena2 = new JTextField();

        Date datum1 = new Date();
        JSpinner spiner1 = new JSpinner(new SpinnerDateModel(datum1, null, null, Calendar.SECOND));
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(spiner1, "yyyy/MM/dd hh:mm");
        spiner1.setEditor(editor1);

        Date datum2 = new Date();
        JSpinner spiner2 = new JSpinner(new SpinnerDateModel(datum2, null, null, Calendar.SECOND));
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(spiner2, "yyyy/MM/dd hh:mm");
        spiner2.setEditor(editor2);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        add(naziv);
        add(unos);
        add(cena);
        add(new JLabel("Min:"));
        add(cena1);
        add(new JLabel("Max:"));

        add(cena2);
        add(datum);
        add(new JLabel("Min:"));

        add(spiner1);
        add(new JLabel("Max:"));

        add(spiner2);

        unos.setEnabled(true);
        cena1.setEnabled(false);
        cena2.setEnabled(false);
        spiner1.setEnabled(false);
        spiner2.setEnabled(false);

        naziv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unos.setEnabled(true);
                cena1.setEnabled(false);
                cena2.setEnabled(false);
                spiner1.setEnabled(false);
                spiner2.setEnabled(false);
            }
        });

        datum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unos.setEnabled(false);
                cena1.setEnabled(false);
                cena2.setEnabled(false);
                spiner1.setEnabled(true);
                spiner2.setEnabled(true);
            }
        });

        cena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unos.setEnabled(false);
                cena1.setEnabled(true);
                cena2.setEnabled(true);
                spiner1.setEnabled(false);
                spiner2.setEnabled(false);
            }
        });

        JButton trazi = new JButton("Pretrazi");

        add(trazi);

        trazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (naziv.isSelected()) {
                    ((DefaultRowSorter) tabela.getRowSorter()).setRowFilter(filterImena(unos.getText().trim()));

                } else if (cena.isSelected()) {
                    float min = Float.MIN_VALUE;
                    float max = Float.MAX_VALUE;
                    try {
                        min = Float.parseFloat(cena1.getText());
                    } catch (Exception ex) {
                    }
                    try {
                        max = Float.parseFloat(cena2.getText());
                    } catch (Exception ex) {
                    }
                    ((DefaultRowSorter) tabela.getRowSorter()).setRowFilter(filterCene(min, max));

                } else {
                    ((DefaultRowSorter) tabela.getRowSorter()).setRowFilter(filterDatuma((Date) spiner1.getValue(), (Date) spiner2.getValue()));

                }
                setVisible(false);
            }
        });


    }

    //FILTERI SA NETA
    // za stringove se koristi regerx uz ignorisanje da li siu slova velika ili mala
    public RowFilter filterImena(String text) {
        return RowFilter.regexFilter("(?i)" + text, 0);
    }

    // za cene, uzima max i min i vraca redove koji su izmedju
    public static RowFilter<Object, Object> filterCene(float min, float max) {
        return new RowFilter<Object, Object>() {
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                AbstractTableModel t = (AbstractTableModel) entry.getModel();
                float price = (float) t.getValueAt((int) entry.getIdentifier(), 2);
                return price >= min && price <= max;
            }
        };

    }

    // filter za datum, kao ovaj iznad
    public static RowFilter<Object, Object> filterDatuma(Date min, Date max) {
        return new RowFilter<Object, Object>() {
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                AbstractTableModel t = (AbstractTableModel) entry.getModel();
                String date = (String) t.getValueAt((int) entry.getIdentifier(), 1);
                Date dateDate;
                try {
                    dateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                    boolean minB = !dateDate.before(min);
                    boolean maxB = !dateDate.after(max);
                    return maxB && minB;
                } catch (ParseException e) {
                }
                return true;
            }
        };
    }
}
