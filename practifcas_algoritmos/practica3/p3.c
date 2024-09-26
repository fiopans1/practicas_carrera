/*
AUTORES:
Diego Suarez Ramos : diego.suarez.ramos@udc.es
Ander Varela Martin : ander.varela@udc.es
Brais Rodruiguez Gonzalez : brais.rodriguez.gonzalez@udc.es
*/

/*!!Falta comprobar el funcionamiento correcto de los algoritmos y
hacer los apartados 3 y 4 de inser, 3, 4 y 5*/

#include <stdio.h>
#include <stdbool.h>
#include <sys/time.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define UMBRAL 10
//ALGORITMOS
void ord_ins (int v [], int n){
	int i, x, j;
	for(i=1; i<n; i++){
		x = v[i] ;
		j = i-1 ;
		while( j > -1 && v[j] > x){
			v[j+1] = v[j];
			j = j-1;
		} 
		v[j+1] = x;
	}
}

void mediana3(int v[], int izq , int der){
	int k, m;
	k=(izq+der)/2;
	if (v[k] > v[der]){ // intercambiar (V[k], V[j])
		m=v[k];
		v[k]=v[der];
		v[der]=m;
	}
	if (v[k] > v[izq]){ //s intercambiar (V[k], V[i])
		m=v[k];
		v[k]=v[izq];
		v[izq]=m;
	}
	if (v[izq] > v[der]){ //s intercambiar (V[i], V[j])
		m=v[izq];
		v[izq]=v[der];
		v[der]=m;
	}
}

void ordenarAux (int v[], int izq , int der){
	int pivote, m,i,j;

	if(UMBRAL+izq<= der){
		mediana3(v, izq, der);
		pivote=v[izq];
		i=izq;
		j=der;
		do{
			while(v[i]<=pivote && i<der)i++;
			while(v[j]>=pivote && j>izq)j--;
			m=v[i];
			v[i]=v[j];
			v[j]=m;
		}while(j>=i);
		//intercambiar V[I] Y V[J]
		m=v[i];
		v[i]=v[j];
		v[j]=m;
		//intercambiar V[IZQ] Y V[J]
		m=v[izq];
		v[izq]=v[j];
		v[j]=m;
		ordenarAux (v, izq, j-1);
		ordenarAux (v, j+1, der);
	}
}

void ord_rapida (int v[], int n){
	ordenarAux(v, 0, n-1);
	if (UMBRAL > 1)
		ord_ins(v, n);
}
//FUNCIONES AUXILIARES


double microsegundos() { /* obtiene la hora del sistema en microsegundos */
	struct timeval t;
	if (gettimeofday(&t, NULL) < 0 ){
		return 0.0;
	}
	return (t.tv_usec + t.tv_sec * 1000000.0);
}

void inicializar_semilla() {
	srand(time(NULL));
/*se establece la semilla de una nueva serie de enteros pseudo-aleatorios */
}

void aleatorio(int v [], int n) {
	int i, m=2*n+1;
	for (i=0; i < n; i++)
		v[i] = (rand() % m) - n;
	/* se generan números pseudoaleatorio entre -n y +n */
}

void ascendente(int v [], int n) {
	int i;
	for (i=0; i < n; i++)
		v[i] = i;
}

void descendente(int v[], int n){
	int i;
	for (i=n; i>0; i--)
		v[n-i] = i;

}

void printfalgord(int alg, int orden){
	if(alg==1) printf("Ordenacion por inserccion ");
	if(alg==2) printf("Ordenacion rapida ");
	if(orden==1) printf("con inicialización ascendente\n");
	if(orden==2) printf("con inicialización descendente\n");
	if(orden==3) printf("con inicialización aleatorio\n");
}
//TESTEAR ORDENACION

void printVector(int v[], int n){
	int i;
	for(i=0; i<n;i++){
		if(i==(n-1)) printf("%d", v[i]);
		else printf("%d, ", v[i]);
	}
	printf("\n");
}

