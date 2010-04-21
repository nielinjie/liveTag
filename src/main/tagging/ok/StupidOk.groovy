package tagging.ok
import tagging.util.*

class StupidOK implements ObjectKeeper{
    def file='/home/nielinjie/savedObjs'
    def objs=[:]
    Object get(def id){
        return objs[id]
    }
    List search(def filter){
    	return objs.values().findAll(filter)
    }
    void clear(){
    	this.objs.clear()
    }
    void put(Object obj){
    	this.objs[obj.id]=obj
    }
    void remove(def id){
    	this.objs.remove(id)
    }
    void close(){
    	def s=XML.toXML(this.objs)
        File f=new File(this.file)
    	f.text=s
    }
    void start(){
    	def s=null
        try{
            File f=new File(this.file)
            s=f.text
        }catch(Exception e){
            e.printStackTrace()
        }
        if(s){
            this.objs=XML.fromXML(s)
        }
    }
}