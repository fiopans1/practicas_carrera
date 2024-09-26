(*• Defina una función primero_que_cumple, que dado un predicado 
(es decir, una función de tipo 'a -> bool) y una lista, 
devuelva el primer elemento de la lista que verifica dicho predicado.
• Indique el tipo de la función primero_que_cumple.
• Utilizando la función primero_que_cumple, defina una función existe, que dado un
predicado y una lista devuelva true si en la lista hay algún elemento que verifica el
predicado, y false en caso contrario.
• Se quiere mantener un conjunto de valores etiquetados de cualquier tipo, mediante una lista
de pares 'a * 'b, donde la primera componente del par es la etiqueta o clave, y la segunda es
 el valor asociado a esa clave en dicho conjunto. Utilizando la función primero_que_cumple,
  defina una función asociado : ('a * 'b) list -> 'a -> 'b, que dado un conjunto y una clave,
   devuelva su valor asociado.*)

  let rec primero_que_cumple f = function
  []-> raise (Not_found)
  | h::t -> if (f h) then h else primero_que_cumple f t;;
  (*Para probar: primero_que_cumple (function x-> x>5) [1;2;3;4;5;6;7;8];;*)

  (*El tipo de esta función será:
     val primero_que_cumple : ('a -> bool) -> 'a list -> 'a = <fun>*)

  let existe f l=
  try (primero_que_cumple f l;true) with
    Not_found-> false;;
(*Para probar: existe (function x-> x>5) [1;2;3;4;5;6;7;8];; ->true*)
(*Para probar: existe (function x-> x>10) [1;2;3;4;5;6;7;8];; ->false*)

  let asociado l a= match (primero_que_cumple (function (x,y)-> x=a) l) with
  a,b-> b;;
(*asociado [(1,'a');(2,'b');(3,'c');(4,'d')] 2;;*)
