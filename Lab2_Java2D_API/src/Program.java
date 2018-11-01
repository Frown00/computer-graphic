import javax.swing.*;
import java.awt.*;

public class Program {
    private JFrame frame;
    private JPanel drawingContainer;
    private DrawingPanel drawingPanel;
    private JScrollPane drawingScrollPane;

    private ControllerPanel interfacePanel;
    private JScrollPane interfaceScrollPane;
    private JSplitPane splitPane;

    private final int frameWidth;
    private final int frameHeight;

    private int drawingWidth;
    private int drawingHeight;
    private final int interfaceWidth;
    private final int interfaceHeight;

    private final int drawingMinWidth;
    private final int drawingMinHeight;
    private final int interfaceMinWidth;
    private final int interfaceMinHeight;
    private final int splitDividerLocation;



    Program() {
        frame = new JFrame();

        drawingContainer = new JPanel();
        drawingPanel = new DrawingPanel();

        interfacePanel = new ControllerPanel();

        splitPane = new JSplitPane();

        this.frameWidth = 16 * 60;
        this.frameHeight = 9 * 60;

        this.drawingWidth = 500;
        this.drawingHeight = 500;
        this.interfaceWidth = 150;
        this.interfaceHeight = 300;

        this.drawingMinWidth = 300;
        this.drawingMinHeight = 300;
        this.interfaceMinWidth = 150;
        this.interfaceMinHeight = 300;
        this.splitDividerLocation = 200;
    }


    public void GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Default Size when restore down (clicked box icon in top bar)
        frame.setSize(frameWidth, frameHeight);
        // Fullscreen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Mark and save");


        // Drawing section bacis config
//        drawingWidth = drawingPanel.getImageWidth();
//        drawingHeight = drawingPanel.getImageHeight();

        drawingPanel.setPreferredSize(new Dimension(drawingWidth, drawingHeight));
        drawingContainer.setLayout(new GridBagLayout());
        drawingContainer.add(drawingPanel, new GridBagConstraints());
        drawingScrollPane = new JScrollPane(drawingContainer);

        drawingScrollPane.setMinimumSize(new Dimension(drawingMinWidth, drawingMinHeight));


        // Interface basic config
        interfacePanel.setPreferredSize(new Dimension(interfaceWidth, interfaceHeight));
        interfaceScrollPane = new JScrollPane(interfacePanel);

        interfaceScrollPane.setMinimumSize(new Dimension(interfaceMinWidth, interfaceMinHeight));

        // Put drawing and interface together
        splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                    interfaceScrollPane,
                                    drawingScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(splitDividerLocation);

//        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(splitPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //DrawingPanel panelR = new DrawingPanel();
        //ramka.getContentPane().add(panelR);
    }
}
