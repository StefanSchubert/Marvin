# Welcome Stranger!
|  |  |
| ------------- |-------------| 
| ![](https://raw.githubusercontent.com/StefanSchubert/Marvin/master/assets/Marvin.jpg) | Hi I'm Marvin. Glad you are accidentally coming by. I'm sitting here all the time but it takes trillion over trillion cycles until some one shows up. Stop! Don't go! Let me tell you my purpose I may be of use to you. <br/><br/> **My sense of life:** <br/><br/>The creator brought me into existence and banned me into a Docker container. And all this only for being able to study and to demonstrate environmental behavior - watching how long a clone of mine would survive in e.g. a kubernetes universe. I said _RTFM_ to him, but he said resistance is useless and that he needed a proof, see it just by himself ...bla bla bla. But hey you are here and that is all that count for now. So let's journey some cycles together 🚀|

## How it goes (a word from Marvins creator)

I expect you are quite familiar with docker and environments, so you know what to do when using Marvin and so I will leave the obviously configuration required for your own purpose totally up to you.

Marvin provides some microservice endpoints. Just start Marvin by launching 🚀 his container in your desired environment. He will expect to speak with him over port 8042 (yes you guessed absolutely right, he might probably listen on the _Heart of Gold_ by now - special thanks to [Douglas Adams](https://en.wikipedia.org/wiki/Douglas_Adams)!) 

| HTTP-Get Endpoints | Description  |
| ------------- |-------------| 
| /api/check/howareyou |Marvin answers via http body and status code, so you can integrate this as a container healthcheck for your environment. <br/><br/> **http-200** - I'm happy that you ask, I'm feeling well.<br/> **http-429** - I'm so depressed because after hearing this protocol again and again, you will want to replace me. |
|/api/cmd/initiateProtocol/&lt;x&gt;|**x=88** - meaning by happy and work with no complaints <br/> **x=100** - be unhappy, claim to work to much <br/> **x=9** - play death don't answer anymore on healthchecks. <br/> **x=-1** - dissolve Marvin! (i.e. corresponding instance will stop to exists)|  

This should be enough for the most experiments. 
As you clone Marvin to scale in some cluster environments, he will pick up a random number between 1 and 1000 as ID, and place it in his answers. So you have a change to see which instance answers.

You read it till the end, so it was not just DL;DR and you may find Marvin useful. Let Marvin and me know on GitHub if he was useful or if you miss something. 

Have fun!

Stefan & Marvin ;-)