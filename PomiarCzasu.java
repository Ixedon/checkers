package com.warcaby;

import javax.swing.*;
import java.awt.*;

public class PomiarCzasu extends JComponent implements Runnable
{
    Thread timer;
    private int width;
    private int milisekundy;
    private int sekundy;
    private  int minuty;
    private int startmin,startsek;
    private int currsek,currmin;
    private boolean timeron;
    private gra gr;
    private  boolean koniec;
    private MainMenu menu;

    public PomiarCzasu(gra gr, MainMenu menu)
    {
        timer = new Thread(this);
        //this.width = width;
        this.milisekundy = 0;
        this.sekundy = 0;
        this.minuty = 0;
        this.currmin = 0;
        this.currsek = 0;
        this.timeron = false;
        this.gr = gr;
        this.koniec = false;
        this.menu = menu;
    }
    @Override
    public void paintComponent (Graphics g) {
      //  super.paintComponent(g);
        int width = 0;
        g.setFont(new Font("default", Font.BOLD, 12));
        String text = "Czas rozgrywki:    ";
        g.drawString(text,width,15);
        width += g.getFontMetrics().stringWidth(text);

        g.setFont(new Font("default", Font.ITALIC, 12));
        String wynik =  str(minuty)+":"+str(sekundy) ;//+ ":"+str( milisekundy/10);
        g.drawString(wynik,width,15);
        width += g.getFontMetrics().stringWidth(wynik) + 20;

        g.setFont(new Font("default", Font.BOLD, 12));
        text = "Timer:   ";
        g.drawString(text,width,15);
        width += g.getFontMetrics().stringWidth(text);

        g.setFont(new Font("default", Font.ITALIC, 12));
        wynik =  str(currmin)+":"+str(currsek);
        g.drawString(wynik,width,15);
        width += g.getFontMetrics().stringWidth(wynik);

    }

    public void  settimer(int startmin, int startsek)
    {
        timeron = true;
        this.currsek = startsek;
        this.currmin = startmin;
        koniec = true;
       // this.startmin = startmin;
       // this.startsek = startsek;

    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
            milisekundy+=25;
            if(milisekundy == 1000){sekundy+=1;milisekundy = 0;}
            if(sekundy == 60){minuty+=1;sekundy = 0;}

            if(timeron && milisekundy == 0 && currsek+currmin > 0)
            {
                currsek -=1;
                if(currsek < 0){currsek +=60; currmin -=1;}
            }
            if(currsek+currmin == 0 && koniec)
            {
                JOptionPane.showMessageDialog(new JFrame(),
                        "End of time limit." +
                                (Plansza.iloscPio2 > Plansza.iloscPio1 ? "Upper player wins" : "Lower player wins"),
                        "Results",
                        JOptionPane.PLAIN_MESSAGE);
                gr.dispose();
                koniec = false;
                menu.setVisible(true);
                Thread.currentThread().interrupt();
            }

            repaint();
        }
    }

    public void begin()
    {
        timer.start();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(300,20);
    }
    private String str(int a)
    {
        String s = Integer.toString(a);
        if(s.length() < 2 )s="0"+s;
        return s;
    }


}
