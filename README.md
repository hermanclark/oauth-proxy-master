# OAuth-ProxyURL

Version 1.0 (alpha)

This is the first iteration of the OAuth and ProxyURL. Only the proxy has been implemented as of this writing (July, 2017)


## Configuration - ProxyURL

OAuth-ProxyURL is a Spring Boot application running in an AWS instance

Spring Boot is Spring's convention-over-configuration solution for creating stand-alone, production-grade Spring-based Applications that will "just run"

The following files are necessary for the application to run:

```
oauthproxy-rest-1.x.jar - resides in /usr/local/pps-oauthproxy
application.properties - resides in /etc/pps
```
  
 The following command will launch the spring boot application:
 
 ```
 java -jar -Dspring.config.location=/etc/pps/application.properties /usr/local/pps-oauthproxy/oauthproxy-rest-1.0.jar 
 or
 systemctl start pps-proxy-oauth
 ```  
 
In order for the oauthproxy to work, the token.uri property must be set in the application.properties file to the environment in which token validation is to occur. 
The following is an example:

```
token.uri= https://login.nwea.org/api/api/session
```

### Description
In order to unify different APIs with a common target and security, the ability to proxy paths to remote targets is desired. 
For example, if a consumer calls this API resource:

```
https://profile-api.nwea.org/people/bissellator@gmail.com/receipts
```

the Profile API will rewrite the request and pull the response from

```
https://app-notifications/v1/receipts?search[field]=recipient&search[value]=$ /v1/receipts?search[field]=recipient&search[value]=bissellator@gmail.com
```
POSTing (and subsequent GETs, PUTs, etc)  for the OAuthProxy will be done through the ProfileAPI

It is recommended that the mapping entity is created prior to creating (linking) a target entity.


### NWEAMappings
We will need a default target for non-proxied resources. You can POST to the following endpoint - this will 
generate a targetId for linking to a target entity


```
/nweamappings
```

```json  
Sample JSON to POST
 
{
    "requestURL": "/people/$userid/receipts",
    "mapTo": "/v1/receipts?search[field]=recipient&search[value]=$userid"
}

would return the following:
{
    "targetId" : "011482f6ad108c507604a6d56808dc0e",
    "mappingId": "2c7c50dfa302d33ed5c20d1cbc7ea8ca",
    "requestURL": "/people/$userid/receipts",
    "mapTo": "/v1/receipts?search[field]=recipient&search[value]=$userid",
    "modified" : NumberInt(1490115880), 
    "created" : NumberInt(1490115880)
}
```

This can be validated by either doing a GET on

```
/target/[targetID]/mappings
```

or GET on


```
/mappings/[targetID]
```
  

### Targets

All resources will require a target mapping regardless of whether they are part of the core Profile API or a remote target.

```
GET on /target

/target/[targetID]
```

Then we need a target configuration so we don’t have to keep things like domain name and Basic Authentication headers in each redirect:

```json

Sample JSON to POST to /target

/target/[targetd] - targetId is from related mapping entity

{
    "description": "App Notification Engine", 
    "domain": "https://app-notifications.nwea.org",
    "headers" : [
        {
            "name" : "Authorization", 
            "value" : "Basic xx"
        }, 
        {
            "name" : "Content-type", 
            "value" : "applicaton/json"
        }
    ]
}

this would return and save the following response:

{
    "targetId" : "5980df0bb103f0c45ccf5a8a", 
    "description" : "App Notification Engine", 
    "domain" : "https://app-notifications.nwea.org", 
    "headers" : [
        {
            "name" : "Authorization", 
            "value" : "Basic xx"
        }, 
        {
            "name" : "Content-type", 
            "value" : "applicaton/json"
        }
    ], 
    "modified" : NumberInt(1501617929), 
    "created" : NumberInt(1501617929)
}

the created and modified attributes are auto-generated

```

The redirect service will then map the request to the target, using the domain and headers from the "redirects" resources. If there is no target, it will map to the "mapTo" attribute described in the 'NWEAMappings' section of this document.

   
### Use of path components variable(s): "Still to be implemented"

