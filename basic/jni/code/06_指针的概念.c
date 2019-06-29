#include<stdlib.h>
#include<stdio.h>

// int* p;
// int * p;
// int *p;
// 类型 * 变量名 声明一个指针变量 用来保存变量的内存地址 
main() {
	int i = 123;
	printf("i的地址%#x\n",&i);
	int* p = &i;
	printf("p的值%#x\n",p);
	printf("*p的值%d\n",*p);
	printf("&p的值%#x\n",&p);
	
	// 操作i 
	*p = 789;
	printf("i = %d\n", i);
	
	// *p  在等号左边 赋值 找到p中保存的地址对应的内存,对这块内存进行赋值的操作 
	// p  保存了其他变量的地址 
	// &p 指针自己的地址 
	
	system("pause");	
}

