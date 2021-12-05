import java.awt.EventQueue;

public class Launcher {
    public static void main(String[] args) {
        ImageEditModel model = new ImageEditModel("image.jpg");
        // FIXME
        EventQueue.invokeLater( () ->
            {
                ImageEditView view = new ImageEditView(model);
                view.pack();
                view.setVisible(true);
            }
        );
    }
}
