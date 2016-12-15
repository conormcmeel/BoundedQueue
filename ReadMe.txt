I ran out of time so here are come considerations.

Issues:
1.	Would have preferred to 'generify' the Producers, passing any type of object.
2.	I used a Linked List as opposed to an array because it's faster for allowing deletion from positions other than head/tail.
3.	I'd have preferred many more tests, especially tests to check the ordering of waiting consumers.
4.	I decided to use simple objects (String/Integer) as opposed to creating my own, as I would have with a proper pub/sub implementation. I did this to enable me to focus on the thread synchronisation parts (it means the takeInternal() method is a bit odd).
5.	I would have preferred my solution to accept different types on objects, e.g. 3 Integers, and 2 Strings.
6.	I added (but ignored) 2 'stress' tests to prove that my program can handle many producers/consumers and try to catch hard to find race conditions. I have noticed that the 2 tests sometimes fail when I run them in a loop of 50. I suspect the reason for this is that my assert check comes too soon for the consumers to clear the queue. But I cannot be certain of this, so I'm highlighting it an issue.
7.  I added System.out.println instead of a logger to allow logging to be written to the console for manual checking.