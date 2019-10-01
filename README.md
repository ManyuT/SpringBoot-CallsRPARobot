# SpringBoot-CallsRPARobot
This Repo is contain the Spring boot project which having integration with the RPA (tool : UiPath)

First you have to create a UiPath Project, automate according to your Use case.

Connect UiPath Robot to Orchestrator.
Publish your project to Orchestrator.

Run project : spring-boot:run

API : http://localhost:9090/
METHOD : POST
REQUEST BODY (JSON) :
```json
{
	"processName":"<process name which you given in Orchestrator>",
	"robotName": "<Robot name which you given in Orchestrator>",
	"inputArgument" : "<JSON With String format like: {\"in_MessageName\" : \"TEST\",\"in_BusinessKey\" : \"TEST\"} >",
	"loginCredential": {
		"tenancyName": "<your_tenancyName>",
		"usernameOrEmailAddress" : "<your_usernameOrEmailAddress>",
		"password":"<Password>"
	}
	
}
```
