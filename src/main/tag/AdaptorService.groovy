package tag
import org.apache.commons.collections.map.MultiKeyMap
//@Singleton
class AdaptorService{
    private Map<String,Class> boClasses=['tag':Tag.class,'tagable':Tagable.class]
    private MultiKeyMap<String,String,Class> adaptorClasses=new MultiKeyMap<String,String,Class>()
    private Map<String,String> boTypeParents=[:]
    Class getAdaptorClass(String adapteeType, String adaptorType){
        def re=this.adaptorClasses.get(adapteeType,adaptorType)
        return re?:this.getAdaptorClass(this.getBoTypeParent(adapteeType),adaptorType)
    }
    Class getBoClass(String boType){
        return this.boClasses.get(boType,null)
    }
    void registerBo(String boType,String parentType,Class boClass){
        if (this.boClasses.containsKey(boType))
            throw new IllegalArgumentException("duplicate type - ${boType}")
        if (!(parentType in this.boClasses.keySet()))
            throw new IllegalArgumentException("parent type not find- ${parentType}")
        this.boClasses.put(boType,boClass)
        this.boTypeParents.put(boType,parentType)
    }
    void registerAdaptor(String adapteeType,String adaptorType,Class adaptorClass){
        if (this.adaptorClasses.containsKey(adapteeType,adapteeType)) throw new IllegalArgumentException("duplicate adapteeType&adaptorType - ${adapteeType}&${adaptorType}")
        this.adaptorClasses.put(adapteeType,adaptorType,adaptorClass)
    }
    private getBoTypeParent(String boType){
        this.boTypeParents.get(boType,null)
    }
}
class AdaptorServiceFactory{
    private static adaptorService=new AdaptorService()
    static AdaptorService getAdaptorService(){
        return adaptorService
    }
}
