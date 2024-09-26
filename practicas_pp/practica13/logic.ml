type log_exp =
Const of bool
| Var of string
| Neg of log_exp
| Disj of log_exp * log_exp
| Conj of log_exp * log_exp
| Cond of log_exp * log_exp
| BiCond of log_exp * log_exp;;

type oper = Not;;
type biOper = Or | And | If | Iff;;
type prop =
C of bool
| V of string
| Op of oper * prop
| BiOp of biOper * prop * prop;;

let rec eval ctx = function
Const b -> b
| Var s -> List.assoc s ctx
| Neg e -> not (eval ctx e)
| Disj (e1, e2) -> (eval ctx e1) || (eval ctx e2)
| Conj (e1, e2) -> (eval ctx e1) && (eval ctx e2)
| Cond (e1, e2) -> (not (eval ctx e1)) || (eval ctx e2)
| BiCond (e1, e2) -> (eval ctx e1) = (eval ctx e2);;

let rec prop_of_log_exp = function 
  Const c -> C c
  | Var s -> V s
  | Neg e -> Op (Not,prop_of_log_exp e)
  | Disj (e1,e2) -> BiOp (Or,prop_of_log_exp e1, prop_of_log_exp e2)
  | Conj (e1,e2) -> BiOp (And, prop_of_log_exp e1,prop_of_log_exp e2)
  | Cond (e1,e2) -> BiOp (If,prop_of_log_exp e1,prop_of_log_exp e2)
  | BiCond (e1,e2) -> BiOp (Iff,prop_of_log_exp e1,prop_of_log_exp e2);;

let rec log_exp_of_prop=function
  C c -> Const c
  | V s -> Var s
  | Op (Not,e)->Neg (log_exp_of_prop e)
  | BiOp (Or,e1,e2)-> Disj (log_exp_of_prop e1, log_exp_of_prop e2)
  | BiOp (And,e1,e2)-> Conj (log_exp_of_prop e1, log_exp_of_prop e2)
  | BiOp (If,e1,e2)-> Cond (log_exp_of_prop e1, log_exp_of_prop e2)
  | BiOp (Iff,e1,e2)-> BiCond (log_exp_of_prop e1, log_exp_of_prop e2);;

let opval = function
  Not -> not ;;

let biopval = function
  Or -> (||)
  | And -> (&&)
  | If -> (!=)
  | Iff -> (==);;

let rec peval ctx = function
  C c ->c
  | V s -> List.assoc s ctx
  | Op (Not,e)->opval Not (peval ctx e)
  | BiOp (Or,e1,e2)->biopval Or (peval ctx e1) (peval ctx e2)
  | BiOp (And,e1,e2)->biopval And (peval ctx e1) (peval ctx e2)
  | BiOp (If,e1,e2)->biopval If (peval ctx e1) (peval ctx e2)
  | BiOp (Iff,e1,e2)->biopval Iff (peval ctx e1) (peval ctx e2);;

  (*EJERCICIO3*)
  let tabla_verdad variables=
  let rec aux lista_tuplas vars =
  match vars with
  [] -> [(List.rev lista_tuplas)]
  | h::t -> (aux ((h,true)::lista_tuplas) t) @ (aux ((h,false)::lista_tuplas) t)
  in aux [] variables;;
  
  let rec unir_sin_duplicar l1 l2=
  match l1 with
  [] -> l2
  | h::t -> if List.mem h l2 then unir_sin_duplicar t l2
  else unir_sin_duplicar t (h::l2);;
  
  let lista_variables p=
  let rec aux lista prop=
  match prop with
  C x -> lista
  | V x -> if (List.mem x lista) then lista else x::lista
  | Op (_,a) -> aux lista a
  | BiOp (_,a,b) -> unir_sin_duplicar (aux lista a) (aux lista b)
  in aux [] p;;
  
  let is_tau prop=
  let variables=lista_variables prop in
  let tabla=tabla_verdad variables in
  let rec aux lista tau=
  match lista with
  [] -> tau
  | h::t -> aux t (tau && (peval h prop))
  in aux tabla true;;
(*Tenemos que crear la combinacion de todos los posibles valores  *)