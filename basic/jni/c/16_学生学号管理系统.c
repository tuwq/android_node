#include<stdlib.h>
#include<stdio.h>

// 输入学生的人数
// 根据学生的人数申请堆内存 用来保存学生的学号
// 让用户输入学号 展示
// 来新人 申请新的堆内存
// 根据新人个数决定申请多大新的堆内存
// 接收用户输入新人的学号
// 展示所有人的学号
// realloc re-allocation 重新分配堆内存(malloc的返回值, 要申请的堆内存的总大小)
main() {
	printf("请输入学生的人数");
	// count用来保存学生的人数 
	int count;
	scanf("%d", &count);
	// 根据用户输入申请堆内存
	int* p = malloc(sizeof(int)*count);
	int i;
	for(i = 0;i < count; i++) {
		printf("请输入第 %d 学生的学号", i+1);
		scanf("%d", p+i);
	}
	for(i = 0;i < count; i++) {
		printf("第 %d 学生的学号是 %d\n", i+1, *p+i);
	}
	printf("请输入新来的同学人数:\n");
	int newCount;
	scanf("%d", &newCount);
	// 调用realloc 重新申请足够大的堆内存 
	p = realloc(p, sizeof(int)*(count+newCount));
	// 输入新来同学的学号 
	for(i = count;i < count + newCount; i++) {
		printf("请输入第 %d 学生的学号", i+1);
		scanf("%d", p+i);
	}
	// 展示所有同学的学号 
	for(i = 0;i < count + newCount; i++) {
		printf("第 %d 学生的学号是 %d\n", i+1, *p+i);
	}
	system("pause");
} 
