#include<stdlib.h>
#include<stdio.h>

// *号作用 * 乘法运算符
// int* p 声明一个指针变量
// *p 要对p中保存的内存地址对应的内存进行操作 

// 返回多个值,实际上并没有返回值 而是把变量的地址传递给函数 函数运行的结果通过指针变量直接修改l临时变量
// 把运行结果保存到临时变量中
// Java中方法 通过返回值来获取方法运行的结果 
void function(int* p, int* q) {
	*p*=2;
	*q*=2;
}

main() {
	int i = 123;
	int j = 456;
	function(&i, &j);
	printf("i = %d, j = %d\n", i , j);
	system("pause");
}

