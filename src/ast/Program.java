package ast;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Line> lines = new ArrayList<Line>();

    private StringBuilder codeBody = new StringBuilder();
    public void addLine(Line l) {
        if (l != null) {
            lines.add(l);
        }
    }

    public void addCode(String codeLine) {
        codeBody.append(codeLine);
        codeBody.append("\n");
    }
    public String toDumbasmo() {

        for (Line l: lines) {
            codeBody.append(l.evaluate());
            codeBody.append("\n");
        }
        return codeBody.toString();
    }
}
