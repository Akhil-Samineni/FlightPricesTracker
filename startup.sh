#!/bin/bash

# Check if environment variables are passed as arguments
if [ $# -lt 2 ]; then
  echo "Usage: $0 <docker-hub-username> <docker-hub-password>"
  exit 1
fi

# Assign argument values to variables
DOCKER_HUB_USERNAME="$1"
DOCKER_HUB_PASSWORD="$2"

# Docker image details
DOCKER_IMAGE_NAME="akhilsamineni47/akhilflighttracker"
DOCKER_IMAGE_TAG="latest"

# Build Docker image
docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG .

# Authenticate with Docker Hub using environment variables
echo "$DOCKER_HUB_PASSWORD" | docker login --username $DOCKER_HUB_USERNAME --password-stdin

# Push Docker image to Docker Hub
docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG

# Create or update Kubernetes deployment
kubectl apply -f deployment.yaml