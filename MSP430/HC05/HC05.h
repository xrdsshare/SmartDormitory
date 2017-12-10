#ifndef HC05_H_
#define HC05_H_

extern void Init_HC05(void);
extern __interrupt void USCI_A0_ISR(void);
extern void HC05_tsend(int datas);

#endif
