package nl.woudstra.maven.plugin;

import com.sun.tools.xjc.Driver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mojo(defaultPhase = LifecyclePhase.GENERATE_SOURCES,name="xsd2java")
public class Xsd2Java extends AbstractMojo {

    @Parameter(defaultValue = "src/main/resources/xsd")
    public File inputDir;

    @Parameter(defaultValue = "src/main/java")
    public File outputDir;

    @Parameter(defaultValue = "xsd.generated")
    public String basePackage;

    @Parameter(defaultValue = "true")
    public boolean skipExistingPackages;

    @Parameter
    public Map<String,String> dir2package;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        validateConfig();
        generateSourcesFromXsd();
    }

    private void validateConfig() throws MojoExecutionException {
        if(!outputDir.exists()){
            throw new MojoExecutionException("Output dir does not exist: " + outputDir.getPath());
        }
        if(!inputDir.exists()){
            throw new MojoExecutionException("Input dir does not exist: " + inputDir.getPath());
        }
        if(basePackage == null || basePackage.isBlank()){
            throw new MojoExecutionException("Base package is not valid: " + basePackage);
        }
    }

    private void generateSourcesFromXsd() {
        try {
            for(String dirname : getDirNames()){
                String xsdDir = inputDir.getPath() + "/" + dirname;
                String packageName = getPackageName(dirname);
                if(skipExistingPackages && isExistingPackage(packageName)) {
                    getLog().info("Skipping package since it already exists: " + packageName);
                } else{
                    var arguments = new String[]{xsdDir, "-d", outputDir.getPath(), "-p", packageName};
                    getLog().info("xjc input: " + Arrays.toString(arguments));
                    Driver.run(arguments, System.out, System.out);
                }
            }
        } catch(Throwable e){
            System.out.println(e);
        }
    }

    private List<String> getDirNames() {
        List<String> dirnames = new ArrayList<>();
        for(File f : inputDir.listFiles()){
            if(f.isDirectory()){
                dirnames.add(f.getName());
            }
        }
        return dirnames;
    }

    private String getPackageName(String dirname) {
        if(dir2package != null && dir2package.containsKey(dirname)){
            dirname = dir2package.get(dirname);
        }

        return basePackage + "." + dirname;
    }

    private boolean isExistingPackage(String packageName) {
        StringBuilder path = new StringBuilder(outputDir.getPath());
        for(String part : packageName.split("\\.")){
            path.append("/").append(part);
        }
        return new File(path.toString()).exists();
    }
}
