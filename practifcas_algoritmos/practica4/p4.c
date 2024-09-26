/*
AUTORES:
Diego Suarez Ramos : diego.suarez.ramos@udc.es
Ander Varela Martin : ander.varela@udc.es
Brais Rodruiguez Gonzalez : brais.rodriguez.gonzalez@udc.es
*/
#include <stdio.h>
#include <stdbool.h>
#include <sys/time.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define TAM_MAX 1600
#define INF (TAM_MAX+1)
typedef int ** matriz;
typedef struct {
	int x, y, peso;
} arista;
typedef arista tipo_elemento;
typedef struct {
	int cabeza, final, tamano;
	tipo_elemento vector[TAM_MAX];
} cola;
void crear_cola(cola *);
int cola_vacia(cola);
void insertar(tipo_elemento, cola *);
tipo_elemento quitar_primero(cola *);
tipo_elemento primero(cola);
void mostrar_cola(cola);

//CODIGO COLA:
void crear_cola(cola *c) {
	c->tamano = 0;
	c->cabeza = 0;
	c->final = -1;
}

int cola_vacia(cola c) {
	return (c.tamano == 0);
}

void incrementar(int *x) { /* privado */
	if (++(*x) == TAM_MAX)
		*x = 0;
}

void insertar(tipo_elemento x, cola *c) {
	if (c->tamano == TAM_MAX) {
		printf("error: cola llena: %d\n", c->tamano);
		exit(EXIT_FAILURE);
	}
	c->tamano++;
	incrementar(&(c->final));
	c->vector[c->final] = x;
}

tipo_elemento primero(cola c) {
	if (cola_vacia(c)) {
		printf("error: cola vacia\n"); exit(EXIT_FAILURE);
	}
	return(c.vector[c.cabeza]);
}

tipo_elemento quitar_primero(cola *c) {
	tipo_elemento x;
	if (cola_vacia(*c)) {
		printf("error: cola vacia\n");
		exit(EXIT_FAILURE);
	}
	c->tamano--;
	x = c->vector[c->cabeza];
	incrementar(&(c->cabeza));
	return x;
}

void imprimir_arbol(cola *c){
	tipo_elemento x;
	int peso=0;
	
	printf("Aristas: ");
	while (!cola_vacia(*c)) {
		x= quitar_primero(c);
		printf("(%d,%d) ", x.x, x.y);
		peso+=x.peso;
	}
	printf("\nPeso: %d\n", peso);
}
//FIN COLA

//ALGORITMO
void prim(matriz m, int nodos, cola *aristas) {
	/* calcular el árbol de recubrimiento mínimo devolviendo
		las aristas del arbol en la cola ’aristas’ */
	int min, i, j, k=0;
	arista a;
	int *masProximo = (int *) malloc(nodos*sizeof(int));
	int *distanciaMinima = (int *) malloc(nodos*sizeof(int));
	crear_cola(aristas);
	distanciaMinima[0] = -1;
	for(i = 1; i < nodos; i++) {
		masProximo[i] = 0;
		distanciaMinima[i] = m[i][0];
	}
	for(i=1;i<nodos;i++){
		min=INF;
		for(j=1;j<nodos;j++){
			if(0<=distanciaMinima[j] && distanciaMinima[j]<min){
				min=distanciaMinima[j];
				k=j;
			}
		}
		a.x=masProximo[k];
		a.y=k;
		a.peso=distanciaMinima[k];
		insertar(a,aristas);
		distanciaMinima[k]=-1;
		for(j=1;j<nodos;j++){
			if(m[j][k]<distanciaMinima[j]){
				distanciaMinima[j]=m[j][k];
				masProximo[j]=k;
			}
		}
	}
	free(masProximo);
	free(distanciaMinima);
}


//FUNCIONES MATRIZ

matriz crear_matriz(int n) {
	int i;
	matriz aux;
	if ((aux = malloc(n*sizeof(int *))) == NULL)
		return NULL;
	for (i=0; i<n; i++)
		if ((aux[i] = malloc(n*sizeof(int))) == NULL)
			return NULL;
	return aux;
}

void inicializar_matriz(matriz m, int n) {
/* Crea un grafo completo no dirigido con valores aleatorios entre 1 y n */
	int i, j;
	for (i=0; i<n; i++)
		for (j=i+1; j<n; j++)
			m[i][j] = rand() % n + 1;
	for (i=0; i<n; i++)
		for (j=0; j<=i; j++)
			if (i==j)
				m[i][j] = 0;
			else
				m[i][j] = m[j][i];
}

