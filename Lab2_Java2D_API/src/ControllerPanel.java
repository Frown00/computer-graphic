import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControllerPanel extends JPanel {
    JButton loadImageBtn;

    JButton drawSquareBtn;
    JButton drawEllipseBtn;
    JButton drawPolygonBtn;

    JButton redColor;
    JButton blueColor;
    JButton cyanColor;
    JButton blackColor;
    JButton greenColor;
    JButton yellowColor;
    JButton orangeColor;
    JButton magentaColor;

    JButton saveBtn;
    JButton deleteBtn;
    public JList<Object> shapesList;
    final DefaultListModel<Object> shapesListModel = new DefaultListModel<>();

    ControllerPanel() {
        JPanel loadImageContainer = new JPanel();
        loadImageBtn = new JButton("Wczytaj obraz");
        loadImageContainer.add(loadImageBtn);
        loadImageContainer.setPreferredSize(new Dimension(200, 30));

        JPanel shapesWithLabel = new JPanel();
        shapesWithLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel shapesContainer = new JPanel();
        shapesContainer.setPreferredSize(new Dimension(170, 80));
        shapesContainer.setLayout(new GridLayout(2, 2, 5, 5));
        JLabel shapesLabel = new JLabel("Sposób zaznaczania");
        drawSquareBtn = new JButton("Kwadrat");
        drawSquareBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        drawEllipseBtn = new JButton("Elipsa");
        drawEllipseBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        drawPolygonBtn = new JButton("Wielokąt");
        drawPolygonBtn.setFont(new Font("Arial", Font.PLAIN, 12));

        shapesContainer.add(drawSquareBtn);
        shapesContainer.add(drawEllipseBtn);
        shapesContainer.add(drawPolygonBtn);

        shapesWithLabel.add(shapesLabel);
        shapesWithLabel.add(shapesContainer);

        JPanel colorsWithLabel = new JPanel();
        colorsWithLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel colorsLabel = new JLabel("Kolor zaznaczenia");
        JPanel colorContainer = new JPanel();
        colorContainer.setPreferredSize(new Dimension(150, 50));
        colorContainer.setLayout(new GridLayout(2, 4, 1, 1));

        redColor = new JButton();
        blueColor = new JButton();
        cyanColor = new JButton();
        blackColor = new JButton();
        greenColor = new JButton();
        yellowColor = new JButton();
        orangeColor = new JButton();
        magentaColor = new JButton();

        redColor.setBackground(Color.RED);
        blueColor.setBackground(Color.BLUE);
        cyanColor.setBackground(Color.CYAN);
        blackColor.setBackground(Color.BLACK);
        greenColor.setBackground(Color.GREEN);
        yellowColor.setBackground(Color.YELLOW);
        orangeColor.setBackground(Color.ORANGE);
        magentaColor.setBackground(Color.MAGENTA);

        colorContainer.add(blackColor);
        colorContainer.add(redColor);
        colorContainer.add(magentaColor);
        colorContainer.add(orangeColor);
        colorContainer.add(yellowColor);
        colorContainer.add(greenColor);
        colorContainer.add(blueColor);
        colorContainer.add(cyanColor);

        colorsWithLabel.add(colorsLabel);
        colorsWithLabel.add(colorContainer);

        JPanel manageButtonContainer = new JPanel();
        saveBtn = new JButton("Zapisz");
        deleteBtn = new JButton("Usuń");
        manageButtonContainer.add(saveBtn);
        manageButtonContainer.add(deleteBtn);

        JPanel markedShapes = new JPanel();
        markedShapes.setPreferredSize(new Dimension(150, -20));
        JLabel markedShapesLabel = new JLabel("Lista zaznaczonych: ");
        shapesList = new JList<>(shapesListModel);
        shapesList.setVisibleRowCount(10);
        JScrollPane scrollList = new JScrollPane(shapesList);
        markedShapes.add(markedShapesLabel);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(170, 700));

        container.add(loadImageContainer);
        container.add(shapesWithLabel);
        container.add(colorsWithLabel);
        container.add(markedShapes);
        container.add(scrollList);
        container.add(manageButtonContainer);

        add(container);
    }

    public JButton getLoadImageBtn() {
        return loadImageBtn;
    }

    void addShapeToList(Object shape) {
        shapesListModel.addElement(shape);
    }

    void addChangeImageListener(ActionListener listenChange) {
        loadImageBtn.addActionListener(listenChange);
    }

    void addDrawSquareListener( ActionListener listenDrawSquare) {
        drawSquareBtn.addActionListener(listenDrawSquare);
    }

    void addDrawEllipseListener(ActionListener listener) {
        drawEllipseBtn.addActionListener(listener);
    }

    void addDrawPolygonListener(ActionListener listener) {
        drawPolygonBtn.addActionListener(listener);
    }


    void addSaveShapeToXMLListener( ActionListener listener) {
        saveBtn.addActionListener(listener);
    }

    void addDeleteShapeListener( ActionListener listener) {
        deleteBtn.addActionListener(listener);
    }

    // Colors Listeners
    void addRedListener(ActionListener listener) {
        redColor.addActionListener(listener);
    }

    void addBlueListener(ActionListener listener) {
        blueColor.addActionListener(listener);
    }

    void addCyanListener(ActionListener listener) {
        cyanColor.addActionListener(listener);
    }

    void addBlackListener(ActionListener listener) {
        blackColor.addActionListener(listener);
    }

    void addGreenListener(ActionListener listener) {
        greenColor.addActionListener(listener);
    }

    void addYellowListener(ActionListener listener) {
        yellowColor.addActionListener(listener);
    }

    void addOrangeListener(ActionListener listener) {
        orangeColor.addActionListener(listener);
    }

    void addMagentaListener(ActionListener listener) {
        magentaColor.addActionListener(listener);
    }
}
