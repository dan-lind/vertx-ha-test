# Bra kommandon


With docker 1.3, there is a new command docker exec. This allows you to enter a running docker:

    docker exec -it "id of running container" bash

Kör en image och radera den skapade containern:

    sudo docker run -it --rm --name test-java-app hello-docker-java
    
Bygg en image från en Dockerfile:

    sudo docker build -t image-name .

Radera en docker image:

    docker rmi image-name
