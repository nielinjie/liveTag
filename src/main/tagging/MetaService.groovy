/**
 * 
 */
package tagging



/**
 * @author nielinjie
 * find info from bo data and cache.
 * download module from site
 *
 */
public class MetaService{
    //should be osgi name?
    List providers=[]
    //if provider is a osgi, no init needed, osgi init itself
    def init(){
        this.providers.each{
            if (!(it instanceof Class))
                if(it instanceof String) it=Class.forName(it)
            it.getMethod('provideMeta').invoke(it,new Object[0])
        }
    }
}

class MetaServiceFactory{
    static MetaService getNewMetaService(){
        return ServiceFactory.getNewService(MetaService.class)
    }
    static MetaService getMetaService(){
        return ServiceFactory.getService(MetaService.class)
    }
}
