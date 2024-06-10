docker build -t gamezone .
docker stop gamezone
docker rm gamezone
docker run -e"SPRING_PROFILES_ACTIVE=dev" --env-file variables.env -d -p 8080:8080 -v/opt/gamezone/GameZone/uploads:/uploads --name gamezone gamezone

