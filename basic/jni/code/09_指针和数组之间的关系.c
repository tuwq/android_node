#include<stdlib.h>
#include<stdio.h>
// ����ռ��һ���������ڴ� 
// ָ���������p+1 +2������λ������ ÿ+1֮���ƶ������ֽ� ȡ����ָ�����������
// int* p  p+1֮���ƶ��ĸ��ֽ�  char* p p+1֮���ƶ�һ���ֽ� 


main() {
	// ����һ��char���� 
	// char arr[] = {'a','b','c','d','\0'};
	int arr[] = {1, 2, 3, 4, 5};
	printf("arr[0]�ĵ�ַ��%#x\n", &arr[0]);
	printf("arr[1]�ĵ�ַ��%#x\n", &arr[1]);
	printf("arr[2]�ĵ�ַ��%#x\n", &arr[2]);
	printf("arr[3]�ĵ�ַ��%#x\n", &arr[3]);
	
	printf("��������arr�ĵ�ַ��%#x\n", &arr);
	
	char* p = &arr[0];
	p = &arr;
//	printf("*(p+0)=%c\n", *(p+0)); 
//	printf("*(p+1)=%c\n", *(p+1)); 
//	printf("*(p+2)=%c\n", *(p+2)); 
//	printf("*(p+3)=%c\n", *(p+3)); 
	printf("p+0 = %#x\n", p+0);
	printf("p+1 = %#x\n", p+1);
	printf("p+2 = %#x\n", p+2);
	printf("p+3 = %#x\n", p+3);
//	printf("*(p+0)=%d\n", *(p+0));
//	printf("*(p+1)=%d\n", *(p+1));
//	printf("*(p+2)=%d\n", *(p+2));
//	printf("*(p+3)=%d\n", *(p+3));
	system("pause");
}
