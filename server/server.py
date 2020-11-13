import socket                
import pyautogui

s = socket.socket()          
print("Socket successfully created")
  
port=8000
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
   if uh.decode()=="left":
   	pyautogui.click()
   elif uh.decode()=="right":
       pyautogui.rightClick()
   else:
       i = uh.decode()
       ls = i.split(",")
       x = float(ls[0])
       y = float(ls[1])
       current_x, current_y = pyautogui.position()


       pyautogui.moveTo(int(x+current_x), int(y+current_y)) 
   
