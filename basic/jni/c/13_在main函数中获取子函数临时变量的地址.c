#include<stdlib.h>
#include<stdio.h>

function(int** q) {
	int i = 123;
	*q = &i; // *q = p = &i
	printf("i�ĵ�ַ%#x\n", &i);
}

main() {
	int* p;
	function(&p);
	printf("*p��ֵ %d\n", *p);
	printf("p�ĵ�ַ %#x\n", p);
	printf("*p��ֵ %d\n", *p);
	system("pause");
}
