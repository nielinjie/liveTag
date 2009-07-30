/**
 * 
 */
package tagging

import groovy.beans.Bindable
/**
 * @author nielinjie
 *
 */
public  class Importer extends SearchView{
	def interval=1
	String type='importer'
	@Bindable boolean stop=false
	Importer(){
		this([:])
	}
	Importer(Map att){
		new Thread({
			while(!stop){
                onTimer()
                Thread.sleep(this.interval*1000)
            }
		} as Runnable).start()
	}
	def  onTimer(){}
}


