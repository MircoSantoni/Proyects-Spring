# Usamos una imagen base de java con una version ligera de linux
FROM amazoncorretto:21-alpine-jdk

# Establezco el directorio de trabajo dentro del contenedor
WORKDIR /app

# Documentamos que el contenedor va a escuchar el puerto 8888
EXPOSE 8761
# Copiamos el archivo jar generado en el target y lo guardamos en el contenedor, este comando puede 
# Reemplazarse con ADD
COPY ./target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar


# Indicamos el comando que se ejecutra cuando el contenedor arranca
ENTRYPOINT [ "java" , "-jar" , "eureka-server.jar"]