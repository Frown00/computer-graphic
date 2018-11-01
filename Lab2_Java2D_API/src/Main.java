import java.awt.*;

public class Main {
    public static void main(String[] args) {

        ProgramModel programModel = new ProgramModel();
        ProgramView programView = new ProgramView();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProgramController(programView, programModel);
            }
        });


    }
}
