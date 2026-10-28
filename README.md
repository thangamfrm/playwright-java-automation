# Playwright Java Automation

This Test Automation Framework and Test Suites ( WebUI and REST API ) are implemented using Java, Maven, TestNG and Playwright Tool.

## Bring up the Applications

This projects does functional testing

1. Spring Pet Clinic REST API
```bash
git clone https://github.com/spring-petclinic/spring-petclinic-rest.git
cd spring-petclinic-rest
./mvnw spring-boot:run
```

2. AWS Secret Agent Shop
```bash
mkdir -p aws-secret-agent-shop
cd aws-secret-agent-shop/
wget https://github.com/aws-containers/retail-store-sample-app/releases/latest/download/docker-compose.yaml
DB_PASSWORD='my-pwd' docker compose --file docker-compose.yaml up
```

## Running Tests

This project uses `TestNG`. 

1. REST API (Spring Pet Clinic ): 
```bash
mvn -Dtest=PetTypeTest test
```

2. WebUI (AWS Secret Agent Shop):
```bash
mvn -Dtest=E2EProductPurchaseFlowTest test
```