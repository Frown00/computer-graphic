import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProgramController {

    private ProgramView programView;
    private ProgramModel programModel;
    final String DEFAULT_PICTURE = "obrazek.jpg";

    public ProgramController(ProgramView programView, ProgramModel programModel) {
        this.programView = programView;
        this.programModel = programModel;

        this.loadImage(DEFAULT_PICTURE);

        programView.controllerPanel.addChangeImageListener(new ChangeImageListener());
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

    public void loadImage(String filepath) {
        try {
            this.programModel.image = ImageIO.read(new File(filepath));
            this.programModel.setDrawingHeight(this.programModel.image.getHeight());
            this.programModel.setDrawingWidth(this.programModel.image.getWidth());
            this.programView.drawingPanel.image = this.programModel.image;

            this.programView.setDrawingPanelSize(this.programModel.getDrawingWidth(),
                    this.programModel.getDrawingHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
