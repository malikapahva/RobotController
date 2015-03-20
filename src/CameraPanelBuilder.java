/**
 * Created by Malika(mxp134930) on 2/21/15.
 * CS6301.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

/**
 * This Class creates the JPanel to display camera/image
 * and controls to resize the image .
 */
public class CameraPanelBuilder {
    private Image image = new Image(0.5);
    public JButton button;
    private JPanel displayImagePanel = new JPanel();
    JButton closeButton = new JButton("Close");
    JLabel label;
    private JSlider zoomSlider = createZoomSlider();
    private RobotPanelBuilder robotPanelBuilder;

    /**
     * This is constructor creates the Camera Panel Builder object
     * with robot panel builder.
     *
     * @param robotPanelBuilder
     */
    public CameraPanelBuilder(RobotPanelBuilder robotPanelBuilder) {
        this.robotPanelBuilder = robotPanelBuilder;
    }

    /**
     * This method creates the JPanel which contains the
     * JPanels to display camera/image and controls to resize the image.
     *
     * @return JPanel
     */
    public JPanel create() {
        JPanel cameraPanel = new JPanel();
        cameraPanel.setLayout(new BoxLayout(cameraPanel, BoxLayout.X_AXIS));
        createDisplayImagePanel();
        JPanel cameraControlPanel = createCameraControlPanel();
        cameraPanel.add(displayImagePanel);
        cameraPanel.add(cameraControlPanel);
        return cameraPanel;
    }

    /**
     * This method creates the JPanel which displays the camera/image.
     *
     * @return JPanel
     */
    private void createDisplayImagePanel() {
        displayImagePanel.setPreferredSize(new Dimension(400, 200));
        displayImagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        BufferedImage buttonIcon = null;
        try {
            URL resource = getClass().getResource("camera.png");
            buttonIcon = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new JButton(new ImageIcon(buttonIcon));
        button.setBorder(BorderFactory.createEmptyBorder());
        Events e = new Events();
        displayImagePanel.add(button);
        closeButton.setVisible(false);
        zoomSlider.setEnabled(false);
        button.addActionListener(e);

        label = new JLabel("Click on camera to turn on Camera");
        displayImagePanel.add(label);
    }

    /**
     * This method creates the JPanel which contains the slider
     * to resize the image.
     *
     * @return JPanel
     */
    private JPanel createCameraControlPanel() {
        JPanel cameraControlPanel = new JPanel();
        cameraControlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cameraControlPanel.setPreferredSize(new Dimension(100, 200));
        cameraControlPanel.add(zoomSlider);
        return cameraControlPanel;
    }

    /**
     * This method creates the JSlider to resize the image
     * displayed by robot.
     *
     * @return JSlider
     */
    private JSlider createZoomSlider() {
        final JSlider zoom = new JSlider(JSlider.VERTICAL, 1, 9, 5);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(1, new JLabel("Zoom Out"));
        labelTable.put(9, new JLabel("Zoom In"));
        zoom.setLabelTable(labelTable);
        zoom.setMajorTickSpacing(1);
        zoom.setPaintTicks(true);
        zoom.setPaintLabels(true);
        zoom.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = zoom.getValue();
                image.setScale((double) value / 10);
                robotPanelBuilder.moveRobotToCurrentPosition();
            }
        });
        return zoom;
    }

    /**
     * This Class implements the action associated with
     * camera to turn it on.
     */
    public class Events implements ActionListener {

        /**
         * This method implements the action to turn on camera and
         * display the image.
         *
         * @param e is an action event.
         */
        public void actionPerformed(ActionEvent e) {
            button.setVisible(false);
            label.setVisible(false);
            displayImagePanel.add(image);
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button.setVisible(true);
                    label.setVisible(true);
                    image.setVisible(false);
                    closeButton.setVisible(false);
                    zoomSlider.setEnabled(false);
                    robotPanelBuilder.moveRobotToCurrentPosition();
                }
            });
            image.setVisible(true);
            closeButton.setVisible(true);
            zoomSlider.setEnabled(true);
            displayImagePanel.add(closeButton);
            robotPanelBuilder.moveRobotToCurrentPosition();
        }
    }
}
