package me.ayumi.prometheus.demo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples.Sample;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

/**
 * Hello world!
 *
 */
public class App {
	
	static Gauge gauge = null;
	
	static {
		try {
			new HTTPServer(8119);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gauge = Gauge.build().name("test").help("this is a test metrics").labelNames("entity_id","service_id").register();
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello World!");
		long i = 0;
		while(true) {
//			gauge.labels("entity_id_" + i%1000,"service_id_" + i%1000).dec(100);
			new Collector() {
				@Override
				public List<MetricFamilySamples> collect() {
					return Collections.singletonList(new MetricFamilySamples("test", Type.COUNTER, "t", Collections.singletonList(new Sample("test", Collections.singletonList("label1"), Collections.singletonList("label_1_value"), 1, System.currentTimeMillis()))));
				}
			}.register();
			Thread.sleep(1000);
			i ++;
		}
	}
}
