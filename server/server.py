import socket
import pyautogui

s = socket.socket()
print("Socket successfully created")

port = 8000
hostname = socket.gethostname()
print(socket.gethostbyname(hostname))
s.bind(('', port))
print(port)

s.listen(5)
print("socket is listening")

while True:
    c, addr = s.accept()
    uh = c.recv(100)
    print('Got connection from', addr)
    print(uh.decode())
    if uh.decode() == "left":
        pyautogui.click()
    elif uh.decode() == "right":
        pyautogui.rightClick()
    else:
        i = uh.decode()
        ls = i.split(",")
        if len(ls)==1 and ls[0]!="":
            x = float(ls[0])
            y = 0.00
            current_x, current_y = pyautogui.position()
            pyautogui.moveTo(int(x + current_x), int(y + current_y))
        elif len(ls)==2 and ls[1]!="" and ls[0]!="":
            x = float(ls[0])
            y = float(ls[1])
            current_x, current_y = pyautogui.position()
            pyautogui.moveTo(int(x + current_x), int(y + current_y))

