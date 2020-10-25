public class InstructionToBinaryParser {

    int command; // holds the number id of the command (from doc)

    public InstructionToBinaryParser() {
        this.command = 0; // represents invalid command
    }

    public String parseInput(String input) {

        String[] splitInput;

        this.command = 0; // represents invalid command

        splitInput = input.trim().split("\\s+");

        if (splitInput.length == 3) {
            return this.getThreeWordInstruction(splitInput);
        } else if (splitInput.length == 4) {
            return this.getFourWordInstruction(splitInput);
        } else { // invalid length -- input can only be 3 or 4 words when separated by spaces
            return null;
        }

    }

    private String getThreeWordInstruction(String[] input) {

        String opcode = null;
        String register1 = null;
        String register2 = null;
        String immediate = null;

        opcode = this.getOpcode(input[0]);

        if (this.command == 0 || this.command > 9 || opcode == null) { // invalid command
            return null;
        }

        register1 = this.getRegister(input[1]);

        if (register1 == null) { return null; } // invalid command

        if (this.command < 4) { // put command - 2nd arg is immediate
            if (this.command == 1) {
                immediate = this.getIntegerImmediate(input[2]);
                if (immediate == null) { return null; } // invalid command
            } else if (this.command == 2) {
                immediate = this.getFloatImmediate(input[2]);
                if (immediate == null) { return null; } // invalid command
            } else { // command 3
                immediate = this.getDoubleImmediate(input[2]);
                if (immediate == null) { return null; } // invalid command
            }
            return opcode + register1 + immediate; // opcode + 1 register + immediate (45 bits or 77 bits)

        } else { // convert command - 2nd arg is register
            register2 = this.getRegister(input[2]);
            if (register2 == null) { return null; } // invalid command
            return opcode + register1 + register2; // opcode + 2 registers (18 bits)
        }

    }

    private String getFourWordInstruction(String[] input) {

        String opcode;

        opcode = this.getOpcode(input[0]);

        if (this.command < 10) { // invalid command
            return null;
        }

        return "";
    }

    private String getOpcode(String input) {

        String[] splitInput = input.split("-");

        // validate by length of command -- can only be 2, 3, or 4 words when separated by dashes
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
                return this.putOpcodes(input[1]);
            case "shift": // commands 10, 11
                return this.shiftOpcodes(input[1]);
            case "not": // commands 12, 13
                return this.notOpcodes(input[1]);
            case "and": // commands 14, 15
                return this.andOpcodes(input[1]);
            case "or": // commands 16, 17
                return this.orOpcodes(input[1]);
            case "xor": // commands 18, 19
                return this.xorOpcodes(input[1]);
            case "add": // commands 20, 21, 22, 23
                return this.addOpcodes(input[1]);
            case "sub": // commands 24, 25, 26, 27
                return this.subOpcodes(input[1]);
            case "mult": // commands 28, 29, 30, 31
                return this.multOpcodes(input[1]);
            case "div": // commands 32, 33, 34, 35
                return this.divOpcodes(input[1]);
            case "mod": // commands 36, 37
                return this.modOpcodes(input[1]);
            case "load": // commands 46, 47, 48
                return this.loadOpcodes(input[1]);
            case "store": // commands 49, 50, 51
                return this.storeOpcodes(input[1]);
            default: // invalid input
                return null;
        }
    }

    private String getThreeWordOpcode(String[] input) {
        switch(input[0]) {
            case "set": // commands 42, 43, 44, 45
                if (input[1].equals("equal")) {
                    return this.setEqualOpcodes(input[2]);
                }
            default: // invalid input
                return null;
        }
    }

    private String getFourWordOpcode(String[] input) {
        switch(input[0]) {
            case "convert": // commands 4, 5, 6, 7, 8, 9
                if (input[2].equals("to")) {
                    return this.convertOpcodes(input[1], input[3]);
                }
            case "set": // commands 38, 39, 40, 41
                if (input[1].equals("less") && input[2].equals("than")) {
                    return this.setLessThanOpcodes(input[3]);
                }
            default: // invalid input
                return null;
        }
    }

    private String putOpcodes(String input) {
        switch (input) {
            case "int":
                this.command = 1;
                return "00000010";
            case "float":
                this.command = 2;
                return "00000100";
            case "double":
                this.command = 3;
                return "00000110";
            default: // invalid input
                return null;
        }
    }

    private String convertOpcodes(String input1, String input2) {
        switch (input1) {
            case "int":
                if (input2.equals("float")) {
                    this.command = 4;
                    return "00001010";
                } else if (input2.equals("double")) {
                    this.command = 5;
                    return "00001011";
                }
            case "float":
                if (input2.equals("int")) {
                    this.command = 6;
                    return "00001100";
                } else if (input2.equals("double")) {
                    this.command = 7;
                    return "00001101";
                }
            case "double":
                if (input2.equals("int")) {
                    this.command = 8;
                    return "00001110";
                } else if (input2.equals("float")) {
                    this.command = 9;
                    return "00001111";
                }
            default: // invalid
                return null;
        }
    }

    private String shiftOpcodes(String input) {
        switch (input) {
            case "left":
                this.command = 10;
                return "00010000";
            case "right":
                this.command = 11;
                return "00011000";
            default: // invalid
                return null;
        }
    }

    private String notOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 12;
                return "00100000";
            case "int":
                this.command = 13;
                return "00100010";
            default: // invalid
                return null;
        }
    }

    private String andOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 14;
                return "0010100"; // last bit set later
            case "int":
                this.command = 15;
                return "00101010";
            default: // invalid
                return null;
        }
    }

    private String orOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 16;
                return "0011000"; // last bit set later
            case "int":
                this.command = 17;
                return "00110010";
            default: // invalid
                return null;
        }
    }

    private String xorOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 18;
                return "0011100"; // last bit set later
            case "int":
                this.command = 19;
                return "00111010";
            default: // invalid
                return null;
        }
    }

    private String addOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 20;
                return "0100000"; // last bit set later
            case "int":
                this.command = 21;
                return "01000010";
            case "float":
                this.command = 22;
                return "01000100";
            case "double":
                this.command = 23;
                return "01000110";
            default: // invalid
                return null;
        }
    }

    private String subOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 24;
                return "0100100"; // last bit set later
            case "int":
                this.command = 25;
                return "01001010";
            case "float":
                this.command = 26;
                return "01001100";
            case "double":
                this.command = 27;
                return "01001110";
            default: // invalid
                return null;
        }
    }

    private String multOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 28;
                return "0101000"; // last bit set later
            case "int":
                this.command = 29;
                return "01010010";
            case "float":
                this.command = 30;
                return "01010100";
            case "double":
                this.command = 31;
                return "01010110";
            default: // invalid
                return null;
        }
    }

    private String divOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 32;
                return "0101100"; // last bit set later
            case "int":
                this.command = 33;
                return "01011010";
            case "float":
                this.command = 34;
                return "01011100";
            case "double":
                this.command = 35;
                return "01011110";
            default: // invalid
                return null;
        }
    }

    private String modOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 36;
                return "0110000"; // last bit set later
            case "int":
                this.command = 37;
                return "01100010";
            default: // invalid
                return null;
        }
    }

    private String setLessThanOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 38;
                return "0110100"; // last bit set later
            case "int":
                this.command = 39;
                return "01101010";
            case "float":
                this.command = 40;
                return "01101100";
            case "double":
                this.command = 41;
                return "01101110";
            default: // invalid
                return null;
        }
    }

    private String setEqualOpcodes(String input) {
        switch (input) {
            case "imm":
                this.command = 42;
                return "0111000"; // last bit set later
            case "int":
                this.command = 43;
                return "01110010";
            case "float":
                this.command = 44;
                return "01110100";
            case "double":
                this.command = 45;
                return "01110110";
            default: // invalid
                return null;
        }
    }

    private String loadOpcodes(String input) {
        switch (input) {
            case "int":
                this.command = 46;
                return "01111010";
            case "float":
                this.command = 47;
                return "01111100";
            case "double":
                this.command = 48;
                return "01111110";
            default: // invalid
                return null;
        }
    }

    private String storeOpcodes(String input) {
        switch (input) {
            case "int":
                this.command = 49;
                return "10000010";
            case "float":
                this.command = 50;
                return "10000100";
            case "double":
                this.command = 51;
                return "10000110";
            default: // invalid
                return null;
        }
    }

    private String getRegister(String input) {

        int registerNumber;

        if (input.length() != 2 && input.length() != 3) {
            return null; // invalid input -- wrong length
        }

        if (!(input.charAt(0) == 'R')) {
            return null; // invalid input -- no register identifier
        }

        try {
            registerNumber = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            return null; // invalid input -- not an integer
        }

        if (registerNumber < 0 || registerNumber > 30) {
            return null; // invalid input -- not a valid register number
        }

        return this.getRegisterNumberInBinary(registerNumber);
    }

    private String getRegisterNumberInBinary(int registerNumber) {
        return this.convertDecimalToBinaryIntegerUnsigned(registerNumber, 5);
    }

    private String getIntegerImmediate(String input) {

        int decimalNumber;

        try {
            decimalNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }

        return this.convertDecimalToBinaryIntegerSigned(decimalNumber, 32);
    }

    private String getFloatImmediate(String input) {

        float floatNumber;

        try {
            floatNumber = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return null;
        }

        return this.convertDecimalTo32BitBinaryFloat(floatNumber);
    }

    private String getDoubleImmediate(String input) {

        double doubleNumber;

        try {
            doubleNumber = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }

        return this.convertDecimalTo64BitBinaryDouble(doubleNumber);
    }

    // IEEE conversion method source: http://sandbox.mc.edu/~bennet/cs110/flt/dtof.html
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

    // IEEE conversion method source: http://web.cse.ohio-state.edu/~reeves.92/CSE2421au12/SlidesDay32.pdf
    private String convertDecimalTo64BitBinaryDouble(double decimalNumber) {

        String result = "";

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

    private String convertDecimalToBinaryIntegerSigned(int decimalNumber, int binaryDigits) {

        String binaryNumber = "";
        String invertedBinaryNumber = "";
        String twosComplementBinaryNumber = "";
        double powerOfTwo = Math.floor(Math.pow(2, binaryDigits - 1));

        while (powerOfTwo > 0) {
            if (powerOfTwo <= decimalNumber) {
                binaryNumber = binaryNumber + "1";
                decimalNumber = (int) (decimalNumber - powerOfTwo);
            } else {
                binaryNumber = binaryNumber + "0";
            }
            powerOfTwo = Math.floor(powerOfTwo / 2);
        }

        if (decimalNumber < 0) {
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

    private String convertFractionalToBinary(float fractional) {

        String binaryNumber = "";
        int maxLength = 56;

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

    private String convertFractionalToBinary(double fractional) {

        String binaryNumber = "";
        int maxLength = 56;

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

}
