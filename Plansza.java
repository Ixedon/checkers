package com.warcaby;

import java.awt.Dimension;
import java.awt.Graphics;


import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.util.Random;

public class Plansza extends JComponent
{

    private static int bok_planszy = 500;  //ustalenie wielkości okienka
    private int ip;
    private  int bok_pola;


    private Dimension prefRozmiar; //definicja parametrow
    private List<Pionek> pionki;
    private Pole [][] pola ;
    private Pole selected, wszselected;
    private List<Pole>mozliwosci;
    private List<Pole>mozbicia;
    private List<Ruch>wszmozliwosci;
    private List<Ruch>wszmozbicia;
    private int aktKolor;
    private boolean komp;
    public static int iloscPio1, iloscPio2;
    Plansza plansza;
    Kolory kol;
    public static boolean inanim = false;
    private MainMenu menu;
    private gra gr;
    public Plansza(int size, Kolory kol, boolean komp, gra gr, MainMenu menu)  //konstruktor
    {
        ip = size + 2;
        bok_pola = bok_planszy/ip;
        prefRozmiar = new Dimension(bok_planszy, bok_planszy);
        pionki = new ArrayList<>();
        mozliwosci = new ArrayList<>();
        mozbicia = new ArrayList<>();
        wszmozliwosci = new ArrayList<>();
        wszmozbicia = new ArrayList<>();
        pola = new Pole[ip][ip];
        plansza = this;
        aktKolor = -1;
        this.komp = komp;
        this.kol = kol;
        Pole.col = kol.c2;
        this.gr = gr;
        this.menu = menu;
        Pionek pionek;
        iloscPio1 = 0;
        iloscPio2 = 0;
        for (int i=0;i<ip;i++)   //tworzenie planszy
        {
            for(int j=0;j<ip;j++)
            {
                pola[i][j] = new Pole(i,j,bok_pola,!naplanszy(i,j));

                if(naplanszy(i,j) && j<ip/2-1 && (i+j)%2==1)
                {
                    pionek = new Pionek(1, i,j, pola[i][j], bok_pola,ip,kol.c1);   //tworzenie pionkow
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                    iloscPio1+=1;
                }

                if( naplanszy(i,j) && j>ip/2 && (i+j)%2==1)
                {
                    pionek = new Pionek(-1, i,j,pola[i][j], bok_pola,ip,kol.c2);   //tworzenie pionkow
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                    iloscPio2+=1;
                }


            }
        }

        addMouseListener(new MouseAdapter() {  //przechwytywanie klikniec
            @Override
            public void mousePressed (MouseEvent me) {
                // Obtain mouse coordinates at time of press.

                int x=0,y=0;
                if( !(komp && aktKolor == 1))
                {
                    x= me.getX() / bok_pola;
                    y= me.getY() / bok_pola;
                    if (!naplanszy(x, y)) return;
                    wszruchy();

                    ruchy(x, y);
                }

            }
        });

    }


    private void zakoncz(boolean kto)   //wygranie gry
    {
        JOptionPane.showMessageDialog(new JFrame(),
                "End of game." +
                        (kto ? "Upper player wins" : "Lower player wins"),
                "Results",
                JOptionPane.PLAIN_MESSAGE);
        gr.dispose();
        menu.setVisible(true);
    }

