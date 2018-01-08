package com.warcaby;

import java.awt.Graphics;
import java.awt.Color;

import java.util.Scanner;

public class Pionek {
    private static int srednica;
    private static int bok_pola;
    private int kolor;
    private int x,y;
    private int typ;
    private Pole pole;

    public Pionek(int kolor,int x,int y, Pole pole, int bok_pola)
    {
        this.kolor = kolor;
        this.x = x;
        this.y = y;
        this.bok_pola = bok_pola;
        this.pole = pole;
        srednica = (int) (bok_pola * 0.8);
    }

    public void draw (Graphics g)
    {
        int roz = (bok_pola - srednica)/2;
        int obw = 4;
        g.setColor(Color.WHITE);

        g.fillOval(x*bok_pola + roz-obw/2, y*bok_pola + roz-obw/2 , srednica+obw, srednica+obw);

        if(kolor==-1) g.setColor(Color.RED);
        else g.setColor(Color.DARK_GRAY);

        g.fillOval(x*bok_pola + roz, y*bok_pola + roz , srednica, srednica);

    }

    public void przeun(int x, int y, Pole pole)
    {
        this.pole.usun();
        this.y = y;
        this.pole = pole;
        this.pole.wstaw(this);

    }

    public int getKolor(){return this.kolor;}
}
