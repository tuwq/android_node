#include<stdlib.h>
#include<stdio.h>

// 32λ���� 4���ֽ�		���뻷�� ����ϵͳƽ̨ cpu 2^32 4byte 
// 64λ���� 8���ֽ� 	
main() {
	int* p;
	double* p1;
	printf("int*ռ%d���ֽ�\n", sizeof p);
	printf("double*ռ%d���ֽ�\n", sizeof p1);
	system("pause");
}
