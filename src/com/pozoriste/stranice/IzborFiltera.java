package com.pozoriste.stranice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class IzborFiltera extends JDialog {
    public IzborFiltera() {
        setModal(true);
        setSize(new Dimension(300, 500));

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
        add(cena1);
        add(cena2);
        add(datum);
        add(spiner1);
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

        JButton trazi=new JButton("Pretrazi");

        add(trazi);

        trazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });

    }
}
