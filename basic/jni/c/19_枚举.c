#include<stdlib.h>
#include<stdio.h>

// ͨ��enum����ö������ �������ȡֵֻ�ܴӶ���õ�ֵ��ȡ
// Ĭ�ϵ�һ��������ֵ��0 ����ı������μ�+1 �����ĳ������������ֵ �����ֵ����������Ļ����ϼ���+1 
enum Weekday {
	SUN,MON,TUE,WEND,THUR,FRI,SAT
};

main(){
	enum Weekday day = SAT;
	printf("day = %d\n", day);
	system("pause");
}
