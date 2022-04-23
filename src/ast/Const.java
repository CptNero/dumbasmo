package ast;

public class Const extends Expression {
    private double value;
    public Const(String token) {
        this.value = Double.parseDouble(token);
    }
    public Eval evaluate(Program p) {
        return new Eval(value);
    }
}