    private void ruchy(int x, int y)    //operacja wykonania ruchu
    {
        if(pola[x][y].czyzajete() == 1 && czytura(pola[x][y].getPionek()))       //na pionek
        {

            if(selected !=null) {selected.deselect(); selected = null;}
            decolor();
            selected = pola[x][y];
            int kolor = selected.getPionek().getKolor();
            pola[x][y].select();

            if(pola[x][y].getPionek().czydamka()== 0)
            {
                int kierunek = pola[x][y].getPionek().getKolor();  //kierunek gora-dol

                bicie(x,y,1,kierunek, true);
                bicie(x,y,-1,kierunek, true);
                bicie(x,y,1,-kierunek, true);
                bicie(x,y,-1,-kierunek, true);

                if(wszmozbicia.size() == 0)
                {
                    ruch(x,y,1,kierunek, true);
                    ruch(x,y,-1,kierunek, true);
                }
            }
            else
            {
                ruchdamka(x,y,1,1, true, kolor);
                ruchdamka(x,y,-1,1, true, kolor);
                ruchdamka(x,y,1,-1, true, kolor);
                ruchdamka(x,y,-1,-1, true, kolor);
            }

        }
        else if(selected != null && pola[x][y].czymozliwe() == 1)    //na puste pole
        {
            decolor();
            selected.getPionek().przesun(x,y,pola[x][y],plansza,false);
            //System.out.println(wszmozbicia.size());
            aktKolor = - aktKolor;
            if(aktKolor == 1)Pole.col = kol.c1;
            else Pole.col = kol.c2;
            wszdecolor();
            if(komp && aktKolor == 1)ruchkomputera();
        }
        else if(selected !=null && pola[x][y].czybicie() == 1)           //bicie
        {
            selected.getPionek().bij(x,y,pola, plansza);
            wszdecolor();
            wszruchy();
            decolor();
            if(Plansza.iloscPio1 <= 0){zakoncz(true);Plansza.iloscPio2 = 100;}
            if(Plansza.iloscPio2 <= 0){zakoncz(false);Plansza.iloscPio1 = 100;}
            if(wszmozbicia.size() <  1) {                      //sprawdzenie czy kolejne bicie
                aktKolor = -aktKolor;
                if(aktKolor == 1)Pole.col = kol.c1;
                else Pole.col = kol.c2;
                wszdecolor();
                if(komp && aktKolor == 1)ruchkomputera();               //odpalenie ruchu komputera
            }

        }


        repaint();
    }


    private void wszruchy()                                 //wszystkie mozliwe ruchy (sprawdzenie czy jakies obowiazkowe bicie)
    {
        for (int i =1;i<ip-1;i++) {
            for (int j = 1; j < ip - 1; j++) {
                if (pola[i][j].czyzajete() == 1 && czytura(pola[i][j].getPionek())) {
                    wszselected = pola[i][j];
                    int kolor = wszselected.getPionek().getKolor();                //kolor pionka

                    if(pola[i][j].getPionek().czydamka()== 0)
                    {
                        int kierunek = pola[i][j].getPionek().getKolor();    //kierunek gora-dol

                        bicie(i,j,1,kierunek, false);
                        bicie(i,j,-1,kierunek, false);
                        bicie(i,j,1,-kierunek, false);
                        bicie(i,j,-1,-kierunek, false);

                        if(mozbicia.size() == 0)
                        {
                            ruch(i,j,1,kierunek, false);
                            ruch(i,j,-1,kierunek, false);
                        }
                    }
                    else
                    {
                        ruchdamka(i,j,1,1, false, kolor);
                        ruchdamka(i,j,-1,1, false, kolor);
                        ruchdamka(i,j,1,-1, false, kolor);
                        ruchdamka(i,j,-1,-1, false, kolor);
                    }
                }
            }
        }
    }

    private void ruchkomputera()      //inicjalizaja watku ruchu komputera
    {
        if(Plansza.iloscPio1 <= 0)zakoncz(true);
        if(Plansza.iloscPio2 <= 0)zakoncz(false);
        Komputer kmp = new Komputer(this);
        Thread watkmp = new Thread(kmp);
        watkmp.start();
    }

    public void komuter()   //ruch komputera
    {
        Random rand = new Random();
        Ruch r=null;
        wszruchy();
        if(wszmozbicia.size() == 0)
        {

            if(wszmozliwosci.size() > 0) r = wszmozliwosci.get(rand.nextInt(wszmozliwosci.size()));
            else zakoncz(true);
        }
        else r = wszmozbicia.get(rand.nextInt(wszmozbicia.size()));

        ruchy(r.pocz.getX(),r.pocz.getY());
        wszruchy();
        ruchy(r.kon.getX(),r.kon.getY());

    }


    private void decolor()   //czysczenie planszy
    {
        for (Pole p : mozliwosci)
        {
            p.niemozliwe();
        }
        mozliwosci.clear();
        mozbicia.clear();
    }
    private void wszdecolor()  //czyszczenie po sprawdzneiu
    {
        wszmozliwosci.clear();
        wszmozbicia.clear();
    }

