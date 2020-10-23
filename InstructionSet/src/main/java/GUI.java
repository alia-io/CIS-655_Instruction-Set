import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GUI {

    JFrame frame;

    JPanel registers;
    JTextArea registerTextArea;
    String registerText;
    HashMap<Integer, String> registerMap;

    JPanel instructions;
    JLabel instructionLabel;
    JTextField instructionTextField;
    JButton instructionButton;

    JPanel memory;
    JTextArea memoryTextArea;
    String memoryText;
    HashMap<Integer, String> memoryMap;

    public GUI() {
        setFrame();
        setPanels();
    }

    private void setFrame() {
        frame = new JFrame("Instruction Set - Alifa Stith");
        frame.setSize(500, 800);
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
        registerTextArea = new JTextArea(10, 20);
        registerText = "";
        registerMap = new HashMap<Integer, String>(32);
        for (int i = 0; i < 32; i++) {
            registerMap.put(i, "00000000");
            registerText = registerText + "\nR" + i + "\t00000000";
        }
        registerTextArea.setText(registerText);
        registerTextArea.setEditable(false);
        registers.add(registerTextArea);
    }

    private void setNewMemoryContents() {
        memoryTextArea = new JTextArea(10, 20);
        memoryText = "";
        memoryMap = new HashMap<Integer, String>(32);
        for (int i = 0; i < 32; i++) {
            memoryMap.put(i, "00000000");
            memoryText = memoryText + "\n" + i + "\t00000000";
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