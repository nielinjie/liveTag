package tagging
class Sync{
	TaggingManager tm
	private List<SyncPort> syncPorts
	ContactManager cm
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
		return []
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