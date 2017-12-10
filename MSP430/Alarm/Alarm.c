#include <msp430.h>

unsigned char AlTime0[6] = { 07, 30, 00 };
unsigned char AlTime1[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime2[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime3[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime4[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime5[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime6[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime7[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime8[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char AlTime9[6] = { 99, 12, 30, 23, 59, 59 };
unsigned char timetemp[6];

/*************************************************

 函数(模块)名称:	setalarm0()

 功能:	设置闹钟

 *************************************************/

void setalarm0(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime0[0] = yeart;
	AlTime0[1] = montht;
	AlTime0[2] = dayt;
//	AlTime0[3] = weekt;
	AlTime0[3] = hourt;
	AlTime0[4] = minutet;
	AlTime0[5] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm1()

 功能:	设置闹钟

 *************************************************/

void setalarm1(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime1[0] = yeart;
	AlTime1[1] = montht;
	AlTime1[2] = dayt;
	AlTime1[3] = weekt;
	AlTime1[4] = hourt;
	AlTime1[5] = minutet;
	AlTime1[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm2()

 功能:	设置闹钟

 *************************************************/

void setalarm2(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime2[0] = yeart;
	AlTime2[1] = montht;
	AlTime2[2] = dayt;
	AlTime2[3] = weekt;
	AlTime2[4] = hourt;
	AlTime2[5] = minutet;
	AlTime2[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm3()

 功能:	设置闹钟

 *************************************************/

void setalarm3(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime3[0] = yeart;
	AlTime3[1] = montht;
	AlTime3[2] = dayt;
	AlTime3[3] = weekt;
	AlTime3[4] = hourt;
	AlTime3[5] = minutet;
	AlTime3[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm4()

 功能:	设置闹钟

 *************************************************/

void setalarm4(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime4[0] = yeart;
	AlTime4[1] = montht;
	AlTime4[2] = dayt;
	AlTime4[3] = weekt;
	AlTime4[4] = hourt;
	AlTime4[5] = minutet;
	AlTime4[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm5()

 功能:	设置闹钟

 *************************************************/

void setalarm5(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime5[0] = yeart;
	AlTime5[1] = montht;
	AlTime5[2] = dayt;
	AlTime5[3] = weekt;
	AlTime5[4] = hourt;
	AlTime5[5] = minutet;
	AlTime5[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm6()

 功能:	设置闹钟

 *************************************************/

void setalarm6(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime6[0] = yeart;
	AlTime6[1] = montht;
	AlTime6[2] = dayt;
	AlTime6[3] = weekt;
	AlTime6[4] = hourt;
	AlTime6[5] = minutet;
	AlTime6[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm7()

 功能:	设置闹钟

 *************************************************/

void setalarm7(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime7[0] = yeart;
	AlTime7[1] = montht;
	AlTime7[2] = dayt;
	AlTime7[3] = weekt;
	AlTime7[4] = hourt;
	AlTime7[5] = minutet;
	AlTime7[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm8()

 功能:	设置闹钟

 *************************************************/

void setalarm8(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime8[0] = yeart;
	AlTime8[1] = montht;
	AlTime8[2] = dayt;
	AlTime8[3] = weekt;
	AlTime8[4] = hourt;
	AlTime8[5] = minutet;
	AlTime8[6] = secondt;
}

/*************************************************

 函数(模块)名称:	setalarm9()

 功能:	设置闹钟

 *************************************************/

void setalarm9(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	AlTime9[0] = yeart;
	AlTime9[1] = montht;
	AlTime9[2] = dayt;
	AlTime9[3] = weekt;
	AlTime9[4] = hourt;
	AlTime9[5] = minutet;
	AlTime9[6] = secondt;
}

/*************************************************

 函数(模块)名称:	settra()

 功能:

 *************************************************/

char is_altime(unsigned char secondt, unsigned char minutet,
		unsigned char hourt, unsigned char weekt, unsigned char dayt,
		unsigned char montht, unsigned char yeart) {
	char istime = 0;
	unsigned char i;
//	unsigned char timetemp[];
//	unsigned char timetemp[] = { yeart, montht, dayt, weekt, hourt, minutet, secondt };
//	timetemp[0] = yeart;
//	timetemp[1] = montht;
//	timetemp[2] = dayt;
//	timetemp[3] = weekt;
//	timetemp[4] = hourt;
//	timetemp[5] = minutet;
//	timetemp[6] = secondt;

	timetemp[3] = hourt;
	timetemp[4] = minutet;
	timetemp[5] = secondt;

	for (i = 0; i < 3; i++) {
		if (AlTime0[i] != timetemp[i]) {
			break;
		} else if ((i == 7) && (AlTime0[i] == timetemp[i])) {
			istime = 1;
		}
	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime1[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime1[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime2[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime2[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime3[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime3[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime4[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime4[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime5[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime5[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime6[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime6[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime7[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime7[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime8[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime8[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}
//
//	for (i = 0; i < 8; i++) {
//		if (AlTime9[i] != timetemp[i]) {
//			break;
//		} else if ((i == 7) && (AlTime9[i] == timetemp[i])) {
//			istime = 1;
//		}
//	}

	return istime;
}
