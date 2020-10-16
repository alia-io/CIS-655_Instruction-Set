import javax.swing.*;
import java.awt.*;

public class GUI {

    static JFrame frame = new JFrame("Instruction Set");
    static JPanel registers = new JPanel();
    static JPanel instructions = new JPanel();
    static JPanel memory = new JPanel();

    public static void runGUI() {
        setFrame();
        setPanels();
    }

    private static void setFrame() {
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void setPanels() {
        registers.add(new JLabel("Register Panel"));
        instructions.add(new JLabel("Instruction Panel"));
        memory.add(new JLabel("Memory Panel"));
        frame.getContentPane().add(registers, BorderLayout.WEST);
        frame.getContentPane().add(instructions, BorderLayout.CENTER);
        frame.getContentPane().add(memory, BorderLayout.EAST);
    }

}
