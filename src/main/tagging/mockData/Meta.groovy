package tagging.mockData
import tagging.*
import tagging.todo.*
import tagging.text.*
import tagging.people.*

class Meta{
    static void provideMeta(){
    	def tm=TaggingManagerFactory.getTaggingManager()
		def sm=SearchServiceFactory.getSearchService()
        def searchViews=[
            new SearchView(name:'All',description:'All Bos',condition:{
            	TaggingManagerFactory.getTaggingManager().findTagable({true})
            },sortComparator:{a,b->0
            }),
            new SearchView(name:'All People',description:'All People',condition:{
                TaggingManagerFactory.getTaggingManager().findTagable{obj->obj instanceof PeopleTagable}
            },sortComparator:{a,b->0
            }),
            new tagging.twitter.TwitterImporter(username:'nielinjie',password:'790127',name:'A Sample Twitter Importer',description:'Sample Twitter Importer',interval:300)
            ]
    	searchViews.each{
    		sm.searchViews<<it
    	}
        def mockTagables=
            (1..50).collect{new TextTagable(name:'mock text tagable',text:"I am mock text tagable - No. ${it}")}
        mockTagables.each{
        	tm.addTagable(it)
        }
    }
}