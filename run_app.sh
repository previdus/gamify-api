cd /Users/ruchimutneja/Downloads/apache-tomcat-7.0.62 2/bin
./shutdown.sh
cd /Users/ruchimutneja/Documents/work/Product Stuff/gamify-api/Gamify
mvn clean install
mv target/Gamify-1.0.0-SNAPSHOT.war ~/Downloads/apache-tomcat-7.0.62\ 2/webapps/
./startup.sh
