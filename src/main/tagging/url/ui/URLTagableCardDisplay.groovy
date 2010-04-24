package tagging.url.ui
import tagging.ui.*
import java.awt.*
/**
 *
 * @author nielinjie
 */
class URLTagableCardDisplay extends DisplayAdaptor{
	def getPanel(){
        return sb.menu(icon:IconManager.getIcon('a'),text:value.url){
            menuItem('Send to default browser',actionPerformed:{Desktop.getDesktop().browse(new java.net.URI(value.url))})
            //widget(DisplayAdaptor.getAdaptor(new KeywordTagSearchView(keywordTag:value),'buttonDisplay'))
        }
    }
}

