open G_tree;;

let rec breadth_first = function
Gt (x, []) -> [x]
| Gt (x, (Gt (y, t2))::t1) -> x :: breadth_first (Gt (y, t1@t2));;

let rec breadth_first' l = function
Gt (x, []) -> (List.rev_append (List.rev l) [x])
| Gt (x, (Gt (y, t2))::t1) -> breadth_first' (List.rev_append (List.rev l) [x]) (Gt (y, (List.rev_append (List.rev t1) t2)));;

let breadth_first_t t= breadth_first' [] t;;

let rec init = function i -> function f ->
  let rec init_aux = (function (l, n, m) ->
    if n > m then init_aux ((f m)::l, n, m + 1) else l)
  in init_aux ([], i, 0);;

let t = Gt(0, init 1_000_000 (fun x -> Gt(x, [])));;