% Use this editor as a MiniZinc scratch book
include "globals.mzn";

int: nObjetos;
array[1..nObjetos] of int: objetos;
int: capacidad;
int: nContenedores;
array[1..nContenedores, 1..nObjetos] of var 0..1: contenedores;
array[1..nContenedores] of var 0..capacidad: almacenado;
var 0..nContenedores: contenedores_usados;

solve minimize contenedores_usados;

constraint
    forall(b in 1..nContenedores) (
       almacenado[b] = sum(s in 1..nObjetos) (objetos[s] * contenedores[b,s]) ) 
    /\
    sum(s in 1..nObjetos) (objetos[s]) = sum(b in 1..nContenedores) (almacenado[b])
    /\ 
    forall(s in 1..nObjetos) ( 
       sum(b in 1..nContenedores) (contenedores[b,s])  = 1 )
    /\
    decreasing(almacenado) :: domain
    /\
    almacenado[1] > 0 
    /\
    contenedores_usados = sum(b in 1..nContenedores) (bool2int(almacenado[b] > 0))
;

output [
       if (b = 1 /\ s = 1) then "Contenedores necesarios: " ++ show(contenedores_usados) ++ "\n" else "" endif ++
       if s = 1 then show(almacenado[b]) ++ " : " else "" endif ++
          show(contenedores[b,s]) ++ 
           if s = nObjetos then "\n" else " " endif
       | b in 1..nContenedores, s in 1..nObjetos
];

nObjetos = 250;
nContenedores = 150;
objetos = [1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0];
capacidad = 150;