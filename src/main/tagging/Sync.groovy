package tagging
import groovy.net.xmlrpc.*
import java.net.ServerSocket
import tagging.contact.*
import tagging.util.*
import tagging.text.*
class Sync{
	TaggingManager tm
	private List<SyncPort> syncPorts
	ContactManager cm
	Map<String,SyncPort> ports=[:]
	List getBOs(Hint hint){
		def r=[]
		r.addAll(this.tm.findTagable{
			hint.filter(it)
		})
		r.addAll(this.tm.findTag{
			hint.filter(it)
		})
		return r
	}
	
	List<BO> request(hint){
		def r=[]
		cm.activeContacts.each{
			r.addAll(it.request(hint))
		}
		return r
		//call contacts' request
	}
	void push(List<BO> bos){
		cm.activeContacts.each{
			it.push(bos)
		}
		//call contacts' push
	}
	List<BO> onRequest(Hint hint){
		return [new TextTagable(text:'on request')]
	}
	void onPush(List<BO> bos){
        tm.fromOther(bos)
	}
}
//listening to contacts
abstract class SyncPort{
	
	def sync
	 def start(){}
	 def stop(){}
}
class XMLRPCSyncPort extends SyncPort{
	int port=7927
	def server
	def start(){
		this. server = new XMLRPCServer()
		server.request={
			String hint->
			this.sync.onRequest(XML.fromXML(hint)).collect{
				it.asString()
			}
		}
		server.push={
			List<String> bos->
			this.sync.onPush(bos.collect{BO.fromString(it)})
		}
		def serverSocket = new ServerSocket(port)   
		Thread.start{
			server.startServer(serverSocket)   
		}
	}
}
class MemorySyncPort extends SyncPort{
	List<BO> request(Hint hint){
		this.sync.onRequest(hint)
	}
	void push(List<BO> bos){
		this.sync.onPush(bos)
	}
}
interface Hint{
	def filter(BO bo)
}
class NewerThanHint implements Hint{
	Date date
	def filter(BO bo){
		return bo.version.newer(date)
	}
}