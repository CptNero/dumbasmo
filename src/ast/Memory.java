package ast;

public class Memory extends Expression {
    private String id;
    public Memory(String id) {
        this.id = String.format("m[%s]", id);
    }
    public Eval evaluate(Program p) {
        return new Eval(true, p.getMemory());
    }
}
