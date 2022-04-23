package ast;

import java.util.Objects;

public class Line {
    private String assignee = null;
    private Expression expr = null;
    private Program program = null;
    public Line(Program p, Expression e) {
        this.program = p;
        this.expr = e;
    }
    public Line(Program p, Expression e, String a) {
        this.program = p;
        this.expr = e;
        this.assignee = a;
    }
    public String evaluate() {
        Eval eval = expr.evaluate(this.program);
        String line = "";

        switch (eval.evalType) {
            case MEMORY -> {
                line = String.format("m[%d]", eval.getMemory());
            }
            case VALUE -> {
                line = Double.toString(eval.getValue());
            }
            case CODE -> {
                String code = eval.getCode();
                if (this.assignee != null) {
                    String assign = String.format("MOV m[%s] %s", assignee, eval.getOp1());
                    return code + "\n" + assign;
                }
                return code;
            }
        }

        return String.format("MOV m[%s] %s", this.assignee, line);
    }
}
