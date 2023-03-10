# First Baptist Awana API [![Build Status](https://github.com/sambutler1017/fb-awana-microservice/actions/workflows/build.yml/badge.svg)](https://github.com/sambutler1017/fb-awana-microservice/actions)

<!-- ABOUT THE PROJECT -->

## About The Project

- Explain what the goal of doing this project is
- How to contribute
- Guidlines to follow
  - Standards, pull request, commit messages, etc.

## Commit Message Guide:

- Commit message should have a purpose and link to the ticket you are working on so blind commits aren't added to the repository.
- An Example would be, say the ticket being worked on as an issue id of 1 that was adding a table to the database. The format would be as follows:
  - `AWANA-1: Added new table to awana_db_dev table`
- It should be prefixed with `AWANA` (Project is Awana project) followed by the issue number and then the message

### Built With

- Java
- Gradle
- Spring Boot framework

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

1. First you will need an IDE, you can either use Vscode or Eclipse for this (It does not matter, both work the same)
2. Then you will need a jdk installed in your computer of 11 or higher. You can go to the following link to do this.
   ```sh
   https://jdk.java.net/java-se-ri/14
   ```

### Installation

1. Clone the repo

   ```sh
   git clone https://github.com/sambutler1017/fb-awana-microservice.git
   ```

2. Build and install packages provided in `build.gradle` file
   ```sh
   gradle build
   ```

<!-- USAGE EXAMPLES -->

## Usage

- Using and running the endpoints you will want an API client such as postman or some extension of chrome that allows you to consume endpoints.

1. The following link is to download postman on your local machine. You will be asked to create an account (it's free)

   ```sh
   https://www.postman.com/downloads/
   ```

2. Next, in the project you will want to copy the `application-local.template.yml` file and paste it and rename it to `application-local.properties`.

3. Then inside you will want to update the following fields:
   ```yml
   spring:
     datasource:
       username: <MYSQL_USERNAME>
       password: <MYSQL_USERNAME>
       url: <MYSQL_URL>
   ```
   - Note: These values are your own database information. You will need to have your own database for this. Either one that is local on your machine or hosted.
   - For example, for the datasource url:
     - `jdbc:mysql://<IP OR DOMAIN>/<SCHEMA>`
   - The db migiration scripts are in the repo on how to recreate the database. If this is run locally it will create the local schema on the database hosted on your machine.
4. Finally, everything is set up and you can run the following command to start a local instance of the project.
   ```sh
   gradle bootrun
   ```
5. If it stood up successfully you should be able to hit the endpoints in the project at the route of `localhost:8080`

<!-- CONTACT -->

## Contact

Samuel Butler - sambutler1017@icloud.com

Project Link: [https://github.com/sambutler1017/fb-awana-microservice](https://github.com/sambutler1017/fb-awana-microservice)

<!-- ACKNOWLEDGEMENTS -->

## Acknowledgements

- Sam Butler
- Josue Van Dyke
