
%{
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
extern int yylex();
extern int yylineno;
void yyerror (char const *);
bool error = false;
%}
%union{
	char* valString;
}
%token <valString> ETIQUETA_APERTURA 
%token <valString> ETIQUETA_CIERRE 
%token TEXTO	
%token CABECERA
%token ETIQUETA_ERROR_CIERRE
%token ETIQUETA_ERROR_APERTURA
%start S
%%
S : CABECERA
	| CABECERA cuerpo
	| CABECERA TEXTO cuerpo {printf("Sintaxis XML incorrecta.\nHay texto entre un tag de cierre y uno de apertura\n"); error=true; return 0;}
	| cuerpo {yyerror("Sintaxis XML incorrecta.\nFalta la linea de la cabecera\n"); error = true;return 0;}
;
cuerpo:
	ETIQUETA_APERTURA contenido ETIQUETA_CIERRE
	| ETIQUETA_APERTURA contenido ETIQUETA_CIERRE contenido {printf("Sintaxis XML incorrecta.\nNo hay un solo tag raíz o hay texto antes o despues del tag en la linea %d\n", yylineno); error = true;return 0;}
;
contenido :
	data
	| data contenido

	;
data: ETIQUETA_APERTURA ETIQUETA_CIERRE

	| ETIQUETA_APERTURA contenido ETIQUETA_CIERRE{
		if($1!= NULL && $3 != NULL){
			if (strcmp($1 + 1, $3 + 2) != 0) {
				printf("Sintaxis XML incorrecta.\n Ha habido un error sintatico en la linea %d,\no falta una etiqueta o los nombres son diferentes\ny donde se esperaba </%s se recibio %s\n", yylineno, $1+1	, $3);
				error= true;
				return 0;
			}
		}
    }
	| ETIQUETA_ERROR_APERTURA  {printf("Sintaxis XML incorrecta.\nEtiqueta sin abrir pero si cerrada en la linea %d\n", yylineno);error=true; return 0;}
	| ETIQUETA_ERROR_CIERRE {printf("Sintaxis XML incorrecta.\nEtiqueta abierta sin cerrar en la linea %d\n", yylineno);error=true; return 0;}
	| TEXTO ;
%%
int main() {
	yyparse();
	if(!error){
		printf("Sintaxis XML correcta.\n"); //imprime bien si la sintaxis es correcta
	}
	return 0;
}
void yyerror (char const *message) { fprintf (stderr, "%s\n", message);}