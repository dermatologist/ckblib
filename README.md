# Clinical Knowledgebase Library  - ckblib

         _________  ____  __.__________           __
         \_   ___ \|    |/ _|\______   \          \ \
  ______ /    \  \/|      <   |    |  _/  ______   \ \
 /_____/ \     \___|    |  \  |    |   \ /_____/   / /
          \______  /____|__ \ |______  /          /_/
                 \/        \/        \/

![omopfhirmap](https://forthebadge.com/images/badges/made-with-java.svg)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/E-Health/omopfhirmap)

[![ckblib](https://raw.github.com/dermatologist/ckblib/develop/notes/dermml.jpg)](https://nuchange.ca)

## About

Providing clinical decision support requires some formal way of representing clinical knowledge and complex algorithms for sophisticated inference. ckblib consists of three modules. The 'library' module wraps the NCBI's E-Utils API to harvest published article abstracts if that is your knowledge source. Though data extraction from EMR's is a recent trend, it is challenging because of the lack of unstructured data and lack of interoperability. The 'qtakes' module provides a programmable interface to my quick-ctakes or the quarkus based apache ctakes, a fast clinical text annotation engine. Finally, the graph module provides the Neo4J models, repositories and services for abstracting as a knowledge graph.

## Author

* [Bell Eapen](https://nuchange.ca) | [contact](https://nuchange.ca/contact) | [![Twitter Follow](https://img.shields.io/twitter/follow/beapen?style=social)](https://twitter.com/beapen)

## Contributors
* PR welcome. Please see CONTRIBUTING.md for details.