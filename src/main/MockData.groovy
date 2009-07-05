/**
 * 
 */


import tagging.*
import tagging.todo.*
import tagging.text.*
import tagging.twitter.*
import tagging.people.*
/**
 * @author nielinjie
 *
 */

public class MockData{
	static def metas=['tagging.twitter.TwitterMeta',TodoTagMeta.class,TextTagableMeta.class,ImporterMeta.class,SearchViewMeta.class]
    static def bos=[
	    new TodoTag(name:'a todo'),
	    new TodoTag(name:'second todo'),
	    new TodoTag(name:'3rd todo'),
	    new TextTagable(name:'text tagable',text:'I am a plain text tagable')
    ]
    static def searchViews=[
	    new SearchView(name:'All',description:'All Bos',condition:{true
		    },sortComparator:{a,b->0
		    }),
		new SearchView(name:'All People',description:'All People',condition:{
	    		TaggingManagerFactory.getTaggingManager().findTagable{obj->obj instanceof PeopleTagable}
            },sortComparator:{a,b->0
            }),
//	    new Importer(name:'A Sample Importer',description:'Sample Importer',condition:{true
//		    },sortComparator:{a,b->0
//		    },interval:3),
	    new tagging.twitter.TwitterImporter(username:'nielinjie',password:'790127',name:'A Sample Twitter Importer',description:'Sample Twitter Importer',interval:300)
    ]
}
