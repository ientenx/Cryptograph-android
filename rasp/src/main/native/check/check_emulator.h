#ifndef RASP_ANTI_EMULATOR_H
#define RASP_ANTI_EMULATOR_H

#include <string>

class EmulatorSecurity {
public:
    std::string check();

private:
    bool check_of_file(std::string file_name);

    bool dir(std::string dir_name);

    std::string check_of_prop(std::string cmd);
};


#endif
