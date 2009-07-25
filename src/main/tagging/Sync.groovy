package tagging
class Sync{
	private TaggingManager tm
	Sync(tm){
		this.tm=tm
	}
	Sync(){
		this(TaggingManagerFactory.getTaggingManager())
	}
	List getBOs(Hint hint){
		def r=[]
		r.addAll(this.tm.findTagable{
			hint.filter(it)
		})
		r.addAll(this.tm.findTag{
			hint.filter(it)
		})
	}
}
interface Hint{
	def filter(BO bo)
}
class NewerThanHint implements Hint{
	Date date
	def filter(BO bo){
		return bo.version.newser(date)
	}
}