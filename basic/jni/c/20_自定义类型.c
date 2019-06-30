#include<stdlib.h>
#include<stdio.h>

typedef int i;
typedef struct Student {
	short age;
	char gender;
	double score;
} stud;

main() {
	i j = 123;
	printf("j = %d\n", j);
	
	stud stu = {18, 'f', 100.0};
	printf("ÄêÁä = %d\n", stu.age);
	system("pause");
}
