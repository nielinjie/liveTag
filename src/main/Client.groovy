import groovy.net.xmlrpc.*
import org.jivesoftware.smack.*
import org.jivesoftware.smack.packet.*
import org.jivesoftware.smack.util.*
def cc=new ConnectionConfiguration('talk.google.com', 5222,'gmail.com')
cc.setSecurityMode(ConnectionConfiguration.SecurityMode.required)
def clientConnection = new XMPPConnection(cc)
clientConnection.connect()
clientConnection.login('elsaxu0816@gmail.com', '54746735','Home')   // logging in as myClientId@example.orgm
//def serverProxy = new JabberRPCServerProxy(clientConnection, 'nielinjie@gmail.com')
//println serverProxy.echo('Hello World!')
Roster roster = clientConnection.getRoster();
Collection<RosterEntry> entries = roster.getEntries();
for (RosterEntry entry : entries) {
    System.out.println(entry);
}
Chat chat = clientConnection.getChatManager().createChat('nielinjie@gmail.com',new MessageParrot());
chat.sendMessage("HI Dude");
while(true){
	sleep(1000)
}


 class MessageParrot implements MessageListener {
    
    //private Message msg = new Message("macro10.com@gmail.com", Message.Type.chat);
    
    // gtalk seems to refuse non-chat messages
    // messages without bodies seem to be caused by things like typing
    public void processMessage(Chat chat, Message message) {
    	println message.dump()
        if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
            System.out.println("Received: " + message.getBody());
            println message.getFrom()
			println "${StringUtils.parseName(message.getFrom())}"
			println "${StringUtils.parseServer(message.getFrom())}"
			println "${StringUtils.parseResource(message.getFrom())}"
//            try {
//                msg.setBody("I am a Java bot. You said: " + message.getBody());
//                chat.sendMessage(msg);
//            } catch (XMPPException ex) {
//                ex.printStackTrace();
//                System.out.println("Failed to send message");
//            }
        } else {
            System.out.println("I got a message I didn''t understand");
        }
    }
}
