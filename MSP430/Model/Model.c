#include <msp430.h>
#include <HC05.h>
#include <Model.h>

//********************变量声明*****************************
#define uint unsigned int
#define uchar unsigned char

unsigned char second, minute, hour, week, day, month, year; //秒、分、时、星期、日、月、年
unsigned char DHT11T_Data_H, DHT11T_Data_L, DHT11RH_Data_H,
		DHT11RH_Data_L, CheckData_temp;
unsigned char T_Data, RH_Data;
int TT_Data, TRH_Data;
char isal, timelong;
int iss = 0;
int ish = 0;
int isa = 0;
int ColValue = 0x0000;
int ColValueTemp = 0x0000;

/*************************************************

 函数(模块)名称:maintra()

 功能:

 *************************************************/

void maintra(void) {
	T_Data = DHT11T_Data_H + TT_Data;
	RH_Data = DHT11RH_Data_H + TRH_Data;
}

/*************************************************

 函数(模块)名称:settra()

 功能:

 *************************************************/

void settra(void) {
	TT_Data = 0;
	TRH_Data = 0;
}
