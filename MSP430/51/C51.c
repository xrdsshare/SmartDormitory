#include <msp430.h>
#include <Model.h>
#include <HC05.h>

//********************��������*****************************
extern int iss;
extern int ColValue;
extern int ColValueTemp;

/*************************************************

 ����(ģ��)����:	init_C51();

 ����:

 *************************************************/

void init_C51(void) {
	P4SEL = BIT4 + BIT5;                        // P4.4,5 = USCI_A0 TXD/RXD

	UCA1CTL1 |= UCSWRST;                      // **Put state machine in reset**
	UCA1CTL1 |= UCSSEL_1;                     // CLK = ACLK
	UCA1BR0 = 0x03;                        // 32kHz/9600=3.41 (see User's Guide)
	UCA1BR1 = 0x00;                           //
	UCA1MCTL = UCBRS_3 + UCBRF_0;               // Modulation UCBRSx=3, UCBRFx=0
	UCA1CTL1 &= ~UCSWRST;                   // **Initialize USCI state machine**
	UCA1IE |= UCRXIE;                         // Enable USCI_A0 RX interrupt

//	__bis_SR_register(LPM3_bits + GIE);
//	// Enter LPM3, interrupts enabled
//	__no_operation();                         // For debugger
}

/*************************************************

 ����(ģ��)����:	C51_tsend();

 ����:        ����ת������

 *************************************************/

void C51_tsend(int datas) {
	unsigned int i = 2;
	while (i) {
		while (!(UCA1IFG & UCTXIFG))
			;  //������Ϊ��
		UCA1TXBUF = (char) ((datas & 0xFF00) >> 8);
		datas <<= 8;
		i--;
		UCA1IFG &= ~UCRXIFG;
	}
}

/*************************************************

 ����(ģ��)����:	C51�ж�

 ����:        �������C51�ж�

 *************************************************/

#pragma vector=USCI_A1_VECTOR
__interrupt void USCI_A1_ISR(void) {
	switch (__even_in_range(UCA1IV, 4)) {
	case 0:
		break;                             // Vector 0 - no interrupt
	case 2:                                   // Vector 2 - RXIFG
		if (iss == 0) {
			ColValueTemp = 0;
			ColValueTemp |= (UCA1RXBUF & 0x00ff);
			iss++;
		} else if (iss == 1) {
			ColValueTemp <<= 8;
			ColValueTemp |= (UCA1RXBUF & 0x00ff);
			iss = 0;
			if (ColValueTemp == 0x4000) {
				C51_tsend(ColValue);
			} else {
				ColValue = ColValueTemp;

				HC05_tsend(ColValue);
			}
		}
		break;
	case 4:
		break;                             // Vector 4 - TXIFG
	default:
		break;
	}
}

