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

analog_sound_sensor_pin = 36
led_pin = 26  # Pin connected to the LED

device_id = "fa81efab546fde6e"

def read_analog_sound_sensor():
    return machine.ADC(machine.Pin(analog_sound_sensor_pin)).read()

def control_led(sound_value):
    led = machine.Pin(led_pin, machine.Pin.OUT)
    if sound_value > 1000:
        led.on()
        return "On"
    else:
        led.off()
        return "Off"

while True:
    try:
        current_time = localtime()

        hour = (current_time[3] - 3) % 24

        date_str = "{:04d}-{:02d}-{:02d}".format(current_time[0], current_time[1], current_time[2])
        time_str = "{:02d}:{:02d}".format(hour, current_time[4])

        sound_sensor_value = read_analog_sound_sensor()
        led_state = control_led(sound_sensor_value)

        data = {
            "deviceid": device_id,
            "sound": sound_sensor_value,
            "led": led_state,
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

    time.sleep(10)
