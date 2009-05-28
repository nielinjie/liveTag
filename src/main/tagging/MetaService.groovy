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
	static def metaService=new MetaService()
	static def getMetaService(){
		return metaService
	}
}
