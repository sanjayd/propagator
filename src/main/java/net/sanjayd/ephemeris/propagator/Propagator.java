package net.sanjayd.ephemeris.propagator;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.time.UTCScale;
import org.orekit.utils.PVCoordinates;

public class Propagator {

	private UTCScale utcTimeScale;

	public Propagator(File orekitDataPath) {
		@SuppressWarnings("deprecation")
		DataProvidersManager manager = DataProvidersManager.getInstance();
		manager.addProvider(new DirectoryCrawler(orekitDataPath));
		this.utcTimeScale = TimeScalesFactory.getUTC();
	}

	public List<EphemerisPoint> propgate(String line1, String line2, Duration timeStep, int numPoints) {
		List<EphemerisPoint> points = new ArrayList<>(numPoints);

		// Initialize propagator and initial state
		TLE tle = new TLE(line1, line2);
		TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);
		SpacecraftState initialState = propagator.getInitialState();

		// Normalize to minute boundary
		long offset = 60000 - initialState.getDate().toDate(TimeScalesFactory.getUTC()).getTime() % 60000;
		AbsoluteDate date = initialState.getDate().shiftedBy(offset / 1000.0);

		// Generate points
		for (int i = 0; i < numPoints; i++) {
			SpacecraftState state = propagator.propagate(date);
			PVCoordinates coordinates = state.getPVCoordinates();
			EphemerisPoint point = new EphemerisPoint(state.getDate().toDate(utcTimeScale).toInstant(),
					coordinates.getPosition(), coordinates.getVelocity());
			points.add(point);

			date = date.shiftedBy(timeStep.get(SECONDS));
		}
		return points;
	}

}
