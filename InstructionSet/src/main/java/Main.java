import javax.swing.*;

public class Main {

    public static ComputerMemory memory;
    public static GUI gui;

    public static void main(String[] args) {
        memory = new ComputerMemory(32, 1024);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new GUI();
            }
        });
    }

}