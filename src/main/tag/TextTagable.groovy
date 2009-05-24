package tag
import groovy.swing.*
import net.miginfocom.swing.MigLayout
class TextTagable extends Tagable{
    String text
    String type='tagable.text'
}
class TextTagableBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return new SwingBuilder().panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:"${value.name} - ${value.text}")
        }
    }
}
class TextTagableDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return new SwingBuilder().panel{
            label(text:"${value.name} - ${value.text}")
        }
    }
}
