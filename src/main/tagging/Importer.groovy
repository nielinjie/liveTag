/**
 * 
 */
package tagging
import groovy.beans.Bindable
import groovy.swing.*

/**
 * @author nielinjie
 *
 */
public  class Importer extends SearchView{
	def interval=1
	String type='importer'
	@Bindable boolean stop=false
	Importer(){
		new Thread({
			while(!stop){
                onTimer()
                Thread.sleep(this.interval*1000)
            }
		} as Runnable).start()
	}
	def  onTimer(){}
}
class ImporterBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
	def getPanel(){
		return new SwingBuilder().panel{
			label(text:value.name)
			label(text:bind{value.stop})
		}
	}
}
class ImporterMeta{
	def static provideMeta(){
		def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer','briefDisplay',ImporterBriefDisplayAdaptor.class)
	}
}
