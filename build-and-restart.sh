docker build -t gamezone .
docker stop gamezone || true
docker rm gamezone || true
docker run -d -p 8080:8080 --name=gamezone gamezone