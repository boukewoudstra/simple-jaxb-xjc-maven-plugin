This plugin is an alternative to the existing xjc plugins for maven and works for Java 11.

<!-- simple config -->

<plugin>
    <groupId>nl.woudstra.maven.plugins</groupId>
    <artifactId>simple-jaxb-xjc-maven-plugin</artifactId>
    <version>0.1</version>
    <!-- optional configuration, defaults are shown -->
    <configuration>
        <inputDir>src/main/resources/xsd</inputDir>
        <outputDir>src/main/java</outputDir>
        <basePackage>xsd.generated</basePackage>
        <skipExistingPackages>true</skipExistingPackages>
        <!-- provide package renames, default package name uses dirname -->
        <dir2package>
            <dirname1>packagename1</dirname1>
            <dirname2>packagename2</dirname2>
        </dir2package>
    </configuration>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>xsd2java</goal>
            </goals>
        </execution>
    </executions>
</plugin>

