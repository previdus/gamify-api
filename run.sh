cd ~/Documents/work/Product\ Stuff/gamify-api/Gamify/
mvn clean install
cp ~/Documents/work/Product\ Stuff/gamify-api/Gamify/target/Gamify-1.0.0-SNAPSHOT.war ~/Downloads/apache-tomcat-7.0.62\ 2/webapps
cd ~/Downloads/apache-tomcat-7.0.62\ 2/bin/
./shutdown.sh 
./startup.sh  
