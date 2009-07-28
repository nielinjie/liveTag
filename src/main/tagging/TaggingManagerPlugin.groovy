// Placed in public domain by Dmitry Olshansky, 2006
package tagging;




import org.java.plugin.boot.Application;
import org.java.plugin.boot.ApplicationPlugin;
import org.java.plugin.boot.Boot;
import org.java.plugin.util.ExtendedProperties;



public  class TaggingManagerPlugin extends ApplicationPlugin implements Application {
    /**
     * This plug-in ID.
     */
    public static final String PLUGIN_ID = "tagging.taggingManager";
    
 
  
    /**
     * @see org.java.plugin.Plugin#doStart()
     */
    @Override
    protected void doStart() throws Exception {
        // no-op
		println 'doStart'
    }

    /**
     * @see org.java.plugin.Plugin#doStop()
     */
    @Override
    protected void doStop() throws Exception {
        // no-op
		println 'doStop'
    }

    /**
     * @see org.java.plugin.boot.ApplicationPlugin#initApplication(
     *      ExtendedProperties, String[])
     */
    @Override
    protected Application initApplication(final ExtendedProperties config,
            final String[] args) throws Exception {
    	println 'initApplication'
        return this;
    }

    /**
     * @see org.java.plugin.boot.Application#startApplication()
     */
    public void startApplication() {
        println 'startApplication'
    }
    
  
}
