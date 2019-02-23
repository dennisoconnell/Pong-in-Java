package pong1;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
public class PongV1 extends Applet
{
    private ArrayList<Ball> balls;
    private int fps=100;
    
    public void paint(Graphics g)
    {
        for(Ball b:balls)
        {
            b.drawBall(g);
        }
        
    }
    public void init()
    {
        this.setSize(1500,750); 
        this.setBackground(Color.green);
        balls=new ArrayList();
        for(int x=0;x<500;x++)
        {
            int cx=(int)(2000*Math.random());
            int cy=(int)(750*Math.random());
            int rad=(int)(30*Math.random());
            balls.add(new Ball(cx,cy,rad));
        }
        
        
        
        Timer time=new Timer();
        time.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                
                for(Ball b:balls)
                    b.move();
                
                repaint();
                
                
                
            }

    }, 0, 1000/fps);
                
        
        
    }
}
class Ball
{
    private int rad;
    private double[] pos, vel;
    private double grav;
    
    public Ball(int cx,int cy,int rad)
    {
        pos=new double[2];
        vel=new double[2];
        pos[0]=cx;
        pos[1]=cy;
        vel[0]=0;
        vel[1]=0;
        this.rad=rad;
        grav=.05;
    }
    public void move()
    {
        if (pos[1]+rad>=750)
        {
            vel[1]*=-1;
        }
            
        vel[0]+=0;
        vel[1]+=grav;
        pos[0]+=vel[0];
        pos[1]+=vel[1];
    }
    public void drawBall(Graphics g)
    {
        int R=(int)(256*Math.random());
        int G=(int)(256*Math.random());
        int B=(int)(256*Math.random());
        g.setColor(new Color(R,G,B));
        g.fillOval((int)(pos[0]-rad), (int)(pos[1]-rad),2*rad, 2*rad);
    }
}