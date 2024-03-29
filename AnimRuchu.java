package com.warcaby;

import javax.swing.JComponent;
import java.awt.Graphics;

import static java.lang.Math.abs;

public class AnimRuchu extends JComponent implements Runnable {  //watek animacji

    private int bok_pola;
    private Thread animator;
    private int x,y,nx,ny;
    Pionek pionek,zbijany;
    Plansza plansza;
    private static int sleep = 5;

    public AnimRuchu(Pionek pio, int nx, int ny, Plansza plansza, int bok_pola)  //konstruktor
    {
        this.pionek = pio;
        this.x = pio.getX();
        this.y = pio.getY();
        this.plansza = plansza;
        this.nx = nx*bok_pola;
        this.ny = ny*bok_pola;
        this.bok_pola = bok_pola;
        this.zbijany = null;
        animator = new Thread(this);
    }
    public void setzbijany(Pionek zbijany)
    {
        this.zbijany = zbijany;
    }

    public void begin()
    {
        animator.start();   //poczatek animacji

    }



    @Override
    public void run() {
        x = pionek.getX()*bok_pola;     //opbranie bezwzglednych wspolrzednych
        y = pionek.getY()*bok_pola;
        while (abs(x - nx) > 0 && abs(y-ny) > 0)
        {
            if(abs(x - nx) >0)
                x-=getsign(x - nx);
            if(abs(y - ny) >0)
                y-=getsign(y - ny);

            pionek.setX(x);
            pionek.setY(y);

            plansza.repaint();
            try {
                Thread.sleep(sleep);     //opzonienie
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
       pionek.setXY(x/bok_pola, y/bok_pola);
        pionek.setInanim(false);    //koniec animacji
        Plansza.inanim = true;
        if(zbijany!=null)zbijany.setZbity(1);
        pionek.checkdamka();  //sprawdzenei czy damka
        plansza.repaint();

    }
    private  int getsign(int a){
        if(a<0)return -1;
        else return 1;
    }

}
