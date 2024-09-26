(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let rec fib n =
if n <= 1 then n
else fib (n-1) + fib (n-2);;

let imprimir n =
let rec fibo i=
i>=n+1 || (print_int(fib(i))==() && print_string("\n")==() && fibo(i+1))
in fibo 0;;

let y=int_of_string(Sys.argv.(1));;

imprimir y;;
