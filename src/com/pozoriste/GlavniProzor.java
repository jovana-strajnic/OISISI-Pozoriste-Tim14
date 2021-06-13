package com.pozoriste;

import com.pozoriste.stranice.Pocetna;
import com.pozoriste.stranice.Predstave;

import javax.swing.*;

public class GlavniProzor extends JFrame {
    public GlavniProzor() {
        setSize(700,400);
        add(new Pocetna());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