int ordenado(int v[], int n){
	int i, b,a;
	for(i=1; i<n;i++){
		a=v[i-1];
		b=v[i];
		if(a>b) return 0;
	}
	return 1;
}
void test(int i, int n,void (*alg_ord)(int v[],int n)){
	int v[n];
	//Ascendente
	aleatorio(v, n);
	printfalgord(i, 3);
	printVector(v, n);
	printf("Ordenado? %d\n", ordenado(v, n));
	printf("Ordenando...\n");
	alg_ord(v,n);
	printVector(v, n);
	printf("Ordenado? %d\n", ordenado(v, n));
	//Descendente
	descendente( v, n);
	printfalgord(i, 2);
	printVector(v, n);
	printf("Ordenado? %d\n", ordenado(v, n));
	printf("Ordenando...\n");
	alg_ord(v,n);
	printVector(v, n);
	printf("Ordenado? %d\n", ordenado(v, n));
}
//CALCULAR TABLA
double calculartiempos(int n,void (*ordenmatriz)(int v [], int n),
	void (*alg_ord)(int v[],int n))
	{

	int v[n],i;
	double t1,t2,t3,t4,t;
	ordenmatriz(v,n);
	t1=microsegundos();
	alg_ord(v,n);
	t2=microsegundos();
	t=t2-t1;
	if(t<500){
		t1=microsegundos();
		for(i=0;i<1000;i++){
			ordenmatriz(v,n);
			alg_ord(v,n);
		}
		t2=microsegundos();
		t3=microsegundos();
		for(i=0;i<1000;i++){
			ordenmatriz(v,n);
		}
		t4=microsegundos();
		printf("(*)");
		t=(t2-t1)-(t4-t3);
		return (t/1000);
	}else{
		printf("   ");
		return t;
	}
}

void tabla(int orden,int alg,void (*ordenmatriz)(int v [], int n),
	void (*alg_ord)(int v[],int n),int tamini,int nmax,float p, int modo)
	{

	double t,contador=tamini;
	int i;
	printfalgord(alg,orden);
	printf("%14s%14s%14s","n","t(n)","t(n)/n^");
	printf("%.2f",p-0.2);
	if(modo==1){ printf("\tn*log(n)"); 
	}else{printf("%13s","t(n)/n^");printf("%.2f",p);}
	printf("%13s","t(n)/n^");
	printf("%.2f\n",p+0.2);
	if(modo==1){
		for(i=1;i<=nmax;i++){
			t=calculartiempos(contador,ordenmatriz,alg_ord);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/(contador*log(contador)),
				t/pow(contador,p+0.2));
			contador=contador*2;
		}
	}else{
		for(i=1;i<=nmax;i++){
			t=calculartiempos(contador,ordenmatriz,alg_ord);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/pow(contador,p),t/pow(contador,p+0.2));
			contador=contador*2;
		}	
	}
}

/*
			Test
1º apartado corresponde al algoritmo que se usa:
	-Ordenacion por insercion(1) y ordenacion rapida(2)
2º apartado corresponde al tamano del vector
*/

/*
			Funcion tiempos

1º apartado corresponde a orden:
	-Ascendente(1), descendente(2) y aleatorio(3)
2º apartado corresponde al algoritmo que se usa:
	-Ordenacion por insercion(1) y ordenacion rapida(2)
3º apartado corresponde a tamano comienzo del vector
4º apartado nº de repeticiones
5º apartado cuota ajustada del algoritmo respecto al vector
6º apartado usar n*logn con 1 o de n^x con 2
*/
int main(){
	inicializar_semilla();
	test(1, 30,ord_ins);
	test(2, 30,ord_rapida);
	tabla(1,1,ascendente,ord_ins,500,8,1,2);
	tabla(1,1,ascendente,ord_ins,500,8,1,2);
	tabla(2,1,descendente,ord_ins,500,8,2,2);
	tabla(3,1,aleatorio,ord_ins,500,8,1.99,2);
	tabla(1,2,ascendente,ord_rapida,500,8,1.01,1);
	tabla(2,2,descendente,ord_rapida,500,8,1.01,1);
	tabla(3,2,aleatorio,ord_rapida,500,8,1.01,1);

	
	return 0;
}