%There are one lady and two tigers.
2 {t1;t2;t3} 2.
%At most, one sign tells the truth.
{p1;p2;p3} 1.

%p1: There is a tiger in this room
%p1<->t1
:- p1, not t1.
:- not p1, t1.
%p2: There is a lady in this room
%p2 <-> not t2
:- p2, t2.
:- not t2, not p2.
%p3: There is a tiger in room2
%p3 <-> t2
:- p3, not t2.
:- t2, not p3.
