import groovy.net.xmlrpc.*
import org.jivesoftware.smack.*

def server = new JabberRPCServer()
server.echo = {return it}  
def cc=new ConnectionConfiguration('talk.google.com', 5222, 'google.com')
cc.setSecurityMode(ConnectionConfiguration.SecurityMode.required)
def serverConnection = new XMPPConnection(cc)
serverConnection.connect()
serverConnection.login('nielinjie@gmail.com', '790127')   // logging in as myServerId@example.org
server.startServer(serverConnection)
while(true){
	sleep(1000)
}
