(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let is_prime n =
let rec check_from i =
i >= n ||
(n mod i <> 0 && check_from (i+1))
in check_from 2;;

let is_p n =
let rec check_from1 i =
  i >= n ||
  (n mod i <> 0 && check_from1 (i+2))
  in check_from1 3;;

let is_prime2 n =
  if n=2 then true else(
  if n mod 2 = 0 then false else (is_p n));;


let rec next_prime z =
  if is_prime(z)=true then z else next_prime(z+1);;

let rec last_prime_to z =
  if (is_prime(z)=true && z>0) then z else last_prime_to(z-1);;
