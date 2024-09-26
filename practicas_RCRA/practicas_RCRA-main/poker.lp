palo(diamantes;corazones;treboles;picas).
valor(2..10;as;k;q;j).


%mano(V,P) = significa tengo la carta V P

5 {mano(V,P) : valor(V), palo(P)} 5.
:- not 3 {mano(as,P) : palo(O)} 3. %No puede ser que la cardinalidad del conjunto de ases en la mano sea menor que 3
:- mano(V,P), v!=as, mano(V,P2), P!=P2. %No puedes tener ninguna carta que no son ases que son repertidas, no pueden tener el mismo valor V para palos distintos
#show mano/2.