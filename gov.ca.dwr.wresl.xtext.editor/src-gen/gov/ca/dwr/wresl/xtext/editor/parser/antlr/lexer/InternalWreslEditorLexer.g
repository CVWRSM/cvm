
/*
* generated by Xtext
*/
lexer grammar InternalWreslEditorLexer;


@header {
package gov.ca.dwr.wresl.xtext.editor.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}




KEYWORD_89 : ('D'|'d')('A'|'a')('Y'|'y')('S'|'s')('I'|'i')('N'|'n')('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

KEYWORD_88 : ('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('E'|'e')('R'|'r')('I'|'i')('E'|'e')('S'|'s');

KEYWORD_83 : ('C'|'c')('O'|'o')('N'|'n')('D'|'d')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

KEYWORD_84 : ('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t')('R'|'r')('A'|'a')('I'|'i')('N'|'n');

KEYWORD_85 : ('O'|'o')('B'|'b')('J'|'j')('E'|'e')('C'|'c')('T'|'t')('I'|'i')('V'|'v')('E'|'e');

KEYWORD_86 : ('U'|'u')('N'|'n')('B'|'b')('O'|'o')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('D'|'d');

KEYWORD_87 : ('W'|'w')('A'|'a')('T'|'t')('E'|'e')('R'|'r')('Y'|'y')('E'|'e')('A'|'a')('R'|'r');

KEYWORD_81 : ('E'|'e')('X'|'x')('T'|'t')('E'|'e')('R'|'r')('N'|'n')('A'|'a')('L'|'l');

KEYWORD_82 : ('S'|'s')('E'|'e')('Q'|'q')('U'|'u')('E'|'e')('N'|'n')('C'|'c')('E'|'e');

KEYWORD_62 : ('C'|'c')('O'|'o')('N'|'n')('V'|'v')('E'|'e')('R'|'r')('T'|'t');

KEYWORD_63 : ('I'|'i')('N'|'n')('C'|'c')('L'|'l')('U'|'u')('D'|'d')('E'|'e');

KEYWORD_64 : ('I'|'i')('N'|'n')('I'|'i')('T'|'t')('I'|'i')('A'|'a')('L'|'l');

KEYWORD_65 : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('G'|'g')('E'|'e')('R'|'r');

KEYWORD_66 : ('P'|'p')('E'|'e')('N'|'n')('A'|'a')('L'|'l')('T'|'t')('Y'|'y');

KEYWORD_67 : ('C'|'c')('F'|'f')('S'|'s')'_'('T'|'t')('A'|'a')('F'|'f');

KEYWORD_68 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('A'|'a')('P'|'p')('R'|'r');

KEYWORD_69 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('A'|'a')('U'|'u')('G'|'g');

KEYWORD_70 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('D'|'d')('E'|'e')('C'|'c');

KEYWORD_71 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('F'|'f')('E'|'e')('B'|'b');

KEYWORD_72 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('A'|'a')('N'|'n');

KEYWORD_73 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('U'|'u')('L'|'l');

KEYWORD_74 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('U'|'u')('N'|'n');

KEYWORD_75 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('M'|'m')('A'|'a')('R'|'r');

KEYWORD_76 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('M'|'m')('A'|'a')('Y'|'y');

KEYWORD_77 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('N'|'n')('O'|'o')('V'|'v');

KEYWORD_78 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('O'|'o')('C'|'c')('T'|'t');

KEYWORD_79 : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('S'|'s')('E'|'e')('P'|'p');

KEYWORD_80 : ('T'|'t')('A'|'a')('F'|'f')'_'('C'|'c')('F'|'f')('S'|'s');

KEYWORD_57 : ('D'|'d')('E'|'e')('F'|'f')('I'|'i')('N'|'n')('E'|'e');

KEYWORD_58 : ('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t');

KEYWORD_59 : ('A'|'a')('F'|'f')'_'('C'|'c')('F'|'f')('S'|'s');

KEYWORD_60 : ('C'|'c')('F'|'f')('S'|'s')'_'('A'|'a')('F'|'f');

KEYWORD_61 : ('D'|'d')('A'|'a')('Y'|'y')('S'|'s')('I'|'i')('N'|'n');

KEYWORD_46 : ('A'|'a')('L'|'l')('I'|'i')('A'|'a')('S'|'s');

KEYWORD_47 : ('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t');

KEYWORD_48 : ('G'|'g')('I'|'i')('V'|'v')('E'|'e')('N'|'n');

KEYWORD_49 : ('L'|'l')('O'|'o')('C'|'c')('A'|'a')('L'|'l');

KEYWORD_50 : ('L'|'l')('O'|'o')('W'|'w')('E'|'e')('R'|'r');

KEYWORD_51 : ('M'|'m')('O'|'o')('D'|'d')('E'|'e')('L'|'l');

KEYWORD_52 : ('U'|'u')('N'|'n')('I'|'i')('T'|'t')('S'|'s');

KEYWORD_53 : ('U'|'u')('P'|'p')('P'|'p')('E'|'e')('R'|'r');

KEYWORD_54 : ('V'|'v')('A'|'a')('L'|'l')('U'|'u')('E'|'e');

KEYWORD_55 : ('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e');

KEYWORD_56 : ('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

KEYWORD_39 : '.'('D'|'d')('L'|'l')('L'|'l');

KEYWORD_40 : ('C'|'c')('A'|'a')('S'|'s')('E'|'e');

KEYWORD_41 : ('D'|'d')('V'|'v')('A'|'a')('R'|'r');

KEYWORD_42 : ('F'|'f')('R'|'r')('O'|'o')('M'|'m');

KEYWORD_43 : ('G'|'g')('O'|'o')('A'|'a')('L'|'l');

KEYWORD_44 : ('K'|'k')('I'|'i')('N'|'n')('D'|'d');

KEYWORD_45 : ('S'|'s')('V'|'v')('A'|'a')('R'|'r');

KEYWORD_21 : ('F'|'f')'9''0';

KEYWORD_22 : ('L'|'l')('H'|'h')('S'|'s');

KEYWORD_23 : ('R'|'r')('H'|'h')('S'|'s');

KEYWORD_24 : ('S'|'s')('T'|'t')('D'|'d');

KEYWORD_25 : ('S'|'s')('U'|'u')('M'|'m');

KEYWORD_26 : ('U'|'u')('S'|'s')('E'|'e');

KEYWORD_27 : ('A'|'a')('P'|'p')('R'|'r');

KEYWORD_28 : ('A'|'a')('U'|'u')('G'|'g');

KEYWORD_29 : ('D'|'d')('E'|'e')('C'|'c');

KEYWORD_30 : ('F'|'f')('E'|'e')('B'|'b');

KEYWORD_31 : ('J'|'j')('A'|'a')('N'|'n');

KEYWORD_32 : ('J'|'j')('U'|'u')('L'|'l');

KEYWORD_33 : ('J'|'j')('U'|'u')('N'|'n');

KEYWORD_34 : ('M'|'m')('A'|'a')('R'|'r');

KEYWORD_35 : ('M'|'m')('A'|'a')('Y'|'y');

KEYWORD_36 : ('N'|'n')('O'|'o')('V'|'v');

KEYWORD_37 : ('O'|'o')('C'|'c')('T'|'t');

KEYWORD_38 : ('S'|'s')('E'|'e')('P'|'p');

KEYWORD_16 : '/''=';

KEYWORD_17 : '<''=';

KEYWORD_18 : '=''=';

KEYWORD_19 : '>''=';

KEYWORD_20 : ('I'|'i')'=';

KEYWORD_1 : '(';

KEYWORD_2 : ')';

KEYWORD_3 : '*';

KEYWORD_4 : '+';

KEYWORD_5 : ',';

KEYWORD_6 : '-';

KEYWORD_7 : '/';

KEYWORD_8 : '<';

KEYWORD_9 : '=';

KEYWORD_10 : '>';

KEYWORD_11 : '[';

KEYWORD_12 : ']';

KEYWORD_13 : ('I'|'i');

KEYWORD_14 : '{';

KEYWORD_15 : '}';



RULE_IF : ('If'|'IF'|'if');

RULE_ELSEIF : ('Elseif'|'ELSEIF'|'elseif'|'ElseIf');

RULE_ELSE : ('Else'|'ELSE'|'else');

RULE_RANGE : ('range'|'RANGE'|'Range');

RULE_MIN : ('min'|'MIN');

RULE_MAX : ('max'|'MAX');

RULE_FLOAT : (RULE_INT '.' RULE_INT*|'.' RULE_INT+);

RULE_AND : ('.and.'|'.AND.');

RULE_OR : ('.or.'|'.OR.');

RULE_NOT : ('.not.'|'.NOT.');

RULE_ALWAYS : 'always';

RULE_ORDER : 'order';

RULE_STRING : '\'' ~(('\''|'\n'|'\r'))* '\'';

RULE_SL_COMMENT : '!' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_ID : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;



