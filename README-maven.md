# MVN

mvn <lifecycle-phase> [plugin:goal]

mvn clean
Deletes the target directory, removing compiled files and build artifacts.

mvn compile
Compiles the source code of the project.

mvn test
Runs the unit tests in the project.

mvn deploy
Copies the final package to a remote repository for sharing with other developers or projects.

mvn clean install
Cleans the project and then builds it, installing the artifact locally. This is the most common command in development.

mvn clean package
Cleans and packages the project without installing to local repo.

mvn spring-boot:run
Run the spring boot application.

mvn clean install -pl module-name
Build a specific module.

mvn clean install -pl module-name -am
Build a module and its dependencies.

# Execute

mvn -pl router exec:java
mvn -pl router exec:java
mvn -pl market exec:java

	•	exec:java is goal-oriented, not standalone like java -jar.
	•	By default, it runs in the validate -> compile -> exec:java order.