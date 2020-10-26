public class InstructionExecuter {

    private boolean executed; // set to true when instruction is successfully completed

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

    }

    // instruction #2
    private void putFloat(String register, String immediate) {

    }

    // instruction #3
    private void putDouble(String register, String immediate) {

    }

    // instruction #4
    private void copySingle(String register1, String register2) {

    }

    // instruction #5
    private void copyDouble(String register1, String register2) {

    }

    // instruction #6
    private void convertIntToFloat(String register1, String register2) {

    }

    // instruction #7
    private void convertIntToDouble(String register1, String register2) {

    }

    // instruction #8
    private void convertFloatToInt(String register1, String register2) {

    }

    // instruction #9
    private void convertFloatToDouble(String register1, String register2) {

    }

    // instruction #10
    private void convertDoubleToInt(String register1, String register2) {

    }

    // instruction #11
    private void convertDoubleToFloat(String register1, String register2) {

    }

    // instruction #12
    private void shiftLeft(String register1, String register2, String immediate) {

    }

    // instruction #13
    private void shiftRight(String register1, String register2, String immediate) {

    }

    // instruction #14
    private void notImm(String register1, String immediate) {

    }

    // instruction #15
    private void notInt(String register1, String register2) {

    }

    // instruction #16
    private void andImm(String register1, String register2, String immediate) {

    }

    // instruction #17
    private void andInt(String register1, String register2, String register3) {

    }

    // instruction #18
    private void orImm(String register1, String register2, String immediate) {

    }

    // instruction #19
    private void orInt(String register1, String register2, String register3) {

    }

    // instruction #20
    private void xorImm(String register1, String register2, String immediate) {

    }

    // instruction #21
    private void xorInt(String register1, String register2, String register3) {

    }

    // instruction #22
    private void addImm(String register1, String register2, String immediate) {

    }

    // instruction #23
    private void addInt(String register1, String register2, String register3) {

    }

    // instruction #24
    private void addFloat(String register1, String register2, String register3) {

    }

    // instruction #25
    private void addDouble(String register1, String register2, String register3) {

    }

    // instruction #26
    private void subImm(String register1, String register2, String immediate, boolean registerFirst) {

    }

    // instruction #27
    private void subInt(String register1, String register2, String register3) {

    }

    // instruction #28
    private void subFloat(String register1, String register2, String register3) {

    }

    // instruction #29
    private void subDouble(String register1, String register2, String register3) {

    }

    // instruction #30
    private void multImm(String register1, String register2, String immediate) {

    }

    // instruction #31
    private void multInt(String register1, String register2, String register3) {

    }

    // instruction #32
    private void multFloat(String register1, String register2, String register3) {

    }

    // instruction #33
    private void multDouble(String register1, String register2, String register3) {

    }

    // instruction #34
    private void divImm(String register1, String register2, String immediate, boolean registerFirst) {

    }

    // instruction #35
    private void divInt(String register1, String register2, String register3) {

    }

    // instruction #36
    private void divFloat(String register1, String register2, String register3) {

    }

    // instruction #37
    private void divDouble(String register1, String register2, String register3) {

    }

    // instruction #38
    private void modImm(String register1, String register2, String immediate, boolean registerFirst) {

    }

    // instruction #39
    private void modInt(String register1, String register2, String register3) {

    }

    // instruction #40
    private void setLessThanImm(String register1, String register2, String immediate, boolean registerFirst) {

    }

    // instruction #41
    private void setLessThanInt(String register1, String register2, String register3) {

    }

    // instruction #42
    private void setLessThanFloat(String register1, String register2, String register3) {

    }

    // instruction #43
    private void setLessThanDouble(String register1, String register2, String register3) {

    }

    // instruction #44
    private void setEqualImm(String register1, String register2, String immediate) {

    }

    // instruction #45
    private void setEqualInt(String register1, String register2, String register3) {

    }

    // instruction #46
    private void setEqualFloat(String register1, String register2, String register3) {

    }

    // instruction #47
    private void setEqualDouble(String register1, String register2, String register3) {

    }

    // instruction #48
    private void loadSingle(String register1, String register2, String immediate) {

    }

    // instruction #49
    private void loadDouble(String register1, String register2, String immediate) {

    }

    // instruction #50
    private void storeSingle(String register1, String register2, String immediate) {

    }

    // instruction #51
    private void storeDouble(String register1, String register2, String immediate) {

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

}
