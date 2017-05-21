# Alexa Smart Home skill

Test implementation of an Alexa Smart Home Skill. Dummy OAuth implementation has been provided.

More details in the [blog](http://rrevo.github.io/2017/05/14/alexa-homeskill/).

## Building

Run ```mvn install ``` in the base folder to generate a jar with dependencies in target/.

The jar can be deployed as an AWS Lambda for OAuth and Smart Home skill integration.

## WARNING

This is a test application and highly insecure. OAuth implementation is completely broken.

## References

* [OAuth](https://tools.ietf.org/html/rfc6750)
* [OAuth linking](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/linking-an-alexa-user-with-a-user-in-your-system)
* [Lambda proxy](http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-create-api-as-simple-proxy-for-lambda.html)
* [Smart Home skill docs](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/steps-to-create-a-smart-home-skill)
