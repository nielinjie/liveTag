package tagging
import java.beans.*
class StatusObservable{
    def eventSetDescriptors=[new EventSetDescriptor(History.class, 'statusChanged',StatusChangedListener.class, 'statusChanged') ]
    private def statusListeners=[]
    void addStatusChangedListener(StatusChangedListener listener){
        statusListeners<<listener
    }
    void removeStatusChangedListener(StatusChangedListener listener){
        statusListeters.remove(listener)
    }
    void statusChange(){
    	statusListeners.each{
            it.statusChanged()
        }

    }
}
interface StatusChangedListener extends EventListener{
    void statusChanged(StatusChangedEvent event)
}
class StatusChangedEvent extends EventObject{
	
}