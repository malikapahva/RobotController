/**
 * Created by Malika(mxp134930) on 2/20/15.
 * CS6301.
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This Class creates the JPanel to display and control
 * the robot.
 */
public class RobotPanelBuilder {
    public static final String BACKWARD_TASK = "Backward Task";
    public static final String FORWARD_TASK = "Forward Task";
    private int angle = 0;
    private static int oneX = 20;
    private static int oneY = 30;
    private Robot robot = new Robot(0, oneX, oneY);
    private Thread moveThread;
    private JFrame frame;
    private JPanel displayRobotPanel;
    private InfoPanelBuilder labelPanel;
    final String DEGREE = "\u00b0";
    private int defaultSpeed = 10;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private JButton normalSpeedButton;
    private JButton fastSpeedButton;
    private JButton slowSpeedButton;

    /**
     * This is a constructor to initialize the JFrame and
     * InfoPanelBuilder.
     *
     * @param frame      to which all the controls and displays are added
     * @param labelPanel which displays the information about status of robot and arm.
     */
    public RobotPanelBuilder(JFrame frame, InfoPanelBuilder labelPanel) {
        this.frame = frame;
        this.labelPanel = labelPanel;
    }

    /**
     * This method creates the JPanel which contains the
     * JPanels to display robot and control the robot.
     *
     * @return JPanel
     */
    public JPanel create() {
        JPanel robotPanel = new JPanel();
        robotPanel.setLayout(new BoxLayout(robotPanel, BoxLayout.X_AXIS));
        displayRobotPanel = createDisplayRobotPanel();
        JPanel robotControlPanel = createRobotControlPanel();
        robotPanel.add(displayRobotPanel);
        robotPanel.add(robotControlPanel);
        return robotPanel;
    }

    /**
     * This method creates the JPanel which displays the robot
     * and toolbar to control the movement of robot.
     *
     * @return JPanel
     */
    private JPanel createDisplayRobotPanel() {
        displayRobotPanel = new JPanel();
        displayRobotPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayRobotPanel.setPreferredSize(new Dimension(400, 200));
        JToolBar toolBar = createToolbar();
        displayRobotPanel.add(toolBar, JPanel.TOP_ALIGNMENT);
        displayRobotPanel.add(robot);
        return displayRobotPanel;
    }

    /**
     * This method creates the JPanel which contains the slider
     * to rotate the robot.
     *
     * @return JPanel
     */
    private JPanel createRobotControlPanel() {
        JPanel robotControlPanel = new JPanel();
        robotControlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        robotControlPanel.setPreferredSize(new Dimension(100, 200));
        final JSlider rotateSlider = createRotateObjectSlider();
        robotControlPanel.add(rotateSlider);
        return robotControlPanel;
    }

    /**
     * This method creates the JToolBar which contains the buttons
     * to control the movement of robot.
     *
     * @return JToolBar
     */
    private JToolBar createToolbar() {
        JToolBar toolBar = new JToolBar();
        createMoveForwardButton(toolBar);
        toolBar.addSeparator();
        createMoveBackwardButton(toolBar);
        toolBar.addSeparator();
        createStopButton(toolBar);
        toolBar.addSeparator();
        createMoveSlowButton(toolBar);
        toolBar.addSeparator();
        createMoveFastButton(toolBar);
        toolBar.addSeparator();
        createResetSpeedButton(toolBar);
        return toolBar;
    }

