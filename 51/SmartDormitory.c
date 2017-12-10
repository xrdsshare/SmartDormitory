#include<reg52.h>
//********************变量声明*****************************
#define uint unsigned int
#define uchar unsigned char	  

/*Define UART parity mode*/
//#define NONE_PARITY     0   //None parity
//#define ODD_PARITY      1   //Odd parity
//#define EVEN_PARITY     2   //Even parity
//#define MARK_PARITY     3   //Mark parity
//#define SPACE_PARITY    4   //Space parity
//#define PARITYBIT EVEN_PARITY   //Testing even parity		

sbit L0=P1^0; 
sbit L1=P1^1; 
sbit L2=P1^2; 
sbit L3=P1^3;

sbit KL0=P1^4;		 
sbit KL1=P1^5; 
sbit KL2=P1^6;		 
sbit KL3=P1^7;

sbit M0=P3^2; 
sbit M1=P3^3; 		 
sbit M2=P3^4;

sbit KM0=P3^5; 
sbit KM1=P3^6; 
sbit KM2=P3^7;

sbit I0=P2^0; 
sbit I1=P2^1;
sbit I2=P2^2;
sbit I3=P2^3;
sbit I4=P2^4;

sbit C0=P2^5;
sbit KC0=P2^6;

sbit DO0=P2^7;

int ColValue = 0x0002;
int ColValueTemp;
uchar iss = 0;

//*******************函数声明******************************
void delay(uint z);
bit col_wei(uchar wei);
void keysd();
void mainshow();
void Init_Serialport1();
void tsend(int datas);

//************************************************************

//---------------------主函数-------------------------------

//************************************************************

void main()
{
	Init_Serialport1();
	M0 = 1;

	while (1)
	{	 
		mainshow();	
		keysd();
	}	  	
}

/*************************************************

 函数(模块)名称:delay()

 功能:        简单延时函数

 *************************************************/

void delay(uint z)
{
	uint x,y;
	for(x=z;x>0;x--)
		for(y=110;y>0;y--);
}

/*************************************************

 函数(模块)名称:  mainshow();

 功能:        根据ColValue的显示控制函数

 *************************************************/

void mainshow(){
	L0 = col_wei(0);	
	L1 = col_wei(1);  
	L2 = col_wei(2);
	L3 = col_wei(3);
	M0 = col_wei(4);
	M1 = col_wei(5);
	M2 = col_wei(6);
	I0 = col_wei(7);
	I1 = col_wei(8);
	I2 = col_wei(9);
	I3 = col_wei(10);
	I4 = col_wei(11);
	C0 = col_wei(12);
	DO0 = col_wei(13);	 
}

/*************************************************

 函数(模块)名称:  keysd();

 功能:        按键扫描函数

 *************************************************/

void keysd(){ 	
	if(KL0==0)
	{
		delay(5);
		if(KL0==0)
		{
			ColValue ^= 0x0001; 
			tsend(ColValue);
		} 
		while(!KL0);
		delay(5);
		while(!KL0);
	}
	   
	if(KL1==0)
	{  
		delay(5);
		if(KL1==0)
		{
			ColValue ^= 0x0002; 
			tsend(ColValue);
		} 
		while(!KL1);
		delay(5);
		while(!KL1);
	} 

	if(KL2==0)
	{  
		delay(5);
		if(KL2==0)
		{
			ColValue ^= 0x0004; 
			tsend(ColValue);
		} 
		while(!KL2);
		delay(5);
		while(!KL2);
	} 

	if(KL3==0)
	{  
		delay(5);
		if(KL3==0)
		{
			ColValue ^= 0x0008; 
			tsend(ColValue);
		} 
		while(!KL3);
		delay(5);
		while(!KL3);
	} 

	if(KM0==0)
	{  
		delay(5);
		if(KM0==0)
		{
			ColValue ^= 0x0010; 
			tsend(ColValue);
		} 
		while(!KM0);
		delay(5);
		while(!KM0);
	}  

	if(KM1==0)
	{  
		delay(5);
		if(KM1==0)
		{
			ColValue ^= 0x0020; 
			tsend(ColValue);
		} 
		while(!KM1);
		delay(5);
		while(!KM1);
	}  

	if(KM2==0)
	{  
		delay(5);
		if(KM2==0)
		{
			ColValue ^= 0x0040; 
			tsend(ColValue);
		} 
		while(!KM2);
		delay(5);
		while(!KM2);
	}

	if(KC0==0)
	{  
		delay(5);
		if(KC0==0)
		{
			ColValue ^= 0x2000; 
			tsend(ColValue);
		} 
		while(!KC0);
		delay(5);
		while(!KC0);
	} 
}

/*************************************************

 函数(模块)名称:  col_wei();

 功能:        mainshow()函数中的判断及位移处理函数

 *************************************************/

bit col_wei(uchar wei){
	bit b = 0;
	int ColTemp = ColValue;
	ColTemp >>= wei;
	b = ColTemp & 1;
	return b;	
}

/*************************************************

 函数(模块)名称:	Init_Serialport1();

 功能:        初始化LED

 *************************************************/

void Init_Serialport1()  //初始化串口1（Serial port）
{  
/*	#if (PARITYBIT == NONE_PARITY)
	    SCON = 0x50;            //8-bit variable UART
	#elif (PARITYBIT == ODD_PARITY) || (PARITYBIT == EVEN_PARITY) || (PARITYBIT == MARK_PARITY)
	    SCON = 0xda;            //9-bit variable UART, parity bit initial to 1
	#elif (PARITYBIT == SPACE_PARITY)
	    SCON = 0xd2;            //9-bit variable UART, parity bit initial to 0
	#endif				   */
	TMOD=TMOD&0x0F;
	TMOD=0x20;
	TH1=0xFD;
	TL1=0xFD;
	TR1=1;
	SCON=SCON&0x0F;
	SCON=0x50;
	SM0=0;
	SM1=1;
	EA=1;
	ES=1;
}	

/*************************************************

 函数(模块)名称:tsend();

 功能:        发送转换函数

 *************************************************/

void tsend(int datas) {
	uchar table[2];	
	table[1] = (char)((datas & 0xFF00) >> 8);
	table[0] = (char)(datas & 0x00FF);	
	ES=0;
	SBUF=table[1];
	while(TI==0);     
	TI=0;  
	
	SBUF=table[0];
	while(TI==0);     
	TI=0; 
	ES=1;		
}

/*************************************************

 函数(模块)名称:ser_int()

 功能:        处理接收中断

 *************************************************/

void ser_int (void) interrupt 4 
{
	if(RI == 1) { 
		RI = 0;      //清除标志.
		if (iss == 0) {
			ColValueTemp = 0;
			ColValueTemp |= (SBUF & 0x00ff);
			iss++;
		} else if (iss == 1) {
			ColValueTemp <<= 8;
			ColValueTemp |= (SBUF & 0x00ff);
			iss = 0;
//			tsend(ColValueTemp);
			if (ColValueTemp == 0x4001) {
				tsend(ColValue);
			} else {
				ColValue = ColValueTemp;
			}
		}
	}
} 