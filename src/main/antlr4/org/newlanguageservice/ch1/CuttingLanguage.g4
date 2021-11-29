grammar CuttingLanguage;

actions
:
	(
		action
		| variable_definition
	)+
;

variable_definition
:
	VAR variableName = ID COLON type = TYPE
	((
		'=' expr = expression
	)?)
;


variable_equal:variable_ref EQUAL expr=expression;


expression
:
	mul_div_expression
;

mul_div_expression
:
	add_or_sub
	((
		(op = MUL
		| DIV) expression
	))
;

add_or_sub
:
	sub_element
	((
		(op = PLUS
		| MINUS) expression
	))
;

sub_element
:
	brackets_expression
	| variable_ref|int_val|point
;

int_val:INT ;

point: '(' x = INT ',' y = INT ')';


brackets_expression
:
	L_BRACKET expr = expression R_BRACKET
;

variable_ref
:
	var_name_ref = ID
;

action
:
	moveTo
	| lineTo
;

moveTo
:
	moveToName = 'MoveTo' point
;

lineTo
:
	lineToName = 'LineTo' point
;

EQUAL:'=';

DIV
:
	'/'
;

MUL
:
	'*'
;

PLUS
:
	'+'
;

MINUS
:
	'-'
;

COLON
:
	':'
;

VAR
:
	'var'
;

L_BRACKET
:
	'('
;

R_BRACKET
:
	')'
;

TYPE
:
	INT_TYPE
	| POINT
;

ID
:
	[_a-zA-Z]
	(
		[_0-9a-zA-Z]*
	)
;


INT
:
	'-'? DIGIT+
;

fragment POINT: 'Point';

fragment
INT_TYPE
:
	'int'
;


fragment
DIGIT
:
	[0-9]
;

WS
:
	[ \n\r\t] -> skip
; 
