#!/usr/bin/env python3
import time
import socket
from rpi_ws281x import PixelStrip, Color

# LED strip configuration
LED_COUNT = 10       # Number of LED pixels
LED_PIN = 18        # GPIO pin connected to the pixels (18 uses PWM!)
LED_FREQ_HZ = 800000 # LED signal frequency in hertz (usually 800khz)
LED_DMA = 10        # DMA channel to use for generating signal (try 10)
LED_BRIGHTNESS = 255 # Set to 0 for darkest and 255 for brightest
LED_INVERT = False   # True to invert the signal (when using NPN transistor level shift)

# Create PixelStrip object with configuration above
strip = PixelStrip(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)

# Initialize the LED strip library (must be called once before other functions)
strip.begin()

# Define colors
RED = Color(255, 0, 0)
GREEN = Color(0, 255, 0)
BLUE = Color(0, 0, 255)
WHITE = Color(255, 255, 255)
BLACK = Color(0, 0, 0)

# Define command-line arguments
color_map = {
    'red': RED,
    'green': GREEN,
    'blue': BLUE,
    'white': WHITE,
    'black': BLACK,
    'rainbow': None,
}

# Helper function for generating rainbow colors
def wheel(pos):
    """Generate rainbow colors across 0-255 positions."""
    if pos < 85:
        return Color(pos * 3, 255 - pos * 3, 0)
    elif pos < 170:
        pos -= 85
        return Color(255 - pos * 3, 0, pos * 3)
    else:
        pos -= 170
        return Color(0, pos * 3, 255 - pos * 3)

# Set up a server socket to listen for incoming commands
HOST = '127.0.0.1'  # Standard loopback interface address (localhost)
PORT = 8000        # Port to listen on (non-privileged ports are > 1023)
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    print(f'LED control server listening on {HOST}:{PORT}')
    while True:
        conn, addr = s.accept()
        print(f'Connected by {addr}')
        with conn:
            while True:
                data = conn.recv(1024)
                if not data:
                    break
                # Parse the received command and set the LED strip color accordingly
                command = data.decode().strip()
                if command == 'rainbow':
                    # Set the LED strip to cycle through rainbow colors
                    for j in range(256):
                        for i in range(strip.numPixels()):
                            strip.setPixelColor(i, wheel((i+j) & 255))
                        strip.show()
                        time.sleep(0.01)
                elif command.startswith('#') and len(command) == 7:
                    # Set the LED strip color based on the hex code sent over the socket
                    try:
                        r, g, b = bytes.fromhex(command[1:])
                        color = Color(r, g, b)
                    except ValueError:
                        color = BLACK
                    for i in range(strip.numPixels()):
                        strip.setPixelColor(i, color)
                    strip.show()
                else:
                    color = color_map.get(command, BLACK)
                    for i in range(strip.numPixels()):
                        strip.setPixelColor(i, color)
                    strip.show()
                conn.sendall(b'OK')
