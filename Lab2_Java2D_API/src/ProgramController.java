import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

import static java.awt.Color.*;

public class ProgramController {

    private ProgramView programView;
    private ProgramModel programModel;
    final String DEFAULT_PICTURE = "obrazek.jpg";
    private  boolean isCreatingPolygon;

    public ProgramController(ProgramView programView, ProgramModel programModel) {
        this.programView = programView;
        this.programModel = programModel;
        this.programView.setProgramSizes(this.programModel.frameWidth, this.programModel.frameHeight,
                                         this.programModel.controllerPanelWidth, this.programModel.controllerPanelHeight,
                                         this.programModel.controllerPanelMinWidth, this.programModel.controllerPanelMinHeight,
                                         this.programModel.drawingMinWidth, this.programModel.drawingMinHeight,
                                         this.programModel.splitDividerLocation);
        this.loadImage(DEFAULT_PICTURE);
        this.isCreatingPolygon = false;

        programView.controllerPanel.addChangeImageListener(new ChangeImageListener());
        programView.controllerPanel.addDrawSquareListener(new DrawSquare());
        programView.controllerPanel.addDrawEllipseListener(new DrawEllipse());
        programView.controllerPanel.addDrawPolygonListener(new DrawPolygon());

        programView.controllerPanel.addSaveShapeToXMLListener(new SaveShapeToXmlListener());
        programView.controllerPanel.addDeleteShapeListener(new DeleteShapeListener());

        // Color buttons
        programView.controllerPanel.addRedListener(new RedColor());
        programView.controllerPanel.addBlueListener(new BlueColor());
        programView.controllerPanel.addCyanListener(new CyanColor());
        programView.controllerPanel.addBlackListener(new BlackColor());
        programView.controllerPanel.addGreenListener(new GreenColor());
        programView.controllerPanel.addYellowListener(new YellowColor());
        programView.controllerPanel.addOrangeListener(new OrangeColor());
        programView.controllerPanel.addMagentaListener(new MagentaColor());
    }

