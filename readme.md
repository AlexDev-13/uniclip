http://localhost:8085/oauth2/authorization/google


sudo docker build -t uniclip-backend .

sudo docker run -p 8085:8085 --name=uniclip-backend uniclip-backend:latest
