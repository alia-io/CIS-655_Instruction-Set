public class InstructionExecuter {

    private boolean executed; // set to true when instruction is successfully completed

    public InstructionExecuter() {
        this.executed = false;
    }

    // return true if successful, false otherwise
    public boolean executeInstruction(String instruction) {

        this.executed = false;

        switch (this.getOpcodeBits0To4(instruction.substring(0, 5))) {
            case 0: // put - 1, 2, 3
                this.executePutInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13));
                break;
            case 1: // copy - 4, 5
                this.executeCopyInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13));
                break;
            case 2: // convert - 6, 7, 8, 9, 10, 11
                this.executeConvertInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13));
                break;
            case 3: // shift-left - 12
                this.executeShiftLeftInstruction(instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 4: // shift-right - 13
                this.executeShiftRightInstruction(instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 5: // not - 14, 15
                this.executeNotInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13));
                break;
            case 6: // and - 16, 17
                this.executeAndInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 7: // or - 18, 19
                this.executeOrInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 8: // xor - 20, 21
                this.executeXorInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 9: // add - 22, 23, 24, 25
                this.executeAddInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 10: // sub - 26, 27, 28, 29
                this.executeSubInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 11: // mult - 30, 31, 32, 33
                this.executeMultInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 12: // div - 34, 35, 36, 37
                this.executeDivInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 13: // mod - 38, 39
                this.executeModInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 14: // set-less-than - 40, 41, 42, 43
                this.executeSetLessThanInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 15: // set-equal - 44, 45, 46, 47
                this.executeSetEqualInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 16: // load - 48, 49
                this.executeLoadInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
            case 17: // store - 50, 51
                this.executeStoreInstruction(instruction.substring(0, 8), instruction.substring(8, 13), instruction.substring(13, 18), instruction.substring(18));
                break;
        }

        return this.executed;
    }

    // instructions 1, 2, 3
    private void executePutInstruction(String opcode, String register, String immediate) {
        if (this.getOpcodeBit7(opcode.substring(7)) == 0) {
            switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
                case 1:
                    if (immediate.length() == 32) { this.putInt(register, immediate); }
                    break;
                case 2:
                    if (immediate.length() == 32) { this.putFloat(register, immediate); }
                    break;
                case 3:
                    if (immediate.length() == 64) { this.putDouble(register, immediate); }
                    break;
            }
        }
    }

    // instructions 4, 5
    private void executeCopyInstruction(String opcode, String register1, String register2) {
        if ((this.getOpcodeBits5And6(opcode.substring(5, 7)) == 0) && (register2.length() == 5)) {
            switch (this.getOpcodeBit7(opcode.substring(7))) {
                case 0:
                    this.copySingle(register1, register2);
                    break;
                case 1:
                    this.copyDouble(register1, register2);
                    break;
            }
        }
    }

    // instructions 6, 7, 8, 9, 10, 11
    private void executeConvertInstruction(String opcode, String register1, String register2) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        if (register2.length() == 5) {
            switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
                case 1: // int-to-
                    if (lastBit == 0) { // -float
                        this.convertIntToFloat(register1, register2);
                    } else if (lastBit == 1) { // -double
                        this.convertIntToDouble(register1, register2);
                    }
                    break;
                case 2: // float-to-
                    if (lastBit == 0) { // -int
                        this.convertFloatToInt(register1, register2);
                    } else if (lastBit == 1) { // -double
                        this.convertFloatToDouble(register1, register2);
                    }
                    break;
                case 3: // double-to-
                    if (lastBit == 0) { // -int
                        this.convertDoubleToInt(register1, register2);
                    } else if (lastBit == 1) { // -float
                        this.convertDoubleToFloat(register1, register2);
                    }
                    break;
            }
        }
    }

    // instruction 12
    private void executeShiftLeftInstruction(String register1, String register2, String immediate) {
        if (immediate.length() == 32) {
            this.shiftLeft(register1, register2, immediate);
        }
    }

    // instruction 13
    private void executeShiftRightInstruction(String register1, String register2, String immediate) {
        if (immediate.length() == 32) {
            this.shiftRight(register1, register2, immediate);
        }
    }

    // instructions 14, 15
    private void executeNotInstruction(String opcode, String register1, String register2OrImmediate) {
        if (this.getOpcodeBit7(opcode.substring(7)) == 0) {
            switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
                case 0:
                    if (register2OrImmediate.length() == 32) { this.notImm(register1, register2OrImmediate); }
                    break;
                case 1:
                    if (register2OrImmediate.length() == 5) { this.notInt(register1, register2OrImmediate); }
                    break;
            }
        }
    }

    // instructions 16, 17
    private void executeAndInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.andImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.andInt(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 18, 19
    private void executeOrInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.orImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.orInt(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 20, 21
    private void executeXorInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.xorImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.xorInt(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 22, 23, 24, 25
    private void executeAddInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.addImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.addInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.addFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.addDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 26, 27, 28, 29
    private void executeSubInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if (register3OrImmediate.length() == 32) {
                    if (lastBit == 0) {
                        this.subImm(register1, register2, register3OrImmediate, true);
                    } else if (lastBit == 1) {
                        this.subImm(register1, register2, register3OrImmediate, false);
                    }
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.subInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.subFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.subDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 30, 31, 32, 33
    private void executeMultInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.multImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.multInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.multFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.multDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 34, 35, 36, 37
    private void executeDivInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if (register3OrImmediate.length() == 32) {
                    if (lastBit == 0) {
                        this.divImm(register1, register2, register3OrImmediate, true);
                    } else if (lastBit == 1) {
                        this.divImm(register1, register2, register3OrImmediate, false);
                    }
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.divInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.divFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.divDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 38, 39
    private void executeModInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if (register3OrImmediate.length() == 32) {
                    if (lastBit == 0) {
                        this.modImm(register1, register2, register3OrImmediate, true);
                    } else if (lastBit == 1) {
                        this.modImm(register1, register2, register3OrImmediate, false);
                    }
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.modInt(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 40, 41, 42, 43
    private void executeSetLessThanInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if (register3OrImmediate.length() == 32) {
                    if (lastBit == 0) {
                        this.setLessThanImm(register1, register2, register3OrImmediate, true);
                    } else if (lastBit == 1) {
                        this.setLessThanImm(register1, register2, register3OrImmediate, false);
                    }
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setLessThanInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setLessThanFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setLessThanDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 44, 45, 46, 47
    private void executeSetEqualInstruction(String opcode, String register1, String register2, String register3OrImmediate) {
        int lastBit = this.getOpcodeBit7(opcode.substring(7));
        switch (this.getOpcodeBits5And6(opcode.substring(5, 7))) {
            case 0:
                if ((lastBit == 0 || lastBit == 1) && register3OrImmediate.length() == 32) {
                    this.setEqualImm(register1, register2, register3OrImmediate);
                }
                break;
            case 1:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setEqualInt(register1, register2, register3OrImmediate);
                }
                break;
            case 2:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setEqualFloat(register1, register2, register3OrImmediate);
                }
                break;
            case 3:
                if (lastBit == 0 && register3OrImmediate.length() == 5) {
                    this.setEqualDouble(register1, register2, register3OrImmediate);
                }
                break;
        }
    }

    // instructions 48, 49
    private void executeLoadInstruction(String opcode, String register1, String register2, String immediate) {
        if ((this.getOpcodeBits5And6(opcode.substring(5, 7)) == 0) && (immediate.length() == 32)) {
            switch (this.getOpcodeBit7(opcode.substring(7))) {
                case 0:
                    this.loadSingle(register1, register2, immediate);
                    break;
                case 1:
                    this.loadDouble(register1, register2, immediate);
                    break;
            }
        }
    }

    // instructions 50, 51
    private void executeStoreInstruction(String opcode, String register1, String register2, String immediate) {
        if ((this.getOpcodeBits5And6(opcode.substring(5, 7)) == 0) && (immediate.length() == 32)) {
            switch (this.getOpcodeBit7(opcode.substring(7))) {
                case 0:
                    this.storeSingle(register1, register2, immediate);
                    break;
                case 1:
                    this.storeDouble(register1, register2, immediate);
                    break;
            }
        }
    }

    // instruction #1
    private void putInt(String register, String immediate) {
        int registerNumber = this.getSingleRegister(register);
        if (registerNumber != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber, immediate);
            this.executed = true;
        }
    }

    // instruction #2
    private void putFloat(String register, String immediate) {
        this.putInt(register, immediate);
    }

    // instruction #3
    private void putDouble(String register, String immediate) {

        int register1 = this.getFirstDoubleRegister(register);
        int register2 = this.getSecondDoubleRegister(register1);

        if (register1 != -1 && register2 != -1) {
            Main.memory.setRegisterContentsByLocation(register1, immediate.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register2, immediate.substring(32));
            this.executed = true;
        }
    }

    // instruction #4
    private void copySingle(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1, Main.memory.getRegisterContentsByLocation(registerNumber2));
            this.executed = true;
        }
    }

    // instruction #5
    private void copyDouble(String register1, String register2) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);

        if (register1Part1 != -1 && register1Part2 != -1 && register2Part1 != -1 && register2Part2 != -1) {
            Main.memory.setRegisterContentsByLocation(register1Part1, Main.memory.getRegisterContentsByLocation(register2Part1));
            Main.memory.setRegisterContentsByLocation(register1Part2, Main.memory.getRegisterContentsByLocation(register2Part2));
            this.executed = true;
        }
    }

    // instruction #6
    private void convertIntToFloat(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(
                            (float) this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))));
            this.executed = true;
        }
    }

    // instruction #7
    private void convertIntToDouble(String register1, String register2) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int registerNumber2 = this.getSingleRegister(register2);
        String valueToSave;

        if (register1Part1 != -1 && register1Part2 != -1 && registerNumber2 != -1) {
            valueToSave = this.convertDecimalTo64BitBinaryDouble(
                    (double) this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, valueToSave.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, valueToSave.substring(32));
            this.executed = true;
        }
    }

    // instruction #8
    private void convertFloatToInt(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            (int) this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))));
            this.executed = true;
        }
    }

    // instruction #9
    private void convertFloatToDouble(String register1, String register2) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int registerNumber2 = this.getSingleRegister(register2);
        String valueToSave;

        if (register1Part1 != -1 && register1Part2 != -1 && registerNumber2 != -1) {
            valueToSave = this.convertDecimalTo64BitBinaryDouble(
                    (double) this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, valueToSave.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, valueToSave.substring(32));
            this.executed = true;
        }
    }

    // instruction #10
    private void convertDoubleToInt(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);

        if (registerNumber1 != -1 && register2Part1 != -1 && register2Part2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            (int) this.convert64BitBinaryDoubleToDecimal(Main.memory.getRegisterContentsByLocation(register2Part1)
                                    + Main.memory.getRegisterContentsByLocation(register2Part2))));
            this.executed = true;
        }
    }

    // instruction #11
    private void convertDoubleToFloat(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);

        if (registerNumber1 != -1 && register2Part1 != -1 && register2Part2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(
                            (float) this.convert64BitBinaryDoubleToDecimal(Main.memory.getMemoryContentsByLocation(register2Part1)
                                    + Main.memory.getRegisterContentsByLocation(register2Part2))));
            this.executed = true;
        }
    }

    // instruction #12
    private void shiftLeft(String register1, String register2, String immediate) {

        int shiftAmount = this.convertBinaryToDecimalSigned(immediate);
        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String binaryValueToShift;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            binaryValueToShift = Main.memory.getRegisterContentsByLocation(registerNumber2);
            if (shiftAmount <= binaryValueToShift.length()) {
                binaryValueToShift = binaryValueToShift.substring(shiftAmount);
                while (binaryValueToShift.length() < 32) {
                    binaryValueToShift = binaryValueToShift + "0";
                }
                Main.memory.setRegisterContentsByLocation(registerNumber1, binaryValueToShift);
                this.executed = true;
            }
        }
    }

    // instruction #13
    private void shiftRight(String register1, String register2, String immediate) {

        int shiftAmount = this.convertBinaryToDecimalSigned(immediate);
        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String binaryValueToShift;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            binaryValueToShift = Main.memory.getRegisterContentsByLocation(registerNumber2);
            if (shiftAmount <= binaryValueToShift.length()) {
                binaryValueToShift = binaryValueToShift.substring(0, binaryValueToShift.length() - shiftAmount);
                while (binaryValueToShift.length() < 32) {
                    binaryValueToShift = "0" + binaryValueToShift;
                }
                Main.memory.setRegisterContentsByLocation(registerNumber1, binaryValueToShift);
                this.executed = true;
            }
        }

    }

    // instruction #14
    private void notImm(String register, String immediate) {

        int registerNumber = this.getSingleRegister(register);
        String value = "";

        if (registerNumber != -1) {
            for (int i = 0; i < immediate.length(); i++) {
                if (immediate.charAt(i) == '0') {
                    value = value + "1";
                } else {
                    value = value + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber, value);
            this.executed = true;
        }
    }

    // instruction #15
    private void notInt(String register1, String register2) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String oldValue;
        String newValue = "";

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            oldValue = Main.memory.getRegisterContentsByLocation(registerNumber2);
            for (int i = 0; i < oldValue.length(); i++) {
                if (oldValue.charAt(i) == '0') {
                    newValue = newValue + '1';
                } else {
                    newValue = newValue + '0';
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, newValue);
            this.executed = true;
        }
    }

    // instruction #16
    private void andImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String valueFromRegister2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            valueFromRegister2 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            for (int i = 0; i < immediate.length(); i++) {
                if (immediate.charAt(i) == '1' && valueFromRegister2.charAt(i) == '1') {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #17
    private void andInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);
        String value1;
        String value2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            value1 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            value2 = Main.memory.getRegisterContentsByLocation(registerNumber3);
            for (int i = 0; i < value1.length(); i++) {
                if (value1.charAt(i) == '1' && value2.charAt(i) == '1') {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #18
    private void orImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String valueFromRegister2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            valueFromRegister2 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            for (int i = 0; i < immediate.length(); i++) {
                if (immediate.charAt(i) == '1' || valueFromRegister2.charAt(i) == '1') {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #19
    private void orInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);
        String value1;
        String value2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            value1 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            value2 = Main.memory.getRegisterContentsByLocation(registerNumber3);
            for (int i = 0; i < value1.length(); i++) {
                if (value1.charAt(i) == '1' || value2.charAt(i) == '1') {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #20
    private void xorImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        String valueFromRegister2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            valueFromRegister2 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            for (int i = 0; i < immediate.length(); i++) {
                if ((immediate.charAt(i) == '1' && valueFromRegister2.charAt(i) == '0')
                        || (immediate.charAt(i) == '0' && valueFromRegister2.charAt(i) == '1')) {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #21
    private void xorInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);
        String value1;
        String value2;
        String result = "";

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            value1 = Main.memory.getRegisterContentsByLocation(registerNumber2);
            value2 = Main.memory.getRegisterContentsByLocation(registerNumber3);
            for (int i = 0; i < value1.length(); i++) {
                if ((value1.charAt(i) == '1' && value2.charAt(i) == '0')
                        || (value1.charAt(i) == '0' && value2.charAt(i) == '1')) {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
            Main.memory.setRegisterContentsByLocation(registerNumber1, result);
            this.executed = true;
        }
    }

    // instruction #22
    private void addImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(this.convertBinaryToDecimalSigned(immediate)
                            + this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))));
            this.executed = true;
        }
    }

    // instruction #23
    private void addInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(this.convertBinaryToDecimalSigned(Main.memory.getMemoryContentsByLocation(registerNumber2))
                            + this.convertBinaryToDecimalSigned(Main.memory.getMemoryContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #24
    private void addFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                            + this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #25
    private void addDouble(String register1, String register2, String register3) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);
        String result;

        if (register1Part1 != -1 && register1Part2 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            result = this.convertDecimalTo64BitBinaryDouble(
                    this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                    + this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, result.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, result.substring(32));
            this.executed = true;
        }
    }

    // instruction #26
    private void subImm(String register1, String register2, String immediate, boolean registerFirst) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int register2Value;
        int immediateValue;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            register2Value = this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            immediateValue = this.convertBinaryToDecimalSigned(immediate);
            if (registerFirst) {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(register2Value - immediateValue));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(immediateValue - register2Value));
            }
            this.executed = true;
        }
    }

    // instruction #27
    private void subInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    - this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #28
    private void subFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(
                            this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    - this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #29
    private void subDouble(String register1, String register2, String register3) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);

        String result;

        if (register1Part1 != -1 && register1Part2 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            result = this.convertDecimalTo64BitBinaryDouble(
                    this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                            - this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, result.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, result.substring(32));
            this.executed = true;
        }
    }

    // instruction #30
    private void multImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(this.convertBinaryToDecimalSigned(immediate)
                            * this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))));
            this.executed = true;
        }
    }

    // instruction #31
    private void multInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    * this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #32
    private void multFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(
                            this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    * this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #33
    private void multDouble(String register1, String register2, String register3) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);

        String result;

        if (register1Part1 != -1 && register1Part2 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            result = this.convertDecimalTo64BitBinaryDouble(
                    this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                            * this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, result.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, result.substring(32));
            this.executed = true;
        }
    }

    // instruction #34
    private void divImm(String register1, String register2, String immediate, boolean registerFirst) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int register2Value;
        int immediateValue;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            register2Value = this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            immediateValue = this.convertBinaryToDecimalSigned(immediate);
            if (registerFirst) {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(register2Value / immediateValue));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(immediateValue / register2Value));
            }
            this.executed = true;
        }
    }

    // instruction #35
    private void divInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    / this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #36
    private void divFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryFloat(
                            this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    / this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #37
    private void divDouble(String register1, String register2, String register3) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);

        String result;

        if (register1Part1 != -1 && register1Part2 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            result = this.convertDecimalTo64BitBinaryDouble(
                    this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                            / this.convert64BitBinaryDoubleToDecimal(
                            Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2)));
            Main.memory.setRegisterContentsByLocation(register1Part1, result.substring(0, 32));
            Main.memory.setRegisterContentsByLocation(register1Part2, result.substring(32));
            this.executed = true;
        }
    }

    // instruction #38
    private void modImm(String register1, String register2, String immediate, boolean registerFirst) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int register2Value;
        int immediateValue;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            register2Value = this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            immediateValue = this.convertBinaryToDecimalSigned(immediate);
            if (registerFirst) {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(register2Value % immediateValue));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        this.convertDecimalTo32BitBinaryIntegerSigned(immediateValue % register2Value));
            }
            this.executed = true;
        }
    }

    // instruction #39
    private void modInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1 && registerNumber3 != -1) {
            Main.memory.setRegisterContentsByLocation(registerNumber1,
                    this.convertDecimalTo32BitBinaryIntegerSigned(
                            this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                                    % this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))));
            this.executed = true;
        }
    }

    // instruction #40
    private void setLessThanImm(String register1, String register2, String immediate, boolean registerFirst) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int register2Value;
        int immediateValue;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            register2Value = this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            immediateValue = this.convertBinaryToDecimalSigned(immediate);
            if (registerFirst) {
                if (register2Value < immediateValue) {
                    Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
                } else {
                    Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
                }
            } else {
                if (immediateValue < register2Value) {
                    Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
                } else {
                    Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
                }
            }
            this.executed = true;
        }
    }

    // instruction #41
    private void setLessThanInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            if (this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                    < this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #42
    private void setLessThanFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            if (this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                    < this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #43
    private void setLessThanDouble(String register1, String register2, String register3) {

        int registerNumber1 = this.getFirstDoubleRegister(register1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);

        if (registerNumber1 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            if (this.convert64BitBinaryDoubleToDecimal(Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                    < this.convert64BitBinaryDoubleToDecimal(Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #44
    private void setEqualImm(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            if (this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                    == this.convertBinaryToDecimalSigned(immediate)) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #45
    private void setEqualInt(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            if (this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber2))
                    == this.convertBinaryToDecimalSigned(Main.memory.getRegisterContentsByLocation(registerNumber3))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #46
    private void setEqualFloat(String register1, String register2, String register3) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int registerNumber3 = this.getSingleRegister(register3);

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            if (this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber2))
                    == this.convert32BitBinaryFloatToDecimal(Main.memory.getRegisterContentsByLocation(registerNumber3))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #47
    private void setEqualDouble(String register1, String register2, String register3) {

        int registerNumber1 = this.getFirstDoubleRegister(register1);
        int register2Part1 = this.getFirstDoubleRegister(register2);
        int register2Part2 = this.getSecondDoubleRegister(register2Part1);
        int register3Part1 = this.getFirstDoubleRegister(register3);
        int register3Part2 = this.getSecondDoubleRegister(register3Part1);

        if (registerNumber1 != -1 && register2Part1 != -1 && register2Part2 != -1 && register3Part1 != -1 && register3Part2 != -1) {
            if (this.convert64BitBinaryDoubleToDecimal(Main.memory.getRegisterContentsByLocation(register2Part1) + Main.memory.getRegisterContentsByLocation(register2Part2))
                    < this.convert64BitBinaryDoubleToDecimal(Main.memory.getRegisterContentsByLocation(register3Part1) + Main.memory.getRegisterContentsByLocation(register3Part2))) {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(1, 32));
            } else {
                Main.memory.setRegisterContentsByLocation(registerNumber1, this.convertDecimalToBinaryIntegerUnsigned(0, 32));
            }
            this.executed = true;
        }
    }

    // instruction #48
    private void loadSingle(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int shiftAmount;
        int addressFromRegister;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            shiftAmount = this.convertBinaryToDecimalSigned(immediate);
            addressFromRegister = this.convertBinaryToDecimalUnsigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            if (shiftAmount % 4 == 0 && addressFromRegister % 4 == 0) {
                Main.memory.setRegisterContentsByLocation(registerNumber1,
                        Main.memory.getMemoryContentsByLocation((shiftAmount + addressFromRegister) / 4));
                this.executed = true;
            }
        }
    }

    // instruction #49
    private void loadDouble(String register1, String register2, String immediate) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int registerNumber2 = this.getFirstDoubleRegister(register2);
        int shiftAmount;
        int addressFromRegister;
        int fullAddress;

        if (register1Part1 != -1 && register1Part2 != -1 && registerNumber2 != -1) {
            shiftAmount = this.convertBinaryToDecimalSigned(immediate);
            addressFromRegister = this.convertBinaryToDecimalUnsigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            if (shiftAmount % 4 == 0 && addressFromRegister == 0) {
                fullAddress = (shiftAmount + addressFromRegister) / 4;
                Main.memory.setRegisterContentsByLocation(register1Part1, Main.memory.getMemoryContentsByLocation(fullAddress));
                Main.memory.setRegisterContentsByLocation(register1Part2, Main.memory.getMemoryContentsByLocation(fullAddress + 1));
                this.executed = true;
            }
        }
    }

    // instruction #50
    private void storeSingle(String register1, String register2, String immediate) {

        int registerNumber1 = this.getSingleRegister(register1);
        int registerNumber2 = this.getSingleRegister(register2);
        int shiftAmount;
        int addressFromRegister;

        if (registerNumber1 != -1 && registerNumber2 != -1) {
            shiftAmount = this.convertBinaryToDecimalSigned(immediate);
            addressFromRegister = this.convertBinaryToDecimalUnsigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            if (shiftAmount % 4 == 0 && addressFromRegister % 4 == 0) {
                Main.memory.setMemoryContentsByLocation((shiftAmount + addressFromRegister) / 4,
                        Main.memory.getRegisterContentsByLocation(registerNumber1));
                this.executed = true;
            }
        }
    }

    // instruction #51
    private void storeDouble(String register1, String register2, String immediate) {

        int register1Part1 = this.getFirstDoubleRegister(register1);
        int register1Part2 = this.getSecondDoubleRegister(register1Part1);
        int registerNumber2 = this.getFirstDoubleRegister(register2);
        int shiftAmount;
        int addressFromRegister;
        int fullAddress;

        if (register1Part1 != -1 && register1Part2 != -1 && registerNumber2 != -1) {
            shiftAmount = this.convertBinaryToDecimalSigned(immediate);
            addressFromRegister = this.convertBinaryToDecimalUnsigned(Main.memory.getRegisterContentsByLocation(registerNumber2));
            if (shiftAmount % 4 == 0 && addressFromRegister == 0) {
                fullAddress = (shiftAmount + addressFromRegister) / 4;
                Main.memory.setMemoryContentsByLocation(fullAddress, Main.memory.getRegisterContentsByLocation(register1Part1));
                Main.memory.setMemoryContentsByLocation(fullAddress + 1, Main.memory.getRegisterContentsByLocation(register1Part2));
                this.executed = true;
            }
        }
    }

    /* one of 18 commands (0-17):
        (0) put, (1) copy, (2) convert, (3) shift-left, (4) shift-right, (5) not,
        (6) and, (7) or, (8) xor, (9) add, (10) sub, (11) mult,
        (12) div, (13) mod, (14) set-less-than, (15) set-equal, (16) load, (17) store
    */
    private int getOpcodeBits0To4(String fiveBitOpcode) {
        return this.convertBinaryToDecimalUnsigned(fiveBitOpcode);
    }

    private int getOpcodeBits5And6(String twoBitOpcode) {
        return this.convertBinaryToDecimalUnsigned(twoBitOpcode);
    }

    private int getOpcodeBit7(String oneBitOpcode) {
        return this.convertBinaryToDecimalUnsigned(oneBitOpcode);
    }

    private int getSingleRegister(String binaryRegister) {
        int decimalRegister = this.convertBinaryToDecimalUnsigned(binaryRegister);
        if (decimalRegister < 0 || decimalRegister > 31) {
            return -1;
        }
        return decimalRegister;
    }

    private int getFirstDoubleRegister(String binaryRegister) {
        int decimalRegister = this.convertBinaryToDecimalUnsigned(binaryRegister);
        if (decimalRegister < 0 || decimalRegister > 30) {
            return -1;
        }
        return decimalRegister;
    }

    private int getSecondDoubleRegister(int firstDoubleRegister) {
        int secondDoubleRegister = firstDoubleRegister + 1;
        if (secondDoubleRegister < 1 || secondDoubleRegister > 31) {
            return -1;
        }
        return secondDoubleRegister;
    }

    private int convertBinaryToDecimalUnsigned(String binaryNumber) {

        int decimalNumber = 0;
        int powerOfTwo = 1;

        for (int i = binaryNumber.length() - 1; i >= 0; i--) {
            if (binaryNumber.charAt(i) == '1') {
                decimalNumber = decimalNumber + powerOfTwo;
            }
            powerOfTwo = powerOfTwo * 2;
        }

        return decimalNumber;
    }

    private int convertBinaryToDecimalSigned(String inputBinaryNumber) {

        String invertedBinaryNumber = "";
        String binaryNumber = "";
        int decimalNumber = 0;
        int powerOfTwo = 1;

        if (inputBinaryNumber.charAt(0) == '1') { // negative
            for (int i = inputBinaryNumber.length() - 1; i >= 0; i--) {
                if (inputBinaryNumber.charAt(i) == '0') {
                    invertedBinaryNumber = "1" + invertedBinaryNumber;
                } else {
                    invertedBinaryNumber = inputBinaryNumber.substring(0, i) + "0" + invertedBinaryNumber;
                    break;
                }
            }
            for (int i = 0; i < invertedBinaryNumber.length(); i++) {
                if (invertedBinaryNumber.charAt(i) == '0') {
                    binaryNumber = binaryNumber + "1";
                } else {
                    binaryNumber = binaryNumber + "0";
                }
            }
        } else {
            binaryNumber = inputBinaryNumber;
        }

        for (int i = binaryNumber.length() - 1; i >= 0; i--) {
            if (binaryNumber.charAt(i) == '1') {
                decimalNumber = decimalNumber + powerOfTwo;
            }
            powerOfTwo = powerOfTwo * 2;
        }

        if (inputBinaryNumber.charAt(0) == '1') {
            decimalNumber = decimalNumber * -1;
        }

        return decimalNumber;
    }

    private String convertDecimalToBinaryIntegerUnsigned(int decimalNumber, int binaryDigits) {

        String binaryNumber = "";
        int powerOfTwo = (int) Math.pow(2, binaryDigits - 1);

        while (powerOfTwo > 0) {
            if (powerOfTwo <= decimalNumber) {
                binaryNumber = binaryNumber + "1";
                decimalNumber = decimalNumber - powerOfTwo;
            } else {
                binaryNumber = binaryNumber + "0";
            }
            powerOfTwo = powerOfTwo / 2;
        }

        return binaryNumber;
    }

    private String convertDecimalTo32BitBinaryIntegerSigned(int decimalNumber) {

        String binaryNumber = "";
        String invertedBinaryNumber = "";
        String twosComplementBinaryNumber = "";
        double powerOfTwo = Math.floor(Math.pow(2, 31));
        boolean negative = false;

        if (decimalNumber < 0) {
            negative = true;
        }

        decimalNumber = Math.abs(decimalNumber);

        while (powerOfTwo > 0) {
            if (powerOfTwo <= decimalNumber) {
                binaryNumber = binaryNumber + "1";
                decimalNumber = (int) (decimalNumber - powerOfTwo);
            } else {
                binaryNumber = binaryNumber + "0";
            }
            powerOfTwo = Math.floor(powerOfTwo / 2);
        }

        if (negative) {
            for (int i = 0; i < binaryNumber.length(); i++) {
                if (binaryNumber.charAt(i) == '0') {
                    invertedBinaryNumber = invertedBinaryNumber + "1";
                } else {
                    invertedBinaryNumber = invertedBinaryNumber + "0";
                }
            }
            for (int i = invertedBinaryNumber.length() - 1; i >= 0; i--) {
                if (invertedBinaryNumber.charAt(i) == '0') {
                    return invertedBinaryNumber.substring(0, i) + "1" + twosComplementBinaryNumber;
                } else {
                    twosComplementBinaryNumber = "0" + twosComplementBinaryNumber;
                }
            }
        }

        return binaryNumber;
    }

    private float convert32BitBinaryFloatToDecimal(String floatNumber) {

        String signBinary = floatNumber.substring(0, 1);
        String exponentBinary = floatNumber.substring(1, 9);
        String mantissaBinary = floatNumber.substring(9);

        int exponent = this.convertBinaryToDecimalSigned(exponentBinary) - 127;

        String integralBinary = "";
        String fractionalBinary = "";

        int integral;
        float fractional;

        boolean foundMantissa = false;

        for (int i = mantissaBinary.length() - 1; i >= 0; i--) {
            if (mantissaBinary.charAt(i) == '1') {
                mantissaBinary = mantissaBinary.substring(0, i + 1);
                foundMantissa = true;
            }
        }

        if (!foundMantissa) {
            mantissaBinary = "";
        }

        if (exponent > 0) {
            integralBinary = "1" + mantissaBinary.substring(0, exponent);
            fractionalBinary = mantissaBinary.substring(exponent);
        } else if (exponent < 0) {
            exponent = Math.abs(exponent);
            integralBinary = "0";
            fractionalBinary = "1" + mantissaBinary;
            for (int i = 0; i < exponent; i++) {
                fractionalBinary = "0" + fractionalBinary;
            }
        } else {
            integralBinary = "1";
            fractionalBinary = mantissaBinary;
        }

        integral = this.convertBinaryToIntegral(integralBinary);
        fractional = this.convertBinaryToFractionalFloat(fractionalBinary);

        if (signBinary == "1") {
            integral = integral * (-1);
        }

        return integral + fractional;
    }

    // IEEE format for single-precision floating-point
    // Source: http://sandbox.mc.edu/~bennet/cs110/flt/dtof.html
    private String convertDecimalTo32BitBinaryFloat(float decimalNumber) {

        String sign;
        String exponent;
        String mantissa;

        int integral;
        float fractional;

        String integralBinary = "";
        String fractionalBinary = "";

        if (decimalNumber < 0) {
            sign = "1";
        } else {
            sign = "0";
        }

        integral = (int) Math.abs(decimalNumber);
        fractional = Math.abs(decimalNumber) - integral;

        integralBinary = this.convertIntegralToBinary(integral);
        fractionalBinary = this.convertFractionalToBinary(fractional);

        if (integral == 0) {
            if (fractional == 0) {
                return sign + "0000000000000000000000000000000";
            } else {
                for (int i = 0; i < fractionalBinary.length(); i++) {
                    if (fractionalBinary.charAt(i) == '1') {
                        exponent = this.convertDecimalToBinaryIntegerUnsigned(i - fractionalBinary.length() + 127, 8);
                        mantissa = fractionalBinary.substring(i + 1);
                        while (mantissa.length() < 23) {
                            mantissa = mantissa + "0";
                        }
                        if (mantissa.length() > 23) {
                            mantissa = mantissa.substring(0, 23);
                        }
                        return sign + exponent + mantissa;
                    }
                }
            }
        } else {
            exponent = this.convertDecimalToBinaryIntegerUnsigned(integralBinary.length() + 126, 8);
            mantissa = integralBinary.substring(1);
            if (fractional != 0) {
                mantissa = mantissa + fractionalBinary;
            }
            while (mantissa.length() < 23) {
                mantissa = mantissa + "0";
            }
            if (mantissa.length() > 23) {
                mantissa = mantissa.substring(0, 23);
            }
            return sign + exponent + mantissa;
        }

        return null;
    }

    private double convert64BitBinaryDoubleToDecimal(String doubleNumber) {

        String signBinary = doubleNumber.substring(0, 1);
        String exponentBinary = doubleNumber.substring(1, 12);
        String mantissaBinary = doubleNumber.substring(12);

        int exponent = this.convertBinaryToDecimalSigned(exponentBinary) - 1023;

        String integralBinary = "";
        String fractionalBinary = "";

        int integral;
        double fractional;

        boolean foundMantissa = false;

        for (int i = mantissaBinary.length() - 1; i >= 0; i--) {
            if (mantissaBinary.charAt(i) == '1') {
                mantissaBinary = mantissaBinary.substring(0, i + 1);
                foundMantissa = true;
            }
        }

        if (!foundMantissa) {
            mantissaBinary = "";
        }

        if (exponent > 0) {
            integralBinary = "1" + mantissaBinary.substring(0, exponent);
            fractionalBinary = mantissaBinary.substring(exponent);
        } else if (exponent < 0) {
            exponent = Math.abs(exponent);
            integralBinary = "0";
            fractionalBinary = "1" + mantissaBinary;
            for (int i = 0; i < exponent; i++) {
                fractionalBinary = "0" + fractionalBinary;
            }
        } else {
            integralBinary = "1";
            fractionalBinary = mantissaBinary;
        }

        integral = this.convertBinaryToIntegral(integralBinary);
        fractional = this.convertBinaryToFractionalDouble(fractionalBinary);

        if (signBinary == "1") {
            integral = integral * (-1);
        }

        return integral + fractional;
    }

    // IEEE format for double-precision floating-point
    // Source: http://web.cse.ohio-state.edu/~reeves.92/CSE2421au12/SlidesDay32.pdf
    private String convertDecimalTo64BitBinaryDouble(double decimalNumber) {

        String sign;
        String exponent;
        String mantissa;

        int integral;
        double fractional;

        String integralBinary = "";
        String fractionalBinary = "";

        if (decimalNumber < 0) {
            sign = "1";
        } else {
            sign = "0";
        }

        integral = (int) Math.abs(decimalNumber);
        fractional = Math.abs(decimalNumber) - integral;

        integralBinary = this.convertIntegralToBinary(integral);
        fractionalBinary = this.convertFractionalToBinary(fractional);

        if (integral == 0) {
            if (fractional == 0) {
                return sign + "000000000000000000000000000000000000000000000000000000000000000";
            } else {
                for (int i = 0; i < fractionalBinary.length(); i++) {
                    if (fractionalBinary.charAt(i) == '1') {
                        exponent = this.convertDecimalToBinaryIntegerUnsigned(i - fractionalBinary.length() + 1023, 11);
                        mantissa = fractionalBinary.substring(i + 1);
                        while (mantissa.length() < 52) {
                            mantissa = mantissa + "0";
                        }
                        if (mantissa.length() > 52) {
                            mantissa = mantissa.substring(0, 52);
                        }
                        return sign + exponent + mantissa;
                    }
                }
            }
        } else {
            exponent = this.convertDecimalToBinaryIntegerUnsigned(integralBinary.length() + 1022, 11);
            mantissa = integralBinary.substring(1);
            if (fractional != 0) {
                mantissa = mantissa + fractionalBinary;
            }
            while (mantissa.length() < 52) {
                mantissa = mantissa + "0";
            }
            if (mantissa.length() > 52) {
                mantissa = mantissa.substring(0, 52);
            }
            return sign + exponent + mantissa;
        }

        return null;
    }

    private String convertIntegralToBinary(int integral) {

        String binaryNumber = "";
        int powerOfTwo = 1;

        if (integral == 0) {
            return "0";
        }

        while (powerOfTwo * 2 < integral) {
            powerOfTwo = powerOfTwo * 2;
        }

        while (powerOfTwo > 0) {
            if (powerOfTwo <= integral) {
                binaryNumber = binaryNumber + "1";
                integral = integral - powerOfTwo;
            } else {
                binaryNumber = binaryNumber + "0";
            }
            powerOfTwo = powerOfTwo / 2;
        }

        return binaryNumber;
    }

    private int convertBinaryToIntegral(String binaryIntegral) {

        int integral = 0;
        int powerOfTwo = 1;

        for (int i = binaryIntegral.length() - 1; i >= 0; i--) {
            if (binaryIntegral.charAt(i) == '1') {
                integral = integral + (int) Math.pow(2, i);
            }
            powerOfTwo = powerOfTwo * 2;
        }

        return integral;
    }

    private String convertFractionalToBinary(float fractional) {

        String binaryNumber = "";
        int maxLength = 23;

        for (int i = 0; (i < maxLength) && (fractional != 0); i++) {
            fractional = fractional * 2;
            if (fractional >= 1) {
                binaryNumber = binaryNumber + "1";
                fractional = fractional - 1;
            } else {
                binaryNumber = binaryNumber + "0";
            }
        }

        return binaryNumber;
    }

    private float convertBinaryToFractionalFloat(String binaryFractional) {

        float fractional = 0;
        int startingIndex = -1;

        for (int i = binaryFractional.length() - 1; i >= 0; i--) {
            if (binaryFractional.charAt(i) == '1') {
                startingIndex = i;
                break;
            }
        }

        if (startingIndex == -1) {
            return 0;
        }

        for (int i = startingIndex; i >= 0; i--) {
            if (binaryFractional.charAt(i) == '1') {
                fractional = fractional + 1;
            }
            fractional = fractional / 2;
        }

        return fractional;
    }

    private String convertFractionalToBinary(double fractional) {

        String binaryNumber = "";
        int maxLength = 52;

        for (int i = 0; (i < maxLength) && (fractional != 0); i++) {
            fractional = fractional * 2;
            if (fractional >= 1) {
                binaryNumber = binaryNumber + "1";
                fractional = fractional - 1;
            } else {
                binaryNumber = binaryNumber + "0";
            }
        }

        return binaryNumber;
    }

    private double convertBinaryToFractionalDouble(String binaryFractional) {

        double fractional = 0;
        int startingIndex = -1;

        for (int i = binaryFractional.length() - 1; i >= 0; i--) {
            if (binaryFractional.charAt(i) == '1') {
                startingIndex = i;
                break;
            }
        }

        if (startingIndex == -1) {
            return 0;
        }

        for (int i = startingIndex; i >= 0; i--) {
            if (binaryFractional.charAt(i) == '1') {
                fractional = fractional + 1;
            }
            fractional = fractional / 2;
        }

        return fractional;
    }

}