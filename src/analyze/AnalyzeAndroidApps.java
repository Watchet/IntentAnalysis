/**
 * 
 */
package analyze;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.umd.cs.findbugs.BugCollectionBugReporter;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.DetectorFactory;
import edu.umd.cs.findbugs.DetectorFactoryCollection;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.Plugin;
import edu.umd.cs.findbugs.PluginException;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.Project;
import edu.umd.cs.findbugs.config.UserPreferences;

/**
 * @author sdienst
 *
 */
public class AnalyzeAndroidApps {

	/**
	 * @param args
	 * @throws PluginException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws PluginException, IOException, InterruptedException {
		AnalyzeAndroidApps a = new AnalyzeAndroidApps();
		FindBugs2 fb = a.setUpEngine("D:/android/jars/PRODUCTIVITY/-102756457831925907/classes.dex");

		fb.execute();
	}


    private FindBugs2 setUpEngine(String fileUriToJar) throws MalformedURLException, PluginException {
    	//Plugin.addCustomPlugin(new URL("file://findintents.jar"));
    	
        FindBugs2 engine = new FindBugs2();
        Project project = new Project();
        project.setProjectName("analyze apps");
        engine.setProject(project);

        DetectorFactoryCollection detectorFactoryCollection = DetectorFactoryCollection.instance();
        engine.setDetectorFactoryCollection(detectorFactoryCollection);

        BugCollectionBugReporter bugReporter = new BugCollectionBugReporter(project);
        bugReporter.setPriorityThreshold(Priorities.LOW_PRIORITY);
        bugReporter.setErrorVerbosity(BugReporter.SILENT);
        
        engine.setBugReporter(bugReporter);
        UserPreferences preferences = UserPreferences.createDefaultUserPreferences();
        DetectorFactory checkExpectedWarnings = DetectorFactoryCollection.instance().getFactory("FindIntentsViaCFG");
        preferences.enableDetector(checkExpectedWarnings, true);
        preferences.getFilterSettings().clearAllCategories();
        engine.setUserPreferences(preferences);

        project.addFile(fileUriToJar);

        project.addAuxClasspathEntry("D:/android/android-sdk-windows/platforms/android-8/android.jar ");
        return engine;
    }
}