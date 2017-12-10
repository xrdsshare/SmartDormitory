
#ifndef _DELAY_H
#define _DELAY_H

#define F_CPU  1000000        //���ݵ�ǰ��Ƶ(MCLK)���㣬��֤��ʱ��������Ƶ�仯(��������430�ڲ��Դ�����ʱ���ܾ�׼)
#define delay_us(A)  __delay_cycles( F_CPU/1000000.0 *A )
#define delay_ms(A)  __delay_cycles( F_CPU/1000.0 *A )
#define delay_s(A)   __delay_cycles( F_CPU/1.0*A )

#endif /* __DELAY_H */
