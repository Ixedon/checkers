package com.warcaby;

import javax.swing.*;
import java.awt.*;


public class PasekCzasu extends JPanel {
    private JButton button;
    public PasekCzasu() {

        setLayout(new FlowLayout());
        button = new JButton("Strat");
        add(button);
    }
}
