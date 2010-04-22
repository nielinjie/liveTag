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
    def interval
    @Bindable boolean stop=false
    Importer(){
        start()
    }
//    Importer(Map att){
//       start()
//    }
    def start(){
         new Thread({
                while(!stop){
                    onTimer()
                    Thread.sleep(this.interval*1000)
                }
            } as Runnable).start()
    }
    def  onTimer(){}
}


