#script (python)
from clingo.symbol import String
def pos(x,y):
    return String(f"{str(x)},{str(y)}!")
#end.

viz_node((X,Y)) :- size(N),X=0..N-1,Y=0..N-1.
viz_edge((I,J)) :- seg(I,J).
viz_attr(graph_nodes, default, shape, circle).
viz_attr(graph_nodes, default, style, filled).
viz_attr(graph_nodes, default, width, "0.1").
viz_attr(graph_nodes, default, fillcolor, black).
viz_circled((X,Y)) :- number((X,Y),_).
viz_attr(node,(X,Y),label, M) :- number((X,Y),N),M=|N|.
viz_attr(node,(X,Y),label, "") :- viz_node((X,Y)), not viz_circled((X,Y)).
viz_attr(node,(X,Y),pos,@pos(Y,-X)):- viz_node((X,Y)).
viz_attr(node,(X,Y),fillcolor,black):- number((X,Y),N),N<0.
viz_attr(node,(X,Y),fontcolor,white):- number((X,Y),N),N<0.
viz_attr(node,(X,Y),fillcolor,white):- number((X,Y),N),N>0.