package net.sanjayd.ephemeris.propagator;

import java.io.Serializable;
import java.time.Instant;

import org.hipparchus.geometry.euclidean.threed.Vector3D;

public class EphemerisPoint implements Serializable {

	private static final long serialVersionUID = 4727364096560973899L;

	private final Instant epoch;

	private final Vector3D position;

	private final Vector3D velocity;

	public EphemerisPoint(Instant epoch, Vector3D position, Vector3D velocity) {
		this.epoch = epoch;
		this.position = position;
		this.velocity = velocity;
	}

	public Instant getEpoch() {
		return epoch;
	}

	public Vector3D getPosition() {
		return position;
	}

	public Vector3D getVelocity() {
		return velocity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((epoch == null) ? 0 : epoch.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EphemerisPoint other = (EphemerisPoint) obj;
		if (epoch == null) {
			if (other.epoch != null)
				return false;
		} else if (!epoch.equals(other.epoch))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (velocity == null) {
			if (other.velocity != null)
				return false;
		} else if (!velocity.equals(other.velocity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EphemerisPoint [epoch=" + epoch + ", position=" + position + ", velocity=" + velocity + "]";
	}
}
