package tagging.text
import tagging.*
import tagging.text.*
class TextTagable extends Tagable{
    String text
    String type='tagable.text'
    def getKeywords(){
        (text=~/#(\w*+)/).collect{
            it[1]
        }
    }
}
