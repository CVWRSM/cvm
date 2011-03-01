tree grammar WreslTreeWalker;

options {
  language = Java;
  tokenVocab = WreslTree;
  ASTLabelType = CommonTree;
}

@header {
  package wresl;
  import java.util.Map;
  import java.util.HashMap;
  import components.Struct;
  import components.Tools;
  import components.LogUtils; 
}

@members {

  public int result;
  public CommonTree commonTree;
  public String currentAbsolutePath;
  	public String currentAbsoluteParent;
  private Map<String, Integer> variables = new HashMap<String, Integer>();
  
  	public Struct F = new Struct();	
  	
  		/// error message	
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        LogUtils.errMsg(hdr + " " + msg);
    }
}

evaluator returns [int result]
@init { F.currentAbsolutePath=currentAbsolutePath;
		F.currentAbsoluteParent=currentAbsoluteParent; }



	:	( pattern |  sequence | model )* EOF
	;

	
pattern
	: dvar
	;


	
	
//assignment
//	:	^(':=' IDENT e=expression)
//			{ variables.put($IDENT.text, e); }
//	;

dvar : dvar_std | dvar_std_local | dvar_nonStd | dvar_nonStd_local ;

dvar_std : ^(Dvar_std i=IDENT Kind k=QUOTE_STRING Units u=QUOTE_STRING)
	{ 	
    	F.dvarStd($i.text, "local", "", Tools.strip($k.text), Tools.strip($u.text));
	};
	
dvar_std_local : ^(Dvar_std_local i=IDENT Kind k=QUOTE_STRING Units u=QUOTE_STRING)
	{ 	
    	F.dvarStd($i.text, "local", "", Tools.strip($k.text), Tools.strip($u.text));
	}	

	;
dvar_nonStd : ^(Dvar_nonStd IDENT lower upper Kind k=QUOTE_STRING Units u=QUOTE_STRING)
	;

dvar_nonStd_local : ^(Dvar_nonStd_local IDENT lower upper Kind k=QUOTE_STRING Units u=QUOTE_STRING)
	;

lower : Lower (Std |Unbounded | expression ) ;
upper : Upper (Std |Unbounded | expression ) ;

model
	: MODEL IDENT '{' (pattern )+  '}' 
	;	
	
sequence
	: SEQUENCE IDENT '{' MODEL IDENT ORDER INTEGER '}' ;	
	
expression returns [int result]
	:	^('+' op1=expression op2=expression) { result = op1 + op2; }
	|	^('-' op1=expression op2=expression) { result = op1 - op2; }
	|	^('*' op1=expression op2=expression) { result = op1 * op2; }
	|	^('/' op1=expression op2=expression) { result = op1 / op2; }
	|	^(NEGATION e=expression)  { result = -e; }
	|	IDENT //{ result = variables.get($IDENT.text); }
	|	INTEGER //{ result = Integer.parseInt($INTEGER.text); }
	;

