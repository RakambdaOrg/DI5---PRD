package fr.mrcraftcod.simulator.jfx.tabs.sensor;

import fr.mrcraftcod.simulator.metrics.MetricEvent;
import fr.mrcraftcod.simulator.metrics.MetricEventDispatcher;
import fr.mrcraftcod.simulator.metrics.MetricEventListener;
import fr.mrcraftcod.simulator.metrics.events.SensorCapacityMetricEvent;
import fr.mrcraftcod.simulator.sensors.Sensor;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class SensorsCapacityChartTab extends Tab implements MetricEventListener{
	
	private final HashMap<Integer, Sensor> sensors;
	private final HashMap<Integer, XYChart.Series<Number, Number>> series;
	
	public SensorsCapacityChartTab(final Collection<? extends Sensor> sensors){
		this.sensors = new HashMap<>();
		this.series = new HashMap<>();
		for(final var s : sensors)
		{
			this.sensors.put(s.getID(), s);
			final var serie = new XYChart.Series<Number, Number>();
			serie.setName(String.format("Sensor[%d]", s.getID()));
			this.series.put(s.getID(), serie);
		}
		
		final var xAxis = new NumberAxis();
		final var yAxis = new NumberAxis();
		
		xAxis.setAnimated(false);
		xAxis.setLabel("Simulation time");
		
		yAxis.setAnimated(false);
		yAxis.setLabel("Capacity");
		
		final var chart = new LineChart<>(xAxis, yAxis);
		chart.setAnimated(false);
		chart.getData().addAll(series.values());
		chart.setCreateSymbols(false);
		
		this.setContent(chart);
		this.setClosable(false);
		this.setText("Sensors capacity");
		MetricEventDispatcher.addListener(this);
	}
	
	@Override
	public void onEvent(final MetricEvent event){
		if(event instanceof SensorCapacityMetricEvent && sensors.values().contains(((SensorCapacityMetricEvent) event).getElement()))
		{
			Platform.runLater(() -> series.get(((SensorCapacityMetricEvent) event).getElement().getID()).getData().add(new XYChart.Data<>(event.getTime(), ((SensorCapacityMetricEvent) event).getNewValue())));
		}
	}
	
	@Override
	public void onEnd(){
	
	}
}