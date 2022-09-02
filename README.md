# ITunes Gallery Project v2021.sp



## Course-Specific Learning Outcomes

* **LO1.d:**	Use shell commands to compile new and existing software solutions that
are organized into multi-level packages and have external dependencies.
* **LO2.e:** Utilize existing generic methods, interfaces, and classes in a software solution.
* **LO3.a:** Create and update source code that adheres to established style guidelines.
* **LO3.b:** Create class, interface, method, and inline documentation that satisfies a set of requirements.
* **LO5.b:** Utilize a build tool such as Maven or Ant to create and manage a complex software solution involving external dependencies.
* **LO7.a:** Design and implement a graphical user interface in a software project.
* **LO7.c:** Use common abstract data types and structures, including lists, queues, arrays, and stacks in solving typical problems.

## Project Description

Your goal is to implement a GUI application in Java using JavaFX 11 that displays a 
gallery of images based on the results of a search query to the 
[iTunes Search API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching).
This will require you to lookup things in Javadoc and apply your knowledge of
things like inheritance, polymorphism, and interfaces. The functional
and non-functional requirements for this project are outlined later in this 
document. Here is an example of what your program might look like:

![screenshot1](https://i.imgur.com/eUh0NbF.png)

Here are links to an animated version: 
[gifv](https://i.imgur.com/tdRDXM1.gifv), 
[gif](https://i.imgur.com/tdRDXM1.gif).

Part of software development is being given a goal but not necessarily being 
given instruction on all of the details needed to accomplish that goal. For example,
even though working with things like images, threads, JSON, and the iTunes Search API 
haven't been covered in class, you are going to need to lookup how to do these things 
in order to complete this project. Starter code and a generously helpful [FAQ](#faq) 
are provided. After actively reading through the main parts of this project description
and taking notes, please read through the [FAQ](#faq) to see if it answers any of your
questions.

This project is also designed to help you better understand the usefulness of good
class design. While you can technically write your entire JavaFX-based
GUI application entirely in the `start` method, this will make your code messy, 
hard to read, possibly redundant, likely more prone to errors, and it wouldn't pass
a style audit. Before you write any code, you should plan out your application's 
scene graph (i.e., the containment hierarchy), and design custom components as needed.
If you find that you are writing a lot of code related to a specific component
(e.g., setting styling, adding event handlers, etc.), then it's probably 
a good idea to make a custom version of that component in order to reduce
clutter. 

We are also using this project to further introduce you to 
[Maven](https://maven.apache.org/what-is-maven.html).
As you are actively encouraged to create and document additional classes in order
to create customized, reusable components (e.g., for dealing with the image views),
it would be slightly tedious to compile and run your code frequently.
Instead, you can use Maven to more easily compile your code and handle dependencies
(e.g., for the Gson library mentioned in the FAQ). Please see the
"Project Structure" sub-section of the 
[Absolute Requirements](#absolute-requirements) section for an overview of
what you can do with Maven. While this project is already provided to you
as a Maven project, you may still find is useful to read the
[CSCI 1302 Maven Tutorial](https://github.com/cs1302uga/cs1302-tutorials/blob/master/maven.md)
to gain a better understanding of what Maven is.
