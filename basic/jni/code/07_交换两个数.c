/*void swap(int i, int j) {
	int temp = i;
	i = j;
	j = temp;
}*/
// ֵ����  		�����˱����б����ֵ 
// ���ô��� 	�������ڴ��ַ��ֵ ����ͨ��ָ����������������ַ��Ӧ������ 
void swap(int* p, int* q) {
	int temp = *p;
	*p = *q;
	*q = temp; 
}

main() {
	int i = 123;
	int j = 456;
	// ֵ���� ͨ��ֵ���� ����ʵ�� �Ӻ����޸���ʱ������ֵ 
	swap(&i, &j);
	printf("i = %d\n",i);
	printf("j = %d\n",j);
	system("pause");
}


