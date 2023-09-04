#ifndef RASP_ANTI_MEM_DUMP_H
#define RASP_ANTI_MEM_DUMP_H

#include <string>

unsigned static int gpCrash = 0xfa91b9cb;

class AntiMemDump {
public:
    static void detect_memory_dump_loop(void *args);

private:
    inline static void detect_fileaccess_for_debugger_memorydump();

    inline static int crash(int randomval);

private:
};


#endif
