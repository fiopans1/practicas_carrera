house(1..5).

type(person,(brit;swede;german;dane;norw)).

type(color,(red;blue;green;yellow;white)).

type(tobacco,(pall;blends;prince;dunhill;bluem)).

type(pet,(bird;dog;cat;horse;fish)).

type(drink,(tea;coffe;milk;water;beer)).
%antes de cada tipo estaba escogiendo un valor unico

%en vez de repetir lo mismo varias veces, agrupamos todo en un predicado

1 {at(X,T,V) : type(T,V)} 1 :- house(X), type(T,_).
:- at(X,T,V), at(X',T,V), X!=X'.

:- at(X,color,red), not at(X,person,brit).
:- at(X,person,swede), not at(X,pet,dog).
:- at(X,person,dane), not at(X,drink,tea).

%ejemplo de como seria con cada uno:
%:- paint(X,C), paint(X2,C) , X!=X2. %no puedes tener dos casas que se pinten del mismo color

%1{lives(X,P) : person(P))}1 :- house(X).

%:-lives(X,P), lives(X2,P), X!=X2.
