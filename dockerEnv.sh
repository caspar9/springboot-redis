
#redis
docker run --name redis-server -p 6379:6379 -v /usr/local/docker/redis/data:/data -d redis:latest --appendonly yes