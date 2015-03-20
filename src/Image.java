/**
 * Created by Malika(mxp134930) on 2/21/15.
 * CS6301.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This Class loads the image and scale it.
 */
public class Image extends JPanel {
    BufferedImage image;
    double scale;

    /**
     * This is a constructor to initialize the scale value and
     * load the image.
     *
     * @param  scale
     */
    public Image(double scale) {
        loadImage();
        this.scale = scale;
        setBackground(Color.black);
    }

    /**
     * This method renders the image.
     *
     * @param g
     *        is a Graphics object
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double x = (w - scale * imageWidth) / 2;
        double y = (h - scale * imageHeight) / 2;

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.scale(scale, scale);
        g2.drawRenderedImage(image, at);
    }

    /**
     * This method sets the preferred size of image.
     *
     * @return Dimension
     */
    public Dimension getPreferredSize() {
        int w = (int) (scale * image.getWidth());
        int h = (int) (scale * image.getHeight());
        return new Dimension(w, h);
    }

    /**
     * This method sets scale of image.
     */
    public void setScale(double s) {
        scale = s;
        revalidate();
        repaint();
    }

    /**
     * This method creates the image which robot displays.
     */
    private void loadImage() {
        URL resource = getClass().getResource("images.jpg");
        try {
            image = ImageIO.read(resource);
        } catch (MalformedURLException mue) {
            System.out.println("URL trouble: " + mue.getMessage());
        } catch (IOException ioe) {
            System.out.println("read trouble: " + ioe.getMessage());
        }
    }
}
