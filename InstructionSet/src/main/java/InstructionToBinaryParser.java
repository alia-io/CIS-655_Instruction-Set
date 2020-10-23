public class InstructionToBinaryParser {

    int command; // holds the number id of the command (from doc)

    public String parseInput(String input) {
        String[] splitInput = input.trim().split("\\s+");
        return this.getBinary(splitInput);
    }

    private String getBinary(String[] input) {
        String opcode = this.getOpCode(input[0]);

        return "";
    }

    private String getOpCode(String input) {

        String[] splitInput = input.split("-");

        // validate by length of command
        switch(splitInput.length) {
            case 2:
                return this.getTwoWordOpcode(splitInput);
            case 3:
                return this.getThreeWordOpcode(splitInput);
            case 4:
                return this.getFourWordOpcode(splitInput);
            default: // invalid input
                return null;
        }
    }

    private String getTwoWordOpcode(String[] input) {
        switch(input[0]) {
            case "put": // commands 1, 2, 3
            case "shift": // commands 10, 11
            case "not": // commands 12, 13
            case "and": // commands 14, 15
            case "or": // commands 16, 17
            case "xor": // commands 18, 19
            case "add": // commands 20, 21, 22, 23
            case "sub": // commands 24, 25, 26, 27
            case "mult": // commands 28, 29, 30, 31
            case "div": // commands 32, 33, 34, 35
            case "mod": // commands 36, 37
            case "load": // commands 46, 47, 48
            case "store": // commands 49, 50, 51
            default: // invalid input
                return null;
        }
    }

    private String getThreeWordOpcode(String[] input) {
        switch(input[0]) {
            case "set": // commands 42, 43, 44, 45
            default: // invalid input
                return null;
        }
    }

    private String getFourWordOpcode(String[] input) {
        switch(input[0]) {
            case "convert": // commands 4, 5, 6, 7, 8, 9
            case "set": // commands 38, 39, 40, 41
            default: // invalid input
                return null;
        }
    }

}
