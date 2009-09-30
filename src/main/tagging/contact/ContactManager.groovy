package tagging.contact
import tagging.*
import groovy.net.xmlrpc.*
import tagging.util.*
class ContactManager{
	List<Contact> contacts
	List<Contact> getActiveContacts(){
		contacts
	}
}
interface Contact{
	List<BO> request(Hint hint)
	void push(List bos)
}
class MemoryContact implements Contact{
	def syncPort
	List<BO> request(Hint hint){
		syncPort.request(hint)
	}
	void push(List<BO> bos){
		syncPort.push(bos)
	}
}
abstract class XMLRCPContact implements Contact{
	protected def proxy
	List<BO> request(Hint hint){
		return proxy.request(XML.toXML(hint)).collect{
			BO.fromString(it)
		}
	}
	void push(List bos){
		proxy.push(bos.collect{it.asString()})
	}
}
class LanContact extends XMLRCPContact{
	def ip='localhost'//like 168.1.1.101
	int port=7927//like 7927
	LanContact(){
		def address="http://${ip}:${port}"
		this.proxy = new XMLRPCServerProxy(address)
	}
	
}
class JabberContact extends XMLRCPContact{
	def address//like xuyi0816@gmail.com
	
}
class ContactManagerFactory{
    private static contactManager=new ContactManager()
    static getNewContactManager(){
        return new ContactManager()
    }
    static getContactManager(){
        return contactManager
    }
}