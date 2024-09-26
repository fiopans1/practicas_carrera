{t1;t2;p1;p2}.
%di: hay una dama, podemos usar not ti como que hay dama
%ti: hay un tigre
%pi: la puerta está abierta

%frase1: not t1 y t2 <-> p1
% not t1 y t2 -> p1
:- not t1, t2, not p1.
% not t1 y t2 <- p1
:- p1, t1.
:- p1, not t2.

%p2 <-> (t1 and not t2) or (t2 and not t1)
%p2 <> t1 xor t2
 :- t1, not t2, not p2.
 :- t2, not t1, not p2.
%p2 <- t1 xor t2
:-p2, t1, t2.
:-p2, not t1, not t2.

%Only one door tells the truth
:- p1, p2.
:- not p1, not p2.