#include<stdlib.h>
#include<stdio.h>

// c ���ڴ�Ĵ�С ȡ���ڵ�ǰϵͳ��״̬ ���ڴ� ��������
// Java������ڴ� new
// malloc memory allocation ������ڴ�
// ���ղ��� ������ڴ�Ĵ�С
// ����ֵ ���뵽���ڴ���׵�ַ 
// Java�Ķ��ڴ�������������մ���
// c�Ķ��ڴ� ��Ҫ����Ա�ֶ�����
// free �ͷ�ͨ��malloc����Ķ��ڴ� ��ǰ���ڴ���Ա���������ʹ�� 
main() {
	// ������Դ��5��Ԫ�ص�int����ֵ�Ķ��ڴ� 
	int* p = malloc(sizeof(int)*5);
	// c forѭ�� ѭ������������Ҫ�ŵ�for������ ��������ʹ�� 
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
