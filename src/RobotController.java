/**
 * Created by Malika(mxp134930) on 2/20/15.
 * CS6301.
 */

import javax.swing.*;
import java.awt.*;

/**
 * This Class creates the main panel which has RobotPanelBuilder,
 * CameraPanelBuilder, ArmPanelBuilder and InfoPanelBuilder.
 */
public class RobotController {

    public static void main(String[] args) {
        new RobotController().createFrame();
    }

    /**
     * This method creates main frame and add main Panel to it.
     *
     */
    private void createFrame() {
        JFrame frame = new JFrame("Robot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = createMainPanel(frame);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(375, 20);
    }

    /**
     * This method creates main panel and add all the other panels i.e.
     * RobotPanelBuilder, CameraPanelBuilder, ArmPanelBuilder and
     * InfoPanelBuilder to it.
     */
    private JPanel createMainPanel(JFrame frame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        InfoPanelBuilder infoPanel = new InfoPanelBuilder();
        JPanel labelPanel = infoPanel.create();
        RobotPanelBuilder robotPanelBuilder = new RobotPanelBuilder(frame, infoPanel);
        infoPanel.setRobotPanelBuilder(robotPanelBuilder);
        JPanel robotPanel = robotPanelBuilder.create();
        JPanel cameraPanel = new CameraPanelBuilder(robotPanelBuilder).create();
        JPanel armPanel = new ArmPanelBuilder(frame, infoPanel).create();
        mainPanel.add(robotPanel);
        mainPanel.add(armPanel);
        mainPanel.add(cameraPanel);
        mainPanel.add(labelPanel);
        return mainPanel;
    }
}