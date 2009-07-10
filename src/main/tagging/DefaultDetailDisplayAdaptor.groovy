package tagging
import groovy.swing.SwingBuilder
import javax.swing.*
abstract class DefaultDetailDisplayAdaptor{
    def iconNames=[]
    @Lazy def icons={
        [:].putAll(
        this.iconNames.collect{
            name->
            new MapEntry(name,new ImageIcon(getClass().getResource("/icons/${name.toLowerCase()}.png")))
        }
        )}()
    def value
    def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}
abstract class DefaultIconDisplayAdaptor{
    def iconNames=[]
    @Lazy def icons={
        [:].putAll(
        this.iconNames.collect{
            name->
            new MapEntry(name,new ImageIcon(getClass().getResource("/icons/${name.toLowerCase()}.png")))
        }
        )}()
    def value
    def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel()
    }
    def abstract getPanel()
}