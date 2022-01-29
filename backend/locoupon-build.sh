echo "Building LoCouponManager Manager"
cd ../locoupon-common
#git pull
./gradlew clean build publish
cd ../locoupon-common-rest
#git pull
./gradlew clean build
cd ../builds

echo "Copying Jars".
cp ../locoupon-common-rest/build/libs/LoCouponManager.war .

