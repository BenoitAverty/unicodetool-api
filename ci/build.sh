version=$(cat VERSION)
./gradlew bootJar
docker build . -t benoitaverty/unicodetool-api:$version