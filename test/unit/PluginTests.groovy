import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at

import org.java.plugin.*
import org.java.plugin.boot.*
import org.java.plugin.util.*
import tagging.*
class PluginTests{
    @Test void boot(){
        String[] args={}
        def prop=new ExtendedProperties()
        prop.put('applicationRoot',new File('.').absolutePath)
		prop.put('org.java.plugin.boot.applicationPlugin','tagging.taggingManager')
        def app=Boot.boot( prop,false,Boot.BOOT_MODE_SHELL ,new BootErrorHandlerConsole(),args)
        at app,is(not(null))
		//def manager= PluginManager.lookup(tagging.TaggingManagerPlugin.class);
        at app.manager ,is (not (null))
		def extensions =
            app.manager.registry.getExtensionPoint(
                'tagging.taggingManager', 'Meta').connectedExtensions
		at extensions.size(),is(1)
//		extensions.each{
//        	def className=it.getParameter('class').valueAsString()
//        	at className,is(not(null))
//        }
        
		
    }
}