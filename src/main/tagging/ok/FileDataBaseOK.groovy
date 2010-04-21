package tagging.ok
import org.apache.log4j.*

class FileDataBaseOK implements ObjectKeeper{
    def dataBase=new FileDatabase(root:'~/LiveTag/objectKeeper/save')
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

class FileDatabase extends FolderUser{
    static log=Logger.getLogger(FileDatabase.class)
    java.io.File rootPath
    List list(String type){
        ensureRootExist()
        def re=[]
        rootPath.eachFile{
            if(it.name.endsWith('.'+type)){
                try{
                    re<<XML.fromXML(it.text)
                }catch(IllegalStateException le){
                    throw new ReadingException(le,"Reading - ${it.absolutePath}",it)
                }
            }
        }
        re
    }
    void save(Object obj,String type,Closure nameCreate={it.id.toString()}){
        log.debug "rootPath in saving - $rootPath"
        ensureRootExist()
        def file=new java.io.File(rootPath,nameCreate(obj)+'.'+type)
        if(!file.exists()){
            file.createNewFile()
        }
        log.debug file.absolutePath
        file.text=XML.toXML(obj)
    }
}
abstract class FolderUser {
    protected ensureRootExist(){
        if(!rootPath.exists()){
            rootPath.mkdir()
        }else{
            if(!rootPath.isDirectory()){
                throw new RuntimeException("file exists, but not a dir - ${rootPath.absolutePath}}")
            }
        }
    }
}
class ReadingException extends IllegalStateException{
    java.io.File file=null
    ReadingException(Throwable e,String string,java.io.File file){
        super(string,e)
        this.file=file
    }
}