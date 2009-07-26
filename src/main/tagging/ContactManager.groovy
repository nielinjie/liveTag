package tagging
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
	void push(List bos){
		syncPort.push(bos)
	}
}
abstract class XMLRCPContact implements Contact{
	private def connection
	private def proxy
	List<BO> request(Hint hint){
		return proxy.request(hint)
	}
	void push(List bos){
		proxy.push(bos)
	}
}
class LanContact extends XMLRCPContact{
	def ip//like 168.1.1.101
}
class JabberContact extends XMLRCPContact{
	def address//like xuyi0816@gmail.com
}