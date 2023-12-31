#include "check_emulator.h"
#include "../io/_mini_io.h"
#include <fcntl.h>
#include <vector>
#include <errno.h>
#include "sys/system_properties.h"
#include "../utils/Utils.h"


std::string EmulatorSecurity::check() {
    std::string result = "Emulator Secure";
    std::vector<std::string> list_of_files =
            {
                    "/boot/bstmods/vboxguest.ko",
                    "/boot/bstmods/vboxsf.ko",
                    "/dev/qemu_pipe",
                    "/dev/socket/qemud",
                    "/dev/socket/windroyed-audio",
                    "/dev/socket/windroyed-camera",
                    "/dev/socket/windroyed-gps",
                    "/dev/socket/windroyed-sensors",
                    "/dev/vboxguest"
            };
    for (auto file: list_of_files) {
        if (check_of_file(file) || dir(file)) {
            result = "checked";
        }
    }

    std::vector<std::string> list_of_props = {
            "ro.redfinger.server.enable",
            "androVM.vbox_dpi",
            "androVM.vbox_graph_mode"
    };

    for (auto prop: list_of_props) {
        if (check_of_prop(prop).find("redfinger") != std::string::npos) {
            result = "checked";
        }
    }

    LOGE("result: %s", result.c_str());
    return result;
}

bool EmulatorSecurity::check_of_file(std::string file_name) {
    int fd = _open(file_name.c_str(), O_RDONLY);
    if (fd == errno || fd == -1) {
        return false;
    }
    return true;
}

bool EmulatorSecurity::dir(std::string dir_name) {
    int fd = _open(dir_name.c_str(), O_DIRECTORY);
    if (fd == errno || fd == -1) {
        return false;
    }
    return true;
}

std::string EmulatorSecurity::check_of_prop(std::string cmd) {
    char value[256];
    std::string result;
    __system_property_get(cmd.c_str(), value);

    result = value;

    memset(value, 0, sizeof value);

    return result;
}
