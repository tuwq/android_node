#include<stdlib.h>
#include<stdio.h>

// 通过enum定义枚举类型 这个类型取值只能从定义好的值中取
// 默认第一个变量的值是0 后面的变量依次加+1 如果给某个变量单独赋值 后面的值在这个变量的基础上继续+1 
enum Weekday {
	SUN,MON,TUE,WEND,THUR,FRI,SAT
};

main(){
	enum Weekday day = SAT;
	printf("day = %d\n", day);
	system("pause");
}
