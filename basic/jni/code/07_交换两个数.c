/*void swap(int i, int j) {
	int temp = i;
	i = j;
	j = temp;
}*/
// 值传递  		传递了变量中保存的值 
// 引用传递 	传递是内存地址的值 可以通过指针变量来操作这个地址对应的内容 
void swap(int* p, int* q) {
	int temp = *p;
	*p = *q;
	*q = temp; 
}

main() {
	int i = 123;
	int j = 456;
	// 值传递 通过值传递 不能实现 子函数修改临时变量的值 
	swap(&i, &j);
	printf("i = %d\n",i);
	printf("j = %d\n",j);
	system("pause");
}