We should be able to reference path variables and query parameters and convert the actual value on the fly. So in this case if I requested

```
/people/bissellator/receipts
```

and the request URL is mapped as

```
/people/$userid/receipts
```

Then "bissellator" will become the value of $userid which can be referenced in the mapTo callout:

```
/v1/receipts?search[field]=recipient&search[value]=$userid
```

which becomes

```
/v1/receipts?search[field]=recipient&search[value]=bissellator
```

Future development should also allow us to map POST payloads and response payload(s) to JSON templates, but for the first release we are 
only planning on mapping URI components.

### Conflicts

If I request

```
/people/bissellator@gmail.com/receipts
```

There could be more than one mapping that could match, for example

```
/people
```

or

```
/people/$userid/receipts
```

In this case, the system should use the rule for the longest matching requestURL mapping, so don't route to /people, 
but rather the longer /people/$userId/receipts mapping.

## ProfileAPI OAuth

### Overview

The Partner Profile Service Profile API (profile-api.nwea.org) currently only uses Basic Auth on an application level.  We would like to extend security to provide user level security based on additional Scopes as returned by the Authorization Service at login.nwea.org.

This will allow us to secure API resources based on

```
Mapping Security Groups to Collections (e.g. you can see /products but not /people)
Mapping Agency Codes to path variables (/agencies/[yourAgencyCode])
Mapping Usernames to path variables (/people/[yourUserName])
```

We will retain the existing Basic Authorization model for trusted applications and backwards compatibility. 
The Partner Profile Service Profile API `(profile-api.nwea.org)` currently only uses Basic Auth on an application level.  We would like to extend security to provide user level security based on additional Scopes as returned by the Authorization Service at login.nwea.org.

This will allow us to secure API resources based on

Mapping Security Groups to Collections (e.g. you can see `/products` but not `/people`)
Mapping Agency Codes to path variables `(/agencies/[yourAgencyCode])`
Mapping Usernames to path variables (`/people/[yourUserName]`)

We will retain the existing Basic Authorization model for trusted applications and backwards compatibility. 

### OAuth: Combining Application and User Security Rules 

When a new access_token is created it is given very specific permissions based on a combination of the resources an Application is allowed to access and the resources the User is allowed to access. Application security is defined with `/apigroup` rules and User security will be defined with a new set of rules defined in the `/scopes` collection

The resulting collection of resources and verbs is granted only where the rules overlap. For example:

An Application can GET, POST and PUT to `/people/*`
A User can only GET to `/people/[myUserID]`
The access_token will only be able to GET `/people/[myUserID]`

The opposite is also true:

An Application cannot read from `/products`
A user can read `/products/*`
The access token will not be able to read from `/products`


### Application Security

All applications must be registered in the `/applications `collection and be granted permissions based on the `/apigroup` collection.

### /apigroup


Access to resources is restricted by the `apiGroupId`, in this case, this application is allowed access to the following resources and verbs:

```
GET /apigroup/57fede8cd4c642e6b10422e6

{
    "apiGroupId": "57fede8cd4c642e6b10422e6",
    "apiGroupName": "Role Management",
    "resources": [
        {
            "resource": "/agencies/*",
            "verbs": [
                "GET"
            ]
        },
        {
            "resource": "/agencies/$agencyCode/agreements/*",
            "verbs": [
                "GET"
                "POST",
                "PUT"
            ]
        },
        {
            "resource": "/roles/*",
            "verbs": [
                "GET",
                "POST",
                "DELETE",
                "PUT"
            ]
        },
        {
            "resource": "/people/*",
            "verbs": [
                "GET"
            ]
        }
    ]
}
```
Note the variable `"$agencyCode"` is generated when the token is minted – see the section in this document entitled Return user details from login.nwea.org. If the variable does not exist, the application should not have access to that resource/verb pair.


### /applications

When an Application is registered it is given a few attributes to describe the behavior.

#### clientID and clientSecret

Used as a username/password in Basic Auth

