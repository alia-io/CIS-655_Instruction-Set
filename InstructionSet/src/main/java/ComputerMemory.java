import java.util.HashMap;

public class ComputerMemory {

    private int numberOfRegisters;
    private int numberOfMemoryItems;
    private HashMap<Integer, String> registerContents;
    private HashMap<Integer, String> memoryContents;

    public ComputerMemory(int numberOfRegisters, int numberOfMemoryItems) {
        this.numberOfRegisters = numberOfRegisters;
        this.numberOfMemoryItems = numberOfMemoryItems;
        this.registerContents = new HashMap<Integer, String>(this.numberOfRegisters);
        this.memoryContents = new HashMap<Integer, String>(this.numberOfMemoryItems);
        this.setInitialRegisterAndMemoryContents();
    }

    private void setInitialRegisterAndMemoryContents() {
        for (int i = 0; i < this.numberOfRegisters; i++) {
            this.registerContents.put(i, "00000000000000000000000000000000");
        }
        for (int i = 0; i < this.numberOfMemoryItems; i++) {
            this.memoryContents.put(i, "00000000000000000000000000000000");
        }
    }

    public String getRegisterContentsByLocation(int key) {
        return this.registerContents.get(key);
    }

    public void setRegisterContentsByLocation(int key, String value) {
        this.registerContents.put(key, value);
    }

    public String getMemoryContentsByLocation(int key) {
        return this.memoryContents.get(key);
    }

    public void setMemoryContentsByLocation(int key, String value) {
        this.memoryContents.put(key, value);
    }

    public int getNumberOfRegisters() {
        return this.numberOfRegisters;
    }

    public int getNumberOfMemoryItems() {
        return this.numberOfMemoryItems;
    }

}