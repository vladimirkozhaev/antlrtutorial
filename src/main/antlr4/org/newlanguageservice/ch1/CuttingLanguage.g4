grammar CuttingLanguage;

actions:action+;

action:moveTo|lineTo;
moveTo: moveToName='MoveTo' '(' x=INT ',' y=INT ')';

lineTo: lineToName='LineTo' '(' x=INT ',' y=INT ')';

INT :'-'? DIGIT+ ; 
fragment DIGIT : [0-9];

WS : [ \n\r\t] -> skip ; 