#### apiGroupId 

References the resources/verbs in the `/apigroup` collection

#### active 

Allows suspension of the application and restore it while investigating issues

```
GET /applications/1cb2f3f638e82fedb21fe32026b79cf0

{
    "id": "1cb2f3f638e82fedb21fe32026b79cf0",
    "applicationId": "1cb2f3f638e82fedb21fe32026b79cf0",
    "applicationName": "Test Application",
    "clientId": "b21fe32026b79cf0",
    "active": "1",
    "clientSecret": "c58ca2c1fadfa79a2eda5241aa5596d9",
    "apiGroupId": "57fede8cd4c642e6b10422e6",
    "modified": "1478128989",
    "created": "1478128989"
}

```
All applications will continue to require a `clientId` and `clientSecret` for Basic Auth as in:

```
GET /auth?grant_type=authorization_code&code=[token from login.nwea.org]
Authorization: Basic [base64 clientId:secret]
```

### /scopes


A new resource will be required to match security groups to OAuth scopes following the same logic as the productconstraints model

```

GET on /scopes
[{
    "scopeId": "5735008f4568a68a27d38972",
    "constraints": [{
        "scope": "5735008f4568a68a27d38971",
        "field": "agencyCode",
        "fieldOperator": "$exists",
        "compareTo": "",
        "compareMath": "",
        "compareConstant": "",
        "createdDate": "1463091343",
        "lastModifiedDate": "1463091343"
    }],
    "resources": [{
        "resourcePath": "/agencies/$agencyCode/*",
        "verbs": ["GET"]
    }, {
        "resourcePath": "/agencies/$agencyCode/agreements/*",
        "verbs": ["GET", "POST"]
    }]
},{
    "scopeId": "d2f3d89f70f0870d6ecb4cccc81a0ecb",
    "constraints": [{
        "scope": "8e68dfd7b07f5204c504e37b9b33c3cd",
        "field": "group",
        "fieldOperator": "$eq",
        "compareTo": "A - MAP (Web-based)",
        "compareMath": "",
        "compareConstant": "",
        "createdDate": "1463091343",
        "lastModifiedDate": "1463091343"
    }],
    "resources": [{
        "resourcePath": "/products/CF2588E6043FC8176685328128C37AAF",
        "verbs": ["GET"]
    }]
}]
```

The assumption is that field values can be referenced in the resource path as variables and that those variables 
will have to match the value passed from login.nwea.org.

```
???This also will require us to keep a cache of the specific result… I'm thinking maybe we have a ???
```

Once the token has been verified and scoped, the result should be added to the `/tokens` resource… which if it's going to be
performant, we would want to include the results of the scope call.

### Generating Tokens
To generate a new OAuth token we will need to do the following:

Verify the Central Auth token
Check Application Security 
Check User Security
Store token with Resources and Verbs

Verifying the Central Auth Token
Currently any application in the `*.nwea.org` domain will be able to access the "token" cookie issued by `login.nwea.org`.  This token will be sent to the Profile API `/auth` service where it will be combined with application restrictions and additional restrictions based on the security groups returned by `login.nwea.org`.


