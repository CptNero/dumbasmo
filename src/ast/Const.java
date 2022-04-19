package ast;

public class Const extends Expression {
    private double value;
    private String literal;
    public Const(String token) {
        System.out.println("Added new Const " + token);
        this.literal = token;
        this.value = Double.parseDouble(token);
    }
    public Eval evaluate(Program p) {
        return new Eval(false, value);
    }
}
