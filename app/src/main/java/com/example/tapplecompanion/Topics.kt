package com.example.tapplecompanion

val tappleTopics = listOf(
    // 1–10
    "Jotain pyöreää",            //1
    "Jotain mikä on kylmää",     //2
    "Eläimiä metsässä",          //3
    "Asioita koulussa",          //4
    "Punaisia asioita",          //5
    "Jotain mikä haisee",        //6
    "Urheilulajeja",             //7
    "Asioita taskussa",          //8
    "Jotain joka on rikki",      //9
    "Ammatteja",                 //10

    // 11–20
    "Asioita rannalla",          //11
    "Jotain makeaa",             //12
    "Kaupunkeja Suomessa",       //13
    "Asioita jotka lentävät",    //14
    "Elokuvien nimiä",           //15
    "Jotain metallista",         //16
    "Asioita bussissa",          //17
    "Jotain mikä on vihreää",    //18
    "Lemmikkieläimiä",            //19
    "Asioita jääkaapissa",       //20

    // 21–30
    "Hedelmiä",                  //21
    "Asioita kylpyhuoneessa",    //22
    "Jotain pehmeää",            //23
    "Julkkiksia",                //24
    "Asioita työpaikalla",       //25
    "Jotain mikä on kallista",   //26
    "Kaupan osastoja",           //27
    "Asioita lentokentällä",     //28
    "Jotain lämmintä",           //29
    "Vaatemerkkejä",             //30

    // 31–40
    "Kalaruokia",                //31
    "Asioita autossa",           //32
    "Jotain joka pitää ääntä",   //33
    "Kirjoja",                   //34
    "Asioita jotka avataan",     //35
    "Eläimiä Afrikassa",         //36
    "Jotain sinistä",            //37
    "Harrastuksia",              //38
    "Asioita keittiön laatikossa",//39
    "TV-sarjoja",                //40

    // 41–50
    "Jotain terävää",            //41
    "Asioita puistossa",         //42
    "Pelejä",                    //43
    "Jotain joka kasvaa",        //44
    "Asioita toimistossa",       //45
    "Jälkiruokia",               //46
    "Asioita jotka ovat kiellettyjä", //47
    "Jotain mikä on märkä",      //48
    "Maat Euroopassa",           //49
    "Asioita matkalaukussa",     //50

    // 51–60
    "Jotain mikä on vanhaa",     //51
    "Asioita sairaalassa",       //52
    "Jotain mikä hajoaa helposti",//53
    "Kasviksia",                 //54
    "Asioita jotka voi kadottaa",//55
    "Jotain mustaa",             //56
    "Lintuja",                   //57
    "Asioita koulurepussa",      //58
    "Jotain mikä on nopeaa",     //59
    "Kahvilassa myytäviä asioita",//60

    // 61–70
    "Asioita joilla on napit",   //61
    "Jotain joka maistuu pahalta",//62
    "Tunnettuja suomalaisia",    //63
    "Asioita elokuvateatterissa",//64
    "Jotain mikä on painavaa",   //65
    "Asioita varastossa",        //66
    "Makeisia",                  //67
    "Jotain mikä on vaarallista",//68
    "Mereneläviä",               //69
    "Asioita juhlapöydässä",     //70

    // 71–80
    "Jotain mikä rullaa",        //71
    "Asioita urheiluhallissa",   //72
    "Sähkölaitteita",            //73
    "Jotain mikä on laitonta",   //74
    "Asioita puhelimessa",       //75
    "Keittoja",                  //76
    "Jotain joka kiiltää",       //77
    "Asioita junassa",           //78
    "Eläimiä vedessä",           //79
    "Asioita joita ei saa unohtaa",//80

    // 81–90
    "Jotain mikä on tahmeaa",    //81
    "Asioita festareilla",       //82
    "Suomalaisia ruokia",        //83
    "Jotain mikä on ruma",       //84
    "Asioita jotka haisevat hyvälle",//85
    "Kengän osia",               //86
    "Asioita luokkahuoneessa",   //87
    "Jotain mikä on kallisarvoista",//88
    "Kalalajeja",                //89
    "Asioita torilla",           //90

    // 91–100
    "Jotain mikä on kielletty lapsilta",//91
    "Asioita hotellissa",        //92
    "Jotain mikä on liian suurta",//93
    "Eläimiä jotka nukkuvat talviunta",//94
    "Asioita joita kerätään",    //95
    "Jotain joka on hajonnut",   //96
    "Pizzan täytteitä",          //97
    "Asioita lentokoneessa",     //98
    "Jotain mikä on tylsää",     //99
    "Videopelihahmoja",          //100

    // 101–110
    "Jotain mikä on yllättävää", //101
    "Asioita kellarissa",        //102
    "Soittimia joissa on kielet",//103
    "Jotain mikä on pehmeää sisältä",//104
    "Asioita häissä",            //105
    "Aamupalalla syötäviä asioita",//106
    "Jotain mikä on vaarallista koskea",//107
    "Asioita kaupungilla",       //108
    "Hyönteisiä",                //109
    "Asioita joita voi kierrättää",//110

    // 111–120
    "Jotain mikä on rikki mutta käytössä",//111
    "Asioita postissa",          //112
    "Juomia",                    //113
    "Jotain mikä on harvinaista",//114
    "Asioita laivalla",          //115
    "Lastenleluja",              //116
    "Jotain mikä on likainen",   //117
    "Asioita joilla mitataan",   //118
    "Mausteita",                 //119
    "Asioita huoltoasemalla",    //120

    // 121–130
    "Jotain mikä on liian painavaa",//121
    "Asioita varpaissa",         //122
    "Karkkeja",                  //123
    "Jotain mikä on arvokasta",  //124
    "Asioita vintillä",          //125
    "Eläimiä jotka lentävät",    //126
    "Jotain mikä on muovia",     //127
    "Asioita ravintolassa",      //128
    "Keittiövälineitä",          //129
    "Asioita joilla leikataan",  //130

    // 131–140
    "Jotain mikä on läpinäkyvää",//131
    "Asioita joita pelätään",    //132
    "Maailman nähtävyyksiä",     //133
    "Jotain mikä on noloa",      //134
    "Asioita joita ei saa koskea",//135
    "Kampauksia",                //136
    "Asioita joita voi rikkoa",  //137
    "Jotain mikä on hauskaa",    //138
    "Eläimiä kotipihassa",       //139
    "Asioita elokuvissa syötäväksi",//140

    // 141–150
    "Jotain mikä on salainen",   //141
    "Asioita joita voi lainata", //142
    "Pelejä joita pelataan ulkona",//143
    "Jotain mikä on tahallista", //144
    "Asioita musiikkitunnilla", //145
    "Leivonnaisia",              //146
    "Jotain mikä on liian pientä",//147
    "Asioita joilla kirjoitetaan",//148
    "Eläimiä eläintarhassa",     //149
    "Asioita jotka ovat kiellettyjä koulussa",//150

    // 151–160
    "Jotain mikä on trendikästä",//151
    "Asioita keikkapaikalla",    //152
    "Juustoja",                  //153
    "Jotain mikä on tylsä",      //154
    "Asioita joita voi kerätä",  //155
    "Värejä",                    //156
    "Asioita joilla liikutaan",  //157
    "Jotain mikä on vaarallista eläimille",//158
    "Asioita mökillä",           //159
    "Asioita jotka kuuluvat kesään",//160

    // 161–170
    "Jotain mikä on talvinen",   //161
    "Asioita joita voi grillata",//162
    "Kakkuja",                   //163
    "Jotain mikä on liian kuumaa",//164
    "Asioita rinteessä",         //165
    "Eläimiä jotka asuvat vedessä",//166
    "Asioita joilla voi satuttaa",//167
    "Jotain mikä on pehmeää päältä",//168
    "Asioita joita käytetään yöllä",//169
    "Asioita jotka kuuluvat juhliin",//170

    // 171–180
    "Jotain mikä on vaarallista lapsille",//171
    "Asioita veneessä",          //172
    "Maitotuotteita",            //173
    "Jotain mikä on ruma mutta hyödyllinen",//174
    "Asioita joilla voi peittää",//175
    "Eläimiä jotka asuvat puissa",//176
    "Asioita jotka katoavat helposti",//177
    "Jotain mikä on kallista korjata",//178
    "Asioita joita voi säilyttää",//179
    "Asioita jotka kuuluvat syksyyn",//180

    // 181–190
    "Jotain mikä on keväinen",   //181
    "Asioita joita löytyy laatikosta",//182
    "Pastaruokia",               //183
    "Jotain mikä on liian kirkasta",//184
    "Asioita jotka vaativat sähköä",//185
    "Eläimiä jotka asuvat luolissa",//186
    "Asioita joilla voi koristella",//187
    "Jotain mikä on tylsää odottaa",//188
    "Asioita joita voi purra",   //189
    "Asioita jotka kuuluvat arkeen",//190

    // 191–200
    "Jotain mikä on luksusta",   //191
    "Asioita jotka löytyvät taskulampusta",//192
    "Riisiruoat",                //193
    "Jotain mikä on vaikea lausua",//194
    "Asioita joita käytetään sateella",//195
    "Eläimiä jotka ovat yöeläimiä",//196
    "Asioita jotka rikkoutuvat helposti",//197
    "Jotain mikä on yllättävän painavaa",//198
    "Asioita joita voi hävittää",//199
    "Asioita jotka kuuluvat lomaan"//200
)