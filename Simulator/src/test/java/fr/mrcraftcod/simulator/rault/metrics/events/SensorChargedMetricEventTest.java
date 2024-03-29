package fr.mrcraftcod.simulator.rault.metrics.events;

import fr.mrcraftcod.simulator.Environment;
import fr.mrcraftcod.simulator.sensors.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorChargedMetricEventTest{
	private Environment environment;
	private Sensor sensor;
	
	static class DataProvider implements ArgumentsProvider{
		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context){
			return Stream.of(new Double[]{
					0D,
					1D
			}, new Double[]{
					15D,
					50D
			}).map(Arguments::of);
		}
	}
	
	@BeforeEach
	void setUp(){
		this.environment = new Environment(null, "junit-test");
		this.sensor = new Sensor(environment);
	}
	
	@ParameterizedTest(name = "Test constructor")
	@ArgumentsSource(DataProvider.class)
	void eventConstruct(final double time, final double value){
		final var event = new SensorChargedMetricEvent(environment, time, sensor, value);
		assertEquals(environment, event.getEnvironment());
		assertEquals(time, event.getTime());
		assertEquals(sensor, event.getElement());
		assertEquals(value, event.getNewValue());
	}
}