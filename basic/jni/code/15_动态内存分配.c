#include<stdlib.h>
#include<stdio.h>

// c 堆内存的大小 取决于当前系统的状态 堆内存 不连续的
// Java申请堆内存 new
// malloc memory allocation 申请堆内存
// 接收参数 申请堆内存的大小
// 返回值 申请到堆内存的首地址 
// Java的堆内存回收由垃圾回收处理
// c的堆内存 需要程序员手动回收
// free 释放通过malloc申请的堆内存 当前的内存可以被其他程序使用 
main() {
	// 申请可以存放5个元素的int类型值的堆内存 
	int* p = malloc(sizeof(int)*5);
	// c for循环 循环变量的声明要放到for的外面 先声明再使用 
	int i;
	for(i=0; i<5; i++) {
		*(p+i) = i;
	}
	printf("*(p+1)=%d\n", *(p+1));
	printf("*(p+2)=%d\n", *(p+2));
	free(p);
	printf("*(p+1)=%d\n", *(p+1));
	printf("*(p+2)=%d\n", *(p+2));
	system("pause");	
}
