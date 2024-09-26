%Atoms for initial state
{a0;b0;c0;light0}.
%Atoms for resulting state
{a1;b1;c1;light1}.
%Actions
{ta;tb;tc}.
%Initial state
light0.
a0.
b0.
c0.
%Execute action ta.
ta. %:- not ta.
%Efectos de las acciones (effect axions)
%Each switch changes light state
:- ta, not light0, not light1.
:-ta , light0, light1.
:-tb, not light0, not light1.
:-tb, light0, light1.
:-tc, not light0, not light1.
%Each toggle changes its own switch
:-ta,a0,a1.
:-ta, not a0, not a1.
:-tb, b0, b1.
:-tb, not b0, not b1.
:-tc, c0, c1.
:-tc, not c0, not c1.

%El frame problem es que en razonamiento de sentido comun,
%tu en logica clasica tenemos que decir todas las acciones que pueden pasar

%FRAME AXIOMS: things do not change unless stated
:-ta, b0, not b1.
:-ta, not b0, b1.
:-ta, c0, not c1.
:-ta, not c0, c1.
%FRAME PROBLEM: Lo que hace es que tienes que poner explicitamente todo aquello
%que no cambio cuando hacemos una accion