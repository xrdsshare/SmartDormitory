#include <msp430.h>
#include <ClassTime.h>
#include <Model.h>

extern char isal, timelong;
extern unsigned char second, minute, hour, week, day, month, year; //秒、分、时、星期、日、月、年

void is_classtime() {
	isal = 0;
	timelong = 30;
//	if (CL1[1] > (59 - timelong)) {
//		CL1[0]++;
//		CL1[1] = CL1[1] - 30;
//	}
	if (CL1[0] == hour) {
		if (CL1[1] == minute) {
			isal = 1;
		}
	}
}

void showclass() {
	Disp_HZ(0x82, CL1s, 4);
	Disp_HZ(0x91, CL1m, 6);
	Disp_HZ(0x8a, CL1k, 4);
	Disp_HZ(0x9b, CL1t, 2);
}
