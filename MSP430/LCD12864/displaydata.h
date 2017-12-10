#ifndef DISPLAYDATA_H_
#define DISPLAYDATA_H_

const unsigned char space[] = { " " };
const unsigned char time[][2] = { "AM", "PM"};
const unsigned char timem[][60] = { ":00:", ":01:", ":02:", ":03:", ":04:",
		":05:", ":06:", ":07:", ":08:", ":09:", ":10:", ":11:", ":12:", ":13:",
		":14:", ":15:", ":16:", ":17:", ":18:", ":19:", ":20:", ":21:", ":22:",
		":23:", ":24:", ":25:", ":26:", ":27:", ":28:", ":29:", ":30:", ":31:",
		":32:", ":33:", ":34:", ":35:", ":36:", ":37:", ":38:", ":39:", ":40:",
		":41:", ":42:", ":43:", ":44:", ":45:", ":46:", ":47:", ":48:", ":49:",
		":50:", ":51:", ":52:", ":53:", ":54:", ":55:", ":56:", ":57:", ":58:",
		":59:"};
const unsigned char weekc[] = { "星期" };
const unsigned char weeks[][7] = { "一", "二", "三", "四", "五", "六", "日" };
const unsigned char hyear[]={"年"};
const unsigned char hmonth[]={"月"};
const unsigned char hday[]={"日"};
const unsigned char temperature[] = { "℃" };
const unsigned char weather[][5]={"晴天", "阴天"};
const unsigned char humidity[] = { "RH" };
const unsigned char light[] = { "LU" };

#endif
