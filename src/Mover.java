import java.awt.*;

public class Mover {
    public static int width =150;
    public static int height = 15;
    Point left_pos;

    public Mover(){
        width = 150;
        height = 15;
        left_pos = new Point(0, 668 - 20);

    }

    public void draw_mover(Graphics g2){
        g2.setColor(Color.lightGray);
        g2.fillRect(left_pos.x, left_pos.y-height/2, width, height);



    }
}