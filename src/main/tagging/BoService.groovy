/**
 * 
 */
package tagging



/**
 * @author nielinjie
 *
 */
public class BoService{
    private Map<String,Class> boClasses=['tag':Tag.class,'tagable':Tagable.class]
    private Map<String,String> boTypeParents=[:]
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
    private getBoTypeParent(String boType){
        this.boTypeParents.get(boType,null)
    }
}
class BoServiceFactory{
    static BoService getNewBoService(){
        return ServiceFactory.getNewService(BoService.class)
    }
    static BoService getBoService(){
        return ServiceFactory.getService(BoService.class)
    }
}
