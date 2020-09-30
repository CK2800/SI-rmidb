# Assignment 3

## _Architectural Styles and the Design of Network-based Software Architectures_

##### https://www.ics.uci.edu/~fielding/pubs/dissertation/fielding_dissertation.pdf

---

## Q1.

What exactly is REST? How does the context of it fit to the title of the dissertation?

## A1.

It is a view of every part of a communication as separate, replacable unit of concern, even the message. <br />This view is established through numerous constraints dictating which concerns lie where.<br />As such it fits the title as REST comprises several premises of a network-based software architecture through the definition of its constraints.

---

## Q2.

Why is the dissertation considered so important for the software-architectural world?

## A2.

It reiterates over the SoC principle and applies it to larger scale components such as servers, clients, proxies, messages and state.<br />Also it brings semantics back to the internet in that URIs should change as infrequently as possible and by defining that a resource should be what the author intends to identify regardless of the result of the request to it.<br />More important it imposes 6 clear constraints which make a web service RESTful, and as such tries to bring consistency to an anarchistic domain.

The 6 constraints are listed here for reference:

1. Uniform interface
2. Client–server
3. Stateless
4. Cacheable
5. Layered system
6. Code on demand (optional)

---

## Q3.

Which is the most valuable outcome you personally get from it?

## A3.

It is really hard to say. As a programmer, I very much like concrete examples and learning by doing. To dissect 100+ pages with this complexity does not make the subject any clearer.
If the take away is the principles of RESTful webservices, I very much like the idea of utilizing the HTTP protocol in the way it, apparently, was laid out to be used.

---

## Q4.

How could you implement it in your own practice as a software developer?

## A4.

I am unsure what is actually meant by this question. However, if the principles of RESTful webservices are the pivotal element here, I guess the principles of SoC, divide-and-conquer and IoC will come to mind more often.

---
