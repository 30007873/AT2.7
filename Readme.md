<h1>JMC File Transfer using Sockets</h1>
<h2>Author: Jai Ananda</h2>
<h3>Description</h3>
<p>This program is intended to transfer the files from server to client.
The program requires the file book1.csv and test.csv to be placed in /uploads folder
of the project home folder. Then the server is started while the client is still not started.
At this point, the server would be listening to any incoming connections from the client. 
Once the client is started, the handshake between the server and the client is established
using sockets. After the handshake is successful, the files book1.csv is transferred to /downloads
 folder for the client; however, test.csv is reserved for testing purposes. Hence, it is recommended that these
  files be not removed for the program to run successfully.</p>