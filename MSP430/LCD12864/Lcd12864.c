#include <msp430f5529.h>
#include "Delay.h"
typedef unsigned int uint;
typedef unsigned char uchar;

#define BIT(x)	(1 << (x))

extern const unsigned char shuzi_table[] = { 48, 49, 50, 51, 52, 53, 54, 55, 56,
		57 };

void Send(uchar type, uchar transdata);
/**********Һ������IO�ĺ궨��*************/
#define cyCS      0    //P3.0��Ƭѡ�ź�
#define cySID     1    //P3.1����������  
#define cyCLK     2    //P3.2��ͬ��ʱ��
#define cyPORT    P3OUT  
#define cyDDR     P3DIR

/*******************************************
 �������ƣ�Ini_Lcd
 ��    �ܣ���ʼ��Һ��ģ��
 ��    ������
 ����ֵ  ����
 ********************************************/
void Ini_Lcd(void) {
	cyDDR |= BIT(cyCLK) + BIT(cySID) + BIT(cyCS);   //��Ӧ��λ�˿�����Ϊ���
	delay_ms(100);
	//��ʱ�ȴ�Һ����ɸ�λ
	Send(0, 0x30); /*��������:һ����8λ����,����ָ�*/
	delay_us(72);
	Send(0, 0x02); /*DDRAM��ַ��λ*/
	delay_us(72);
	Send(0, 0x0c); /*��ʾ�趨:����ʾ,����ʾ���,������ǰ��ʾλ��������*/
	delay_us(72);
	Send(0, 0x01); /*��������DDRAM��λַ����������Ϊ��00H��*/
	delay_us(72);
	Send(0, 0x06); /*�������ã����趨:��ʾ�ַ�/����������λ,DDRAM��ַ��1*/
	delay_us(72);
}
/*******************************************
 �������ƣ�Send
 ��    �ܣ�MCU��Һ��ģ�鷢��1һ���ֽڵ�����
 ��    ����type--�������ͣ�0--�������1--��ʾ����
 transdata--���͵�����
 ����ֵ  ����
 ********************************************/
void Send(uchar type, uchar transdata) {
	uchar firstbyte = 0xf8;
	uchar temp;
	uchar i, j = 3;

	if (type)
		firstbyte |= 0x02;

	cyPORT |= BIT(cyCS);
	cyPORT &= ~BIT(cyCLK);
	while (j > 0) {
		if (j == 3)
			temp = firstbyte;
		else if (j == 2)
			temp = transdata & 0xf0;
		else
			temp = (transdata << 4) & 0xf0;

		for (i = 8; i > 0; i--) {
			if (temp & 0x80)
				cyPORT |= BIT(cySID);
			else
				cyPORT &= ~BIT(cySID);
			cyPORT |= BIT(cyCLK);
			temp <<= 1;
			cyPORT &= ~BIT(cyCLK);
		}
		//�����ֽ�֮��һ��Ҫ���㹻����ʱ�������׳���ʱ������
		if (j == 3)
			delay_us(600);
		else
			delay_us(200);

		j--;
	}

	cyPORT &= ~BIT(cySID);
	cyPORT &= ~BIT(cyCS);
}
/*******************************************
 �������ƣ�Clear_GDRAM
 ��    �ܣ����Һ��GDRAM�ڲ����������
 ��    ������
 ����ֵ  ����
 ********************************************/
void Clear_GDRAM(void) {
	uchar i, j, k;

	Send(0, 0x34);        //����չָ�
	i = 0x80;
	for (j = 0; j < 32; j++) {
		Send(0, i++);
		Send(0, 0x80);
		for (k = 0; k < 16; k++) {
			Send(1, 0x00);
		}
	}
	i = 0x80;
	for (j = 0; j < 32; j++) {
		Send(0, i++);
		Send(0, 0x88);
		for (k = 0; k < 16; k++) {
			Send(1, 0x00);
		}
	}
	Send(0, 0x30);        //�ص�����ָ�
}
/*******************************************
 �������ƣ�Disp_HZ
 ��    �ܣ���ʾ���ֳ���
 ��    ����addr--��ʾλ�õ��׵�ַ
 pt--ָ����ʾ���ݵ�ָ��
 num--��ʾ���ݵĸ���
 ����ֵ  ����
 ********************************************/
void Disp_HZ(uchar addr, const uchar * pt, uchar num) {
	uchar i;

	Send(0, addr);
	for (i = 0; i < (num * 2); i++)
		Send(1, *(pt++));
}

/*******************************************
 �������ƣ�Disp_SZ
 ��    �ܣ���ʾһ����λ����
 ��    ����addr--��ʾ��ַ
 ����--��ʾ������
 ����ֵ  ����
 ********************************************/
void Disp_SZ(uchar addr, uint shuzi) {
	uchar tmp0, tmp1;

	tmp0 = shuzi / 10;
	tmp1 = shuzi % 10;

	Send(0, addr);
	Send(1, shuzi_table[tmp0]);
	Send(1, shuzi_table[tmp1]);

}
