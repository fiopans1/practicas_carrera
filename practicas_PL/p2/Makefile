FUENTE = practica2
PRUEBA = ejemplos_XML/textoentrecerrarabriretiqueta.xml
LIB = ll

all: compile run

compile:
	flex --yylineno $(FUENTE).l
	bison  -o  $(FUENTE).tab.c $(FUENTE).y -yd -r all
	gcc -o $(FUENTE) lex.yy.c $(FUENTE).tab.c -$(LIB) -ly

run:
	./$(FUENTE) < $(PRUEBA)

run2:
	./$(FUENTE) $(PRUEBA)

clean:
	rm $(FUENTE) lex.yy.c $(FUENTE).tab.c $(FUENTE).tab.h $(FUENTE).output

