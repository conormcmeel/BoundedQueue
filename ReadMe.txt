I ran out of time so here are come considerations.

Issues:
1. Coupling between Producers and Consumers - in an ideal world, my producer wouldn't know who was consuming.
2. Would have preferred to 'generify' the Producers and Consumers, passing any type of object.
3. I wasn't able to allow Consumers to register their interest in an object. I consider the Observer pattern but decided against it.
4. I used a Linked List as opposed to an array because it's faster for allowing deletion from positions other than head/tail.
5. There are too many arguments in the Producer and Consumer constructors.
6. I'd have preferred many more tests.
7. I decided to use simple objects (String/Integer) as opposed to creating my own, as I would have with a proper pub/sub implementation.
   I did this to enable me to focus on the thread synchronisation parts (it means the removeElement() method is a bit odd).
8. I would have preferred my solution to accept different types on objects, e.g. 3 Integers, and 2 Strings.