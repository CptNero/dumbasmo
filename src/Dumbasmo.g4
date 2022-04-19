grammar Dumbasmo;

options {
    language = Java;
}

@header {
import java.io.FileWriter;
import java.io.IOException;
}

@members {
    public static void main(String[] args) throws Exception {
         DumbasmoLexer lex = new DumbasmoLexer(new ANTLRFileStream(args[0]));
         CommonTokenStream tokens = new CommonTokenStream(lex);
         DumbasmoParser parser = new DumbasmoParser(tokens);
         parser.start();
    }
}

start
    @init { ast.Program p = new ast.Program(); }
    @after {
        String dumbasm = p.toDumbasmo();
        try {
            FileWriter fw = new FileWriter("out.txt");
            fw.write(dumbasm);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    : (line[p] { p.addLine($line.node); } LF)* EOF
    ;

line [ ast.Program p ] returns [ ast.Line node ]
    : expression { $node = new ast.Line($p, $expression.node); }
    | MEMORY LBRAC NUMBER RBRAC '=' expression { $node = new ast.Line($p, $expression.node); }
    | READ LPAR NUMBER RPAR
    ;

expression returns [ ast.Expression node ]
    : first_op=addop { $node = $first_op.node; } (OPADD next_op=addop
        { $node = new ast.Binary($OPADD.text, $node, $next_op.node); })*
    ;

addop returns [ ast.Expression node ]
    : first_op=mulop { $node = $first_op.node; } (OPMUL next_op=mulop
        { $node = new ast.Binary($OPMUL.text, $node, $next_op.node); })*
    ;

mulop returns [ ast.Expression node ]
    : NUMBER { $node = new ast.Const($NUMBER.text); }
    | LPAR expression { $node = new ast.Parens($expression.node); } RPAR
    | MEMORY LBRAC NUMBER RBRAC { $node = new ast.Memory($NUMBER.text); }
    ;

LF      : '\n';
NUMBER  : [0-9]+;
OPADD   : '+' | '-';
OPMUL   : '*' | '/';
LPAR    : '(';
RPAR    : ')';
LBRAC   : '[';
RBRAC   : ']';
MEMORY  : 'm';
READ    : 'read';


