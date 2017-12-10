#include <msp430.h>
#include <HC05.h>
#include <Model.h>
#include <C51.h>
#include <DS1302.h>
#include <Delay.h>

//********************变量声明*****************************
extern int ish;
extern int ColValue;
extern int ColValueTemp;
extern int TT_Data, T_Data;
extern int isa;
int yeart, mont, dayt, hourt, mint, cont, weekt;

/*************************************************

 函数(模块)名称:	Init_HC05();

 功能:        初始化蓝牙

 *************************************************/

void Init_HC05(void) {
	P3SEL |= BIT3 + BIT4;                       // P3.3,4 = USCI_A0 TXD/RXD

	UCA0CTL1 |= UCSWRST;                      // **Put state machine in reset**
	UCA0CTL1 |= UCSSEL_1;                     // CLK = ACLK
	UCA0BR0 = 0x03;                        // 32kHz/9600=3.41 (see User's Guide)
	UCA0BR1 = 0x00;                           //
	UCA0MCTL = UCBRS_3 + UCBRF_0;               // Modulation UCBRSx=3, UCBRFx=0
	UCA0CTL1 &= ~UCSWRST;                   // **Initialize USCI state machine**
	UCA0IE |= UCRXIE;                         // Enable USCI_A0 RX interrupt

//	__bis_SR_register(LPM3_bits + GIE);
//	__bis_SR_register(GIE);
	// Enter LPM3, interrupts enabled
//	__no_operation();                         // For debugger
}

/*************************************************

 函数(模块)名称:	HC05_tsend();

 功能:        发送转换函数

 *************************************************/

void HC05_tsend(int datas) {
	unsigned int i = 2;
	while (i) {
		while (!(UCA0IFG & UCTXIFG))
			;  //待发送为空
		UCA0TXBUF = (char) ((datas & 0xFF00) >> 8);
		datas <<= 8;
		i--;
		UCA0IFG &= ~UCRXIFG;
	}
}

/*************************************************

 函数(模块)名称:蓝牙中断

 功能:        处理接收蓝牙中断

 *************************************************/

#pragma vector=USCI_A0_VECTOR
__interrupt void USCI_A0_ISR(void) {
//	char aa, bb, cc;
//	aa = bb = cc = 0;
	switch (__even_in_range(UCA0IV, 4)) {
	case 0:
		break;                             // Vector 0 - no interrupt
	case 2:                                   // Vector 2 - RXIFG
		if (ish == 0) {
			ColValueTemp = 0;
			ColValueTemp |= (UCA0RXBUF & 0x00ff);
			ish = 1;
		} else if (ish == 1) {
			ColValueTemp <<= 8;
			ColValueTemp |= (UCA0RXBUF & 0x00ff);
			ish = 0;
//			HC05_tsend(ColValue);
			if (ColValueTemp == 0x4000) {
				HC05_tsend(ColValue);
			} else if (ColValueTemp == 0x6002) {
				isa = 7;
			} else if (ColValueTemp == 0x6005) {
				isa = 8;
			} else if (ColValueTemp == 0x6008) {                    //发送蓝牙门锁控制命令
				P7DIR = BIT0;
				P7OUT ^= BIT0;
				delay_ms(100);
				P7OUT &= ~BIT0;
			} else {
				switch (isa) {
				case 8:
					TT_Data = ColValueTemp;
					HC05_tsend(ColValueTemp);
					isa = 0;
					break;
				case 7:
					yeart = ColValueTemp;
					isa--;
					break;
				case 6:
					mont = ColValueTemp;
					isa--;
					break;
				case 5:
					dayt = ColValueTemp;
					isa--;
					break;
				case 4:
					hourt = ColValueTemp;
					isa--;
					break;
				case 3:
					mint = ColValueTemp;
					isa--;
					break;
				case 2:
					cont = ColValueTemp;
					isa--;
//						init_1302(cont, mint, hourt, dayt, mont, yeart);
					break;
				case 1:
					weekt = ColValueTemp;
					isa--;
					init_1302(cont, mint, hourt, weekt, dayt, mont, yeart);
					break;
				default:
					ColValue = ColValueTemp;
					C51_tsend(ColValue);
					break;
				}
			}
		}
		break;
	case 4:
		break;                             // Vector 4 - TXIFG
	default:
		break;
	}
}
