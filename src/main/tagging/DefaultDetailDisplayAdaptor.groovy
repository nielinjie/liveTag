package tagging
import groovy.swing.SwingBuilder
abstract class DefaultDetailDisplayAdaptor{
    def value
	def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}
abstract class DefaultIconDisplayAdaptor{
    def value
	def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}