package ast;

public class Eval {
    boolean isMemory = false;
    double value = 0;
    String memoryId = "";

    Eval(boolean isMemory, double value) {
        this.isMemory = isMemory;
        this.value = value;
    }

    Eval(boolean isMemory, String memoryId) {
        this.isMemory = isMemory;
        this.memoryId = memoryId;
    }

    public String getValue() {
        if (isMemory) {
            return memoryId;
        }
        return Double.toString(value);
    }
}