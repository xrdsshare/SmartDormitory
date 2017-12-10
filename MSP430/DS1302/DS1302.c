#include <msp430.h>
#include <Model.h>
typedef unsigned char uchar;
typedef unsigned int uint;

/**************�궨��***************/
#define DS_RST  BIT0 //DS_RST = P2.0
#define DS_SCL  BIT3  //DS_SCL = P2.3
#define DS_SDA  BIT6  //DS_SDA = P2.6
#define DS_RST_IN P2DIR &= ~DS_RST
#define DS_RST_OUT P2DIR |= DS_RST
#define DS_RST0 P2OUT &= ~DS_RST
#define DS_RST1 P2OUT |= DS_RST
#define DS_SCL_IN P2DIR &= ~DS_SCL
#define DS_SCL_OUT P2DIR |= DS_SCL
#define DS_SCL0 P2OUT &= ~DS_SCL
#define DS_SCL1 P2OUT |= DS_SCL
#define DS_SDA_IN P2DIR &= ~DS_SDA
#define DS_SDA_OUT P2DIR |= DS_SDA
#define DS_SDA0 P2OUT &= ~DS_SDA
#define DS_SDA1 P2OUT |= DS_SDA
#define DS_SDA_BIT P2IN & DS_SDA

unsigned char flag; //����һ������־λ
extern unsigned char second, minute, hour, week, day, month, year; //�롢�֡�ʱ�����ڡ��ա��¡���

void Init_DS1302(void) {
	P2DIR = BIT0 + BIT3 + BIT6;
	P2OUT = BIT0 + BIT3 + BIT6;
}

/*******************************************
 �������ƣ�delay
 ��    �ܣ���ʱһ��ʱ��
 ��    ����time--��ʱ����
 ********************************************/
void delay(uint time) {
	uint i;
	for (i = 0; i < time; i++)
		_NOP();
}

/*******************************************
 �������ƣ�Reset_DS1302
 ��    �ܣ���DS1302���и�λ����
 ********************************************/
void Reset_DS1302(void) {
	DS_RST_OUT;  //RST��Ӧ��IO����Ϊ���״̬
	DS_SCL_OUT;  //SCLK��Ӧ��IO����Ϊ���״̬
	DS_SCL0;     //SCLK=0
	DS_RST0;     //RST=0
	delay(10);
	DS_SCL1;    //SCLK=1
}
/*******************************************
 �������ƣ�Write1Byte
 ��    �ܣ���DS1302д��1���ֽڵ�����
 ********************************************/
void Write_Byte(uchar wdata) {
	uchar i;

	DS_SDA_OUT;  //SDA��Ӧ��IO����Ϊ���״̬
	DS_RST1;  //REST=1;

	for (i = 8; i > 0; i--) {
		if (wdata & 0x01)
			DS_SDA1;
		else
			DS_SDA0;
		DS_SCL0;
		delay(10);
		DS_SCL1;
		delay(10);
		wdata >>= 1;
	}
}
/*******************************************
 �������ƣ�Read1Byte
 ��    �ܣ���DS1302����1���ֽڵ�����
 ********************************************/
uchar Read_Byte() {
	uchar i;
	uchar rdata = 0X00;

	DS_SDA_IN;  //SDA��Ӧ��IO����Ϊ����״̬
	DS_RST1;    //REST=1;

	for (i = 8; i > 0; i--) {
		DS_SCL1;
		delay(10);
		DS_SCL0;
		delay(10);
		rdata >>= 1;
		if (DS_SDA_BIT)
			rdata |= 0x80;
	}

	return (rdata);
}
/*******************************************
 �������ƣ�Write_dat
 ��    �ܣ���ĳ���Ĵ���д��һ���ֽ�����
 ��    ����add--�Ĵ�����ַ
 dat--д�������
 ********************************************/
void Write_dat(uchar add, uchar dat) {
	DS_RST0;
	DS_SCL0;
	_NOP();
	DS_RST1;
	Write_Byte(add);   //д���ַ
	Write_Byte(dat);  //д������
	DS_SCL1;
	DS_RST0;
}
/*******************************************
 �������ƣ�Read_dat
 ��    �ܣ���ĳ���Ĵ�������һ���ֽ�����
 ��    ����addr--�Ĵ�����ַ
 ����ֵ  ������������
 ********************************************/
uchar read_1302add(uchar add) {
	uchar rdata;
	DS_RST0;
	DS_SCL0;
	_NOP();
	DS_RST1;
	Write_Byte(add);    //д���ַ
	rdata = Read_Byte();  //��������
	DS_SCL1;
	DS_RST0;

	return (rdata);
}
/***********��ʼ��1302*************/
void init_1302(unsigned char second, unsigned char minute, unsigned char hour, unsigned char week,
		unsigned char day, unsigned char month, unsigned char year) {
	flag = read_1302add(0x81);  //����Ĵ������λ,����ʱ��״̬
	if (flag & 0x80)
		;  //�ж�ʱ���Ƿ�ر�,���ڲ��ر�,���ʼ��,���������
	{
		Write_dat(0x8e, 0x00);
		Write_dat(0x80, ((second / 10) << 4 | (second % 10)));  //д��Ĵ�������д���ֵ55
		Write_dat(0x82, ((minute / 10) << 4 | (minute % 10)));  //д�ּĴ�������д���ֵ59
		Write_dat(0x84, ((hour / 10) << 4 | (hour % 10)));  //дСʱ�Ĵ�������д���ֵ23
		Write_dat(0x86, ((day / 10) << 4 | (day % 10)));  //д�ռĴ�������д���ֵ18
		Write_dat(0x88, ((month / 10) << 4 | (month % 10)));  //д�¼Ĵ�������д���ֵ2
		Write_dat(0x8a, ((week / 10) << 4 | (week % 10)));  //д�ܼĴ�������д���ֵ5
		Write_dat(0x8c, ((year / 10) << 4 | (year % 10))); //д��Ĵ�������д���ֵ12������д2012��
		Write_dat(0x90, 0xa5);  //д��緽ʽ
		Write_dat(0x8e, 0x80);  //����д����

	}

}

/*****************�������ʮ������***************************/

uchar readsecond() {
	uchar dat;
	dat = read_1302add(0x81);
	second = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return second;
}

/*****************�����ֵ�ʮ������***************************/

uchar readminute() {
	uchar dat;
	dat = read_1302add(0x83);
	minute = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return minute;
}

/*****************����Сʱ��ʮ������***************************/

uchar readhour() {
	uchar dat;
	dat = read_1302add(0x85);
	hour = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return hour;
}

/*****************�������ʮ������***************************/

uchar readday() {
	uchar dat;
	dat = read_1302add(0x87);
	day = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return day;
}

/*****************�����µ�ʮ������***************************/

uchar readmonth() {
	uchar dat;
	dat = read_1302add(0x89);
	month = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return month;
}

/*****************�����ܵ�ʮ������***************************/

uchar readweek() {
	uchar dat;
	dat = read_1302add(0x8b);
	week = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return week;
}

/*****************�������ʮ������***************************/

uchar readyear() {
	uchar dat;
	dat = read_1302add(0x8d);
	year = ((dat & 0xf0) >> 4) * 10 + (dat & 0x0f);
	return year;
}

/************************��������ʱ��**********************/

void readtime() {
	readsecond();
	readminute();
	readhour();
	readday();
	readmonth();
	readweek();
	readyear();
}
