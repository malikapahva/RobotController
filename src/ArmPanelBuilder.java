/**
 * Created by Malika(mxp134930) on 2/21/15.
 * CS6301
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This Class creates the JPanel to display mechanical
 * arm and buttons to control the arm .
 */
public class ArmPanelBuilder {
    private Arm arm = new Arm();
    private JPanel displayArmPanel;
    private JFrame frame;
    private InfoPanelBuilder infoPanel;
    private Thread rotateThread;
    private int angle = 360;
    private boolean left;
    private boolean right;

    /**
     * This is a constructor to initialize the JFrame and
     * InfoPanelBuilder.
     *
     * @param frame     to which all the controls and displays are added
     * @param infoPanel which displays the information about status of robot and arm.
     */
    public ArmPanelBuilder(JFrame frame, InfoPanelBuilder infoPanel) {
        this.frame = frame;
        this.infoPanel = infoPanel;
    }

    /**
     * This method creates the JPanel which contains the
     * JPanels to display mechanical arm and buttons to control
     * the arm.
     *
     * @return JPanel
     */
    public JPanel create() {
        JPanel armPanel = new JPanel();
        armPanel.setLayout(new BoxLayout(armPanel, BoxLayout.X_AXIS));
        displayArmPanel = createDisplayArmPanel();
        JPanel armControlPanel = createArmControlPanel();
        armPanel.add(displayArmPanel);
        armPanel.add(armControlPanel);
        return armPanel;
    }

    /**
     * This method creates the JPanel which displays the mechanical
     * arm.
     *
     * @return JPanel
     */
    protected JPanel createDisplayArmPanel() {
        displayArmPanel = new JPanel();
        displayArmPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayArmPanel.setPreferredSize(new Dimension(400, 200));
        displayArmPanel.add(this.arm);
        return displayArmPanel;
    }

    /**
     * This method creates the JPanel which contains the buttons
     * to control the mechanical arm and claws.
     *
     * @return JPanel
     */
    private JPanel createArmControlPanel() {
        JPanel armControlPanel = new JPanel();
        armControlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        armControlPanel.setPreferredSize(new Dimension(100, 200));
        JToolBar toolBar = createToolbar();
        armControlPanel.add(toolBar);
        return armControlPanel;
    }

    /**
     * This method creates the JToolBar which contains the buttons
     * to control arm and claws.
     *
     * @return JToolBar
     */
    private JToolBar createToolbar() {
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        createOpenClawButton(toolBar);
        toolBar.addSeparator();
        createCloseClawButton(toolBar);
        toolBar.addSeparator();
        createRotateArmButton(toolBar);
        toolBar.addSeparator();
        createStopArmButton(toolBar);
        return toolBar;
    }

    /**
     * This method creates the button to rotate mechanical arm.
     *
     * @param toolBar which contains control buttons.
     */
    private void createRotateArmButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Rotate Arm") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotateThread != null) {
                    rotateThread.stop();
                }
                rotateThread = new Thread(new RotateArmTask());
                rotateThread.start();
                refreshArmInfo();
            }
        }));
    }


    /**
     * This method creates the button to stop the rotation
     * of mechanical arm.
     *
     * @param toolBar which contains control buttons.
     */
    private void createStopArmButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Stop Arm") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotateThread != null) {
                    rotateThread.stop();
                    infoPanel.refreshArmLabel("Mechanical Arm is stopped");
                }
            }
        }));
    }


    /**
     * This method creates the button to close claws.
     *
     * @param toolBar which contains control buttons.
     */
    private void createCloseClawButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Close Claw") {
            @Override
            public void actionPerformed(ActionEvent e) {
                arm.closeClaw();
                frame.repaint();
                refreshArmInfo();
            }
        }));
    }

    /**
     * This method creates the button to open the claws.
     *
     * @param toolBar which contains control buttons.
     */
    private void createOpenClawButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Open Claw") {
            @Override
            public void actionPerformed(ActionEvent e) {
                arm.openClaw();
                frame.repaint();
                refreshArmInfo();
            }
        }));
    }

    /**
     * This method creates the messages to display on screen
     * about the status of mechanical arm and claws.
     */
    private void refreshArmInfo() {
        StringBuilder builder = new StringBuilder();
        if (angle == 270) {
            builder.append("Mechanical arm is Vertical and ");
        } else if(angle == 360) {
            builder.append("Mechanical arm is Horizontal and ");
        }else{
           builder.append("Mechanical arm is rotating and ");
        }

        if (arm.isOpenClaw()) {
            builder.append("Claw is open");
        } else {
            builder.append("Claw is closed");
        }
        infoPanel.refreshArmLabel(builder.toString());
    }

    /**
     * This Class is a thread which changes the angle to rotate
     * the mechanical arm 90 degree.
     */
    private class RotateArmTask implements Runnable {
private int defaultSpeed = 30;
        @Override
        public void run() {
            while (true) {
                arm.rotateArm(angle);
                if (angle == 360) {
                    left = true;
                    right = false;
                }

                if (angle == 270) {
                    left = false;
                    right = true;
                }

                if(angle == 360 || angle == 270){
                    defaultSpeed = 800;
                }else {
                    defaultSpeed = 30;
                }

                if(!infoPanel.getArmLabel().contains("arm is rotating")){
                    refreshArmInfo();
                }

                if (right) {
                    angle++;
                }

                if (left) {
                    angle--;
                }

                try {
                    Thread.sleep(defaultSpeed);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                frame.repaint();
            }
        }
    }
}
