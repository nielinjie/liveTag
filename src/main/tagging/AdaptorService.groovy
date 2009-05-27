package tagging
import org.apache.commons.collections.map.MultiKeyMap
//@Singleton
class AdaptorService{
    def boService=BoServiceFactory.getBoService()
    private MultiKeyMap<String,String,Class> adaptorClasses=new MultiKeyMap<String,String,Class>()

    Class getAdaptorClass(String adapteeType, String adaptorType){
        if (!adaptorType || !adapteeType) return null
        def re=this.adaptorClasses.get(adapteeType,adaptorType)
        return re?:this.getAdaptorClass(boService.getBoTypeParent(adapteeType),adaptorType)
    }
    
    void registerAdaptor(String adapteeType,String adaptorType,Class adaptorClass){
        if (this.adaptorClasses.containsKey(adapteeType,adapteeType)) throw new IllegalArgumentException("duplicate adapteeType&adaptorType - ${adapteeType}&${adaptorType}")
        this.adaptorClasses.put(adapteeType,adaptorType,adaptorClass)
    }
  
}
class AdaptorServiceFactory{
    private static adaptorService=new AdaptorService()
    static AdaptorService getAdaptorService(){
        return adaptorService
    }
}
