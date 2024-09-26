%{
int numcaracteres = 0;
int numpalabras = 0;    
int nummayusculas = 0;
int numminusculas = 0;
int numnumericas = 0;
int numfrases = 0;
int numpalabrasraras = 0;
#include <ctype.h>
%}
DIGITO     [0-9]
MAYUSCULAS [A-Z]                                 
MINUSCULAS [a-z]
%%
{DIGITO}+ { numnumericas++; numpalabras++; numcaracteres+=yyleng;}
{MAYUSCULAS}+ {nummayusculas++; numpalabras++; numcaracteres+=yyleng;}
{MINUSCULAS}+ {numminusculas++; numpalabras++; numcaracteres+=yyleng;}
[^ \t\n\.]+ { numcaracteres+=yyleng; numpalabras++; numpalabrasraras++; }
"." {numcaracteres++; numfrases++;}

[ /n/t] {}

. {}


%%

#include <stdio.h>
int main (){
    yylex();
    printf("Numero de caracteres: %d\n", numcaracteres);
    printf("Numero de palabras: %d\n", numpalabras);
    printf("Numero de palabras raras: %d\n", numpalabrasraras);
    printf("Numero de palabras mayusculas: %d\n", nummayusculas);
    printf("Numero de palabras minusculas: %d\n", numminusculas);
    printf("Numero de palabras numericas: %d\n", numnumericas);
    printf("Numero de frases: %d\n", numfrases);
    return 0;
}

