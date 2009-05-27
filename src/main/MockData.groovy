/**
 * 
 */


import tagging.*
import tagging.todo.*
import tagging.text.*
/**
 * @author nielinjie
 *
 */

public class MockData{
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
	    new Importer(name:'A Sample Importer',description:'Sample Importer',condition:{true
		    },sortComparator:{a,b->0
		    },interval:3),
	    new tagging.twitter.TwitterImporter(name:'A Sample Twitter Importer',description:'Sample Twitter Importer',condition:{true
		    },sortComparator:{a,b->0
		    },interval:3)
    ]
}
