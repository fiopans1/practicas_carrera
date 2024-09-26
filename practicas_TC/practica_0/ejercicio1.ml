(*La función mapdoble recibe como argumentos dos funciones
 y una lista, y devuelve una lista resultado de aplicar la primera
  función a los elementos de la lista que ocupan posición impar,
   y la segunda a los que ocupan posición par.
Por ejemplo:
     # mapdoble (function x -> x) (function x -> -x) [1;1;1;1;1];;
     - : int list = [1; -1; 1; -1; 1]
Se pide:
• Implementelafunciónmapdoble.
• Indique el tipo de la función mapdoble.
• Indique el valor de:
         mapdoble (function x -> x*2) (function x -> "x") [1;2;3;4;5];;
• Indique el tipo de:
let y = function x -> 5 in mapdoble y;;*)



let mapdoble f g list = let rec map f g res pos=function
[]-> List.rev(res)
| h::t -> if(pos mod 2)=0 then (map f g ((g h)::res) (pos+1) t) else (map f g ((f h)::res) (pos+1) t)
in map f g [] 1 list;;
(*Tipo de la función:
   val mapdoble : ('a -> 'b) -> ('a -> 'b) -> 'a list -> 'b list = <fun>*)

(*La expresion "mapdoble (function x -> x*2) (function x -> "x") [1;2;3;4;5];;" daría
   error porque en la segunda función se esperaría un int al evaluar primero la primera
   pero estaríamos devolviendo un char, y ambas funciones deben devolver lo mismo*)


(*- : ('_weak1 -> int) -> '_weak1 list -> int list = <fun>*)


(*Para probar: mapdoble (function x -> x*2) (function x -> x*3) [1;2;3;4;5];;*)