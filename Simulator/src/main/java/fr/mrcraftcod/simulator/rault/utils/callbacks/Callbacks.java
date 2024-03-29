package fr.mrcraftcod.simulator.rault.utils.callbacks;

import com.google.ortools.constraintsolver.NodeEvaluator2;

/**
 * A {@link NodeEvaluator2} with a multiplicand for the weights.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-09.
 *
 * @author Thomas Couchoud
 * @since 2019-01-09
 */
public abstract class Callbacks extends NodeEvaluator2{
	public static final double COST_MULTIPLICAND = 2;
}
