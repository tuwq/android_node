#include<stdlib.h>
#include<stdio.h>

// 32位环境 4个字节		编译环境 操作系统平台 cpu 2^32 4byte 
// 64位环境 8个字节 	
main() {
	int* p;
	double* p1;
	printf("int*占%d个字节\n", sizeof p);
	printf("double*占%d个字节\n", sizeof p1);
	system("pause");
}
