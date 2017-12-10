#include <msp430.h>
#include <displaydata.h>
#include <Lcd12864.h>

/*******************************************
 �������ƣ�Times();
 ��    �ܣ�ʱ����ʾ����
 ��    ����


 ����ֵ  ��
 ********************************************/
void Times(unsigned char hour, unsigned char minute, unsigned char second) {
	Disp_SZ(0x91, hour);
	Disp_HZ(0X92, timem[minute], 2);
	Disp_SZ(0x94, second);
	if (hour < 12) {
		Disp_HZ(0X96, time[0], 1);
	} else {
		Disp_HZ(0X96, time[1], 1);
	}
}

/*******************************************
 �������ƣ�Mons();
 ��    �ܣ�������ʾ����
 ��    ����


 ����ֵ  ��
 ********************************************/
void Mons(unsigned char year, unsigned char month, unsigned char day) {
	Disp_SZ(0x80, 20);
	Disp_SZ(0x81, year);
	Disp_HZ(0X82, hyear, 1); //2
	Disp_SZ(0x84, month);
	Disp_HZ(0X85, hmonth, 1); //2
	Disp_SZ(0x86, day);
	Disp_HZ(0X87, hday, 1); //2
}

/*******************************************
 �������ƣ�Weeks();
 ��    �ܣ�������ʾ����
 ��    ����


 ����ֵ  ��
 ********************************************/
void Weeks(unsigned char weekt) {
	weekt--;
	Disp_HZ(0X89, weekc, 2);
	Disp_HZ(0X8b, weeks[weekt], 1);
}

/*******************************************
 �������ƣ�Datas();
 ��    �ܣ�����������ʾ����
 ��    ����


 ����ֵ  ��
 ********************************************/
void Datas(unsigned char tem, unsigned char hum, unsigned char lig) {
	Disp_SZ(0x98, tem);
	Disp_HZ(0X99, temperature, 1); //3
	Disp_SZ(0x9b, hum);
	Disp_HZ(0X9c, humidity, 1); //3
	Disp_SZ(0x9e, lig);
	Disp_HZ(0X9f, light, 1); //3

	if (lig > 50) {
		Disp_HZ(0X8d, weather[0], 2);
	} else {
		Disp_HZ(0X8d, weather[1], 2);
	}
}
