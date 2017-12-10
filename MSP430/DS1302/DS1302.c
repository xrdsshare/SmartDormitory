#include <msp430.h>
#include <Model.h>
typedef unsigned char uchar;
typedef unsigned int uint;

/**************宏定义***************/
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

unsigned char flag; //定义一个读标志位
extern unsigned char second, minute, hour, week, day, month, year; //秒、分、时、星期、日、月、年

void Init_DS1302(void) {
	P2DIR = BIT0 + BIT3 + BIT6;
	P2OUT = BIT0 + BIT3 + BIT6;
}

/*******************************************
 函数名称：delay
 功    能：延时一段时间
 参    数：time--延时长度
 ********************************************/
void delay(uint time) {
	uint i;
	for (i = 0; i < time; i++)
		_NOP();
}

/*******************************************
 函数名称：Reset_DS1302
 功    能：对DS1302进行复位操作
 ********************************************/
void Reset_DS1302(void) {
	DS_RST_OUT;  //RST对应的IO设置为输出状态
	DS_SCL_OUT;  //SCLK对应的IO设置为输出状态
	DS_SCL0;     //SCLK=0
	DS_RST0;     //RST=0
	delay(10);
	DS_SCL1;    //SCLK=1
}
/*******************************************
 函数名称：Write1Byte
 功    能：对DS1302写入1个字节的数据
 ********************************************/
void Write_Byte(uchar wdata) {
	uchar i;

	DS_SDA_OUT;  //SDA对应的IO设置为输出状态
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
 函数名称：Read1Byte
 功    能：从DS1302读出1个字节的数据
 ********************************************/
uchar Read_Byte() {
	uchar i;
	uchar rdata = 0X00;

	DS_SDA_IN;  //SDA对应的IO设置为输入状态
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
 函数名称：Write_dat
 功    能：向某个寄存器写入一个字节数据
 参    数：add--寄存器地址
 dat--写入的数据
 ********************************************/
void Write_dat(uchar add, uchar dat) {
	DS_RST0;
	DS_SCL0;
	_NOP();
	DS_RST1;
	Write_Byte(add);   //写入地址
	Write_Byte(dat);  //写入数据
	DS_SCL1;
	DS_RST0;
}
/*******************************************
 函数名称：Read_dat
 功    能：从某个寄存器读出一个字节数据
 参    数：addr--寄存器地址
 返回值  ：读出的数据
 ********************************************/
uchar read_1302add(uchar add) {
	uchar rdata;
	DS_RST0;
	DS_SCL0;
	_NOP();
	DS_RST1;
	Write_Byte(add);    //写入地址
	rdata = Read_Byte();  //读出数据
	DS_SCL1;
	DS_RST0;

	return (rdata);
}
/***********初始化1302*************/
void init_1302(unsigned char second, unsigned char minute, unsigned char hour, unsigned char week,
		unsigned char day, unsigned char month, unsigned char year) {
	flag = read_1302add(0x81);  //读秒寄存器最高位,读出时钟状态
	if (flag & 0x80)
		;  //判断时钟是否关闭,若内部关闭,则初始化,否则继续走
	{
		Write_dat(0x8e, 0x00);
		Write_dat(0x80, ((second / 10) << 4 | (second % 10)));  //写秒寄存器，并写入初值55
		Write_dat(0x82, ((minute / 10) << 4 | (minute % 10)));  //写分寄存器，并写入初值59
		Write_dat(0x84, ((hour / 10) << 4 | (hour % 10)));  //写小时寄存器，并写入初值23
		Write_dat(0x86, ((day / 10) << 4 | (day % 10)));  //写日寄存器，并写入初值18
		Write_dat(0x88, ((month / 10) << 4 | (month % 10)));  //写月寄存器，并写入初值2
		Write_dat(0x8a, ((week / 10) << 4 | (week % 10)));  //写周寄存器，并写入初值5
		Write_dat(0x8c, ((year / 10) << 4 | (year % 10))); //写年寄存器，并写入初值12，不能写2012年
		Write_dat(0x90, 0xa5);  //写充电方式
		Write_dat(0x8e, 0x80);  //加上写保护

	}

}

/*****************读出秒的十进制数***************************/

uchar readsecond() {
	uchar dat;
	dat = read_1302add(0x81);
	second = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return second;
}

/*****************读出分的十进制数***************************/

uchar readminute() {
	uchar dat;
	dat = read_1302add(0x83);
	minute = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return minute;
}

/*****************读出小时的十进制数***************************/

uchar readhour() {
	uchar dat;
	dat = read_1302add(0x85);
	hour = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return hour;
}

/*****************读出天的十进制数***************************/

uchar readday() {
	uchar dat;
	dat = read_1302add(0x87);
	day = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return day;
}

/*****************读出月的十进制数***************************/

uchar readmonth() {
	uchar dat;
	dat = read_1302add(0x89);
	month = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return month;
}

/*****************读出周的十进制数***************************/

uchar readweek() {
	uchar dat;
	dat = read_1302add(0x8b);
	week = ((dat & 0x70) >> 4) * 10 + (dat & 0x0f);
	return week;
}

/*****************读出年的十进制数***************************/

uchar readyear() {
	uchar dat;
	dat = read_1302add(0x8d);
	year = ((dat & 0xf0) >> 4) * 10 + (dat & 0x0f);
	return year;
}

/************************读出所有时间**********************/

void readtime() {
	readsecond();
	readminute();
	readhour();
	readday();
	readmonth();
	readweek();
	readyear();
}
