package ast;

public abstract class Expression {
    public abstract Eval evaluate(Program p);
}
