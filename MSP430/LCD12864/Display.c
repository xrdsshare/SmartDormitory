#include <msp430.h>
#include <displaydata.h>
#include <Lcd12864.h>

/*******************************************
 函数名称：Times();
 功    能：时间显示函数
 参    数：


 返回值  ：
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
 函数名称：Mons();
 功    能：日期显示函数
 参    数：


 返回值  ：
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
 函数名称：Weeks();
 功    能：星期显示函数
 参    数：


 返回值  ：
 ********************************************/
void Weeks(unsigned char weekt) {
	weekt--;
	Disp_HZ(0X89, weekc, 2);
	Disp_HZ(0X8b, weeks[weekt], 1);
}

/*******************************************
 函数名称：Datas();
 功    能：参数数据显示函数
 参    数：


 返回值  ：
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
