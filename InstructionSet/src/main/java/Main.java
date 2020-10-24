import javax.swing.*;

public class Main {

    public static ComputerMemory mainMemory;
    public static GUI mainGui;

    public static void main(String[] args) {
        mainMemory = new ComputerMemory();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainGui = new GUI();
            }
        });
    }

}