package tagging
import groovy.swing.SwingBuilder
abstract class DefaultDetailDisplayAdaptor{
    def value
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}
