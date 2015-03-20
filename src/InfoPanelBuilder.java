/**
 * Created by Malika(mxp134930) on 2/21/15.
 * CS6301.
 */

import javax.print.attribute.SetOfIntegerSyntax;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
/**
 * This Class creates the JPanel to display the robot's
 * status, mechanical arm's status and Temperature .
 */
public class InfoPanelBuilder {
    final String DEGREE  = "\u00b0";
    private JPanel infoPanel;
    private JLabel robotInfoLabel;
    private JLabel armInfoLabel;
    private JLabel tempValueLabel;
    private RobotPanelBuilder robotPanelBuilder;
    private SetOfIntegerSyntax armLabel;

    /**
     * This method creates the JPanel which contains the
     * JPanels to display mechanical arm's status, robot's
     * status and temperature senses by the robot.
     *
     * @return JPanel
     */
    public JPanel create(){
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        JPanel labelPanel = createLabelPanel();
        JPanel tempPanel = createTemperaturePanel();
        infoPanel.add(labelPanel);
        infoPanel.add(tempPanel);
        return infoPanel;
    }

    /**
     * This method creates the JPanel to display the robot
     * and mechanical arm's status.
     *
     * @return JPanel
     */
    private JPanel createLabelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelPanel.setPreferredSize(new Dimension(400, 50));
        robotInfoLabel = new JLabel();
        labelPanel.add(robotInfoLabel);
        armInfoLabel = new JLabel();
        labelPanel.add(armInfoLabel);
        return labelPanel;
    }

    /**
     * This method creates the JPanel to display the temperature.
     *
     * @return JPanel
     */
    private JPanel createTemperaturePanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
        tempPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tempPanel.setSize(new Dimension(100, 50));
        JButton temperatureButton = createTemperatureButton();
        JPanel valuePanel = createTemperatureValuePanel();
        tempPanel.add(temperatureButton);
        tempPanel.add(valuePanel);
        return tempPanel;
    }

    /**
     * This method creates the JPanel which displays the temperature
     * senses by the robot.
     *
     * @return JPanel
     */
    private JPanel createTemperatureValuePanel() {
        JPanel valuePanel = new JPanel();
        valuePanel.setPreferredSize(new Dimension(100,25));
        tempValueLabel = new JLabel();
        valuePanel.add(tempValueLabel);
        generateTemperature();
        return valuePanel;
    }

    /**
     * This method creates the Button to get the temperature.
     *
     * @return JButton
     */
    private JButton createTemperatureButton() {

        return new JButton(new AbstractAction("Temperature") {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTemperature();
            }
        });
    }

    /**
     * This method generates the temperature and update label.
     */
    private void generateTemperature(){
        Random random = new Random();
        int generatedTemp = random.nextInt((100 - 30) + 1) + 30;
        String temp  = generatedTemp + DEGREE + " F";
        tempValueLabel.setText(temp);
        if (robotPanelBuilder != null){
            robotPanelBuilder.moveRobotToCurrentPosition();
        }
    }

    /**
     * This method refreshes the robot's status.
     */
    public void refreshRobotLabel(String info){
        robotInfoLabel.setText(info);
        infoPanel.revalidate();
        infoPanel.repaint();
        robotPanelBuilder.moveRobotToCurrentPosition();
    }

    /**
     * This method refreshes the mechanical arm's status.
     */
    public void refreshArmLabel(String info){
        armInfoLabel.setText(info);
        infoPanel.revalidate();
        infoPanel.repaint();
        robotPanelBuilder.moveRobotToCurrentPosition();
    }


    /**
     * This method sets the robotPanelBuilder object.
     *
     * @param robotPanelBuilder
     */
    public void setRobotPanelBuilder(RobotPanelBuilder robotPanelBuilder) {
        this.robotPanelBuilder = robotPanelBuilder;
    }

    /**
     * This method return the arm message info
     */
    public String getArmLabel() {
        return armInfoLabel.getText();
    }
}
