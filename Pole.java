package com.warcaby;

import java.awt.Color;
import java.awt.Graphics;

public class Pole {
    private int x,y;
    private int bok_pola;
    private int selected;
    private int mozliwe;
    private int zajete;
    private Pionek pionek;

    public Pole(int x,int y, int bok_pola)
    {
        this.x = x;
        this.y = y;
        this.bok_pola = bok_pola;
        this.selected = 0;
        this.mozliwe = 0;
        this.zajete = 0;
    }
    public void draw(Graphics g)
    {
        if(selected == 1) g.setColor(Color.BLUE);
        else if(mozliwe == 1) g.setColor(Color.GREEN);
        else if((x+y)%2==1)g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.fillRect(x*bok_pola, y*bok_pola, bok_pola, bok_pola);
    }
    public void select() {this.selected=1;}
    public void deselect() {this.selected=0;}

    public void mozliwe() {this.mozliwe=1;}
    public void niemozliwe() {this.mozliwe=0;}

    public void wstaw(Pionek pionek) {this.zajete=1; this.pionek = pionek;}
    public void usun() {this.zajete=0; this.deselect(); this.niemozliwe();}

    public int czymozliwe() {return this.mozliwe;}
    public int czyzajete() {return this.zajete;}
    public Pionek getPionek() {return this.pionek;}

}
