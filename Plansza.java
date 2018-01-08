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
    private Pole [][] pola ;
    private Pole selected;
    private List<Pole>mozliwosci;
    private List<Pole>mozbicia;

    public Plansza()
    {
        prefRozmiar = new Dimension(bok_planszy, bok_planszy);
        pionki = new ArrayList<>();
        mozliwosci = new ArrayList<>();
        mozbicia = new ArrayList<>();
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


                if(pola[x][y].czyzajete() == 1)       //na pionek
                {

                    if(selected !=null) {selected.deselect(); selected = null;}
                    decolor();
                    selected = pola[x][y];
                    pola[x][y].select();

                    if(pola[x][y].getPionek().czydamka()== 0)
                    {
                        int kierunek = pola[x][y].getPionek().getKolor();
                        ruch(x,y,1,kierunek);
                        ruch(x,y,-1,kierunek);

                        bicie(x,y,1,kierunek);
                        bicie(x,y,-1,kierunek);
                        bicie(x,y,1,-kierunek);
                        bicie(x,y,-1,-kierunek);
                    }
                    else
                    {
                        ruchdamka(x,y,1,1);
                        ruchdamka(x,y,-1,1);
                        ruchdamka(x,y,1,-1);
                        ruchdamka(x,y,-1,-1);
                    }

                }
                else if(selected != null && pola[x][y].czymozliwe() == 1)    //na puste pole
                {
                    decolor();
                    selected.getPionek().przesun(x,y,pola[x][y]);
                }
                else if(selected !=null && pola[x][y].czybicie() == 1)           //bicie
                {
                    decolor();
                    selected.getPionek().bij(x,y,pola);

                }


                repaint();
            }

        });

    }



    private void decolor()
    {
        for (Pole p : mozliwosci)
        {
            p.niemozliwe();
        }
        mozliwosci.clear();
    }


    private boolean naplanszy(int x, int y)
    {
        return x>=0 && x<8 && y>=0 && y<8;
    }

    private boolean czybicie(int x, int y,int strona, int kierunek)
    {
        return  naplanszy(x,y) &&
                naplanszy(x+strona,y+kierunek) &&
                pola[x][y].czyzajete() == 1 &&
                pola[x+strona][y+kierunek].czyzajete() == 0 &&
                pola[x][y].getPionek().getKolor() != selected.getPionek().getKolor();

    }

    private void bicie(int x,int y, int strona, int kierunek)
    {
        if(czybicie(x+strona,y+kierunek,strona, kierunek))
        {
            pola[x+strona*2][y+kierunek*2].mozbicie(pola[x+strona][y+kierunek].getPionek());
            mozliwosci.add(pola[x+strona*2][y+kierunek*2]);
        }
    }

    private void ruch(int x,int y, int strona, int kierunek)
    {
        if(naplanszy(x+strona,y+kierunek) && pola[x+strona][y+kierunek].czyzajete() == 0)
        {
            pola[x+strona][y+kierunek].mozliwe();
            mozliwosci.add(pola[x+strona][y+kierunek]);
        }
    }

    private void ruchdamka(int x,int y, int strona, int kierunek)
    {
        int a=strona,b=kierunek,c=0;
        Pionek zbijany = selected.getPionek();
        while (naplanszy(x+a, y+b))
        {
            if(pola[x+a][y+b].czyzajete() == 0)
            {
                if(c==0) pola[x + a][y + b].mozliwe();
                else if(c == 1) pola[x + a][y + b].mozbicie(zbijany);
                else break;
                mozliwosci.add(pola[x+a][y+b]);
            }
            else if(selected.getPionek().getKolor() != pola[x+a][y+b].getPionek().getKolor())
            {c++; zbijany = pola[x+a][y+b].getPionek(); }
            x+=strona;
            y+=kierunek;
        }
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
            if(pio.czyzbity() == 0) pio.draw(g);
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