    class ChangeImageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final JFileChooser fc = new JFileChooser("D:\\Studia\\Studia\\Semestr 5\\Grafika_komputerowa\\Lab2_Java2D_API\\images");
            int returnVal = fc.showOpenDialog(fc);
            String filePath = null;

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                filePath = fc.getSelectedFile().getAbsolutePath();
                loadImage(filePath);

            } else {
                System.out.println("Nie wybrano pliku");
//                System.exit(1);
            }

        }
    }


    public void addRectangle(Rectangle2D.Double rectangle, Color color)
    {
        //  Add the Rectangle to the List so it can be repainted

        ColoredShape cr = new ColoredShape(color, rectangle);
        cr.saveFragment(this.programModel.image);
        this.programModel.coloredShapes.add( cr );

        this.programView.controllerPanel.addShapeToList( cr );
        this.programView.drawingPanel.setListShapes(this.programModel.coloredShapes);
        this.programView.drawingPanel.repaint();
    }

    public void addEllipse(Ellipse2D.Double ellipse, Color color) {
        ColoredShape cr = new ColoredShape(color, ellipse);
        cr.saveFragment(this.programModel.image);
        this.programModel.coloredShapes.add( cr );

        this.programView.controllerPanel.addShapeToList( cr );
        this.programView.drawingPanel.setListShapes(this.programModel.coloredShapes);
        this.programView.drawingPanel.repaint();
    }

    public void addPolygon(Polygon polygon, Color color) {
        ColoredShape cr = new ColoredShape(color, polygon);
        cr.saveFragment(this.programModel.image);
        this.programModel.coloredShapes.add( cr );

        this.programView.controllerPanel.addShapeToList( cr );
        this.programView.drawingPanel.setListShapes(this.programModel.coloredShapes);
        this.programView.drawingPanel.repaint();
    }

    public void loadImage(String filepath) {
        try {
            this.programModel.image = ImageIO.read(new File(filepath));
            this.programModel.setDrawingHeight(this.programModel.image.getHeight());
            this.programModel.setDrawingWidth(this.programModel.image.getWidth());
            this.programView.drawingPanel.setImage(this.programModel.image);

            this.programView.setDrawingPanelSize(this.programModel.getDrawingWidth(),
                    this.programModel.getDrawingHeight());

            this.programModel.coloredShapes.clear();
            this.programView.controllerPanel.shapesListModel.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO change to drawing square
    class DrawSquare implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!programModel.isDrawing) {
                programView.drawingPanel.addListenMouse(new MouseListener());
                programModel.isDrawing = true;
            }
            programView.drawingPanel.selectedTypeOfShape = programModel.RECTANGLE_SHAPE;
        }
    }

    class DrawEllipse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!programModel.isDrawing) {
                programView.drawingPanel.addListenMouse(new MouseListener());
                programModel.isDrawing = true;
            }
            programView.drawingPanel.selectedTypeOfShape = programModel.ELLIPSE_SHAPE;

        }
    }

    class DrawPolygon implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!programModel.isDrawing) {
                programView.drawingPanel.addListenMouse(new MouseListener());
                programModel.isDrawing = true;
            }
            programView.drawingPanel.selectedTypeOfShape = programModel.POLYGON_SHAPE;
            programView.drawingPanel.shape = new DrawingPolygon();

        }
    }



    class MouseListener extends MouseInputAdapter {
        private Point startPoint;

        public void mousePressed(MouseEvent e)
        {
            startPoint = e.getPoint();


            programView.drawingPanel.shapeColor = programModel.shapeColor;

            switch(programView.drawingPanel.selectedTypeOfShape) {
                case ProgramModel.RECTANGLE_SHAPE : {
                    programView.drawingPanel.shape = new Rectangle2D.Double();
                }
                break;

                case ProgramModel.ELLIPSE_SHAPE: {
                    programView.drawingPanel.shape = new Ellipse2D.Double();
                }
                break;

                case ProgramModel.POLYGON_SHAPE: {

                }
                break;

            }
        }

        public void mouseMoved(MouseEvent e) {
            if(programView.drawingPanel.selectedTypeOfShape == ProgramModel.POLYGON_SHAPE && startPoint != null) {
                if(isCreatingPolygon) {
                    programView.drawingPanel.drawingLine = new Line2D.Double(startPoint.getX(), startPoint.getY(), e.getX(), e.getY());
                    programView.drawingPanel.repaint();
                }

            }
        }

        public void mouseDragged(MouseEvent e)
        {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            switch(programView.drawingPanel.selectedTypeOfShape) {
                case ProgramModel.RECTANGLE_SHAPE : {
                    ((Rectangle2D.Double)(programView.drawingPanel.shape)).setRect(x, y, width, height);
                }
                break;

                case ProgramModel.ELLIPSE_SHAPE: {
                    ((Ellipse2D.Double)(programView.drawingPanel.shape)).setFrame(x, y, width, height);
                }
                break;

                case ProgramModel.POLYGON_SHAPE: {

                }
                break;

            }


            programView.drawingPanel.repaint();
        }

        public void mouseReleased(MouseEvent e)
        {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());

            switch(programView.drawingPanel.selectedTypeOfShape) {
                case ProgramModel.RECTANGLE_SHAPE : {
                    Rectangle2D.Double rectangle = (Rectangle2D.Double) programView.drawingPanel.shape;
                    if (rectangle.width != 0 || rectangle.height != 0)
                    {
                        addRectangle(rectangle, programModel.shapeColor);
                    }
                }
                break;

                case ProgramModel.ELLIPSE_SHAPE: {
                    Ellipse2D.Double ellipse = (Ellipse2D.Double) programView.drawingPanel.shape;
                    if (ellipse.width != 0 || ellipse.height != 0)
                    {
                        addEllipse(ellipse, programModel.shapeColor);
                    }
                }
                break;

                case ProgramModel.POLYGON_SHAPE: {
                    Point2D.Double point = new Point2D.Double(x, y);
                    ((DrawingPolygon) programView.drawingPanel.shape).addPoint(point);
                    isCreatingPolygon = true;
                    boolean isLastPoint = ((DrawingPolygon) programView.drawingPanel.shape).isLastPoint(point);
                    int pointSize = ((DrawingPolygon) programView.drawingPanel.shape).points.size();
                    if(isLastPoint && pointSize > 1) {
                        Polygon polygon = ((DrawingPolygon) programView.drawingPanel.shape).createPolygon();
                        if(polygon != null) {
                            addPolygon(polygon, programModel.shapeColor);

                        } else {
                            System.out.println("To nie jest wielokat!");
                        }
                        programView.drawingPanel.shape = new DrawingPolygon();
                        isCreatingPolygon = false;

                    } else {
                    }
                    programView.drawingPanel.repaint();

                }
                break;

            }

            if(!(programView.drawingPanel.shape instanceof DrawingPolygon))
                programView.drawingPanel.shape = null;

//            programModel.shape = null;
        }
    }


    class SaveShapeToXmlListener implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
//                programView.controllerPanel.shapesListModel.remove(shapeIndex);
                saveShape(shape);
                System.out.println(shape);
                programView.repaint();
            }
        }
    }


    class DeleteShapeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                programView.controllerPanel.shapesListModel.remove(shapeIndex);
                removeShape(shape);

                System.out.println(shape);
                programView.repaint();
            }
            //            deleteShape(programModel.coloredShapes, shape);
//            programModel.coloredShapes.clear();
        }
    }

    void saveShape(ColoredShape shape) {
        shape.saveToXML();
    }

    void removeShape(ColoredShape shape) {
        for(int i = 0; i < programModel.coloredShapes.size(); i++) {
            if(shape.getId().equals(programModel.coloredShapes.get(i).getId())) {
                programModel.coloredShapes.remove(i);
            }
        }
    }

    // COLORS LISTENERS
    class RedColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = RED;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();

                programView.repaint();
            }

        }
    }

    class BlueColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = BLUE;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();

                programView.repaint();

            }
        }
    }

    class CyanColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = CYAN;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();
                programView.repaint();
            }
        }
    }

    class BlackColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = BLACK;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();
                programView.repaint();
            }
        }
    }

    class GreenColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = GREEN;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();

                programView.repaint();
            }
        }
    }

    class YellowColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = YELLOW;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();

                programView.repaint();
            }
        }
    }

    class OrangeColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = ORANGE;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();
                programView.repaint();
            }
        }
    }

    class MagentaColor implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            programModel.shapeColor = MAGENTA;
            int shapeIndex = programView.controllerPanel.shapesList.getSelectedIndex();
            if(shapeIndex >= 0) {
                ColoredShape shape = (ColoredShape)programView.controllerPanel.shapesListModel.getElementAt(shapeIndex);
                shape.setStrokeColor(programModel.shapeColor);
                programView.controllerPanel.shapesList.clearSelection();

                programView.repaint();
            }
        }
    }
}
