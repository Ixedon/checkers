package com.warcaby;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

public class gra extends JFrame {

    public gra(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Plansza plansza = new Plansza();
        //PomiarCzasu timer = new PomiarCzasu(plansza.getPreferredSize().width);
        PasekCzasu pasek = new PasekCzasu();
       // add(timer);
        add(pasek);
        add (plansza);

       // timer.begin();
        //setContentPane(plansza);
        pack();
        setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new gra("Warcaby");
            }
        });
    }
}