%Antes tenia ta,tb,tc y lo tenia como un solo átomo, me
%interesa tener t de cualquiera, ahora voy a tener t de x mayúscula
%y lo voy a variar con el switch
switch(a).
switch(b).
switch(c).
%Atoms for initial state
{up(a,0);up(b,0);up(c,0);light(0)}. %aquí declaras un modelo, que es si tenemos estos estados lo que puede pasar
%Atoms for resulting state
{up(a,1);up(b,1);up(c,1);light(1)}.
%Actions
1 {t(a);t(b);t(c)} 1.
%Initial state
light(0).
up(a,0).
up(b,0).
up(c,0). 
%Execute action ta.
t(a). %:- not ta.
%Efectos de las acciones (effect axions)
%Each switch changes light state
:-switch(X), t(X), not light(0), not light(1).
:-switch(X), t(X) , light(0), light(1).
%Each toggle changes its own switch
:-switch(X), t(X), up(X,0), up(X,1).
:-switch(X), t(X), not up(X,0), not up(X,1).

%El frame problem es que en razonamiento de sentido comun,
%tu en logica clasica tenemos que decir todas las acciones que pueden pasar

%FRAME AXIOMS: things do not change unless stated
:-switch(X), t(X), switch(Y),Y!=X ,up(Y,0), not up(Y,1).
:-switch(X), t(X), switch(Y),Y!=X ,not up(Y,0), up(Y,1).
%FRAME PROBLEM: Lo que hace es que tienes que poner explicitamente todo aquello
%que no cambio cuando hacemos una accion

%Las variables las vamos a representar por mayúsculas


%Cuando pones p(x,y) es un predicado, antes teníamos
%a0= "el interruptor está arriba en el estado J"
%si esto lo tengo que parametrizar, tenemos las variables
%interruptor X y estado J, pues el predicado es P(X,J)

%También cuado declaramos switch(a). , switch(b). y switch(c).
%podemos abreviarlo como switch(a;b;c)