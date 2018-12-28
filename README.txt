<!-- simple config -->

<plugin>
    <groupId>nl.woudstra.maven.plugins</groupId>
    <artifactId>simple-jaxb-xjc-maven-plugin</artifactId>
    <version>1.0</version>
    <!-- optional configuration -->
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>xsd2java</goal>
            </goals>
        </execution>
    </executions>
</plugin>

<!-- optional configuration -->
<configuration>
    <dir2package>
        <dirname1>packagename1</dirname1>
    </dir2package>
</configuration>