import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Observer;

public class Model extends Thread {
    public Game gm;
    private ArrayList<IView> views = new ArrayList<IView>();

    public Model(Game gm){
        this.gm = gm;
        Timer timer = new Timer();
        TimerTask task = new TimerTask()  {
            @Override
            public void run() {
                if(!Game.paused && Game.running && !Game.new_lv) {
                    Game.ball.delta_move();
                }
                if(Game.won_game){
                    A2.sp.jf.setVisible(false);
                    EndScreen ed = new EndScreen();
                }
            }
        };
        timer.schedule(task, 0, 5);
    }


    public void run(){
        gm.last_paint = System.nanoTime();
        gm.last_move = System.nanoTime();
        //Game.FPS *= 2;
        while(Game.running){
            if(!Game.paused) {
                if(SplashScreen.gm.get_remain() <= 0){
                    Game.level++;
                    gm.brick_line();
                    Game.paused = !Game.paused;
                    Game.ball.pos = new Point(Game.wid/2, Game.hei /2);
                    if(Ball.move_y < 0){
                        Ball.move_y *= -1;
                    }
                    Ball.move_x = 1;
                    gm.new_lv = true;
                    //gm.last_paint = System.nanoTime();
                    //gm.repaint();
                }
                SplashScreen.gm.repaint();
                gm.last_paint = System.nanoTime();
                try {
                    Thread.sleep((long) (1000 / Game.FPS / 2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                gm.last_paint = System.nanoTime();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }

    }
    /**************************************************************************************************************
     **************************************************MVC Functions****************************************
     **************************************************************************************************************/


    public void addView(IView view) {
        views.add(view);
        // update the view to current state of the model
        view.updateView();
    }

    public void notifyObservers() {
        for (IView view : this.views) {
            System.out.println("Model: notify View");
            view.updateView();
        }
    }

}
