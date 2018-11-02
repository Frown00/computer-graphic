import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControllerPanel extends JPanel {
    JButton loadImageBtn;
    JButton drawSquareBtn;

    ControllerPanel() {
        loadImageBtn = new JButton("Wczytaj obraz");
        drawSquareBtn = new JButton("Kwadrat");
        add(loadImageBtn);
        add(drawSquareBtn);

    }

    public JButton getLoadImageBtn() {
        return loadImageBtn;
    }

    void addChangeImageListener(ActionListener listenChange) {
        loadImageBtn.addActionListener(listenChange);
    }

    void addDrawSquareListener( ActionListener listenDrawSquare) {
        drawSquareBtn.addActionListener(listenDrawSquare);
    }

}
