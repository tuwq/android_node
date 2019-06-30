#include<stdlib.h>
#include<stdio.h>

function(int** q) {
	int i = 123;
	*q = &i; // *q = p = &i
	printf("i的地址%#x\n", &i);
}

main() {
	int* p;
	function(&p);
	printf("*p的值 %d\n", *p);
	printf("p的地址 %#x\n", p);
	printf("*p的值 %d\n", *p);
	system("pause");
}
