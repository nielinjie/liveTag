package tagging
import groovy.swing.SwingBuilder
abstract class DefaultDetailDisplayAdaptor{
    def value
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}
