AWS_ACCOUNT=xxx
AWS_REGION=xxx
PROGRAM_NAME=app
PROGRAM_VERSION=22.07.06
HTTP_PORT=8080
ECR_URL=$(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com/test

prod-app-build: | prod-app-build-image prod-app-push-image

prod-app-install: | app-prod-env yaml install-app

app-prod-env:
	$(eval IMAGE=$(PROGRAM_NAME)-v$(PROGRAM_VERSION))
	$(eval URL=$(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com/test:prod-app-v$(PROGRAM_VERSION))
	$(eval IMAGEPULLPOLICY=Always)
	$(eval HTTP_PORT=$(HTTP_PORT))
	$(eval MIN_REPLICAS=1)
	$(eval MAX_REPLICAS=1)


prod-app-build-image:
	docker build -f ./Dockerfile -t ${ECR_URL}:prod-app-v$(PROGRAM_VERSION) .
prod-app-push-image:
	docker push $(ECR_URL):prod-app-v$(PROGRAM_VERSION)

yaml:
	sed \
  	-e 's|URL|$(URL)|' \
	-e 's|PROGRAM_NAME|$(PROGRAM_NAME)|' \
	-e 's|PROGRAM_VERSION|$(PROGRAM_VERSION)|' \
	-e 's|MIN_REPLICAS|$(MIN_REPLICAS)|' \
	-e 's|MAX_REPLICAS|$(MAX_REPLICAS)|' \
	-e 's|IMAGEPULLPOLICY|$(IMAGEPULLPOLICY)|' \
	-e 's|ENV_VALUE|$(ENV_VALUE)|' \
	-e 's|ECR_URL|$(ECR_URL)|' \
	-e 's|IMAGE|$(IMAGE)|' \
	-e 's|HTTP_PORT|$(HTTP_PORT)|' \
	aws-eks.yaml > $(PROGRAM_NAME).yaml

install-app:
	kubectl apply -f $(PROGRAM_NAME).yaml -n baobeica
	-rm $(PROGRAM_NAME).yaml



ecr-login:
	aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin $(ECR_URL)
