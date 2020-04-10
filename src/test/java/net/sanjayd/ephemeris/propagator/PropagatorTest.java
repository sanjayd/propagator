package net.sanjayd.ephemeris.propagator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class PropagatorTest {

	private Propagator propagator;

	@Before
	public void setUp() {
		File orekitDataDir = new File(System.getProperty("OREKIT_DATA_DIR"));
		propagator = new Propagator(orekitDataDir);
	}
	
	@Test
	public void propagateOneHour() {
		InputStream activeStream = Main.class.getClassLoader().getResourceAsStream("25544.tle");
		Scanner scan = new Scanner(new InputStreamReader(activeStream));
		String line1 = scan.nextLine();
		String line2 = scan.nextLine();
		scan.close();
		
		List<EphemerisPoint> points = propagator.propgate(line1, line2, Duration.ofMinutes(1), 60);
		assertEquals(60, points.size());
	}
}
