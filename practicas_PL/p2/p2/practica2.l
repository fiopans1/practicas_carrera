%{
#include <stdlib.h>
#include "p2.tab.h"
%}
contenido       ([A-Za-zÁÉÍÓÚáéíóú0-9]+|[-\/\!\?¿¡\(\)''#\"\$\%\*\-\.,:=])*
%%
"<?xml"[ \t\r\n]+"version=\""[0-9]+"."[0-9]"\""+[ \t\r\n]+"encoding="[^?]+"?>" {return CABECERA;} //esta regla reconoce la cabecera
"<"[^<>!/]+">"       {yylval.valString = strdup(yytext); return ETIQUETA_APERTURA; }
"</"[^<>]+">"      {yylval.valString =strdup(yytext); return ETIQUETA_CIERRE; }
"<!--"[^-]+"-->"    {}
"<"               {return ETIQUETA_ERROR_CIERRE;}
">"               {return ETIQUETA_ERROR_APERTURA;}
{contenido}       {yylval.valString =strdup(yytext);return TEXTO;}
[\n\t\r]+ {}
. {};

%%
