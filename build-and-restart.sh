docker build -t gamezone .
docker stop gamezone
docker rm gamezone
docker run -d -p 8080:8080 --name gamezone gamezone