#include<stdlib.h>
#include<stdio.h>

// ����ѧ��������
// ����ѧ��������������ڴ� ��������ѧ����ѧ��
// ���û�����ѧ�� չʾ
// ������ �����µĶ��ڴ�
// �������˸��������������µĶ��ڴ�
// �����û��������˵�ѧ��
// չʾ�����˵�ѧ��
// realloc re-allocation ���·�����ڴ�(malloc�ķ���ֵ, Ҫ����Ķ��ڴ���ܴ�С)
main() {
	printf("������ѧ��������");
	// count��������ѧ�������� 
	int count;
	scanf("%d", &count);
	// �����û�����������ڴ�
	int* p = malloc(sizeof(int)*count);
	int i;
	for(i = 0;i < count; i++) {
		printf("������� %d ѧ����ѧ��", i+1);
		scanf("%d", p+i);
	}
	for(i = 0;i < count; i++) {
		printf("�� %d ѧ����ѧ���� %d\n", i+1, *p+i);
	}
	printf("������������ͬѧ����:\n");
	int newCount;
	scanf("%d", &newCount);
	// ����realloc ���������㹻��Ķ��ڴ� 
	p = realloc(p, sizeof(int)*(count+newCount));
	// ��������ͬѧ��ѧ�� 
	for(i = count;i < count + newCount; i++) {
		printf("������� %d ѧ����ѧ��", i+1);
		scanf("%d", p+i);
	}
	// չʾ����ͬѧ��ѧ�� 
	for(i = 0;i < count + newCount; i++) {
		printf("�� %d ѧ����ѧ���� %d\n", i+1, *p+i);
	}
	system("pause");
} 
