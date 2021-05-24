# Clinical Knowledgebase Library  - ckblib
<pre>
         _________  ____  __.__________           __
         \_   ___ \|    |/ _|\______   \          \ \
  ______ /    \  \/|      <   |    |  _/  ______   \ \
 /_____/ \     \___|    |  \  |    |   \ /_____/   / /
          \______  /____|__ \ |______  /          /_/
                 \/        \/        \/
</pre>
![omopfhirmap](https://forthebadge.com/images/badges/made-with-java.svg)

[![ckblib](https://github.com/dermatologist/ckblib/blob/develop/notes/dermml.jpg)](https://nuchange.ca)

## About

Providing clinical decision support requires some formal way of representing clinical knowledge and complex algorithms for sophisticated inference. ckblib is a java library to facilitate knowledge extraction, annotation and representation as a Neo4J graph. These knowledge graphs can be visualized in a semantically enriched way that we call ClinGraph™ (see above). ckblib consists of three modules:

* The 'library' module wraps the NCBI's E-Utils API to harvest published article abstracts.
* The 'qtakes' module provides a programmable interface to my quick-ctakes or the quarkus based apache ctakes, a fast clinical text annotation engine.
* Finally, the graph module provides the Neo4J models, repositories and services for abstracting as a knowledge graph.

## How to use

* [Read how to install mvn packages from GitHub](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#installing-a-package)
* Include dependencies in pom.xml
* See an application [![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg) using ckblib: [GulfDoctor](https://gulfdoctor.net)

```
<dependency>
  <groupId>com.canehealth.ckblib</groupId>
  <artifactId>library</artifactId>
  <version>${latest-version-from-releases-tab}</version>
</dependency>

<dependency>
  <groupId>com.canehealth.ckblib</groupId>
  <artifactId>qtakes</artifactId>
  <version>${latest-version-from-releases-tab}</version>
</dependency>

<dependency>
  <groupId>com.canehealth.ckblib</groupId>
  <artifactId>graph</artifactId>
  <version>${latest-version-from-releases-tab}</version>
</dependency>
```
* Read [javadoc](https://dermatologist.github.io/ckblib/index.html). Documentation is WIP.
* Check the application module for an example end-to-end use.

## Author

* [Bell Eapen](https://nuchange.ca) | [contact](https://nuchange.ca/contact) | [![Twitter Follow](https://img.shields.io/twitter/follow/beapen?style=social)](https://twitter.com/beapen)

## Contributors
* PR welcome.
* Please see CONTRIBUTING.md for details.
