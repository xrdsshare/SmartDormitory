#ifndef DS302_H_
#define DS302_H_

typedef unsigned char uchar;
typedef unsigned int uint;

extern void Init_DS1302(void);
extern void delay(unsigned int time);
extern void Reset_DS1302(void);
extern void Write_Byte(uchar wdata);
extern unsigned char Read_Byte();
extern void Write_dat(uchar add, uchar dat);
extern unsigned char read_1302add(uchar add);
extern void init_1302(unsigned char second, unsigned char minute,
		unsigned char hour, unsigned char week, unsigned char day, unsigned char month,
		unsigned char year);
extern unsigned char readsecond();
extern unsigned char readminute();
extern unsigned char readhour();
extern unsigned char readday();
extern unsigned char readmonth();
extern unsigned char readweek();
extern unsigned char readyear();
extern void readtime();

#endif
