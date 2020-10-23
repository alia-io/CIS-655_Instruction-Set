import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlAndGUI {

    ComputerMemory mainMemory;
    JFrame frame;

    JPanel registers;
    JTextArea registerTextArea;
    String registerText;

    JPanel instructions;
    JLabel instructionLabel;
    JTextField instructionTextField;
    JButton instructionButton;

    JPanel memory;
    JTextArea memoryTextArea;
    String memoryText;

    public ControlAndGUI() {
        mainMemory = new ComputerMemory();
        setFrame();
        setPanels();
    }

    private void setFrame() {
        frame = new JFrame("Instruction Set - Alifa Stith");
        frame.setSize(700, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setPanels() {
        setRegisterPanel();
        setInstructionPanel();
        setMemoryPanel();
    }

    private void setRegisterPanel() {
        registers = new JPanel();
        registers.setLayout(new BoxLayout(registers, BoxLayout.Y_AXIS));
        registers.add(new JLabel("Registers:"));
        setNewRegisterTextArea();
        frame.getContentPane().add(registers, BorderLayout.WEST);
    }

    private void setInstructionPanel() {
        instructions = new JPanel();
        instructionLabel = new JLabel("Type an instruction below to run...");
        instructionTextField = new JTextField(20);
        instructionButton = new JButton("Run Instruction");
        instructions.setLayout(new BoxLayout(instructions, BoxLayout.Y_AXIS));
        instructions.add(instructionLabel);
        setInstructionTextField();
        setInstructionButton();
        frame.getContentPane().add(instructions, BorderLayout.SOUTH);
    }

    private void setMemoryPanel() {
        memory = new JPanel();
        memory.setLayout(new BoxLayout(memory, BoxLayout.Y_AXIS));
        memory.add(new JLabel("Memory:"));
        setNewMemoryContents();
        frame.getContentPane().add(memory, BorderLayout.EAST);
    }

    private void setNewRegisterTextArea() {
        registerTextArea = new JTextArea(10, 30);
        registerText = "";
        for (int i = 0; i < 32; i++) {
            registerText = registerText + "\nR" + i + "\t" + mainMemory.getRegisterContentsByLocation(i);
        }
        registerTextArea.setText(registerText);
        registerTextArea.setEditable(false);
        registers.add(registerTextArea);
    }

    private void setNewMemoryContents() {
        memoryTextArea = new JTextArea(10, 30);
        memoryText = "";
        for (int i = 0; i < 32; i++) {
            memoryText = memoryText + "\n" + i + "\t" + mainMemory.getMemoryContentsByLocation(i);
        }
        memoryTextArea.setText(memoryText);
        memoryTextArea.setEditable(false);
        memory.add(memoryTextArea);
    }

    private void setInstructionTextField() {
        instructionTextField = new JTextField(20);
        instructions.add(instructionTextField);
        instructionTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    runInstruction();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    private void setInstructionButton() {
        instructionButton = new JButton("Run Instruction");
        instructions.add(instructionButton);
        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runInstruction();
            }
        });
    }

    private void runInstruction() {
        String instruction = instructionTextField.getText();
        if (instruction.equals("")) {
            instructionLabel.setText("Type an instruction below to run...");
            return;
        }
        // TODO: run instruction
        instructionLabel.setText("Translated Instruction: "); // TODO: put translated instruction here
        instructionTextField.setText("");
    }

}