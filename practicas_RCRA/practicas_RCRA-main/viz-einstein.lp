#script (python)
from clingo.symbol import String
import os
def pos(x,y):
    return String(f"{str(x)},{str(y)}!")
def imgfile(x):
    s=os.getcwd() + "/pics/" + str(x) + ".png"
    return String(s)
#end.

row(person,3).
row(drink,2).
row(pet,1).
cell(X,1..5) :- house(X).
attr(graph_nodes, default, fillcolor, white).
attr(graph_nodes, default, label, "").
attr(graph_nodes, default, fontsize, "20").
node((X,Y)):-cell(X,Y).
attr(node,(X,Y),width,1):-cell(X,Y).
attr(node,(X,Y),shape,square):-cell(X,Y).
attr(node,(X,Y),pos,@pos(X,Y)):-cell(X,Y).
attr(node,(X,Y),style,filled):-cell(X,Y).
attr(node,(H,5),label,H) :- house(H).
attr(node,(H,R),image,@imgfile(V)) :- at(H,T,V),row(T,R).
attr(node,(H,4),label,V) :- at(H,tobacco,V).
attr(node,(H,5),fillcolor,V):- house(H),at(H,color,V).

