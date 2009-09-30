package tagging.ui

import javax.swing.*

class IconManager{
    static def icons=[:]
    static def getIcon(String name){
        if(icons[name]){
        }else{
            icons[name]=new ImageIcon(getClass().getResource("/icons/${name.toLowerCase()}.png"))
        }
        return icons[name]
    }
    
}