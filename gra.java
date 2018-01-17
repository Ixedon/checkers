package com.warcaby;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class gra extends JFrame {

    public gra(String title, MainMenu menu, int size, Kolory kol, boolean komp) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Plansza plansza = new Plansza(size, kol, komp, this, menu);  //tworzeni planszy
        PasekCzasu pasek = new PasekCzasu(this, menu);  //tworeznie pasku
        add(pasek);
        add(plansza);
        pack();
        setResizable(false);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing(WindowEvent e) {

                menu.setVisible(true);  //wyjscie do menu
            }
        });

    }
}
