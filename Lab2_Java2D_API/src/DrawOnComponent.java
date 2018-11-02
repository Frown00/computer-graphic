import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

/**
 *  Note: Normally the ButtonPanel and DrawingArea would not be static classes.
 *  This was done for the convenience of posting the code in one class and to
 *  highlight the differences between the two approaches. All the differences
 *  are found in the DrawingArea class.
 */
public class DrawOnComponent
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI()
    {
        DrawingArea drawingArea = new DrawingArea();
        JPanel drawingContainer = new JPanel();
        drawingContainer.setLayout(new GridBagLayout());
        drawingContainer.add(drawingArea, new GridBagConstraints());
        ButtonPanel buttonPanel = new ButtonPanel( drawingContainer );

//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Draw On Component");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add(drawingContainer);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLocationRelativeTo( null );
        frame.pack();
        frame.setVisible(true);
    }

    static class ButtonPanel extends JPanel implements ActionListener
    {
        private JPanel drawingArea;

        public ButtonPanel(JPanel drawingArea)
        {
            this.drawingArea = drawingArea;
            add( createButton("	", Color.BLACK) );
            add( createButton("	", Color.RED) );
            add( createButton("	", Color.GREEN) );
            add( createButton("	", Color.BLUE) );
            add( createButton("	", Color.ORANGE) );
            add( createButton("	", Color.YELLOW) );
            add( createButton("Clear Drawing", null) );
        }

        private JButton createButton(String text, Color background)
        {
            JButton button = new JButton( text );
            button.setBackground( background );
            button.addActionListener( this );

            return button;
        }

        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton)e.getSource();

//            if ("Clear Drawing".equals(e.getActionCommand()))
//                drawingArea.clear();
//            else
//                drawingArea.setForeground( button.getBackground() );
        }
    }

    static class DrawingArea extends JPanel
    {
        private final static int AREA_SIZE = 400;
        private ArrayList<ColoredRectangle> coloredRectangles = new ArrayList<ColoredRectangle>();
        private Rectangle shape;

        BufferedImage image;


        public DrawingArea()
        {
            try {
                image = ImageIO.read(new File("obrazek.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
            addMouseMotionListener(ml);



        }

        @Override
        public Dimension getPreferredSize()
        {
            return isPreferredSizeSet() ?
                    super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            //  Custom code to paint all the Rectangles from the List
            Color foreground = g.getColor();
            //  Paint the Rectangle as the mouse is being dragged
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage( image, 0, 0, null);
            g.setColor( Color.BLACK );
            g.drawString("Add a rectangle by doing mouse press, drag and release!", 40, 15);

            for (DrawingArea.ColoredRectangle cr : coloredRectangles)
            {
                g.setColor( cr.getForeground() );
                Rectangle r = cr.getRectangle();
                g.drawRect(r.x, r.y, r.width, r.height);
            }



            if (shape != null)
            {
                g2d.setColor( foreground );
                g2d.draw( shape );
            }
        }

        public void addRectangle(Rectangle rectangle, Color color)
        {
            //  Add the Rectangle to the List so it can be repainted

            ColoredRectangle cr = new ColoredRectangle(color, rectangle);
            coloredRectangles.add( cr );
            repaint();
        }

        public void clear()
        {
//            coloredRectangles.clear();
            coloredRectangles.remove(0);
            repaint();
        }

        class MyMouseListener extends MouseInputAdapter
        {
            private Point startPoint;

            public void mousePressed(MouseEvent e)
            {
                startPoint = e.getPoint();
                shape = new Rectangle();
            }

            public void mouseDragged(MouseEvent e)
            {
                int x = Math.min(startPoint.x, e.getX());
                int y = Math.min(startPoint.y, e.getY());
                int width = Math.abs(startPoint.x - e.getX());
                int height = Math.abs(startPoint.y - e.getY());

                shape.setBounds(x, y, width, height);
                repaint();
            }

            public void mouseReleased(MouseEvent e)
            {
                if (shape.width != 0 || shape.height != 0)
                {
                    addRectangle(shape, e.getComponent().getForeground());
                }

                shape = null;
            }
        }

        class ColoredRectangle
        {
            private Color foreground;
            private Rectangle rectangle;

            public ColoredRectangle(Color foreground, Rectangle rectangle)
            {
                this.foreground = foreground;
                this.rectangle = rectangle;
            }

            public Color getForeground()
            {
                return foreground;
            }

            public void setForeground(Color foreground)
            {
                this.foreground = foreground;
            }

            public Rectangle getRectangle()
            {
                return rectangle;
            }
        }
    }
}
