#include<stdlib.h>
#include<stdio.h>

/*
	class Student {
		short age;
		char gender;
		int score;
		void study() {
			system.out.println("");
		}
	}
Student stu = new Student();
�ṹ��Ĵ�С ���ڻ��ߵ������г�Ա��С�ĺ� ���������ĳ�Ա��С�������� 
// ����ָ��Ķ��巽ʽ ����ֵ(*����ָ�������)(�β�) 
*/ 
struct Student { // 16
	short age; // 2
	char gender; // 1
	double score; // 8
	void(*study)();
};
void study() {
	printf("good good study, day day up");
}

main(){
	// struct �ؼ��ֲ��ܶ��� 
	struct Student stu = {18, 'f', 100.0, &study};
	printf("����= %hd\n", stu.age);
	printf("�Ա�= %c\n", stu.gender);
	stu.study = &study;
	stu.study();
	stu.score = 80.0;
	printf("�ɼ�= %lf\n", stu.score);
	printf("stu�ṹ��ռ %d ���ֽ�\n",sizeof stu);
	// ������һ���ṹ���һ��ָ�� ���ָ��ָ����stu������� 
	struct Student* stup = &stu;
	// �ṹ��ָ����ʽṹ������ĳ�Ա (*�ṹ��ָ��).��Ա 
	(*stup).age = 80;
	// �ṹ��ָ����ʽṹ������ĵڶ��ַ�ʽ ָ��->��Ա
	stup->study();
	
	struct Student** p2 = &stup;
	(**p2).age = 70; 
	printf("����= %hd\n", stu.age);
	(*p2)->age = 19; 
	printf("����= %hd\n", stu.age);
	system("pause");
} 
