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

    public gra(String title, MainMenu menu, int size, Kolory kol) {
        super(title);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Plansza plansza = new Plansza(size,kol);
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


        addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing(WindowEvent e) {
//                if(JOptionPane.showConfirmDialog(frame, "Are you sure ?") == JOptionPane.OK_OPTION){
//                    frame.setVisible(false);
//                    frame.dispose();
            //}
                menu.setVisible(true);
            }
        });

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                new gra("Warcaby");
//            }
//        });
//    }
}