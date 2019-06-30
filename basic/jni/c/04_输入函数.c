#include<stdlib.h>
#include<stdio.h>

main() {
	printf("请输入班级的人数\n");
	int count;
	printf("count的地址:%d\n", &count); 
	scanf("%d",&count); // &取地址符
	printf("班级的人数为%d\n", count); 
	
	printf("请输入班级的名字\n");
	char name[8];
	printf("name的地址:%d\n", &name); 
	scanf("%s",&name);
	printf("班级的名字是:%s", name); 
	system("pause");
}
