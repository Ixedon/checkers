package com.warcaby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;


public class PasekCzasu extends JPanel {
    private JButton button;
    private int minuty;
    private int sekundy;
    public PasekCzasu(gra gr, MainMenu menu) {

        setLayout(new FlowLayout());
        PomiarCzasu timer = new PomiarCzasu(gr,menu);
        add(timer);
        timer.begin();

        JTextField jtxt = new JTextField("05",2);
        add(jtxt);
        JLabel label3 = new JLabel(":");
        add(label3);
        JTextField jtxt2 = new JTextField("00",2);
        add(jtxt2);

        button = new JButton("Start");
        add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            minuty = parseInt(jtxt.getText());
            sekundy = parseInt(jtxt2.getText());
            timer.settimer(minuty, sekundy);
            }
        });
    }
}
