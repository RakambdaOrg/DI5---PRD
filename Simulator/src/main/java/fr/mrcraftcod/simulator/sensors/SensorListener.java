package fr.mrcraftcod.simulator.sensors;

import fr.mrcraftcod.simulator.Environment;
import org.jetbrains.annotations.NotNull;

/**
 * Define an interface to listen to events inside a sensor.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2018-10-04.
 *
 * @author Thomas Couchoud
 */
public interface SensorListener{
	/**
	 * Called when a sensor current capacity changes.
	 *
	 * @param environment        The environment.
	 * @param sensor             The sensor concerned.
	 * @param oldCurrentCapacity The old capacity value.
	 * @param newCurrentCapacity The new capacity value.
	 */
	void onSensorCurrentCapacityChange(final Environment environment, @NotNull final Sensor sensor, @SuppressWarnings("unused") double oldCurrentCapacity, double newCurrentCapacity);
}
