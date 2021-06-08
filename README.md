# IK1203-Labs

##Task 1 - TCPAsk

The first programming assignment is to implement a TCP client, called TCPAsk. TCPAsk operates in a straight-forward manner:

- Open a TCP connection to a server at a given host address and port number.
- Take any data that the server sends, and and print the data.
- TCPAsk takes an optional string as parameter. This string is sent as data to the server when the TCP connection is opened, followed by a newline character (linefeed '\n').

| Protocol  | Server name	 | Port  | Arguments | Comment |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Daytime  | time.nist.gov  | 13 | -  | Public server |
| Daytime  | java.lab.ssvl.kth.se  | 13  | -  | KTH server |
| Whois  | whois.iis.se  | 43  | (domain name, IP address or AS number) | Public server |
| Whois  | whois.internic.net  | 43  | (domain name, IP address or AS number)  | Public server |
| Echo  | java.lab.ssvl.kth.se  | 7  | String  | KTH server |
| Discard | java.lab.ssvl.kth.se  | 9  | String  | KTH server |
| Chargen | java.lab.ssvl.kth.se  | 19  | -  | KTH server |

Protocol	Server name	        Port	Arguments	Comment
Daytime	   time.nist.gov	    13	       –	    Publicserver

Daytime	   java.lab.ssvl.kth.se	13	       –	    KTH server
Whois	    whois.iis.se	    43	    String      Public server
                                    (domain name, 
                                    IP address or 
                                    AS number) 	
Whois	  whois.internic.net	43	String          Public server
                                    (domain name, 
                                    IP address or 
                                    AS number)	
Echo	 java.lab.ssvl.kth.se	7	String	        KTH Server 

Discard	 java.lab.ssvl.kth.se	9	String	        KTH Server

Chargen	 java.lab.ssvl.kth.se	19	  –	            KTH Server

##Task 2 - HTTPEcho Server

In this part, you will implement a web server. It is a web server that does not do very much, but you will probably find it useful for the rest of this assignment. The server accepts an incoming TCP connection, reads data from it until the client closes the connection, and returns ("echoes") an HTTP response back with the data in it. 

This may seem a bit odd. But the idea is that if you use your web browser to navigate to this server, what you will see in your browser window is the complete HTTP request that your browser sent.

So, let's say that you are running this server on port 8888 on your computer. In the navigation field of your browser, type in the URL "http://localhost:8888". What you then should see in your browser is something like this:

```
GET / HTTP/1.1
Host: localhost:8888
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7
Accept-Language: en-us
Accept-Encoding: gzip, deflate
Connection: keep-alive
```

###Evaluation
- Send one line to HTTPEcho server. The HTTPEcho server returns a correct HTTP response, with the line in the data section of the response.
- Send fives lines to HTTPEcho server. The HTTPEcho server returns a correct HTTP response, with the five lines in the data section of the response.
- Repeat test no. 2 several times after each other. Each time, the HTTPEcho server returns a correct HTTP response.

##Task3 - HTTPAsk Server

In this part, you will implement another web server – HTTPAsk. This is a web server that uses the client you did in Task 1. When you send an HTTP request to HTTPAsk, you provide a hostname, a port number, and optionally a string as parameters for the request.

When HTTPAsk receives the HTTP request, it will call the method TCPClient.askServer, (which you wrote for Task 1, remember?), and return the output as an HTTP response. This may seem confusing, but it is really very simple: instead of running TCPAsk from the command line, you will build a web server that runs TCPAsk for you, and presents the result as a web page (an HTTP response).

Your job is to implement a class called HTTPAsk. It's "main" method implements the server. It takes one argument: the port number. So if you want your server to run on port 8888, you would start it in the following way:

```
$ java HTTPAsk 888

```
You need to write the code that validates the request, "picks apart" the URI component of the request and extracts the parameters and their values.

### Evaluation

- Send a request to connect to a daytime server, and verify that the output is correct
- Send a request to send one line to an echo server, and verify that the output is correct
- Send a bad request, and verify that the server returns a proper HTTP response

##Task 4 - Concurrent HTTPAsk Server

In this task, you will take the HTTPAsk server you did in Task 3, and turn it into a concurrent server. The server you did for Task 3 deals with one client at a time (most likely). A concurrent server can handle multiple clients in parallel.

You would start it in the following way to run on port 8888:

```
$ java ConcHTTPAsk 888

```

### Evalutation

- Send a request to connect to a daytime server, and verify that the output is correct.
- Send requests to connect to two daytime servers, one fast and one slow, and verify that the output is correct for both requests.