package com.warcaby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class MainMenu extends JFrame{

    private int size;
    private Kolory kol;
    private boolean komp;
    private Image image;
    public MainMenu(String name)
    {
        super(name);
        this.size = 8;
        komp = false;
        kol = new Kolory();
        kol.c1 = Color.RED;
        kol.c2 = Color.DARK_GRAY;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel listPane = new JPanel();

//        try {
//            image = ImageIO.read(new File("background.jpg"));
//        } catch (IOException ex) {
//            // handle exception...
//        }
//        JPanel imagePlanel = new JPanel()
//        {
//            @Override
//            public void paintComponent(Graphics g)
//            {
//                g.drawImage(image, 0, 0, null);
//            }
//        };

        setSize(new Dimension(200,200));
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
        listPane.add(Box.createRigidArea(new Dimension(0,15)));
        //listPane.add(Box.createHorizontalGlue());

        JButton start = new JButton("Start game");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPane.add(start);
        listPane.add(Box.createRigidArea(new Dimension(0,10)));
        JButton optb = new JButton("Options");
        optb.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPane.add(optb);
        listPane.add(Box.createRigidArea(new Dimension(0,10)));
        JButton close = new JButton("Quit");
        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPane.add(close);



        JFrame options;
        options  = new JFrame("Options");
        options.setSize(new Dimension(250,200));

        JPanel optPane = new JPanel();
        JRadioButton jradio1 = new JRadioButton("Play with 2nd player");
        jradio1.setSelected(true);
        JRadioButton jradio2 = new JRadioButton(" Play with computer ");

        optPane.add(jradio1);
        optPane.add(jradio2);

        ButtonGroup group = new ButtonGroup();
        group.add(jradio1);
        group.add(jradio2);

        JLabel label = new JLabel("Select board size");
        optPane.add(label);
        String[] sizes = { "4x4", "6x6", "8x8","10x10", "12x12" };
        JComboBox rozmiary = new JComboBox(sizes);
        rozmiary.setSelectedIndex(2);
        optPane.add(rozmiary);

        JLabel label2 = new JLabel("Select color");
        optPane.add(label2);
        String[] kolory = {"Red-Black", "White-Red", "White-Black"};
        JComboBox kolor = new JComboBox(kolory);
        kolor.setSelectedIndex(0);
        optPane.add(kolor);


        JButton submit = new JButton("Apply");
        optPane.add(submit);

        options.setContentPane(optPane);


        MainMenu menu = this;
        //add(imagePlanel);
        //add(listPane);
        setContentPane(listPane);
        setVisible(true);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String s = String.valueOf(rozmiary.getSelectedItem());
                String[] parts = s.split("x");
                size = Integer.parseInt(parts[0]);

                s = String.valueOf(kolor.getSelectedItem());
                if(s == "Red-Black"){kol.c2 = Color.RED; kol.c1 = Color.DARK_GRAY;}
                if(s == "White-Red"){kol.c2 = Color.LIGHT_GRAY; kol.c1 = Color.RED;}
                if(s == "White-Black"){kol.c2 = Color.LIGHT_GRAY; kol.c1 = Color.DARK_GRAY;}

                komp = jradio2.isSelected();


                options.setVisible(false);
            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              gra gra1 = new gra("Warcaby",menu,size ,kol, komp);
              setVisible(false);
            }
        });

        optb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                options.setVisible(true);
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                menu.dispose();
            }
        });



    }

    public static void main(String[] args) {
       MainMenu menu = new MainMenu("Menu");
    }
}

class Kolory
{
    public Color c1,c2;
}
