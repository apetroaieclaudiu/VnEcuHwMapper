from time import sleep
from machine import Pin

channels_list = ["CAN1", "CAN4", "CAN8", "CAN6"]
relay = Pin(16, Pin.OUT)

mux = {
    "CAN1": 0,
    "CAN2": 1,
    "CAN3": 2,
    "CAN4": 3,
    "CAN5": 4,
    "CAN6": 5,
    "CAN7": 6,
    "CAN8": 7
    }

def output_channels():
    for channel in channels_list:
        print(channel)

def change_channel(position: int, channel: str):
    channels_list[position] = channel
    position += 1
    S0 = Pin(0 * position, Pin.OUT)
    S1 = Pin(1 * position, Pin.OUT)
    S2 = Pin(2 * position, Pin.OUT)
    S3 = Pin(3 * position, Pin.OUT)

    if (mux[channel] & 0b0001):
        S0.value(1)
    else:
        S0.value(0)

    if (mux[channel] & 0b0010):
        S1.value(1)
    else:
        S1.value(0)

    if (mux[channel] & 0b0100):
        S2.value(1)
    else:
        S2.value(0)

    if (mux[channel] & 0b1000):
        S3.value(1)
    else:
        S3.value(0)

def triggerRelay():
    relay.toggle()
    sleep(5)
    relay.toggle()

def init_channels():
    for i in range(0, 16):
        pin = Pin(i, Pin.OUT)
        pin.value(0)

init_channels()
while True:
    buff = input()
    if (buff == "read_all"):
        output_channels()
    elif ("Chn" in buff):
        position = int(input())
        buff = input()
        change_channel(position, buff)
    else:
        triggerRelay()
