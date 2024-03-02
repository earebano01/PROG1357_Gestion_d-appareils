import network
import urequests as requests
import machine
import time
import json

ssid = "UNIFI_IDO2"
password = "99Bidules!"

api_endpoint = "http://192.168.5.199:8080/api/data"

photoresistor_pin = 36

device_id = "ce25e9768cfe0061"

wifi = network.WLAN(network.STA_IF)
wifi.active(True)
wifi.connect(ssid, password)

while not wifi.isconnected():
    pass

print("WiFi connected")
print("IP address:", wifi.ifconfig()[0])

def read_photoresistor():
    return machine.ADC(machine.Pin(photoresistor_pin)).read()

while True:
    photoresistor_value = read_photoresistor()

    data = {
        "deviceid": device_id,
        "photoresistor": photoresistor_value
    }

    response = requests.post(api_endpoint, json=data)

    if response.status_code == 200:
        print("Data sent successfully:", json.dumps(data))
    else:
        print("Failed to send data. Status code:", response.status_code)

    time.sleep(5)
