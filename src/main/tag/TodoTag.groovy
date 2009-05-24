package tag
import groovy.swing.*
import net.miginfocom.swing.MigLayout
import java.awt.SystemColor
class TodoTag extends Tag{
    Date deadline
    Boolean done
    String type='tag.todo'
}
class TodoTagBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return new SwingBuilder().panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:value.name)
        }
    }
}
class TodoTagDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return new SwingBuilder().panel{
            label("${value.name}")
        }
    }
}
