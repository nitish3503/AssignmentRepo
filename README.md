Typeahead suggestions enable users to search for known and frequently searched terms. As the user types into the search box, it tries to predict the query based on the characters the user has entered and gives a list of suggestions to complete the query. Typeahead suggestions help the user to articulate their search queries better. It’s not about speeding up the search process but rather about guiding the users and lending them a helping hand in constructing their search query. 

Question: What data structure would be used for the same and how?
Answer: I have used trie data structure to store the search element.

Solution: Created Spring Boot Microservice which contains REST END POINT. Below is the curl command of REST API

curl --location --request GET 'http://localhost:8080/suggestion/v1/b'

This API accept input as pathvariable and check if input has some value, If Yes it got inserted in Trie data structure and then search of all suggestion and return result to consumer.
insert

Design an API Rate limiting system that monitors the number of requests per a window time(i.e. could be second/hour etc) a service agrees to allow. IF the number of requests exceeds the allowed limit the rate limiter should block all excess calls.
System should be designed considering the following:

Question: Rate limiting should work for a distributed set up as the APIs are available through a group of API Gateways
Answer: Use redis along with sliding window counter in this approch we will store timestamp of window in array but in counter window t is more than slindng window in which if we have two same time stamp like 11:29:30 and 11:29:30 ,so we will store in array like timestamp with counter. 
Question: What database would be used and the rationale behind the choice 
Answer: Redis is the best option here because it is fast, has a simple API to manipulate the data and supports a time-based expiration strategy
Question how would throttling be done :
Answer: using sliding window counter algorithm, the system should be highly available :redis

solution 2: doc file at root directory .

How would you design a garbage collection system? The idea here is to design a system that recycles unused memory in the program. Here the key is to find which piece of memory is unused. What data structure would be chosen and how would it be used to ensure garbage collection does not halt the main process?

Data Structure : we used graph for containing the reference also using mark ans sweep algorithm.

Identification of unused references and finalization of object happens concurrently.

it is divided into three parts -

Garbaage Collector: This is the main entry point of the module and performs following operations

a) get(Object) : Add new object to the reference graph
b) release(Object) : To indicate that object is no more required
c) runGC() : Request to start the garbage collection
HeapOperation: This class is to traverse through graph and identify unused references. It also push collected objects(unused references) to finalize queue, which is taken care by FinalizeTask

TaskFinalizer: TaskFinalizer concurrently processes(calls finalize) objects pushed to finalize queue.

ObjectReference: Basic reference implementation is node of graph.

Use cases covered: avoiding cyclic references during traversal. works for both objects with or without finalize method. non blocking implementation of finalize.
