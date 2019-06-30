#include<stdlib.h>
#include<stdio.h>

// 联合体 多个变量共享同一块内存 大小取决于最大的变量 
// 联合体 对不同成员多次赋值 只有最后一次赋值有效 
union un { // 4
	short s; // 2
	int i; // 4
};

main() {
	union un u;
	printf("u的大小 %d\n", sizeof(u));
	u.s = 1234;
	u.i = 12345678;
	printf("u.s = %hd\n", u.s);
}
