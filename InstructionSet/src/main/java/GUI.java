import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI {

    JFrame frame;
    InstructionToBinaryParser parser;

    JPanel registers;
    JTextArea registerTextArea;
    String registerText;

    JPanel instructions;
    JLabel instructionLabel1;
    JLabel instructionLabel2;
    JTextField instructionTextField;
    JButton instructionButton;

    JPanel memory;
    JTextArea memoryTextArea;
    String memoryText;

    public GUI() {
        parser = new InstructionToBinaryParser();
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
        instructionLabel1 = new JLabel("Type an instruction below to run...");
        instructionLabel2 = new JLabel("");
        instructionTextField = new JTextField(20);
        instructionButton = new JButton("Run Instruction");
        instructions.setLayout(new BoxLayout(instructions, BoxLayout.Y_AXIS));
        instructions.add(instructionLabel1);
        instructions.add(instructionLabel2);
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
        updateRegisterTextArea();
        registerTextArea.setEditable(false);
        registers.add(registerTextArea);
    }

    private void setNewMemoryContents() {
        memoryTextArea = new JTextArea(10, 30);
        updateMemoryTextArea();
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
        String rawResult;
        String displayResult = "";

        if (instruction.equals("")) {
            instructionLabel1.setText("Type an instruction below to run...");
            instructionLabel2.setText("");
            return;
        }

        rawResult = parser.parseInput(instruction);

        if (rawResult == null) {
            instructionLabel1.setText("Invalid instruction! Type an instruction below to run...");
            instructionLabel2.setText("");
        } else {
            instructionLabel1.setText("Instruction: " + instruction);
            if (rawResult.length() == 18 || rawResult.length() == 45) { // opcode (8) - register (5) - register (5) || opcode (8) - register(5) - immediate (32)
                displayResult = rawResult.substring(0, 8) + " " + rawResult.substring(8, 13) + " " + rawResult.substring(13);
            } else if (rawResult.length() == 23 || rawResult.length() == 50) { // opcode (8) - register (5) - register (5) - register (5) || // opcode (8) - register (5) - register (5) - immediate (32)
                displayResult = rawResult.substring(0, 8) + " " + rawResult.substring(8, 13) + " " + rawResult.substring(13, 18) + " " + rawResult.substring(18);
            } else if (rawResult.length() == 77) { // opcode (8) - register(5) - immediate (32) - immediate (32)
                displayResult = rawResult.substring(0, 8) + " " + rawResult.substring(8, 13) + " " + rawResult.substring(13, 45) + " " + rawResult.substring(45);
            } else if (rawResult.length() == 82) { // opcode (8) - register (5) - register (5) - immediate (32) - immediate (32)
                displayResult = rawResult.substring(0, 8) + " " + rawResult.substring(8, 13) + " " + rawResult.substring(13, 18) + " " + rawResult.substring(18, 50) + " " + rawResult.substring(50);
            }
            instructionLabel2.setText("Translation: " + displayResult);
        }

        instructionTextField.setText("");
    }

    public void updateRegisterTextArea() {
        registerText = "";
        for (int i = 0; i < 32; i++) {
            registerText = registerText + "\nR" + i + "\t" + Main.mainMemory.getRegisterContentsByLocation(i);
        }
        registerTextArea.setText(registerText);
    }

    public void updateMemoryTextArea() {
        memoryText = "";
        for (int i = 0; i < 32; i++) {
            memoryText = memoryText + "\n" + i + "\t" + Main.mainMemory.getMemoryContentsByLocation(i);
        }
        memoryTextArea.setText(memoryText);
    }

}