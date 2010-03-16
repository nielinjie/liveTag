package tagging.util
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 *
 * @author nielinjie
 */
class ClasspathScanner {
    Set<ClassName> getResources(String basePackage) {
        try {
            Set<URL> rootDirectories = getRootDirectories(ClassUtils.convertClassNameToResourcePath(basePackage));
            Set<ClassName> resources = new HashSet<ClassName>();

            for (URL rootDirectory : rootDirectories) {
                //rootDirectory = resolveRootDirectory(rootDirectory);

                if (ResourceUtils.isJarURL(rootDirectory)) {
                    resources.addAll(findJarResources(rootDirectory));
                } else {
                    resources.addAll(findFileResources(rootDirectory,basePackage));
                }
            }
            //println resources.join('\n')
            return resources;
        } catch (Exception e) {
            e.printStackTrace()
            return Collections.emptySet();
        }
    }
    Set<ClassName> findJarResources(URL rootDirectory) throws IOException {
        URLConnection con = rootDirectory.openConnection();
        JarFile jarFile = null;
        String jarFileUrl = null;
        String rootEntryPath = null;
        boolean newJarFile = false;

        if (con instanceof JarURLConnection) {
            // Should usually be the case for traditional JAR files.
            JarURLConnection jarCon = (JarURLConnection) con;
            jarCon.setUseCaches(false);
            jarFile = jarCon.getJarFile();
            jarFileUrl = jarCon.getJarFileURL().toExternalForm();
            JarEntry jarEntry = jarCon.getJarEntry();
            rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
        }
        else {
            // No JarURLConnection -> need to resort to URL file parsing.
            // We'll assume URLs of the format "jar:path!/entry", with the protocol
            // being arbitrary as long as following the entry format.
            // We'll also handle paths with and without leading "file:" prefix.
            String urlFile = rootDirectory.getFile();
            int separatorIndex = urlFile.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
            if (separatorIndex != -1) {
                jarFileUrl = urlFile.substring(0, separatorIndex);
                rootEntryPath = urlFile.substring(separatorIndex + ResourceUtils.JAR_URL_SEPARATOR.length());
                jarFile = getJarFile(jarFileUrl);
            }
            else {
                jarFile = new JarFile(urlFile);
                jarFileUrl = urlFile;
                rootEntryPath = "";
            }
            newJarFile = true;
        }

        try {
            //			if (logger.isDebugEnabled()) {
            //				logger.debug("Looking for matching resources in jar file [" + jarFileUrl + "]");
            //			}
            println "Looking for matching resources in jar file [" + jarFileUrl + "]"
            if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
                // Root entry path must end with slash to allow for proper matching.
                // The Sun JRE does not return a slash here, but BEA JRockit does.
                rootEntryPath = rootEntryPath + "/";
            }
//            println rootEntryPath
            Set<ClassName> resources = new LinkedHashSet<ClassName>();

            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
                JarEntry entry = entries.nextElement();
                //println entry
                if (isCandidate(entry, rootEntryPath)) {
                    String entryPath = entry.getName();
					//println entryPath
                    String relativePath = entryPath.substring(rootEntryPath.length());
                    //                    URL resourceUrl = new URL(rootDirectory, relativePath);
                    resources.add(new ClassName(resourcePath:entryPath));
					
                }
            }

            return resources;
        }
        finally {
            // Close jar file, but only if freshly obtained -
            // not from JarURLConnection, which might cache the file reference.
            if (newJarFile) {
                jarFile.close();
            }
        }
    }
    Set<URL> findFileResources(URL rootDirectory,String basePackage) {
        //println basePackage
        try {
            File file = ResourceUtils.getFile(rootDirectory).getAbsoluteFile();
            if (file.isDirectory()) {
                return doFindFileResources(file,
                    new LinkedHashSet<URL>()).collect{
                    new ClassName(resourcePath:basePackage+(it.absolutePath.substring(file.absolutePath.size())))
                    };
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
        return Collections.emptySet();

    }

    private Set<File> doFindFileResources(File root, Set<File> resources) {
        File[] files = root.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                doFindFileResources(file, resources);
            } else if (file.isFile()) {
                resources.add(file);
            }
        }
        return resources;
    }
    private boolean isCandidate(JarEntry entry, String rootEntryPath) {
//        println "isDirectory - ${entry.isDirectory()}"
//        println "entryName - ${entry.name}"
//        println "rootEntryPath- $rootEntryPath"
        def re=
        !entry.isDirectory() && entry.getName().startsWith(rootEntryPath);
//        println re
        return re
    }
    Set<URL> getRootDirectories(String basePackage){
        Set<URL> re=new HashSet<URL>()
        ClasspathScanner.class.getClassLoader().getResources(basePackage).iterator().each{
            re<<it
        }
        re
    }
}
class ClassName{
    String resourcePath
    String getFullClassName(){
        ClassUtils.convertResourcePathToClassName(resourcePath)-'.class'
    }
    String getClassName(){
        this.fullClassName.split(/\./)[-1]
    }
    @Override String toString(){
        "className: - $className, fullclassName - $fullClassName".toString()
    }
}


