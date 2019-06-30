#include<stdlib.h>
#include<stdio.h>
/*
	%d		int
	%ld 	long int
	%lld 	long long
	%hd 	短整型
	%c 		char
	%f 		float
	%lf 	double
	%u 		无符号数
	%x 		十六进制输出 int或者long int或者short int
	%o 		八进制输出
	%s 		字符串
	
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
	printf("f = %.2f\n", f); // 默认输出小数的时候,默认保留六位有效数字,可以通过%.2f来指定有效数字的位数 
	printf("d = %.7lf\n", d);
	
	printf("i八进制%#o\n", i);
	printf("i十六进制%#x\n", i);
	
	// c定义数组,[]必须放在变量名后面,c的数组不检测越界 
	char str[] = {'a','b','c','d','\0'}; // \0就是字符串结束的标志 
	char str1[] = "hello world! 你好世界";
	
	printf("%s\n", str1);
	
	system("pause");
}
