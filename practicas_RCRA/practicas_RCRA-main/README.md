# Guía de Clingo:

### 1. Comentarios:
Los comentarios en Clingo comienzan con el símoblo % y continúan hasta el final de la línea.

Ejemplo:
```
% Este es un comentario en Clingo
```
### 2. Directivas:
Las directivas en Clingo comienzan con el símbolo "#" y se utilizan para configurar el comportamiento del motor de inferencia. Es como definir una constante.

Ejemplo:
```
#const n=10.
```
### 3. Hechos:
Los hechos son declaraciones que establecen una relación entre un predicado y sus argumentos. Los hechos se escriben en la forma de un predicado sin variables.

Ejemplo:
```
humano(juan).
```
### 4. Reglas:
Las reglas son la forma principal de definir conocimiento en Clingo. Se utilizan para establecer relaciones más complejas entre predicados.

Ejemplo:
```
padre(X,Y) :- hombre(X), progenitor(X,Y).
```
### 5. Cuerpos de reglas:
Los cuerpos de las reglas especifican las condiciones bajo las cuales se debe aplicar la regla. Estas condiciones se escriben en términos de otros predicados.

Ejemplo:
```
hombre(juan) :- padre(jose,juan).
progenitor(jose,juan).

```
### 6. Variables:
Las variables se utilizan para hacer referencia a argumentos que no son conocidos de antemano. Las variables comienzan con una letra mayúscula o un guión bajo.

Ejemplo:
```
padre(X,Y) :- hombre(X), progenitor(X,Y), Y!=maria.

```

### 7. Funciones:
Las funciones se utilizan para construir valores complejos a partir de valores más simples. Las funciones se definen utilizando paréntesis y comas para separar los argumentos.

Ejemplo:
```
mayor(X,Y,Z) :- X = max(Y,Z).
```

### 8. Operadores:
Los operadores se utilizan para combinar valores y expresiones. Los operadores incluyen operadores aritméticos, operadores de comparación y operadores lógicos.
Sirven para por ejemplo indicar que dos variables deben ser diferentes, etc.

Ejemplo:
```
padre(X,Y) :- hombre(X), progenitor(X,Y), Y>18.

```

### 9. Conjuntos:
Los conjuntos se utilizan para agrupar valores relacionados. Los conjuntos se definen utilizando llaves y comas para separar los elementos.

Ejemplo:
```
grupo([juan,pedro,maria]).
```

### 10. Programas completos:

Un programa completo en Clingo consta de una serie de reglas, hechos y directivas. El programa comienza con las directivas y luego sigue con los hechos y las reglas.

Ejemplo:
```
#const n=10.
hombre(juan).
progenitor(jose,juan).
padre(X,Y) :- hombre(X), progenitor(X,Y), Y>18.
```





# TIPOS DE REGLAS:


## Tipos de Reglas en Clingo
### Reglas de Programación
Una regla de programación es la forma más básica de escribir una regla en Clingo. Se compone de un cuerpo y una cabeza y se utiliza para definir un hecho, una regla o un conjunto de hechos.

La cabeza de una regla de programación es la parte izquierda de la regla, y se compone de un átomo. El cuerpo es la parte derecha de la regla, y se compone de una lista de átomos separados por comas.

Por ejemplo:
```
hombre(juan).
mujer(maria).
padre(juan, pedro).

```
En este ejemplo, la cabeza de cada regla es un átomo y el cuerpo está vacío.

### Reglas de Clasificación
Las reglas de clasificación en Clingo se utilizan para definir cómo se clasifica un conjunto de hechos en diferentes categorías. Estas reglas son similares a las reglas de programación, pero en lugar de definir un hecho, definen una categoría.

Por ejemplo:

```
bueno(X) :- puntuacion(X, P), P >= 8.
malo(X) :- puntuacion(X, P), P < 4.
regular(X) :- puntuacion(X, P), P >= 4, P < 8.
```
En este ejemplo, se definen tres categorías diferentes: "bueno", "malo" y "regular". Cada categoría se define en términos de una puntuación, y la regla clasifica cada hecho en la categoría correspondiente.

