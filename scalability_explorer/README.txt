The Scalability Explorer provides a template to create experiments for scalability assessment of choreographies. It is composed by interfaces and abstract classes. An scalability test is built plugging implementations of them.

Planning the test before its implementation is important, first, because the framework provides abstract extensions of Experiment with specific behaviors and choosing the correct one makes the implementation simpler. Also, concrete implementations of the remaining elements are provided and choosing a set of them to build a test is the fastest way to have it ready. Note that, depending of the requirements of the test case, it might be required to create your own implementation of some components.
Following comes a brief explanation about how Scalability Explore's classes behave and collaborate, including the available extensions or implementations.

## Experiment
An experiment is a sequence of iterations where, for each iteration, scalability parameters related to workload and/or the system architecture vary. In each iteration, a number of requests is performed to the evaluated system. Performance metrics are collected during the execution and, after the iterations, an analysis is performed. Experiment contains the template for this procedure and the actions performed when it is executed are defined by objects implementing the interfaces detailed bellow or overriding methods defined in this class.

Three extension are provided:
- ArchitectureExperiment: this is an experiment that varies a parameter used to specify some facet of the system architecture, such as number of instances of a service or hardware resources available in a virtual machine. The scaling up can be executed with the help of a Deployer;
- WorkloadExperiment: for experiments that vary the interval between the requests to the system;
- ScalabilityExperiment: in these experiments the architecture and workload parameters vary together.

More details about experiments below.

## Deployer
This is an interface for a component used to deploy services during the experiment. Using a Deployer is optional, once the user may write a scalability test for a system that is already running.
It defines two methods that are called by Experiment.

- enact() is called at the beginning of the experiment to set up the deploy the system;
- scale(int) is called before each iteration passing the architectural scalability parameter as argument.


Another method defined by this interface is getServiceUris(String), that should be used the to retrieve the URIs of a given service during the experiment.

The EEDeployer is an abstract implementation that encapsulates an Enactment Engine client. It can be extended by overriding the the methods enactmentSpec() and scaleSpec(int) to return the choreography specification to be sent to the Enactment Engine when enact() and scale(int) are called, respectively.

## ScalabilityFunction
An ScalabilityFunction defines how the scalability parameters must vary from an iteration to the next. The provided implementations are LinearIncrease, QuadraticIncrease, and ExponentialIncrease.

## LoadGenerator
This is an interface for a component that sequentially triggers the request performed during the tests, according to a distribution. Implementation for Gaussian, Poisson, random and degenerated distributions are provided.

## Analyzer
Called at the end of the experiment to support examining the results of a scalability test. It handles the collected metrics and scalability parameter values generating a meaningful output. Currently, Scalability Explorer provides these analyzers:
- RelativePerformance: displays a chart that shows how the performance varied in comparison with the first iteration. It can be used to create speedup or degradation charts;
- AggregatePerformance: aggregates the measurements made in each iteration in a single value using an AggregationFunction and plots a chart with the aggregated performance obtained in each iteration.
- ANOVA: performs hypothesis test to verify if the mean performance obtained in each iteration are equivalent;
- SampleSizeEstimation: estimates the minimum number of request that should be performed per iteration to make the ANOVA test meaningful.
- ComposedAnalysis: allows the user to use more then one Analyzer in an experiment.


## AggregationFunction
These components are used by some analyzers to summarize a collection of values in a single number. The framework provides classes to calculate arithmetic mean or percentile.






Writing a scalability test

Common scalability tests include:
- increase the resources available to the system and verify if and how the performance improves;
- increase the workload to verify if and how the performance degrades;
- increase the the workload and system resources at the same time to verify how system behaves.

The Scalability Explorer provides an extension of Experiment for each of these kinds of test, presented in previour section.
The first step to write a test case is to choose which one to extend. 
Experiment provides methods that must be overridden to define how the experiment interacts with the system tested. 
The request method must be overridden to make the communication with the system. It defines what is made when the LoadGenerator triggers a request. Additionaly, there are specific methods to include behavior before and after the whole experiment, each iteration and each request, that can be overridden according to user needs.

The execution of an experiment is made by instantiating its class, seting the previously presented components and other atributes to define the number of iteration, number of requests per iteration initial values for the scalability parameters, and calling its run(String) method. This method reveives a label used to identify this experiment in the Analyzer output. Optionally, run(String, boolean) can used, where the second parameter defines if the analysis should be performed. Even when the analysis is not executed, the metrics are stored. Thus, it is easy to execute more than one test, with different configurations, and analyze them togheter in the end.

