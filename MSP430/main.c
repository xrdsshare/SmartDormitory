#include <msp430.h> 
#include <LED.h>
#include <HC05.h>
#include <Model.h>
#include <C51.h>
#include <Lcd12864.h>
#include <Display.h>
#include <Delay.h>
#include <DHT11.h>
#include <DS1302.h>
#include <Alarm.h>
#include <Schedule.h>

extern unsigned char second, minute, hour, week, day, month, year; //�롢�֡�ʱ�����ڡ��ա��¡���
extern unsigned char T_Data, RH_Data;
unsigned char secondts, mints, T_Datatemp;
extern char isal;
extern int ColValue;

int main(void) {
	WDTCTL = WDTPW | WDTHOLD;	// Stop watchdog timer

	P8DIR |= BIT2;
	P8OUT &= ~BIT2;

	Init_DS1302();
	Init_HC05();
	init_C51();
	C51_tsend(0x4000);

	P7DIR = BIT0;
	P7OUT &= ~BIT0;

//	__bis_SR_register(LPM3_bits + GIE);
	__bis_SR_register(GIE);
	// Enter LPM3, interrupts enabled
//	__no_operation();
	Ini_Lcd();
	start_DHT11();
	Send(0, 0x01);	//����
	while (1) {
		readtime();	//��ȡʱ��
		start_DHT11();	//��ȡDHT11
		Times(hour, minute, second);
		Weeks(week);
		Mons(year, month, day);

		if (secondts == second)	//����ʪ��ÿ��ˢ��һ��
			maintra();	//����ת��
			Datas(T_Data, RH_Data, 60);
//		secondts = second + 1;
		is_classtime();	//�ж��Ƿ��пγ�
		if (is_altime(year, month, day, week, hour, minute, second)) {	//��������
			P8OUT |= BIT2;
			delay_us(2000);
			P8OUT &= ~BIT2;
			delay_us(2000);
//			P8OUT |= BIT2;
//			delay_us(2000);
//			P8OUT &= ~BIT2;
//			delay_us(2000);
		}

		if (isal) {
			Ini_Lcd();
			showclass();
			P8OUT |= BIT2;
			delay_us(2000);
			P8OUT &= ~BIT2;
			delay_us(2000);
//			P8OUT |= BIT2;
//			delay_us(2000);
//			P8OUT &= ~BIT2;
//			delay_us(2000);
			delay_s(30);
//			while (!mints == minute)
//				;
			Ini_Lcd();
		}

		if (T_Datatemp != T_Data) {//�ж��¶��Ƿ񵽴���Ƶ�
			if (T_Data > 30) {
				ColValue |= 0x0020 + 0x0040;
				HC05_tsend(ColValue);
				C51_tsend(ColValue);
			} else if (T_Data < 25) {
				ColValue &= ~(0x0020 + 0x0040);
				HC05_tsend(ColValue);
				C51_tsend(ColValue);
			}
		}
		T_Datatemp = T_Data;
		secondts = second + 1;
		mints = minute + 1;
	}
}
