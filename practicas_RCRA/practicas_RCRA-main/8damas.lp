%queen(X,Y) means there is a queen at X,Y
%declaramos filas y columnas
#const n=8.
row(1..n).
col(1..n).
%n {queen(X,Y) : row(X), col(Y)} n.
%1 {queen(X,Y) : row(X)} 1 :- col(Y).
1 {queen(X,Y) : col(Y)} 1 :- row(X).
:- queen(X,Y), queen(X2,Y), X!=X2. %Si tengo dos X Y y están en la misma columna, eso no puede ser
:-queen(X,Y), queen(X2,Y2), |X-X2|=|Y-Y2|, X!=X2. %Esto evitando que estas dos damas estén en la misma diagonal
:-queen(X,Y), queen(X2,Y2), |X-X2|=|Y-Y2|, Y!=Y2.
#show queen/2.