void liberar_matriz(matriz m, int n) {
	int i;
	for (i=0; i<n; i++)
		free(m[i]);
	free(m);
}

//Test:
double microsegundos() { /* obtiene la hora del sistema en microsegundos */
	struct timeval t;
	if (gettimeofday(&t, NULL) < 0 ){
		return 0.0;
	}
	return (t.tv_usec + t.tv_sec * 1000000.0);
}	

int elegirTest(int n){
	int i=0;
	
	switch(n){
		case 1: i= 5;
			break;
		case 2: i= 4;
			break;
		case 3: i= 7;
			break;
	}
	return i;
}

matriz iniciarMatrizTest(int n){
	int i,j;
	matriz  m;
	m= crear_matriz(n);
	switch(n){
		case 5: m[0][1]=1; m[0][2]=8; m[0][3]=4; m[0][4]=7;
			m[1][2]=2; m[1][3]=6; m[1][4]=5; m[2][3]=9;
			m[2][4]=5; m[3][4]=3;
			break;
		case 4: m[0][1]=1; m[0][2]=4; m[0][3]=7; m[1][2]=2; 
			m[1][3]=8; m[2][3]=3; 
			break;
		case 7: m[0][1]=7; m[0][2]=INF; m[0][3]=5; m[0][4]=INF;
			m[0][5]=INF; m[0][6]=INF; m[1][2]=8; m[1][3]=9; 
			m[1][4]=7; m[1][5]=INF; m[1][6]=INF; m[2][3]=INF; 
			m[2][4]=5; m[2][5]=INF; m[2][6]=INF; m[3][4]=15; 
			m[3][5]=6; m[3][6]=INF; m[4][5]=8; m[4][6]=9; m[5][6]=11;
			break;
	}
	for (i=0; i<n; i++)
		for (j=0; j<=i; j++)
			if (i==j)
				m[i][j] = 0;
			else
				m[i][j] = m[j][i];
	return m;
}

void test(void (*alg)(matriz m, int nodos, cola *aristas),int opcion){
	int n=elegirTest(opcion);
	matriz m;
	cola c;
	m= iniciarMatrizTest(n);
	alg(m, n, &c);
	imprimir_arbol(&c);
	liberar_matriz(m,n);
}
double calcularTiempos(void (*alg)(matriz m, int nodos, cola *aristas),int n){
	int i;
	double t1,t2,t;
	matriz m;
	cola c;
	m=crear_matriz(n);
	inicializar_matriz(m, n);
	t1=microsegundos();

	alg(m, n, &c);

	t2=microsegundos();
	t=t2-t1;
	if(t<500){
		t1=microsegundos();
		for(i=0;i<1000;i++){

			alg(m, n, &c);
		}
		t2=microsegundos();
		printf("(*)");
		liberar_matriz(m, n);
		return ((t2-t1)/1000);
	}else{
		printf("   ");
		liberar_matriz(m, n);
		return t;
	}

}

void tabla(void (*alg)(matriz m, int nodos, cola *aristas),
	int tamini, int nmax, float p, int modo) 
	{

	double t, contador=tamini;
	int i;
	printf("Algoritmo de prim con inicializacion aleatoria\n");
	printf("%14s%14s%14s","n","t(n)","t(n)/n^");
	printf("%.2f",p-0.2);
	printf("%13s","t(n)/n^");
	printf("%.2f",p);
	printf("%13s","t(n)/n^");
	printf("%.2f\n",p+0.2);
	if (modo==1){
		for(i=1;i<=nmax && contador<TAM_MAX;i++){
			t=calcularTiempos(alg,contador);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/pow(contador,p),t/pow(contador,p+0.2));
			contador=contador*2;
		}
	}else{
		for(i=1;i<=nmax && contador<TAM_MAX;i++){
			t=calcularTiempos(alg,contador);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/(contador*log(contador)),
				t/pow(contador,p+0.2));
			contador=contador*2;
		}		
	}
}
/*Para la funcion tiempos:
-1 PARAMETRO: ALGORITMO
-2 PARAMETRO: Valor inicial
-3 PARAMETRO: Numero de columnas
-4 PARAMETRO: z correspondiente a n^z*/
int main(){
	
	test(prim,1);
	test(prim,2);
	test(prim,3);
	tabla(prim,10,7,2,1);
	tabla(prim,10,7,2,1);
	tabla(prim,10,7,2,1);

	
	return 0;
}
