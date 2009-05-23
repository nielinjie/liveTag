import org.gmock.WithGMock
import tag.*

@WithGMock
class LiveTaggedStory extends GroovyTestCase{
    void testListTagable(){
        def tm=TaggingManagerFactory.getTaggingManager()
        mock(tm).
            findTagable(match{it}).returns([]).atLeastOnce()
        play{
            tm.findTagable{true}
        }
    }
    void testListInTagablePanel(){
        def tm=mock(){
            findTagable(match{it}).returns([]).atLeastOnce()
        }
        play{
            def currentSearchView=new SearchView(condition:{true})
            tm.findTagable(){currentSearchView.condition}
        }
    }
    void testTagableSelected(){
        def tm=mock(){
            getTagsForTagable(match{it && it instanceof Tagable}).returns([new Tag()]).atLeastOnce()
        }
        def aS=mock(){
            getAdaptor(match{it instanceof Tagable},match{it instanceof String}).returns(null).once()
            getAdaptor(match{it instanceof Tag},match{it instanceof String}).atLeastOnce()
        }
        play{
            def selectedTagable=new Tagable()
            aS.getAdaptor(selectedTagable,'detailDisplay')
            def tags=tm.getTagsForTagable(selectedTagable)
            tags.each{
                aS.getAdaptor(it,'detailDisplay')
            }
        }
    }
    void testSearchViewSelected(){
        def tm=mock(){
            findTagable(match{it}).returns([]).once()
            findTag(match{it}).returns([new Tag()]).once()
        }
        def vm=mock()
        SearchView sv=new SearchView()
        mock(sv).condition.returns({true}).atLeastOnce()
        vm.getSearchViews().returns([sv]).atLeastOnce()
        def aS=mock(){
            getAdaptor(match{it instanceof Tagable},'briefDisplay').stub()
            getAdaptor(match{it instanceof Tag},'briefDisplay').once()
            getAdaptor(match{it instanceof SearchView},'briefDisplay').atLeastOnce()
        }
        play{
            //display
            def searchViews=vm.getSearchViews()
            searchViews.each{
                aS.getAdaptor(it,'briefDisplay')
            }
            //select
            def selectedSearchView=searchViews[0]
            assertNotNull(selectedSearchView.condition)
            //search
            def tagables=tm.findTagable(selectedSearchView.condition)
            def tags=tm.findTag(selectedSearchView.condition)
            //display
            tagables.each{
                aS.getAdaptor(it,'briefDisplay')
            }
            tags.each{
                aS.getAdaptor(it,'briefDisplay')
            }

        }
    }
}
