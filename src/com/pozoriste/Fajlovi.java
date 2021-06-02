package com.pozoriste;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Fajlovi {

    public static Object ProcitajIzFajla(String gde) {
        try {
            FileInputStream ulaz = new FileInputStream(gde);
            ObjectInputStream objIn = new ObjectInputStream(ulaz);
            Object obj = objIn.readObject();
            objIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void SnimiUFajl(Object sta, String gde) {
        try {
            FileOutputStream fileOut = new FileOutputStream(gde);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(sta);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
