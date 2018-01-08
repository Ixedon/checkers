package com.warcaby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;

public class Plansza extends JComponent
{

    private static int bok_planszy = 600;
    private static int bok_pola = bok_planszy/8;

    private Dimension prefRozmiar;
    private List<Pionek> pionki;
   // private Pionek [][] plan_pion ;
    private Pole [][] pola ;
    private Pole selected;
    private List<Pole>mozliwosci;

    public Plansza()
    {
        prefRozmiar = new Dimension(bok_planszy, bok_planszy);
        pionki = new ArrayList<>();
        mozliwosci = new ArrayList<>();
       // plan_pion = new Pionek[8][8];
        pola = new Pole[8][8];

        Pionek pionek;

        for (int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                pola[i][j] = new Pole(i,j,bok_pola);

                if(j<3 && (i+j)%2==1)
                {
                    pionek = new Pionek(1, i,j, pola[i][j], bok_pola);
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                }

                if(j>4 && (i+j)%2==1)
                {
                    pionek = new Pionek(-1, i,j,pola[i][j], bok_pola);
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                }


            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent me)
            {
                // Obtain mouse coordinates at time of press.

                int x = me.getX()/bok_pola;
                int y = me.getY()/bok_pola;
//                System.out.println(x);
//                System.out.println(y);


                if(pola[x][y].czyzajete() == 1)
                {

                    if(selected !=null) {selected.deselect(); selected = null;}
                    decolor();
                    selected = pola[x][y];
                    pola[x][y].select();

                    int kierunek = pola[x][y].getPionek().getKolor();
                    if(x>0 && (kierunek > 0 ? y<7 : y>0) && pola[x-1][y+kierunek].czyzajete() == 0)
                    {
                        pola[x-1][y+kierunek].mozliwe();
                        mozliwosci.add(pola[x-1][y+kierunek]);
                    }
                    if(x<7 && (kierunek > 0 ? y<7 : y>0) && pola[x+1][y+kierunek].czyzajete() == 0)
                    {
                        pola[x+1][y+kierunek].mozliwe();
                        mozliwosci.add(pola[x+1][y+kierunek]);
                    }

                }
                else if(selected != null && pola[x][y].czymozliwe() == 1)
                {
                    decolor();
                    selected.getPionek().przeun(x,y,pola[x][y]);

                }


                repaint();
            }

        });

    }

    void decolor()
    {
        for (Pole p : mozliwosci)
        {
            p.niemozliwe();
        }
        mozliwosci.clear();
    }


    @Override
    public Dimension getPreferredSize()
    {
        return prefRozmiar;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        rysujPlansze(g);
        for(Pionek pio : pionki)
        {
            pio.draw(g);
        }
    }

    private void rysujPlansze(Graphics g)
    {
        for (int i =0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                pola[i][j].draw(g);
            }

        }

    }

}
