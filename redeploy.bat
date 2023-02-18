docker stop  spring-demo-integration
docker rm  spring-demo-integration
docker image rm project_1_repository-spring-demo-integration
docker volume rm  project_1_repository_spring-demo-integration
docker stop react-demo-integration
docker rm react-demo-integration
docker image rm project_1_repository-react-demo-integration
docker stop rabbitmq-demo-integration
docker rm rabbitmq-demo-integration
docker image rm project_1_repository-rabbitmq-demo-integration
docker stop  mysql-demo-integration
docker rm  mysql-demo-integration
docker image rm mysql
docker volume rm  project_1_repository_mysql-demo-integration

docker-compose up --build