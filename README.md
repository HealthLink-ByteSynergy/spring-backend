# Spring-Boot Backend

## Overview

- This repository contains the backend code for the application. It's written in Java using the Spring-Boot framework to create a REST API that can be used by various frontend applications.
- Submission By Team TeleHealth Innovators (Kalyan Ram, Ram Sai Koushik)

- Link to Report : https://docs.google.com/document/d/1gYXBzJ9zCVyO50d3rp7PXXCTlhG0Hhpnr0Td2cD57WY/edit

## How To Run The Application

- Clone the github repo using the `git clone` command.
- Open the folder in `VS Code` and ensure that the standard java extensions are downloaded.
- Make sure mysql is present in the system. Create a database `bytesynergy`.
- In the `src/main` folder, create a `resources` folder and create an `application.yml` file.
- Copy the content of the `exampleapplication.yml` file to `application.yml` and fill the empty fields. The api keys can be obtained from the links provided at the end.
- Then open the `demoApplication.java` file and press run to start up the backend server.

## Functionality

- Authentication: 
  
  - The REST API can only be accessed by users who are authenticated. This is done by using the `jwt token` that is sent when logged in.
  - There are 2 types of Sign Up: Patient Sign Up and Doctor Sign Up 
  - There are 3 roles that a user can have: `USER, DOCTOR, BOT`.
  
- Multiple Patients in Under One User:
 
  - A User can add multiple patients like family members to the same account. 
  - Each patient can chat with the bot to get optimized responsed based on the patient's health condition.
  - The patient details like age, height, medical conditions,etc are taken.
  - They have options to update their details and delete.

- Doctors Usage : 
  
  - When a doctor sign's up, the specialization and availability details are taken.
  - The specialization is used when doctors are suggested by the chat feature to the patients based on the specialization that is recommend based on their problem.
  - The doctors can update their specialization details, and their availability. A user is suggested a doctor only if they are available.

- Temporary Chat and Summaries(Summary Sharing)
  
  - Doctors have the option to make summaries of the temporary chats that they had with a user. This is done to avoid cluttering of chats at the doctor side.
  - The summaries are generated using the `Cohere.Ai API` which has a route to summarizes messages and some optimized prompts are added to the message before summarizing.
  - The doctor can then see the patients summaries they have had a conversation with.
  - The patient can also see the summaries from the different doctors they have communicated with.

- Maintaining Prescription

  - Doctors can add prescriptions specific to each patient.
  - They can add medicines that are relevant to the patient's health problem.

- Audio And Video calls for Critical Cases

  - In the temporary chat , the patient can make a voice call to the doctor using the mobiles inbuilt calling feature.
  - If the situation requires to meet face to face, then they can create a new meeting.
  - The meeting is created using the `Digital Samba API`.

- Unified Chat Interface

  - Once a patient submits their details, the chat interface opens up where they can chat with a bot that gives responses to the patients health problems based on their prompts.
  - The user has a option to get specialists based on the severity of the issue. This is done by getting the context from the conversation that has taken place so far.
  - The bot then takes this information and determines if it is severe using the `Cohere.AI api` along with some optimized prompts. If it is, then the doctors that are suitable to the situation are suggested to the patient.
  - The patient can then join a temporary chat from the suggestion made by the bot. 
  - Here two different users can have a live conversation without the intervension of the bot.
  - The bot response is based on the `Cohere.Ai API` which is used in the backend where the messages from the the users are taken as input, a prompt is created which also uses the patient details and some prompt optimization to get a suitable response.

## Endpoints

Once the backend code is run, the REST API endpoints can be seen using swagger docs. 

The link to the swagger docs is `http://localhost:5000/swagger-ui/index.html`

There are a total of 8 controllers in the application that is modularized and optimized for the different functionalities mentioned above.

- Message Controller
- Doctor Controller
- Patient Controller
- Summaries Controller
- User Controller
- Prescription Controller
- Medicine Controller
- Meeting Controller

## API's Used

There are 2 API's that are used in the application:

- [Cohere.AI](https://docs.cohere.com/reference/generate) : This tool is used to generate the chats that are sent by the bot. It also helps in summarizing the messages. Sign up to the website and get the API KEY and add them to the `application.yml` file

- [DigitalSamba](https://dashboard.digitalsamba.com/team) : This tool is used to create the meeting. Sign Up to the application and go to the teams page to get the Team ID and Developer Key.

