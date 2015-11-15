# FyberChallenge
App for a challenge


##Introduction

Hi, I have enjoyed a lot writing this little project, here I used some libraries to reduce plumbing
code like assigning Views to variables and some stuff like that, here you will find 2 code bases let 
me explain, as I was working trying to user your API with the information provided I got several errors
regarding the timestamp being incorrect, thing that could not be possible because I was taking the 
timestamp from the system directly, I tried changing my test phone to germany timezone and some other
tweaks but mostly got the same result, so in order to write the Android code correctly and with less
bugs I did a micro Server app with Golang, to take the request make the hash validation and generation
and return a proper response. That could be made with mocking libraries like Mockito, but I simply wanted
to reproduce as better as possible your backend servers so I did that. Inside the fyberBackend folder 
you will find the Go project source code ready to run, will explain how to below.

##Instructions

1. Go to fyberBackend folder in your terminal and run `go run main.go` (Might need Go 1.4+ in order to run)
2. check your ip address with `ifconfig` or `hostname -I` either way is fine
3. Check that your port `8080` is open to outside connections
4. Go to `app/build.gradle` file and modify the server_name string param under the debug and debugTest blocks
 with the proper ip `resValue "string", "server_domain", "http://10.0.0.4:8080"` optionally you can
 just change it to point the proper fyber servers like in the production mode.
5. Go to `{project_root}/video` folder and check the video also for a functionality check
6. Hire me for being so cool :).