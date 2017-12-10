#include <msp430.h>
#include <Model.h>
#include <Delay.h>
#include <DHT11.h>

#define DHT11_OUTPUT   P6DIR|=BIT5
#define DHT11_INPUT   P6DIR&=~BIT5
#define DHT11_H   P6OUT|=BIT5
#define DHT11_L   P6OUT&=~BIT5
#define DHT11_IN   (P6IN&BIT5)

extern unsigned char DHT11T_Data_H, DHT11T_Data_L, DHT11RH_Data_H,
		DHT11RH_Data_L, CheckData_temp;
//DHT11T_Data_H = DHT11T_Data_L = DHT11RH_Data_H = DHT11RH_Data_L =
//		CheckData_temp = 0;

unsigned char start_DHT11(void) {
	unsigned char TData_H_temp, TData_L_temp, RHData_H_temp, RHData_L_temp,
			checktemp;
	unsigned char presence, flag;
	unsigned int count;
	DHT11_OUTPUT;
	DHT11_L;    //����18ms����
	delay_ms(20);
	DHT11_H;
	DHT11_INPUT;
	delay_us(40);
	presence = DHT11_IN;
	if (!presence) {
		count = 2;
		while ((!DHT11_IN)&&count++);    //�ȴ��͵�ƽ
		count = 2;
		while ((DHT11_IN) && count++)
			;    //�ȴ��ߵ�ƽ
		RHData_H_temp = DHT11_ReadChar();
		RHData_L_temp = DHT11_ReadChar();
		TData_H_temp = DHT11_ReadChar();
		TData_L_temp = DHT11_ReadChar();
		CheckData_temp = DHT11_ReadChar();
		DHT11_OUTPUT;
		DHT11_H;
		checktemp =
				(RHData_H_temp + RHData_L_temp + TData_H_temp + TData_L_temp);
		if (checktemp == CheckData_temp) {
			DHT11RH_Data_H = RHData_H_temp;
			DHT11RH_Data_L = RHData_L_temp;
			DHT11T_Data_H = TData_H_temp;
			DHT11T_Data_L = TData_L_temp;
			flag = 1;
		}
	}
//	P1OUT |=BIT0;
	return flag;
}

unsigned char DHT11_ReadChar(void) {
	unsigned char dat;
	unsigned int count;     //������ֹ����
	unsigned char i;
	for (i = 0; i < 8; i++) {
		count = 2;
		while ((!DHT11_IN)&&count++);     //�ȴ�50us�͵�ƽ����
		delay_us(40);
		//40us
		dat <<= 1;        //50us�͵�ƽ+28us�ߵ�ƽ��ʾ'0'
		if (DHT11_IN)    //50us�͵�ƽ+70us�ߵ�ƽ��ʾ'1'
			dat |= 1;
		count = 2;
		while ((DHT11_IN) && count++)
			;
		if (count == 1)      //��ʱ������forѭ��
			break;
	}
	return dat;
}

