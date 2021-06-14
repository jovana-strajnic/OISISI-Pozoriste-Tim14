package com.pozoriste.stranice;

import com.pozoriste.Fajlovi;
import com.pozoriste.model.Karta;
import com.pozoriste.model.Korisnik;
import com.pozoriste.model.Predstava;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DetaljiPredstave extends JDialog {
    public DetaljiPredstave(Predstava p, Korisnik korisnik, java.util.List<Karta> sveKarte, java.util.List<Predstava> svePredstave) {
        final Map<Integer, String> izabranaSedista = new HashMap<>();
        setModal(true);
        setSize(new Dimension(700, 500));

        JPanel informacije = new JPanel();
        informacije.setLayout(new GridLayout(4, 2));

        informacije.add(new JLabel("Naziv"));
        informacije.add(new JLabel(p.getNaziv()));

        informacije.add(new JLabel("Opis"));
        informacije.add(new JLabel(p.getOpis()));

        informacije.add(new JLabel("Cena"));
        informacije.add(new JLabel("" + p.getCena()));

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        informacije.add(new JLabel("Datum"));
        informacije.add(new JLabel(p.getDatum().format(formater)));

        JPanel sedista = new JPanel();
        sedista.setLayout(new GridLayout(6, 5));
        final JLabel ukupnaCena = new JLabel("");
        for (int i = 0; i < 30; i++) {
            String red = "" + (1 + (int) i / 5);
            String kolona = "" + (1 + i % 6);
            JCheckBox sediste = new JCheckBox("R: " + red + "\nK: " + kolona);
            if (p.getSedista().get(i)) {
                sediste.setSelected(true);
                sediste.setEnabled(false);
            }
            sediste.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    if (cb.isSelected()) {
                        for (int sed = 0; sed < sedista.getComponents().length; sed++) {
                            if (cb.equals(sedista.getComponents()[sed])) {
                                izabranaSedista.put(sed, "");
                                break;
                            }
                        }
                    } else {
                        for (int sed = 0; sed < sedista.getComponents().length; sed++) {
                            if (cb.equals(sedista.getComponents()[sed])) {
                                izabranaSedista.remove(sed);
                                break;
                            }
                        }
                    }
                    ukupnaCena.setText("Ukupna cena: " + p.getCena() * izabranaSedista.size());

                }
            });
            sedista.add(sediste);
        }
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(informacije);
        split.setRightComponent(sedista);
        // add(split);

        informacije.setBackground(new Color(130, 72, 57));
        sedista.setBackground(new Color(175, 122, 110));
        JPanel dugmici = new JPanel();
        dugmici.add(ukupnaCena);
        dugmici.setSize(new Dimension(2000, 150));
        // add(dugmici);

        JButton rezervisi = new JButton("Rezervisi");
        rezervisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (izabranaSedista.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Nije izabrano sediste", "greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (Integer i : izabranaSedista.keySet()) {
                    Karta k = new Karta();
                    k.setId(sveKarte.size());
                    k.setKorisnik(korisnik);
                    k.setCena(p.getCena());
                    k.setPredstava(p);
                    k.setRed(i);
                    k.setRed(1 + (int) i / 5);
                    k.setKolona((1 + i % 6));
                    sveKarte.add(k);
                    Fajlovi.SnimiUFajl(sveKarte, "./karte.k");
                    p.getSedista().put(i, true);
                }
                if (p.getSedista().values().stream().filter(b -> b).count() == 30) {
                    p.setRasprodato(true);
                }
                Fajlovi.SnimiUFajl(svePredstave, "./predstave.p");
                setVisible(false);
            }
        });
        dugmici.add(rezervisi);
        JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jsp.setTopComponent(split);
        jsp.setBottomComponent(dugmici);
        jsp.setDividerLocation(0.9);
        add(jsp);
    }
}
