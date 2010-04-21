package tagging.ui
import groovy.swing.SwingBuilder
import tagging.util.*
import tagging.*
/**
 *
 * @author nielinjie
 */
abstract class DisplayAdaptor {
    //helper
    static getAdaptor(Object obj,String displayType){
        def fm=ServiceFactory.getService(FunctionMatrix.class)
        def w=fm.getFunction(obj.class.simpleName,displayType)
        if(w)
        w.value=obj
        w?.component
    }
    def value
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel().with{
            //it.opaque=true
            it
        }
    }
    def abstract getPanel()
}

