%The set of facts up(X,T) such that X is a switch
{up(X,T) : switch(X),time(T)}. %Es un conjunto,Mete en el conjunto up tales que x es un switch y t es un time
{light(T): time(T)}. %aquí declaras un modelo, que es si tenemos estos estados lo que puede pasar
1 {t(X,T) : switch(X)} 1 :- time(T), T>0. %primero defino la T y luego por cada T hay 
switch(a;b;c). time(0..2).
%Estado inicial
up(a,0). up(b,0). up(c,0). light(0).
%Execute action ta.
t(a,1). t(c,2).
%Efectos de las acciones (effect axions)
:-switch(X), time(T), t(X,T+1), not light(T), not light(T+1).
:-switch(X), time(T), t(X,T+1), light(T), light(T+1).
:-switch(X), time(T), t(X,T+1), up(X,T), up(X,T+1).
:-switch(X), time(T), t(X,T+1), not up(X,T), not up(X,T+1).

%El frame problem es que en razonamiento de sentido comun,
%tu en logica clasica tenemos que decir todas las acciones que pueden pasar

%FRAME AXIOMS: things do not change unless stated
:-time(T), switch(X), switch(Y),Y!=X, t(X,T+1) ,up(Y,T), not up(Y,T+1).
:-time(T), switch(X), switch(Y),Y!=X, t(X, T+1) ,not up(Y,T), up(Y,T+1).


%El show lo que hace es decirle que solo me muestre esto
#show up/2. %que te muestre la variable up con 2 argumentos
#show light/1.
#show t/2.

%También cuado declaramos switch(a). , switch(b). y switch(c).
%podemos abreviarlo como switch(a;b;c)