    private boolean czytura(Pionek pio)   //sprawdzenie tury
    {
        return (pio.getKolor() == aktKolor);
    }

    private boolean naplanszy(int x, int y)  //czy na planszy
    {
        return x>=1 && x<ip-1 && y>=1 && y<ip-1;
    }

    private boolean czybicie(int x, int y,int strona, int kierunek, boolean zaznacz)   //warunki wykonanie bicia
    {
        return  naplanszy(x,y) &&
                naplanszy(x+strona,y+kierunek) &&
                pola[x][y].czyzajete() == 1 &&
                pola[x+strona][y+kierunek].czyzajete() == 0 &&
                (zaznacz ? pola[x][y].getPionek().getKolor() != selected.getPionek().getKolor()
                : pola[x][y].getPionek().getKolor() != wszselected.getPionek().getKolor());

    }

    private void bicie(int x,int y, int strona, int kierunek, boolean zaznacz)    //bicie zwykle
    {
        if(czybicie(x+strona,y+kierunek,strona, kierunek, zaznacz))
        {
            if(zaznacz){ pola[x+strona*2][y+kierunek*2].mozbicie(pola[x+strona][y+kierunek].getPionek());
            mozliwosci.add(pola[x+strona*2][y+kierunek*2]);
            mozbicia.add(pola[x+strona*2][y+kierunek*2]);}
            else {
                wszmozliwosci.add(new Ruch(wszselected,pola[x+strona*2][y+kierunek*2]));
                wszmozbicia.add(new Ruch (wszselected, pola[x+strona*2][y+kierunek*2]));}
        }
    }

    private void ruch(int x,int y, int strona, int kierunek, boolean zaznacz)   //zwykly ruch
    {
        if(naplanszy(x+strona,y+kierunek) && pola[x+strona][y+kierunek].czyzajete() == 0)
        {
            if(zaznacz){pola[x+strona][y+kierunek].mozliwe();
            mozliwosci.add(pola[x+strona][y+kierunek]);}
            else wszmozliwosci.add(new Ruch (wszselected,pola[x+strona][y+kierunek]));
        }
    }

    private void ruchdamka(int x,int y, int strona, int kierunek, boolean zaznacz, int kolor)   //ruch i bicie damka
    {
        int a=strona,b=kierunek,c=0;
        Pionek zbijany = null;
        while (naplanszy(x+a, y+b))     //ruch danym katem
        {
            if(pola[x+a][y+b].czyzajete() == 0)
            {
                if(c==0 && (!zaznacz || wszmozbicia.size() == 0)){ if(zaznacz) pola[x + a][y + b].mozliwe();}       //ruch na puste
                else if(c == 1 && zbijany!=null) {                         // bicie
                    if (zaznacz) {pola[x + a][y + b].mozbicie(zbijany); mozbicia.add(pola[x + a][y + b]);}
                    else {wszmozbicia.add(new Ruch(wszselected, pola[x + a][y + b]));
                        wszmozliwosci.add(new Ruch(wszselected, pola[x + a][y + b]));}
                }
                else break;
                if(zaznacz) mozliwosci.add(pola[x+a][y+b]);
                else wszmozliwosci.add(new Ruch(wszselected,pola[x+a][y+b]));
            }
            else
            {
                c++;
                if(kolor != pola[x+a][y+b].getPionek().getKolor())
                {
                    zbijany = pola[x+a][y+b].getPionek();              //przechwycenie zbijanego pionka
                }
            }
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
            if(pio.czyzbity() == 0)
            {
                if(!pio.isInanim())pio.draw(g);  //ryssowanie pionkow nieruchomych
            }
        }
        for(Pionek pio : pionki)if(pio.isInanim())pio.drawonpoint(g); //ryssowanie pionkow animowanych
    }

    private void rysujPlansze(Graphics g)
    {
        for (int i =0;i<ip;i++)
        {
            for(int j=0;j<ip;j++)
            {
                pola[i][j].draw(g);  //ryskowanie pol planszy
            }

        }

    }

}

class Ruch   //struktura ruchu, pocz pole i koncowe pole
{
    public Pole pocz,kon;
    public Ruch(Pole pocz, Pole kon)
    {
        this.pocz = pocz;
        this.kon = kon;

    }
}