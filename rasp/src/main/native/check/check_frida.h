#ifndef RASP_ANTI_FRIDA_H
#define RASP_ANTI_FRIDA_H

#include <string>

class FridaSecure {
public:
    void check();

private:
    uint64_t frida_find_library_base(std::string library_name, char **library_path);

    uint64_t frida_find_library_space_base(uint64_t base, uint32_t page_size);
};


#endif
