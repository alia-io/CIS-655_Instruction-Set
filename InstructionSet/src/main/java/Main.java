import javax.swing.*;

public class Main {

    public static ComputerMemory mainMemory;

    public static void main(String[] args) {
        mainMemory = new ComputerMemory();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ControlAndGUI controlAndgui = new ControlAndGUI();
            }
        });
    }

}