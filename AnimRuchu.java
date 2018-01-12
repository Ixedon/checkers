package com.warcaby;

import javax.swing.JComponent;
import java.awt.Graphics;

import static java.lang.Math.abs;

public class AnimRuchu extends JComponent implements Runnable {

    private int bok_pola;
    private Thread animator;
    private int x,y,nx,ny;
    Pionek pionek,zbijany;
    Plansza plansza;
    private static int sleep = 5;

    public AnimRuchu(Pionek pio, int nx, int ny, Plansza plansza, int bok_pola)
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
        animator.start();

    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public void run() {

        //pionek.setX(0);
        //pionek.setY(0);
        x = pionek.getX()*bok_pola;
        y = pionek.getY()*bok_pola;
        while (abs(x - nx) > 0 && abs(y-ny) > 0)
        {
            if(abs(x - nx) >0)
                x-=getsign(x - nx);
            if(abs(y - ny) >0)
                y-=getsign(y - ny);

            pionek.setX(x);
            pionek.setY(y);

            //pionek.drawonpoint();

            plansza.repaint();
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
        System.out.println("End");
        pionek.setX(x/bok_pola);
        pionek.setY(y/bok_pola);
        pionek.setInanim(false);
        //System.out.println(pionek.isInanim());
        if(zbijany!=null)zbijany.setZbity(1);
        pionek.checkdamka();
        plansza.repaint();
        //Thread.currentThread().interrupt();

    }
    private  int getsign(int a){
        if(a<0)return -1;
        else return 1;
    }

}
