package tagging.todo
import tagging.*
import tagging.text.*

class TodoTag extends Tag{
    Date deadline
    Boolean done
    String type='tag.todo'
	static newTodoTag(text){
    	println text
    	TaggingManagerFactory.getTaggingManager().tagging(new TextTagable(text:text),[new TodoTag(done:false)])
    }
}
class TodoSearchView extends SearchView{
	def name='Todo'
	def description='All todos'
	def condition={
		def tm=TaggingManagerFactory.getTaggingManager()
		def todoTags=tm.findTag{
			it.type=='tag.todo'
		}
		def re=[]
		todoTags.each{
			re.addAll(tm.getTagableForTag(it))
		}
		re
	}
}
class NotFinishedTodoSearchView extends SearchView{
	def name='Not done Todo'
	def description='All todos not done'
		def condition={
	        def tm=TaggingManagerFactory.getTaggingManager()
	        def todoTags=tm.findTag{
	            it.type=='tag.todo'&& (it.done==null || !it.done) 
	        }
	        def re=[]
	        todoTags.each{
	            re.addAll(tm.getTagableForTag(it))
	        }
	        re
	    }
}

