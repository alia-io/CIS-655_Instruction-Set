import java.util.HashMap;

public class ComputerMemory {

    private HashMap<Integer, String> registerContents;
    private HashMap<Integer, String> memoryContents;

    public ComputerMemory() {
        this.registerContents = new HashMap<Integer, String>(31);
        this.memoryContents = new HashMap<Integer, String>(32);
        this.setInitialRegisterAndMemoryContents();
    }

    private void setInitialRegisterAndMemoryContents() {
        for (int i = 0; i < 32; i++) {
            this.registerContents.put(i, "00000000000000000000000000000000");
        }
        for (int i = 0; i < 256; i++) {
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

}