    /**
     * This method creates the button to reset the robot's speed to
     * normal.
     *
     * @param toolBar which contains control buttons.
     */
    private void createResetSpeedButton(JToolBar toolBar) {
        normalSpeedButton = new JButton();
        normalSpeedButton.setAction(new AbstractAction("Medium") {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSpeed = 10;
                refreshLabelInfo();
            }
        });
        toolBar.add(normalSpeedButton);
    }

    /**
     * This method moves the robot to location where it was
     * stopped.
     */
    public void moveRobotToCurrentPosition() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                robot.setLocation(oneX, oneY);
            }
        });
    }

    /**
     * This method creates the button to move the robot forward.
     *
     * @param toolBar which contains control buttons.
     */
    private void createMoveForwardButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Forward") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moveThread != null) {
                    moveThread.stop();
                }

                normalSpeedButton.setEnabled(true);
                fastSpeedButton.setEnabled(true);
                slowSpeedButton.setEnabled(true);
                robot.setLocation(oneX, oneY);
                robot.changeAngle(0);

                moveThread = new Thread(new MoveForwardTask(), FORWARD_TASK);
                moveThread.start();
                refreshLabelInfo();
            }
        }));
    }

    /**
     * This method creates the button to move the robot backward.
     *
     * @param toolBar which contains control buttons.
     */
    private void createMoveBackwardButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Backward") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moveThread != null) {
                    moveThread.stop();
                }
                normalSpeedButton.setEnabled(true);
                fastSpeedButton.setEnabled(true);
                slowSpeedButton.setEnabled(true);
                robot.changeLocation(20, 20);
                robot.changeAngle(0);

                moveThread = new Thread(new MoveBackwardTask(), BACKWARD_TASK);
                moveThread.start();
                refreshLabelInfo();
            }
        }));
    }

    /**
     * This method creates an action to slow down the robot's speed
     * and add it to tool bar.
     *
     * @param toolBar which contains control buttons.
     */
    private void createMoveSlowButton(JToolBar toolBar) {
        slowSpeedButton = new JButton();
        slowSpeedButton.setAction(new AbstractAction("Slow") {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSpeed = 30;
                refreshLabelInfo();
            }
        }
        );
        toolBar.add(slowSpeedButton);
    }

    /**
     * This method creates an action to increase the robot's speed
     * and add it to tool bar.
     *
     * @param toolBar which contains control buttons.
     */
    private void createMoveFastButton(JToolBar toolBar) {
        fastSpeedButton = new JButton();
        fastSpeedButton.setAction(new AbstractAction("Fast") {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSpeed = 3;
                refreshLabelInfo();
            }
        });
        toolBar.add(fastSpeedButton);
    }

    /**
     * This method creates the button to stop the robot.
     *
     * @param toolBar which contains control buttons.
     */
    private void createStopButton(JToolBar toolBar) {
        toolBar.add(new JButton(new AbstractAction("Stop") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moveThread != null) {
                    normalSpeedButton.setEnabled(false);
                    fastSpeedButton.setEnabled(false);
                    slowSpeedButton.setEnabled(false);
                    labelPanel.refreshRobotLabel("Robot stopped.");
                    moveThread.stop();
                }
            }
        }));
    }

    /**
     * This method creates the JSlider to rotate the robot.
     *
     * @return JSlider
     */
    private JSlider createRotateObjectSlider() {
        final JSlider slider = new JSlider(JSlider.VERTICAL, 0, 360, 0);
        slider.setMajorTickSpacing(45);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (moveThread != null) {
                    moveThread.stop();
                }
                angle = slider.getValue();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        robot.changeAngle(angle);
                        robot.setLocation(oneX, oneY);
                        frame.repaint();
                    }
                });
                labelPanel.refreshRobotLabel("Robot is rotated at angle : " + angle + DEGREE);
            }
        });
        return slider;
    }

    /**
     * This method creates the messages to display on screen
     * about the status of robot.
     */
    private void refreshLabelInfo() {
        StringBuilder builder = new StringBuilder();
        if (moveThread != null) {
            builder.append("Robot is moving ");
            if (moveThread.getName().equals(FORWARD_TASK)) {
                builder.append(" forward ");
            } else if (moveThread.getName().equals(BACKWARD_TASK)) {
                builder.append(" backward ");
            }

            if (defaultSpeed == 30) {
                builder.append(" at slow speed");
            } else if (defaultSpeed == 10) {
                builder.append(" at normal speed");
            } else {
                builder.append(" at fast speed");
            }

            labelPanel.refreshRobotLabel(builder.toString());
        }
    }

    /**
     * This Class is a thread which changes x and y co-ordinates
     * to move the robot forward. It stops the robot if reaches
     * boundary.
     */
    private class MoveForwardTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (angle == 180) {
                    robot.changeAngle(angle);
                    left = true;
                    right = false;
                    up = false;
                    down = false;
                }

                if (angle == 270) {
                    robot.changeAngle(angle);
                    left = false;
                    right = false;
                    up = true;
                    down = false;
                }

                if (angle == 0) {
                    robot.changeAngle(angle);
                    left = false;
                    right = true;
                    up = false;
                    down = false;
                }

                if (angle == 90) {
                    robot.changeAngle(angle);
                    left = false;
                    right = false;
                    up = false;
                    down = true;
                }
                if (angle > 0 && angle < 90) {
                    robot.changeAngle(angle);
                    up = false;
                    right = true;
                    left = false;
                    down = true;
                }

                if (angle > 90 && angle < 180) {
                    robot.changeAngle(angle);
                    up = false;
                    right = false;
                    left = true;
                    down = true;
                }

                if (angle > 180 && angle < 270) {
                    robot.changeAngle(angle);
                    left = true;
                    up = true;
                    right = false;
                    down = false;
                }

                if (angle > 270 && angle < 360) {
                    robot.changeAngle(angle);
                    up = true;
                    right = true;
                    left = false;
                    down = false;
                }

                if (right) {
                    oneX++;
                }
                if (left) {
                    oneX--;
                }
                if (up) {
                    oneY--;
                }
                if (down) {
                    oneY++;
                }
                try {
                    Thread.sleep(defaultSpeed);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }

                robot.setLocation(oneX, oneY);
                frame.repaint();

                stopRobotIfRequired();

            }
        }

        /**
         * This method checks the boundary conditions to stop the
         * forward moving robot and stop it.
         */
        private void stopRobotIfRequired() {
            if (isAngleBetween(90, 180) && (oneY >= 151 || oneX <= 19)) {
                moveThread.stop();
            }

            if (isAngleBetween(180, 270) && (oneY <= 29 || oneX <= 19)) {
                moveThread.stop();
            }

            if (isAngleBetween(270, 360) && (oneY <= 29 || oneX >= 351)) {
                moveThread.stop();
            }

            if (isAngleBetween(0, 90) && (oneY >= 151 || oneX >= 351)) {
                moveThread.stop();
            }

            if ((angle == 0 && oneX >= 351)
                    || (angle == 90 && oneY >= 151)
                    || (angle == 180 && oneX <= 19)
                    || (angle == 270 && oneY <= 29)
                    || (angle == 360 && oneX >= 351)) {
                moveThread.stop();
            }
        }
    }

    /**
     * This method checks if angle is within range.
     *
     * @param min minimum value of range
     * @param max maximum value of range
     */
    private boolean isAngleBetween(int min, int max) {
        return (angle > min && angle < max);
    }

    /**
     * This Class is a thread which changes x and y co-ordinates
     * to move the robot backward.It stops the robot if reaches
     * boundary.
     */
    private class MoveBackwardTask implements Runnable {
        @Override
        public void run() {

            while (true) {
                if (angle == 90) {
                    robot.changeAngle(angle);
                    left = false;
                    right = false;
                    up = true;
                    down = false;
                }

                if (angle == 180) {
                    robot.changeAngle(angle);
                    left = false;
                    right = true;
                    up = false;
                    down = false;
                }

                if (angle == 270) {
                    robot.changeAngle(angle);
                    left = false;
                    right = false;
                    up = false;
                    down = true;
                }

                if (angle == 0) {
                    robot.changeAngle(angle);
                    left = true;
                    right = false;
                    up = false;
                    down = false;
                }

                if (angle > 0 && angle < 90) {
                    robot.changeAngle(angle);
                    left = true;
                    up = true;
                    right = false;
                    down = false;
                }

                if (angle > 90 && angle < 180) {
                    robot.changeAngle(angle);
                    up = true;
                    right = true;
                    left = false;
                    down = false;

                }

                if (angle > 180 && angle < 270) {
                    robot.changeAngle(angle);
                    up = false;
                    right = true;
                    left = false;
                    down = true;
                }

                if (angle > 270 && angle < 360) {
                    robot.changeAngle(angle);
                    up = false;
                    right = false;
                    left = true;
                    down = true;
                }

                if (right) {
                    oneX++;
                }
                if (left) {
                    oneX--;
                }
                if (up) {
                    oneY--;
                }
                if (down) {
                    oneY++;
                }


                try {
                    Thread.sleep(defaultSpeed);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }

                robot.setLocation(oneX, oneY);
                frame.repaint();

                stopRobotIfRequired();

            }

        }


        /**
         * This method checks the boundary conditions to stop the
         * backward moving robot and stop it.
         */

        private void stopRobotIfRequired() {
            if (isAngleBetween(90, 180) && (oneX >= 351 || oneY <= 29)) {
                moveThread.stop();
            }

            if (isAngleBetween(180, 270) && (oneX >= 351 || oneY >= 151)) {
                moveThread.stop();
            }

            if (isAngleBetween(270, 360) && (oneX <= 19 || oneY >= 151)) {
                moveThread.stop();
            }

            if (isAngleBetween(0, 90) && (oneX <= 19 || oneY <= 29)) {
                moveThread.stop();
            }

            if ((angle == 0 && oneX <= 19)
                    || (angle == 90 && oneY <= 29)
                    || (angle == 180 && oneX >= 351)
                    || (angle == 270 && oneY >= 151)
                    || (angle == 360 && oneX <= 19)) {
                moveThread.stop();
            }
        }
    }
}

