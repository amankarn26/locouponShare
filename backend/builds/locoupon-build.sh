echo "Closing Tomcat"
sudo $CATALINA_HOME/bin/shutdown.sh


echo "Building LoCouponManager Manager"
cd ../locoupon-common
chmod +x gradlew
./gradlew clean build publish
cd ../locoupon-common-rest
chmod +x gradlew
./gradlew clean build
cd ../builds

echo "Copying Jars".
cp ../locoupon-common-rest/build/libs/LoCouponManager.war .


echo "Deploying War"
sudo rm $CATALINA_HOME/webapps/LoCouponManager.war
sudo rm -rf $CATALINA_HOME/webapps/LoCouponManager
sudo cp *.war $CATALINA_HOME/webapps/ 

echo "Starting Tomcat"
sudo $CATALINA_HOME/bin/startup.sh

sudo tail -f  $CATALINA_HOME/logs/catalina.out