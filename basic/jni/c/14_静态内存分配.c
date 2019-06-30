#include<stdlib.h>
#include<stdio.h>

// 静态内存分配,分配的是栈内存 栈内存特点 大小固定 由系统分配系统回收 
int* function() {
	// 声明一个int类型的数组 
	int arr1[] = {1, 2, 3, 4, 5};
	// 用int类型的指针变量保存int数组的首地址 
	int* p = &arr1;
	printf("arr1的地址%#x\n", arr1);
	return p;
}

int * function2() {
	int arr2[] = {5, 4, 3, 2, 1};
	int* p = &arr2;
	printf("arr2的地址%#x\n", arr2);
	return p;
}

main(){
	int* q = function();
	function2();
	printf("*q=%d, *(q+1)=%d, *(q+2)=%d\n", *q, *(q+1), *(q+2));
	printf("*q=%d, *(q+1)=%d, *(q+2)=%d\n", *q, *(q+1), *(q+2));
	system("pause");
} 
