import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProgramView extends JFrame {
    DrawingPanel drawingPanel = new DrawingPanel();
    JPanel drawingContainer = new JPanel();
    JScrollPane drawingScrollPane = new JScrollPane(drawingContainer);
    ControllerPanel controllerPanel = new ControllerPanel();
    JScrollPane controllerScrollPane = new JScrollPane(controllerPanel);
    JSplitPane splitPane;
    //private JSplitPane splitPane = new JSplitPane();
    ProgramView() {
        this.setTitle("Mark and save");

        drawingContainer.setLayout(new GridBagLayout());
        drawingContainer.add(drawingPanel, new GridBagConstraints());

        // Put drawing and interface together
        splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                controllerScrollPane,
                drawingScrollPane);
        splitPane.setOneTouchExpandable(true);

        this.getContentPane().add(splitPane);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void setDrawingPanelSize(int width, int height) {
        drawingPanel.setPreferredSize(new Dimension(width, height));
        drawingPanel.revalidate();
        drawingPanel.repaint();
    }

    public void setProgramSizes(int frameWidth, int frameHeight,
                                int controllerPanelWidth, int controllerPanelHeight,
                                int controllerPanelMinWidth, int controllerPanelMinHeight,
                                int drawingMinWidth, int drawingMinHeight,
                                int splitDividerLocation)
    {
        // Size when "restore down" ( box icon clicked )
        this.setSize(frameWidth, frameHeight);

        drawingScrollPane.setMinimumSize(new Dimension(drawingMinWidth, drawingMinHeight));
        controllerPanel.setPreferredSize(new Dimension(controllerPanelWidth, controllerPanelHeight));
        controllerScrollPane.setMinimumSize(new Dimension(controllerPanelMinWidth, controllerPanelMinHeight));
        splitPane.setDividerLocation(splitDividerLocation);

        // Fullscreen mode with top label
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    void addChangeImageListener(ActionListener listenerForChange) {

    }
}
