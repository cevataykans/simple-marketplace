test:
	./mvnw -Dmicronaut.environments=dev clean test

deploy:
	docker build . -t simplemarket
	docker-compose -f postgres-compose.yml up
