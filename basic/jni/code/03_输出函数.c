#include<stdlib.h>
#include<stdio.h>
/*
	%d		int
	%ld 	long int
	%lld 	long long
	%hd 	������
	%c 		char
	%f 		float
	%lf 	double
	%u 		�޷�����
	%x 		ʮ��������� int����long int����short int
	%o 		�˽������
	%s 		�ַ���
	
	12345678 1011 1100 0110 0001 0100 1110
	24910 	 110 0001 0100 1110 
*/
main() {
	char c = "c";
	short s = 1234;
	int i = 12345678;
	long l = 1234567890;
	float f = 3.14;
	double d = 3.1415926;
	printf("c = %c\n",c);
	printf("s = %hd\n",s);
	printf("i = %d\n",i);
	printf("l = %ld\n",l);
	printf("f = %.2f\n", f); // Ĭ�����С����ʱ��,Ĭ�ϱ�����λ��Ч����,����ͨ��%.2f��ָ����Ч���ֵ�λ�� 
	printf("d = %.7lf\n", d);
	
	printf("i�˽���%#o\n", i);
	printf("iʮ������%#x\n", i);
	
	// c��������,[]������ڱ���������,c�����鲻���Խ�� 
	char str[] = {'a','b','c','d','\0'}; // \0�����ַ��������ı�־ 
	char str1[] = "hello world! �������";
	
	printf("%s\n", str1);
	
	system("pause");
}
