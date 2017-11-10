import socket, pickle

import sys
import numpy as num_p
from xml.dom import minidom
xmlfilename = minidom.parse('data.xml')
itemlist = xmlfilename.getElementsByTagName('Item')



s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 12348               # Reserve a port for your service.
s.bind((host, port))        # Bind to the port

s.listen(3)                 # Now wait for client connection.




while True:
	c, addr = s.accept()     # Establish connection with client.
	print 'Got connection from', addr

	c.send(pickle.dumps(itemlist))

	op = c.recv(10000)
	print "Soln is "
	print op
 
	c.close()
'''
[bbuhariwala@localhost B1]$ python queens_server.py 
Got connection from ('127.0.0.1', 34474)
Soln is 
Q***********Q**********Q*****Q****Q***********Q**Q*********Q****
^CTraceback (most recent call last):
  File "queens_server.py", line 22, in <module>
    c, addr = s.accept()     # Establish connection with client.
  File "/usr/lib64/python2.7/socket.py", line 202, in accept
    sock, addr = self._sock.accept()
KeyboardInterrupt
[bbuhariwala@localhost B1]$ 


'''
