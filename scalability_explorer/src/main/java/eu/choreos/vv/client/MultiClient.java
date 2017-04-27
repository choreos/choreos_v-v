package eu.choreos.vv.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.LoadGeneratorFactory;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

public class MultiClient<T extends Client> extends BaseClient implements Callable<ReportData> {

	List<T> clients;
	Iterator<T> iterator;

	int numberOfCalls;
	long delay;

	protected void createClients(Class<T> cls, long qtd) throws InstantiationException, IllegalAccessException {
		clients = new ArrayList<T>();
		for (int i = 0; i < qtd; i++)
			clients.add(cls.newInstance());
	}

	@Override
	public ReportData execute(int numberOfCalls, long delay, Map params) throws Exception {
		final ExecutorService executorService = Executors.newFixedThreadPool(clients.size());
		final List<Future<ReportData>> futureResults = new ArrayList<Future<ReportData>>();

		ReportData report = new ReportData();

		this.params = params;
		this.numberOfCalls = numberOfCalls;
		this.delay = delay;

		this.setUp();

		iterator = clients.iterator();
		try {
			while (iterator.hasNext()) {
				futureResults.add(executorService.submit(this));
				// report.merge(client.execute(numberOfCalls, delay, params));
			}

			executorService.shutdown();
			while (!executorService.awaitTermination(LoadGeneratorFactory.getInstance().getTimeout(), TimeUnit.SECONDS))
				;
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			throw e;
		}
		
		for (Future<ReportData> future : futureResults)
			report.merge(future.get());
		
		this.tearDown();

		return report;
	}

	@Override
	public ReportData call() throws Exception {
		Client client = iterator.next();
		return client.execute(numberOfCalls, delay, params);
	}
}
