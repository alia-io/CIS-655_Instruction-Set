import java.util.HashMap;

public class ComputerMemory {

    private HashMap<Integer, String> registerContents;
    private HashMap<Integer, String> memoryContents;
    String immediateRegister1;
    String immediateRegister2;

    public ComputerMemory() {
        this.registerContents = new HashMap<Integer, String>(31);
        this.memoryContents = new HashMap<Integer, String>(32);
        this.setInitialRegisterAndMemoryContents();
    }

    private void setInitialRegisterAndMemoryContents() {
        for (int i = 0; i < 31; i++) {
            this.registerContents.put(i, "00000000000000000000000000000000");
        }
        for (int i = 0; i < 32; i++) {
            this.memoryContents.put(i, "00000000000000000000000000000000");
        }
        this.immediateRegister1 = "00000000000000000000000000000000";
        this.immediateRegister2 = "00000000000000000000000000000000";
    }

    public String getRegisterContentsByLocation(int key) {
        return this.registerContents.get(key);
    }

    public String getMemoryContentsByLocation(int key) {
        return this.memoryContents.get(key);
    }

    public String getImmediateRegister1Value() {
        return this.immediateRegister1;
    }

    public String getImmediateRegister2Value() {
        return this.immediateRegister2;
    }

    // value should be 32 bits
    public void setImmediateRegister1Value(String value) {
        this.immediateRegister1 = value;
    }

    // value should be 64 bits
    public void setImmediateRegister1And2Values(String value) {
        this.immediateRegister1 = value.substring(0, 32);
        this.immediateRegister2 = value.substring(32);
    }

}
