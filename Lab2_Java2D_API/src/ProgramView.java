import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProgramView extends JFrame {
    DrawingPanel drawingPanel = new DrawingPanel();
    JPanel drawingContainer = new JPanel();
    JScrollPane drawingScrollPane = new JScrollPane(drawingContainer);
    JSplitPane splitPane;
    ControllerPanel controllerPanel = new ControllerPanel();
    JScrollPane controllerScrollPane = new JScrollPane(controllerPanel);

    //private JSplitPane splitPane = new JSplitPane();
    ProgramView() {
        JPanel program = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Mark and save");

//        drawingPanel.setPreferredSize(new Dimension(300, 300));

        drawingContainer.setLayout(new GridBagLayout());
        drawingContainer.add(drawingPanel, new GridBagConstraints());

        drawingScrollPane.setMinimumSize(new Dimension(30, 30));


        // Controller panel basic config
        controllerPanel.setPreferredSize(new Dimension(200, 300));

        controllerScrollPane.setMinimumSize(new Dimension(150, 300));

        // Put drawing and interface together
        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                controllerScrollPane,
                drawingScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        this.getContentPane().add(splitPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setDrawingPanelSize(int width, int height) {
        drawingPanel.setPreferredSize(new Dimension(width, height));
        drawingPanel.revalidate();
        drawingPanel.repaint();
    }



    void addChangeImageListener(ActionListener listenerForChange) {

    }
}
