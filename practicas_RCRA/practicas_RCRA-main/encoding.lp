#const n=8.
row(0..n-1).
col(0..n-1).
cell(X,Y) :- row(X),col(Y).

%n {queen(X,Y) : row(X), col(Y)} n.
%1 {queen(X,Y) : row(X)} 1 :- col(Y).
1 {queen(X,Y) : col(Y)} 1 :- row(X). %Por cada fila, escoja una columna y solo una para colcar una dama, con los dos puntos indicas el cojunto de Y tales que col de Y
%este conjunto está diciendo si por ejemplo estas en la fila 1, está metiendo en el conjunto uno a cada uno
%sta haceindo queen01,queen11,queen21..queen71
:-queen(Y,X), queen(Y,X'), X!=X'.
:-queen(X,Y), queen(X2,Y2), |X-X2| = |Y-Y2|, (X,Y) != (X2,Y2).