<<<<<<< HEAD
![OAuth Flow diagram](https://github.com/hermanclark/oauth-proxy-master/edit/main/oauthflow.png)
=======
![OAuth Flow diagram](https://github.com/BST-ITS-NWEA/oauth-proxy/blob/master/oauthflow.png)
>>>>>>> 093571e (Initial commit)

### New /auth service

A new API service (not a collection) will need to be created that will
```
•  Generate access_token in different ways:
•  Authorization Token Grant (Verify the CA token)
•  Refresh Token Grant (New access_token from existing refresh_token)
•  Password Grant (Accept username and password and verify against CA)
•  Client Credentials Grant (Check clientId:clientSecret against /applications)
•  Validate access_token
```

### Authorization Token Grant
Because the token isn't generated by `profile-api.nwea.org`, the application will use the "token" returned by `login.nwea.org` in the place of an Authorization Grant

```
GET /auth?grant_type=authorization_code&code=[token from login.nwea.org]
Authorization: Basic [base64 clientId:secret]

{
    "access_token": "a50199947bad94ef05b35b04ae9b4895",
    "token_type": "bearer",
    "expires_in": "8600",
    "refresh_token": "55d88171cc431f61771e66b90cb65259"
}
```
The actual flow for this resource will require it to use three resources:

```
•   Call login.nwea.org/ui/api/session to get security groups for this user
•   Call  the new /scopes resource referencing security groups and other attributes returned by the Authorization server. 
•   Call /apigroup associated with the clientId for app level resource/verb restrictions
```

### Verify Existing Login Token

<<<<<<< HEAD
![OAuth Flow diagram](https://github.com/hermanclark/oauth-proxy-master/edit/main/)
=======
![OAuth Flow diagram](https://github.com/BST-ITS-NWEA/oauth-proxy/blob/master/tokenflow.png)

>>>>>>> 093571e (Initial commit)
All subsequent calls will require the access token to be present as a bearer token as in

```
Authorization: Bearer a50199947bad94ef05b35b04ae9b4895
```

When the API receives the Bearer token, it will verify it against an internal `/auth/[token]` resource and receive a payload with the 
resources and verbs allowed by that token with values populated from the original request creating (hopefully) a very cacheable, 
performant source for restrictions. 

```
{
    "resources": [{
        "resourcePath": "/agencies/000000008 /*",
        "verbs": ["GET"]
    }, {
        "resourcePath": "/people/bissellator@gmail.com/*",
        "verbs": ["GET", "PUT"]
    }]
}
```
### Refresh Token Grant

Refreshing the Token is a simple matter of sending it with the grant_type to the `/auth` service as in:

```
GET /auth?grant_type=refresh_token&refresh_token=[refresh token]
Authorization: Basic [base64 clientId:secret]

{
    "access_token": "3ec8c1c5ce3d3bd77bbe1466e9174c59",
    "token_type": "bearer",
    "expires_in": "8600",
    "refresh_token": "55d88171cc431f61771e66b90cb65259"
}
```

This will delete the previous access_token and generate a new access_token with a new refresh_token. 

### Password Grant
(need to find the specific documentation to allow a password to be passed to Syntegrity and mint a new session)

Validate Token `(GET /auth)`
You can check the validity of a token by sending it directly to the `/auth` resource as a bearer token:

```
GET /auth
Authorization: Bearer [access_token]

{
    "access_token": "a50199947bad94ef05b35b04ae9b4895",
    "token_type": "bearer",
    "expires_in": "8600",
    "refresh_token": "55d88171cc431f61771e66b90cb65259"
}
```
A verbose response will show the resources and verbs the token is allowed to access. 

### Access Token Details

An access token references the otherwise hidden collection /tokens and should contain the following information:

```
access_token: Unique ID for looking up the token
refresh_token: Unique ID for looking up the token for refresh flow
clientId: Application that requested the token
token_type: Bearer for now
expires_in: Seconds from the created timestamp to deactivate the token
created: epoch timestamp
Resources and Verbs: A collection of endpoints this token is allowed to access
```

### Get user details from `login.nwea.org`

When the Profile API is given a Bearer Token, it will verify it against login.nwea.org's session endpoint using the Bearer as a custom header called "token"

```
GET https://login.nwea.org/ui/api/session
Accept: application/json
token: 462ab6ab-2dc4-494e-bbca-4775cf0ca321
```

Which responds with
```
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, must-revalidate
Cache-control: no-cache="set-cookie"
Content-Type: application/json
Date: Sun, 29 Jan 2017 23:27:37 GMT
Expires: 0
Pragma: no-cache
Server: nginx
Set-Cookie: AWSELB=47819123040DB76D23B8A390495FDB1BBE0BD4F1F9CEAB75ABB41C5BA8525A9FF4866730407728C9DDB9263A70136D6454D6017C234FF1F9B21CE155A5FF3A07DC8CDC60A7;PATH=/
Content-Length: 813
Connection: keep-alive

{
    "identifierEmails": ["tier2.ka+user28@gmail.com", "bissellator+20161109@gmail.com"],
    "entitlements": [],
    "lastName": "Name",
    "agencySelectionRequired": null,
    "googleAuthSecretAccepted": "false",
    "customerAlias": null,
    "groups": ["F-PD-ProvFeedback", "D - Skills Navigator / Skills Pointer", "D - Skills Navigator", "F-PD-Activating", "F-PD-Eliciting", "F-PD-Clarifying", "A - MAP (Web-based)", "Partner"],
    "emailVerificationRequired": null,
    "agencyCode": ["000000008"],
    "mfaMethod": null,
    "locale": null,
    "eulaApproval": "false",
    "uuid": "0add57ea-f339-40a4-a157-e982fe385f3b",
    "firstName": "My",
    "uid": null,
    "dpdIdentifier": "tier2.ka+user28@gmail.com",
    "defaultEmail": "tier2.ka+user28@gmail.com",
    "entitlementGroups": ["SELF_IDENTITY_MGMT_GROUP"],
    "loginIdentifier": "tier2.ka+user28@gmail.com",
    "agencyCodes": null,
    "authLevel": 35,
    "customer": "syntegrity"
}
```

### Mapping groups to scopes
The elements returned to Profile API from login.nwea.org can be mapped to security rules using the new /scopes resource.  So, in the example above, the user has the following:

```
Email Addresses:
identifierEmails:
tier2.ka+user28@gmail.com
bissellator+20161109@gmail.com
defaultEmail: tier2.ka+user28@gmail.com
loginIdentifier: tier2.ka+user28@gmail.com

Agency Code: 000000008

Groups:
A - MAP (Web-based)
D - Skills Navigator
D - Skills Navigator / Skills Pointer
F-PD-Activating
F-PD-Clarifying
F-PD-Eliciting
F-PD-ProvFeedback
Partner
```

Scopes match on an Identifier, so if we had the following scopes:

```
[{
    "scopeId": "5735008f4568a68a27d38972",
    "constraints": [{
        "scope": "5735008f4568a68a27d38971",
        "field": "agencyCode",
        "fieldOperator": "$exists",
        "compareTo": "",
        "compareMath": "",
        "compareConstant": "",
        "createdDate": "1463091343",
        "lastModifiedDate": "1463091343"
    }],
    "resources": [{
        "resourcePath": "/agencies/$agencyCode/*",
        "verbs": ["GET"]
    }, {
        "resourcePath": "/agencies/$agencyCode/agreements/*",
        "verbs": ["GET", "POST"]
    }]
},{
    "scopeId": "d2f3d89f70f0870d6ecb4cccc81a0ecb",
    "constraints": [{
        "scope": "8e68dfd7b07f5204c504e37b9b33c3cd",
        "field": "group",
        "fieldOperator": "$eq",
        "compareTo": "A - MAP (Web-based)",
        "compareMath": "",
        "compareConstant": "",
        "createdDate": "1463091343",
        "lastModifiedDate": "1463091343"
    }],
    "resources": [{
        "resourcePath": "/products/CF2588E6043FC8176685328128C37AAF",
        "verbs": ["GET"]
    }]
}]
```

we would end up with the following resources and verbs associated with the `access_token`:

```
    "resources": [{
        "resourcePath": "/agencies/000000008/*",
        "verbs": ["GET"]
    }, {
        "resourcePath": "/agencies/000000008/agreements/*",
        "verbs": ["GET", "POST"]
    }, {
        "resourcePath": "/products/CF2588E6043FC8176685328128C37AAF",
        "verbs": ["GET"]
    }]
```

If the `apigroup` for the requesting application doesn't allow access to `/products`, then we would end up with 

```
    "resources": [{
        "resourcePath": "/agencies/000000008/*",
        "verbs": ["GET"]
    }, {
        "resourcePath": "/agencies/000000008/agreements/*",
        "verbs": ["GET", "POST"]
    }
    ]

```

