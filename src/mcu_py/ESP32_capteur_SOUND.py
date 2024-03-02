import network
import urequests as requests
import machine
import time
import json


ssid = "UNIFI_IDO2"
password = "99Bidules!"

api_endpoint = "http://192.168.5.199:8080/api/data"

analog_sound_sensor_pin = 36

device_id = "fa81efab546fde6e"

wifi = network.WLAN(network.STA_IF)
wifi.active(True)
wifi.connect(ssid, password)

while not wifi.isconnected():
    pass

print("WiFi connected")
print("IP address:", wifi.ifconfig()[0])

def read_analog_sound_sensor():
    return machine.ADC(machine.Pin(analog_sound_sensor_pin)).read()

while True:
    
    sound_sensor_value = read_analog_sound_sensor()

    data = {
        "deviceid": device_id,
        "sound_sensor": sound_sensor_value
    }

    response = requests.post(api_endpoint, json=data)

    if response.status_code == 200:
        print("Data sent successfully:", json.dumps(data))
    else:
        print("Failed to send data. Status code:", response.status_code)

    time.sleep(5)