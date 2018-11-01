import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControllerPanel extends JPanel {
    JButton loadImageBtn;
    JButton squareShape;

    ControllerPanel() {
        loadImageBtn = new JButton("Wczytaj obraz");
        squareShape = new JButton("Kwadrat");
        add(loadImageBtn);
        add(squareShape);



    }

    public JButton getLoadImageBtn() {
        return loadImageBtn;
    }

    void addChangeImageListener(ActionListener listenChange) {
        loadImageBtn.addActionListener(listenChange);
    }


}