### Reglas de Proyección
Las reglas de proyección en Clingo se utilizan para seleccionar un subconjunto de un conjunto de hechos. Estas reglas son similares a las reglas de clasificación, pero en lugar de definir categorías, definen un subconjunto de hechos.

Por ejemplo:
```
hombre(X) :- persona(X), genero(X, masculino).
mujer(X) :- persona(X), genero(X, femenino).

```

En este ejemplo, se definen dos subconjuntos de hechos: "hombre" y "mujer". Cada subconjunto se define en términos de un género, y la regla selecciona los hechos correspondientes.


# OTRA GUÍA A PARTE:

Clingo es un lenguaje de programación de lógica y restricciones que se utiliza para resolver problemas de satisfacción de restricciones (CSP). Aquí te proporciono una guía básica de la sintaxis de Clingo:

Clingo se compone de hechos, reglas y directivas.
  * Los hechos son afirmaciones que siempre son verdaderas. Estos se escriben en la forma 
  ***'nombre_de_predicado(argumento1, argumento2, ..., argumenton).' Ejemplo: 'persona(juan, 25, hombre).'***

  * Las reglas se utilizan para deducir hechos nuevos a partir de otros hechos y/o reglas. Una regla consta de una cabeza y un cuerpo. La cabeza es una afirmación que     se deduce a partir del cuerpo. Las reglas se escriben en la forma ***c'abeza :- cuerpo.'*** Ejemplo: 'hijo(X,Y) :- padre(Y,X).'
  
  * Las directivas se utilizan para controlar el comportamiento del programa Clingo. Una directiva típica es la especificación del número máximo de respuestas que         Clingo debe devolver. Ejemplo: #max_model 5. También puede haber constantes, etc

Además, Clingo tiene una serie de operadores que se utilizan para construir fórmulas lógicas. Algunos de los operadores más comunes son:

':-': significa "implica" y se utiliza para definir reglas.<br>
',': significa "y" lógico.<br>
';': significa "o" lógico.<br>
'.': se utiliza para separar los hechos y las reglas.<br>
'not': se utiliza para negar una fórmula lógica.<br>
'=', '!=', '<', '>', '<=', '>=': se utilizan para comparar valores.<br>

Por último, es importante destacar que Clingo permite la utilización de restricciones para modelar problemas de optimización. Estas restricciones se especifican utilizando la palabra clave ':~'. Por ejemplo: ':~ no-factible'. especifica que la solución no es factible.

Esta es una guía muy básica de la sintaxis de Clingo, pero espero que te haya sido útil.

También indicar a mayores que cuando ponemos un hecho solo estamos indicando que es verda, y cuando lo ponemos así: ':- p' estamos indicando que no puede ser p


## Ejemplo explicación de una regla complicada:

```
1 {queen(X,Y) : col(Y)} 1 :- row(X). 
```
La expresión que has dado es una regla en Clingo, que se utiliza en la programación lógica. Esta regla se lee de la siguiente manera:

1. La regla tiene un cuerpo y una cabeza. La cabeza de la regla es "1" y representa el peso o la prioridad de la regla.
2. El cuerpo de la regla es "queen(X,Y) : col(Y)" y establece una condición para que la regla sea válida. Esto significa que se puede agregar un hecho "queen(X,Y)" si   se cumple la condición "col(Y)".
3. El signo ":" se utiliza para denotar la implicación lógica. En este caso, significa que si se cumple la condición, se puede agregar el hecho.
4. La condición "row(X)" en la cabeza de la regla indica que solo se permitirá un hecho "queen(X,Y)" por cada fila "X" del tablero de ajedrez.

En resumen, esta regla establece que se puede agregar un hecho "queen(X,Y)" si la columna "Y" no está ocupada por otra reina y si no hay otra reina en la misma fila "X" del tablero de ajedrez.

Si vamos al ejemplo de encoding.lp, lo que estamos haciendo en verdad es:

Por cada fila, escoja una columna y solo una para colocar una dama, con los dos puntos indicas el cojunto de Y tales que col de Y este conjunto está diciendo si por ejemplo estas en la columna 1, está metiendo en el conjunto uno a cada uno esta haciendo queen01,queen11,queen21..queen71










