from logcat_monitor.logcat_monitor import LogcatMonitor

if __name__ == '__main__':
    ...
    # Monitor logcat in real time
    logcat_monitor = LogcatMonitor(main_device.serial_number, parent_folder, key_dict={"E/ActivityManager": "Test"}, rows=50)
    logcat_monitor.start_monitor()

    ...
    
    logcat_monitor.stop_monitor()