#include<stdlib.h>
#include<stdio.h>

main() {
	printf("������༶������\n");
	int count;
	printf("count�ĵ�ַ:%d\n", &count); 
	scanf("%d",&count); // &ȡ��ַ��
	printf("�༶������Ϊ%d\n", count); 
	
	printf("������༶������\n");
	char name[8];
	printf("name�ĵ�ַ:%d\n", &name); 
	scanf("%s",&name);
	printf("�༶��������:%s", name); 
	system("pause");
}
