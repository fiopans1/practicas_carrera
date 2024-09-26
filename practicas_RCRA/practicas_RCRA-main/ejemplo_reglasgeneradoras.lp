%2 {p;q;r;s} 3. %le decimos que queremos minimo conjuntos de 2 elementos, pero como máximo de 3
%si ponemos combinaciones de 4 elementos tomados de 2 en 2
%sería si ponemos 2 {p,q,r,s} 2 por ejemplo

%Podemos hacer distintos generadores
{p;q}.
{r;s}.


%Y esto lo podemos meter en las restricciones
:- 3 {p;q;r} 4 %prohibimos que haya combinaciones de 3 o 4 elementos


%un exor de por ejemplo p1 y p2 se puede representar:
% 1 {p1;p2} 1