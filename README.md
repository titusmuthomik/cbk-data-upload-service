# Springboot Skeleton Service

Skeleton project for springboot framework in java & kotlin with gradle. For the items to updates, please refer to [Changes to Make](./docs/ChangesReadME.md) ReadME file.

### Key objectives
Sample
```
* Consolidate all the decision criteria that is needed for each loan application i.e. rules, credit decision, fraud decision, downsample
* Provide transactional record at the end of each final decision
* Provide records for loan terms
```


## Project Structure

### Versioning
This project uses [Semantic Versioning 2.0.0](https://talamobile.atlassian.net/wiki/spaces/ATLAS/pages/885948433/Semantic+Versioning) starting with `v1.0.0`.

### Project Language/Platform/Boundaries
List of framework/platform/dependent components this project uses.

* Language [VERSION]
* Framework [VERSION]  (Possibly add link to confluence on how TALA utilizes the framework including any specific features of the framework.)
* Any specific plugins and module for the above framework
* ORM Framework, HTTP Framework, etc.
* Queues
* Databases (Redis, MariaDB, Cassandra)
* INBOUND Boundary Components/Services (internal/external)
* OUTBOUND Boundary Components/Services (internal/external)

### Project Architecture
This part should explain HOW the project achieves the key objectives for engineers/devops audience.  
This may be very different from the design doc which may have different audiences.
The focus should be on the HOW the service achieves its goals such as explaining the internal framework/architecture.

Should include flow chart of the happy path(s) that includes the internal details such as intra messaging, any usage of db/apis, etc.
Include the names of core implementation file in the flow chart.

Make sure explain how the inbound/outbound channels are configured. (REST vs Queue)

This section should also include links to [Design Document](http://replace.me) and [Swagger Document](http://replace.me).

## Service Maintenance
This section is required for all service repositories. For common libraries, only the **Maintainers** section is required.

### Maintainers
In general, the service maintenance duty should be assigned to a specific team instead of individual person.
This approach provides a better continuity of ownership when team member move in and out of the team/company.
With that said, please provide the GitHub and Slack handles of the team members assigned to be the designated code owners.
This information should be updated when a code owner leaves the team/company.
- Foo [GitHub](https://github.com/foo) and [Slack](https://talamobile.slack.com/team/foo) ([local time](https://www.google.com/search?q=time+in+Africa%2FNairobi))
- Bar [GitHub](https://github.com/bar) and [Slack](https://talamobile.slack.com/team/bar) ([local time](https://www.google.com/search?q=time+in+America%2FLos_Angeles))

### Critical Issues
This section covers information about handling P0 or P1 issues as defined in
[this guide](https://talamobile.atlassian.net/wiki/spaces/PS/pages/1901887766/Issue+Criticality+Guide+P0-P3).

For instance, when this service goes down and no automated alert gets triggered,
provide an email address to for other team members to reach out to the person on-call,
e.g. [example@inventure.pagerduty.com](mailto:example@inventure.pagerduty.com).

### Non-Critical Issues
This section covers information about handling P2 or P3 issues as defined in 
[this guide](https://talamobile.atlassian.net/wiki/spaces/PS/pages/1901887766/Issue+Criticality+Guide+P0-P3))
or other general inquiries.

For the best visibility and response time, please **avoid** using Slack private channel or DM.
Instead, post them in [#sample-slack-public-channel](https://app.slack.com/client/T024F7881/C01ARBF94RY).

### Logging
Provide information about where the log for this service can be found. For instance, provide a sample SumoLogic query. 

### Monitoring
Provide links to dashboards (SumoLogic, Instana, Grafana, etc.).
If the service has dedicated endpoint for health check and metrics, provide that as well.
Explain which metrics are collected and how they are configured.

For information about available monitoring and alerting tools, check out
[Tala Monitoring & Alerting Guidelines](https://talamobile.atlassian.net/wiki/spaces/ATLAS/pages/2770665504/Monitoring+Alerting+Guidelines)


### Alerting
Provide links to all automated alerts configured for this service.
Make sure to provide information about what each alert mean.

### Troubleshooting Runbook
Provide information on how to troubleshoot this service in production.
Add any common issues that developer/devops/operation needs to be aware of (e.g. list of FAQs).


## Building and Testing
Subsections can include more details on any local configuration such as but not limited to
* Environment variable such as JFROG credentials
* Downloading any prerequisite binaries.
* Test configuration
* Starting locally vs starting with Docker
* Charles proxy set up
* Localstack set up

This section could be lengthy. To improve the readability, feel free to create a separate markdown file
in `docs/BUILDING.md`. See [example](https://github.com/nodejs/node/blob/master/BUILDING.md).

## Contributing
Subsections can include more details on contributing guidelines such as but not limited to
* [Pull Request Process](https://talamobile.atlassian.net/wiki/spaces/ATLAS/pages/1096583374/Code+Review+Pull+Request+Guidelines)
* [GitHub Process](https://talamobile.atlassian.net/wiki/spaces/ATLAS/pages/2764898308/Git+Best+Practice+Rebasing+instead+of+Merging)
* [Programming Style](https://talamobile.atlassian.net/wiki/spaces/ATLAS/pages/1096550136/Programming+Style+Guides)

This section could be lengthy. To improve the readability, feel free to create a separate markdown file
in `docs/CONTRIBUTING.md`.

## Releasing
Subsections can include more details on releasing guidelines such as but not limited to
* Checks required before deploying to production
* Steps to deploy changes to production
* Release tag creation

This section could be lengthy. To improve the readability, feel free to create a separate markdown file
in `docs/RELEASING.md`.

## Packaging and Deployment
Subsections can include more details on deployment guidelines such as but not limited to
* Any notes regarding the build process, deployment including containerization.
* Link to CI tool
