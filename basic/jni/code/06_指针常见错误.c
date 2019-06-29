#include<stdlib.h>
#include<stdio.h>

// 常见错误(1) 野指针 指针变量没有初始化 就用*p= 进行赋值 修改内存对应的内容 
// (2)指针声明的类型 要跟保存地址的类型一一对应 int类型的指针变量 指向int变量的地址
// double类型的指针变量指向double类型的地址... 
main() {
	int i;
	double d = 1.23345;
	int* p = &i; // 声明一个变量,就是给这个变量分配了一块内存空间 
	printf("p=%#x\n", p);
	// 野指针,指针变量要初始化之后再去做*p 赋值的操作
	// 拿着当前程序中的变量地址 给指针变量进行初始化 
	*p = 12345;
	printf("d = %lf\n", d);
	printf("i=%d\n", i);
	printf("*p=%d\n", *p);
	printf("&p=%#x\n", &p);
	system("pause");
}
