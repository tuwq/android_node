#include<stdlib.h>
#include<stdio.h>

// int* p;
// int * p;
// int *p;
// ���� * ������ ����һ��ָ����� ��������������ڴ��ַ 
main() {
	int i = 123;
	printf("i�ĵ�ַ%#x\n",&i);
	int* p = &i;
	printf("p��ֵ%#x\n",p);
	printf("*p��ֵ%d\n",*p);
	printf("&p��ֵ%#x\n",&p);
	
	// ����i 
	*p = 789;
	printf("i = %d\n", i);
	
	// *p  �ڵȺ���� ��ֵ �ҵ�p�б���ĵ�ַ��Ӧ���ڴ�,������ڴ���и�ֵ�Ĳ��� 
	// p  ���������������ĵ�ַ 
	// &p ָ���Լ��ĵ�ַ 
	
	system("pause");	
}

