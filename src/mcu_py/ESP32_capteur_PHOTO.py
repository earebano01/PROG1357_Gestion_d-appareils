import network
import urequests as requests
import machine
import time
import json
from ntptime import settime
from machine import RTC
from time import localtime, sleep

rtc = RTC()

ssid = "BELL197-V"
password = "Wel245come!@home"
wifi = network.WLAN(network.STA_IF)
wifi.active(True)
wifi.connect(ssid, password)

while not wifi.isconnected():
    sleep(1)

settime()

api_endpoint = "http://192.168.5.199:8080/api/data"

photoresistor_pin = 36

device_id = "ce25e9768cfe0061"

def read_photoresistor():
    return machine.ADC(machine.Pin(photoresistor_pin)).read()

while True:
    try:
        current_time = localtime()

        hour = (current_time[3] - 3) % 24

        date_str = "{:04d}-{:02d}-{:02d}".format(current_time[0], current_time[1], current_time[2])
        time_str = "{:02d}:{:02d}".format(hour, current_time[4])

        data = {
            "deviceid": device_id,
            "photoresistor": read_photoresistor(),
            "date": date_str,
            "time": time_str
        }

        response = requests.post(api_endpoint, json=data)

        if response.status_code == 200:
            print("Data sent successfully:", json.dumps(data))
        else:
            print("Failed to send data. Status code:", response.status_code)
    except Exception as e:
        print("Error:", e)

    time.sleep(5)

