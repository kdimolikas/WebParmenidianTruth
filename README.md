# WebParmenidianTruth

A web application that can be used to visualize the schema evolution of relational databases. We should mention that this application as well as the [ParmenidianTruth-API] are submodules of the [WebSchemaVisualizer] project\*.

The main functionalities of the application can be summarized as follows:

* **Visualize** DB's schemata as graphs with nodes and edges representing the including tables and foreign keys, respectively.
* **Visualize** several evolution-related patterns
* **Compute** evolution-related statistics

The data required from the application are provided by the ParmenidianTruth-API, a refactored version of the ParmenidianTruth tool. We make use of this data to create a new project that offers the aforementioned functionalities.

The visualization part of the application is implemented via [D3] , a javascript library that enables the visual representation of the data related to the schema evolution.

For more information on how to run web application, please refer to the [README] file of the parent project WebSchemaVisualizer.

[D3]: https://github.com/d3

[ParmenidianTruth-API]:https://github.com/kdimolikas/ParmenidianTruth-API

[WebSchemaVisualizer]:https://github.com/kdimolikas/WebSchemaVisualizer

[README]: https://github.com/kdimolikas/WebSchemaVisualizer/blob/master/README.md

\*: *created in the context of my MSc thesis at the department of Computer Science & Engineering of the University of Ioannina, under the supervision of associate professor Panos Vassiliadis*. 
