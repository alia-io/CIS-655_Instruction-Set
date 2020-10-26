public class InstructionExecuter {

    // return true if successful, false otherwise
    public boolean executeInstruction(String instruction) {

        // separate by length
        if (instruction.length() == 18) { // 4, 5, 6, 7, 8, 9, 10, 11, 15

        } else if (instruction.length() == 23) { // 17, 21, 23, 24, 25, 27, 28, 29, 31, 32, 33, 35, 36, 37, 39, 41, 42, 43, 45, 46, 47

        } else if (instruction.length() == 45) { // 1, 2, 14

        } else if (instruction.length() == 50) { // 12, 13, 16, 20, 22, 26, 30, 34, 38, 40, 44, 48, 49, 50, 51

        } else if (instruction.length() == 77) { // 3

        }

        return false;
    }

}
