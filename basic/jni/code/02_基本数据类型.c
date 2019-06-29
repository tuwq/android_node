#include<stdlib.h>
#include<stdio.h>
main() {
	// char占1个字节,Java的char占2个字节 
	 printf("char类型占%d个字节\n",sizeof(char));
	 printf("short类型占%d个字节\n",sizeof(short));
	 printf("int类型占%d个字节\n",sizeof(int));
	 printf("long类型占%d个字节\n",sizeof(long));
	 printf("float类型占%d个字节\n",sizeof(float));
	 printf("double类型占%d个字节\n",sizeof(double));
	 
	 unsigned char c = 255; // 无符号char255是上限 
	 printf("c = %d\n",c);
	 system("pause");
}
