package pong2;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
public class PongV2 extends Applet implements KeyListener
{
    private ArrayList<Ball> balls;
    private ArrayList<Paddle> paddles;
    private ArrayList<Player> players;
    private boolean[] keys =new boolean [4];
    private int fps=20;
    
        public void paint(Graphics g)
        {
            drawCourt(g);
            for(Ball b:balls)
            {
                b.drawBall(g);
            }
            for(Paddle p:paddles)
            {
                p.drawPaddle(g);
            }
        }
        
        public void init()
        {
            this.setSize(1500,750);
            this.setBackground(Color.BLACK);
            this.addKeyListener(this);
            
            balls=new ArrayList();
            balls.add(new Ball(750,375,10));
            
            paddles=new ArrayList();
            paddles.add(new Paddle(55,375));
            paddles.add(new Paddle(1445,375));
            
            players=new ArrayList();
            players.add(new Player());
            players.add(new Player());
            
            players.get(0).addPaddle(paddles.get(0));
            players.get(1).addPaddle(paddles.get(1));
            
            for(boolean b:keys)
                b=false;
            
            
            Timer time=new Timer();
        time.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                
                checkKeys();
                
                ballCheck();
                
                for(Ball b:balls)
                {
                    b.move();
                    b.speedUp();
                }
                
                for(Player p:players)
                    p.getPaddle().move();
                
                repaint();
                
                
                
            }

    }, 0, 1000/fps);
        }
        
        public void checkKeys()
        {
            if(keys[0]==true)
                players.get(0).getPaddle().changeVelocity(true);
            if(keys[1]==true)
                players.get(0).getPaddle().changeVelocity(false);
            if(keys[2]==true)
                players.get(1).getPaddle().changeVelocity(true);
            if(keys[3]==true)
                players.get(1).getPaddle().changeVelocity(false);
        }
        
        public void ballCheck()
        {
            
            double[] ballPos=balls.get(0).getPos();
            double[] paddle1Pos=paddles.get(0).getPos();
            double[] paddle2Pos=paddles.get(1).getPos();
            int rad=balls.get(0).getRad();
            int[] info1=players.get(1).getPaddle().getInfo();
            int[] info2=players.get(1).getPaddle().getInfo();

            if(ballPos[1]>paddle1Pos[1]-info1[0]/2 && ballPos[1]<paddle1Pos[1]+info1[0]/2)
            {
                if(ballPos[0]-rad<=paddle1Pos[0]+info1[1]/2 && ballPos[0]-rad>=paddle1Pos[0]-info1[1]/2)
                {
                    balls.get(0).reflect();
                }
            }
            
            if(ballPos[1]>paddle2Pos[1]-info1[0]/2 && ballPos[1]<paddle2Pos[1]+info1[0]/2)
            {
                if(ballPos[0]+rad>=paddle2Pos[0]-info1[1]/2 && ballPos[0]+rad<=paddle2Pos[0]+info1[1]/2)
                {
                    balls.get(0).reflect();
                }
            }
            if(ballPos[0]-rad<=50)
            {
                players.get(1).changeScore();
                reset();
            }
            if(ballPos[0]+rad>=1450)
            {
                players.get(0).changeScore();
                reset();
            }
        }
        public void reset()
        {
           balls.clear();
           balls.add(new Ball(750,375,10)); 
        }
        
        public void drawCourt(Graphics g)
        {
            g.setColor(Color.GREEN);
            g.drawRect(50, 50, 1400, 650);
            g.drawString("Player 1: "+players.get(0).getScore(), 50, 45);
            g.drawString("Player 2: "+players.get(1).getScore(),1385, 45);
        }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar())
        {
            case 'w':keys[0]=true;
                break;
            case 's':keys[1]=true;
                break;
            case 'i':keys[2]=true;
                break;
            case 'k':keys[3]=true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyChar())
        {
            case 'w':keys[0]=false; players.get(0).getPaddle().setZero();
                break;
            case 's':keys[1]=false; players.get(0).getPaddle().setZero();
                break;
            case 'i':keys[2]=false; players.get(1).getPaddle().setZero();
                break;
            case 'k':keys[3]=false; players.get(1).getPaddle().setZero();
                break;
        }
    }
        
}
class Ball
{
    private int rad;
    private double velocity;
    private double[] pos, vel;
    
    public Ball(int cx, int cy, int rad)
    {
           velocity=5*1.25;
           pos=new double[2];
           vel=new double[2];
           pos[0]=cx;
           pos[1]=cy;
           if(Math.random()<.5)
               vel[0]=velocity;
           else
               vel[0]=-velocity;
           if(Math.random()<.5)
               vel[1]=velocity;
           else
               vel[1]=-velocity;
           this.rad=rad;
    }
    public void move()
    {
        if(pos[1]+rad>=700 || pos[1]-rad<=50)
            vel[1]*=-1;
        
        
        pos[0]+=vel[0];
        pos[1]+=vel[1];
      
    }
    
    public void speedUp()
    {
        velocity+=1.0/500.0;
        if(vel[0]<0)
        {
            vel[0]=-velocity;
        }
        else
        {
            vel[0]=velocity;
        }
    }
    
    public void drawBall(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval((int)(pos[0]-rad), (int)(pos[1]-rad),2*rad, 2*rad);
    }
    
    public void reflect()
    {
        vel[0]*=-1;
    }
    
    public double[] getPos()
    {
        return pos;
    }
    
    public int getRad()
    {
        return rad;   
    }
}
class Paddle
{
    private int length, width;
    private double[] pos,vel;
    
    public Paddle(int cx, int cy)
    {
        pos=new double[2];
        vel=new double[2];
        pos[0]=cx;
        pos[1]=cy;
        length=90;
        width=10;
    }
    public void drawPaddle(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRect((int)(pos[0]-width/2),(int)(pos[1]-length/2),width,length);
    }
    public void move()
    {
        pos[0]+=vel[0];
        pos[1]+=vel[1];
    }
    public void changeVelocity(boolean direction)
    {
        
        
        if(direction==true && pos[1]-length/2>50)
        {
            if(vel[1]!=0)
                return;
            vel[1]-=5*2.5;
        }
        else if(direction==false && pos[1]+length/2<700)
        {
            if(vel[1]!=0)
                return;
            vel[1]+=5*2.5;
        }
        else
        {
            setZero();
        }
    }
    public void setZero()
    {
        vel[1]=0;
    }
    
    public double[] getPos()
    {
        return pos;
    }
    public int[] getInfo()
    {
        int[] info={length, width};
        return info;
    }
}
class Player
{
    private int score;
    private Paddle p;
    
    public Player()
    {
        score=0;
    }
    
    public void changeScore()
    {
        score++;
    }
    public int getScore()
    {
        return score;
    }
    public void addPaddle(Paddle P)
    {
        this.p=P;
    }
    public Paddle getPaddle()
    {
        return p;
    }
}