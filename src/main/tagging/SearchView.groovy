package tagging
import groovy.swing.*
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
    def getPanel(){
        return new SwingBuilder().panel{
            etchedBorder(parent:true)
            label(text:value.name)
        }
    }
}
class SearchViewMeta{
	def static provideMeta(){
		def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
	}
}
