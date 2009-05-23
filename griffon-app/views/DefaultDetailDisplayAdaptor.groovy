import groovy.swing.SwingBuilder
class DefaultDetailDisplayAdaptor{
    def value
    def getComponent(){
        return new SwingBuilder().panel{
            label("${value.name}")
        }
    }
}
