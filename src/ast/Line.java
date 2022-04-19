package ast;

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
        if (this.assignee != null) {
            program.setMemory(eval.memoryId);
        }
        System.out.println(eval.getValue());
        return eval.getValue();
    }
}
