import requests
import Adafruit_DHT
import time

API_ENDPOINT = "http://192.168.5.199:8080/api/data"
DHT_PIN = 4  

def read_dht11_data():
    humidity, temperature = Adafruit_DHT.read_retry(Adafruit_DHT.DHT11, DHT_PIN)
    return temperature, humidity

def read_deviceid():
    deviceid = "ca492093a449d75d"  
    return deviceid

def send_data(deviceid, temperature, humidity):
    data = {
        "deviceid": deviceid,
        "temperature": temperature,
        "humidity": humidity
    }
    try:
        response = requests.post(API_ENDPOINT, json=data)
        if response.status_code == 200:
            print(data)
        else:
            print(f"Failed to send data. Status code: {response.status_code}")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    interval_seconds = 5

    while True:
        deviceid = read_deviceid()
        temperature, humidity = read_dht11_data()
        send_data(deviceid, temperature, humidity)
        time.sleep(interval_seconds)

