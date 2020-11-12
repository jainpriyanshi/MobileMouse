import socket                
import pyautogui

s = socket.socket()          
print("Socket successfully created")
  
port = 8000                                                                                                                                   
HEADER_LENGTH = 10 
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
   else:
   	pyautogui.rightClick()	
   	
   #print(username)
   
