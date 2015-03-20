/**
 * Created by Malika(mxp134930) on 2/21/15.
 * CS6301
 */

import javax.swing.*;
import java.awt.*;

/**
 * This Class creates the mechanical arm with claws and
 * contains methods to rotate arm, open and close claws.
 */
public class Arm extends JPanel {

    private boolean openClaw;
    private int angle;

    /**
     * This method draws the mechanical arm and claws.
     *
     * @param g is a Graphics object
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(400, 200));
        graphics2D.setColor(Color.BLACK);
        graphics2D.rotate(Math.toRadians(angle), 130, 100);
        graphics2D.drawLine(130, 100, 200, 100);

        if (openClaw) {
            graphics2D.rotate(Math.toRadians(315), 200, 100);
        }

        graphics2D.drawLine(200, 99, 220, 99);
        graphics2D.drawLine(200, 99, 210, 90);
        graphics2D.drawLine(210, 90, 220, 99);

        if (openClaw) {
            graphics2D.rotate(Math.toRadians(90), 200, 100);
        }

        graphics2D.drawLine(200, 101, 220, 101);
        graphics2D.drawLine(200, 101, 210, 110);
        graphics2D.drawLine(210, 110, 220, 101);

    }

    /**
     * This method rotates the mechanical arm to 0 or 90 degrees.
     */
    public void rotateArm(int angle) {
        this.angle = angle;
    }

    /**
     * This method opens the claws.
     */
    public void openClaw() {
        openClaw = true;
    }


    /**
     * This method closes the claws.
     */
    public void closeClaw() {
        openClaw = false;
    }

    /**
     * This method check whether the claws are open or not.
     *
     * @return boolean.
     */
    public boolean isOpenClaw() {
        return openClaw;
    }
}
