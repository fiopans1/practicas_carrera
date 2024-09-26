(*Se ha construido el siguiente tipo de dato con el fin de poder representar
 árboles binarios donde la información que aparece en cada nodo puede ser de
  cualquier tipo:
   type 'a arbol_binario =
      Vacio
    | Nodo of 'a * 'a arbol_binario * 'a arbol_binario;;
Se pide:
construya las funciones in_orden, pre_orden, post_orden y anchura,
 todas ellas de tipo 'a arbol_binario -> 'a list, que devuelvan
  los correspondientes recorridos sobre un árbol binario dado, tal y
   como se muestra en los siguientes ejemplos:
*)
type 'a arbol_binario =
      Vacio
    | Nodo of 'a * 'a arbol_binario * 'a arbol_binario;;

let rec in_orden =function
Vacio->[]
| Nodo (a,izq,der)-> (in_orden izq)@a::(in_orden der);;

let rec pre_orden =function
Vacio->[]
| Nodo (a,izq,der)-> a::(pre_orden izq)@(pre_orden der);;

let rec post_orden =function
Vacio->[]
| Nodo (a,izq,der)-> (post_orden izq)@(post_orden der)@[a];;

let anchura tree= let rec recorrido acum=function
[]-> List.rev acum
| Vacio::t -> recorrido acum t
| Nodo (a, Vacio, Vacio)::t-> recorrido (a::acum) t
| Nodo (a,r, Vacio)::t | Nodo (a,Vacio,r)::t-> recorrido (a::acum) (t@[r])
| Nodo (a,i,d)::t -> recorrido (a::acum) (t@[i]@[d])
in recorrido [] [tree];;


(*Para probar:
let t= Nodo(3,Nodo(2,Vacio,Vacio),Nodo(5,Nodo(4,Vacio,Vacio),Nodo(1,Vacio,Vacio)));;
in_orden t;;
pre_orden t;;
post_orden t;;
anchura t;;
*)