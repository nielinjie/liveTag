package tagging.ui
import groovy.swing.SwingBuilder
import javax.swing.JLabel
import javax.swing.ImageIcon
import java.net.URL
/**
 *
 * @author nielinjie
 */
class RemoteImageLabel extends JLabel{
    def placeHolder=IconManager.getIcon('Unknown48')
    def real=null
    RemoteImageLabel(String url){
        super()
        this.icon=placeHolder
        //this.repaint()
        //sleep(1000)
        new SwingBuilder().doLater{
            setUrl(url)
        }
    }
    def setUrl(String url){
        new SwingBuilder().doOutside{
//            println 'before image'
            real=new FixedSizeImageIcon(48, 48, new URL(url))
//            println 'after image'
            edt{
                this.setIcon(real)
            }
        }
    }
}

