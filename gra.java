package com.warcaby;



import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class gra extends JFrame {

    public gra(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Plansza plansza = new Plansza();

        setContentPane(plansza);
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