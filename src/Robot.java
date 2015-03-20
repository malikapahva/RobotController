/**
 * Created by Malika(mxp134930) on 2/20/15.
 * Cs6301
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * This Class creates the robot and rotate it.
 */
public class Robot extends JPanel {
    private int angle;
    private int x;
    private int y;


    /**
     * This is a constructor to initialize the angle, x and
     * y co-ordinate to draw the robot.
     *
     * @param  angle
     *         to rotate the robot
     * @param  x
     *         x co-ordinate to draw the robot.
     * @param  y
     *         y co-ordinate to draw the robot.
     */
    Robot(int angle, int x, int y) {
        this.angle = angle;
        this.x = x;
        this.y = y;
    }

    /**
     * This method draws the robot and rotate it.
     *
     * @param g
     *         is a Graphics object
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(400, 150));
        graphics2D.setColor(Color.BLACK);
        graphics2D.rotate(Math.toRadians(angle), x, y);
        int[] w = {x, x+10, x, x+20};
        int[] z = {y+20, y+10, y, y+10};
        graphics2D.fillPolygon(w, z, 4);
    }

    /**
     * This method changes the location of robot.
     *
     * @param x
     *         x co-ordinate to draw the robot.
     * @param y
     *         y co-ordinate to draw the robot.
     */
    public void changeLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method changes the angle of robot.
     *
     * @param value
     *         angle to rotate object.
     */
    public void changeAngle(int value) {
        angle = value;
    }
}
