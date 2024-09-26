row(1..N) :- size(N).
col(1..N) :- size(N).

%Todos los posibles segmentos:
{seg((X,Y), (X1,Y1)): row(X), col(Y), row(X1), col(Y1)}.

%HAY QUE CONSEGUIR QUE NO GENERE SEGMENTOS EN DIAGONAL
:- number((X,Y),_), not seg((X1,Y1),(X,Y)), row(X1), col(Y1), (X,Y)!=(X1,Y1), |X-X1| != |Y-Y1|.%A cada circulo debe llegar un segmento
:- number((X,Y),_), not seg((X,Y),(X1,Y1)), row(X1), col(Y1), (X,Y)!=(X1,Y1), |X-X1| != |Y-Y1|.%De cada circulo debe salir un segmento

%:- seg((X1,Y1),(X2,Y2)), not seg((X,Y),(X1,Y1)), row(X), col(Y), (X,Y)!=(X1,Y1), (X,Y)!=(X2,Y2).%A cada segmento debe llegar otro
%:- seg((X1,Y1),(X2,Y2)), not seg((X2,Y2),(X,Y)), row(X), col(Y), (X,Y)!=(X1,Y1), (X,Y)!=(X2,Y2).%De cada segmento debe salir otro

%No puede haber segmentos de longitud 0
%:- seg((X,Y),(X,Y)). 

%No puede haber segmentos en diagonal.
%:- seg((X,Y),(X1,Y1)), Y!=Y1, X!=X1.

































%No puede haber segmentos repetidos
%:- seg((X,Y),(X1,Y1)), seg((X1,Y1),(X,Y)).

%En segmentos horizontales, la primera componente del primer punto debe ser menor que la del segundo
%:- seg((X,Y),(X,Y1)), Y>Y1.

%En segmentos verticales, la segunda componente del primer punto debe ser menor que la del segundo
%:- seg((X,Y),(X1,Y)), X>X1.

%Los círculos blancos deben atravesarse en línea recta
%:- number((X,Y),N), N>1, seg((X1,Y1),(X,Y)), seg((X,Y),(X2,Y2)), Y1!=Y2, X1!=X2.

%Los círculos negros no pueden atravesarse en línea recta
%:- number((X,Y),N), N<1, seg((X1,Y1),(X,Y)), seg((X,Y),(X2,Y2)), not Y1!=Y2, not X1!=X2.

% Restricciones para que haya segmentos que forman parte de otros más grandes
%:- seg((X,Y),(X1,Y)), seg((X2,Y),(X3,Y)), |X-X2|+|X1-X2| = |X-X1|, |X-X3|+|X1-X3| = |X-X1|, X2!=X, X2!=X1, X3!=X, X3!=X1. %P.ej no puede darse que seg((1,1),(5,1)) y seg((2,1),(3,1))
%:- seg((X,Y),(X,Y1)), seg((X,Y2),(X,Y3)), |Y-Y2|+|Y1-Y2| = |Y-Y1|, |Y-Y3|+|Y1-Y3| = |Y-Y1|, Y2!=Y, Y2!=Y1, Y3!=X, Y3!=Y1. %P.ej no puede darse que seg((1,1),(1,5)) y seg((1,2),(1,3))

%P.ej no puede darse que seg((1,1),(5,1)) y seg((1,1),(3,1)) o seg((3,1),(1,1)) o seg((3,1),(5,1)) o seg((5,1),(3,1))
%:- seg((X,Y),(X1,Y)), seg((X2,Y),(X1,Y)), |X-X2|+|X1-X2| = |X-X1|, X2!=X, X2!=X1.
%:- seg((X,Y),(X1,Y)), seg((X2,Y),(X,Y)), |X-X2|+|X1-X2| = |X-X1|, X2!=X, X2!=X1.
%:- seg((X,Y),(X1,Y)), seg((X1,Y),(X2,Y)), |X-X2|+|X1-X2| = |X-X1|, X2!=X, X2!=X1.
%:- seg((X,Y),(X1,Y)), seg((X,Y),(X2,Y)), |X-X2|+|X1-X2| = |X-X1|, X2!=X, X2!=X1.

%P.ej no puede darse que seg((1,1),(1,5)) y seg((1,1),(1,3)) o seg((1,3),(1,1)) o seg((1,3),(1,5)) o seg((1,5),(1,3))
%:- seg((X,Y),(X,Y1)), seg((X,Y2),(X,Y1)), |Y-Y2|+|Y1-Y2| = |Y-Y1|, Y2!=Y, Y2!=Y1.
%:- seg((X,Y),(X,Y1)), seg((X,Y2),(X,Y)), |Y-Y2|+|Y1-Y2| = |Y-Y1|, Y2!=Y, Y2!=Y1.
%:- seg((X,Y),(X,Y1)), seg((X,Y1),(X,Y2)), |Y-Y2|+|Y1-Y2| = |Y-Y1|, Y2!=Y, Y2!=Y1.
%:- seg((X,Y),(X,Y1)), seg((X,Y),(X,Y2)), |Y-Y2|+|Y1-Y2| = |Y-Y1|, Y2!=Y, Y2!=Y1.

%No puede haber un circulo en medio de un segmento
%:- number((X,Y),N), seg((X1,Y),(X2,Y)), |X1-X|+|X2-X| = |X1-X2|, X!=X1, X!=X2.
%:- number((X,Y),N), seg((X,Y1),(X,Y2)), |Y1-Y|+|Y2-Y| = |Y1-Y2|, Y!=Y1, Y!=Y2.

%El número en cada círculo debe ser la suma de las longitudes de las 2 líneas rectas de segmentos que salen de ese círculo
%Círculo blanco
%- Línea horizontal
%:- number((X,Y),N), seg((X,Y1),(X,Y)), seg((X,Y),(X,Y2)), |Y1-Y2|!=N.

%- Línea vertical
%:- number((X,Y),N), seg((X1,Y),(X,Y)), seg((X,Y),(X2,Y)), |X1-X2|!=N.

%Círculo negro
%La línea entra por la misma fila y sale por la misma columna
%:- number((X,Y),N), seg((X,Y1),(X,Y)), seg((X,Y),(X1,Y)), |Y-Y1|+|X-X1|!=|N|.

%La línea entra por la misma columna y sale por la misma fila
%:- number((X,Y),N), seg((X1,Y),(X,Y)), seg((X,Y),(X,Y1)), |Y-Y1|+|X-X1|!=|N|.

#show seg/2.
