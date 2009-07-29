package tagging
import groovy.swing.*
import javax.swing.*
import java.beans.*
class SearchView{
    def name
    def description
    def condition
    def sortComparator
    def type='searchView'
}
class SearchService{
    List<SearchView> searchView=[]
}

class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
	def iconNames=['search']
    def getPanel(){
        return new SwingBuilder().panel{
            etchedBorder(parent:true)
            label(text:value.name)
            button(icon:this.icons['search'],text:'Search',actionPerformed:{ controller?.selectSearchView(value)})
        }
    }
}
class SearchViewMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
    }
}
class ViewFrameManager{
    
}
class ViewFrame extends StatusObservable{
    def deltaSize=20
    private int currentMaxIndex=0
	def description='a common view frame'
    def allContent=[]
    def getDelta(){
    	if(allContent.size==0)return[]
        def re= allContent[currentMaxIndex..([currentMaxIndex+deltaSize-1,allContent.size-1].min())]
    	currentMaxIndex=([currentMaxIndex+deltaSize,allContent.size].min())
		statusChange()
		return re
    }
    def getRequested(){
    	if(allContent.size==0)return[]
        return allContent[0..currentMaxIndex-1]
    }
    def hasMore(){
    	return allContent.size!=0 && currentMaxIndex<allContent.size
    }
    def reset(){
        this.currentMaxIndex=0
		statusChange()
    }
}
class History extends StatusObservable{
    private int currentIndex=0
    def queue=[]
    def getCurrent(){
    	return this.queue[this.currentIndex]
    }
    def to(int index){
    	assert index>=0
    	this.currentIndex=index
        def current=queue[index]
    	statusChange()
        return current
    }
    def back(){
        this.currentIndex-=1
        if(this.currentIndex<0)this.currentIndex=0
        statusChange()
        return queue[currentIndex]
    }
    def forward(){
    	this.currentIndex+=1
		if (this.currentIndex>this.queue.size-1)this.currentIndex=this.queue.size-1
		statusChange()
    	return queue[currentIndex]
    }
    def hasBack(){
    	return this.currentIndex>0
    }
    def hasForward(){
    	return this.currentIndex<this.queue.size-1
    }
    def add(item){
        while(queue.size>this.currentIndex+1)
            queue.remove(queue.size-1)
        queue<<item
        currentIndex=queue.size-1
		statusChange()
    }
}

