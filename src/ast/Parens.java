package ast;

public class Parens extends Expression {
    private Expression exp;
    public Parens(Expression exp) {
        this.exp = exp;
    }
    public Eval evaluate(Program p) {
        return exp.evaluate(p);
    }
}
