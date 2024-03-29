package fr.mrcraftcod.simulator.rault.events;

import fr.mrcraftcod.simulator.Environment;
import fr.mrcraftcod.simulator.rault.metrics.events.LrRequestMetricEvent;
import fr.mrcraftcod.simulator.sensors.Sensor;
import fr.mrcraftcod.simulator.simulation.SimulationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.HashSet;

/**
 * The event of a Lr request from a sensor.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2018-11-07.
 *
 * @author Thomas Couchoud
 */
public class LrRequestEvent extends SimulationEvent{
	private static final Logger LOGGER = LoggerFactory.getLogger(LrRequestEvent.class);
	private static final Collection<Sensor> requestingSensors = new HashSet<>();
	private final Sensor sensor;
	
	/**
	 * Constructor.
	 *
	 * @param time   The time of the event.
	 * @param sensor The sensor requesting.
	 */
	public LrRequestEvent(final double time, final Sensor sensor){
		super(time);
		this.sensor = sensor;
	}
	
	@Override
	public void accept(final Environment environment){
		LOGGER.debug("Registered Lr request from {}", getSensor().getUniqueIdentifier());
		environment.getSimulator().getMetricEventDispatcher().dispatchEvent(new LrRequestMetricEvent(environment, getTime(), getSensor()));
		requestingSensors.add(getSensor());
	}
	
	/**
	 * Get the sensor requesting.
	 *
	 * @return The sensor.
	 */
	private Sensor getSensor(){
		return sensor;
	}
	
	/**
	 * Get the requesting sensors.
	 *
	 * @return The requesting sensors.
	 */
	static Collection<Sensor> getRequestingSensors(){
		return requestingSensors;
	}
}
