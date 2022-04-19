package ast;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Line> lines = new ArrayList<Line>();
    private String memory;
    public void addLine(Line l) {
        if (l != null) {
            lines.add(l);
        }
    }
    public void setMemory(String val) { memory = val; }
    public String getMemory() { return memory; }
    public String toDumbasmo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Line l: lines) {
            stringBuilder.append(l.evaluate());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
