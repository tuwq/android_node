#include<stdlib.h>
#include<stdio.h>

// ��̬�ڴ����,�������ջ�ڴ� ջ�ڴ��ص� ��С�̶� ��ϵͳ����ϵͳ���� 
int* function() {
	// ����һ��int���͵����� 
	int arr1[] = {1, 2, 3, 4, 5};
	// ��int���͵�ָ���������int������׵�ַ 
	int* p = &arr1;
	printf("arr1�ĵ�ַ%#x\n", arr1);
	return p;
}

int * function2() {
	int arr2[] = {5, 4, 3, 2, 1};
	int* p = &arr2;
	printf("arr2�ĵ�ַ%#x\n", arr2);
	return p;
}

main(){
	int* q = function();
	function2();
	printf("*q=%d, *(q+1)=%d, *(q+2)=%d\n", *q, *(q+1), *(q+2));
	printf("*q=%d, *(q+1)=%d, *(q+2)=%d\n", *q, *(q+1), *(q+2));
	system("pause");
} 
