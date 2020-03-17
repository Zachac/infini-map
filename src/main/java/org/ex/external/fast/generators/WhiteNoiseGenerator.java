package org.ex.external.fast.generators;

public class WhiteNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetWhiteNoise(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetWhiteNoise(x, y);
	}

	public float GetWhiteNoise(float x, float y, float z, float w) {
		int xi = FloatCast2Int(x);
		int yi = FloatCast2Int(y);
		int zi = FloatCast2Int(z);
		int wi = FloatCast2Int(w);

		return ValCoord4D(m_seed, xi, yi, zi, wi);
	}

	public float GetWhiteNoise(float x, float y, float z) {
		int xi = FloatCast2Int(x);
		int yi = FloatCast2Int(y);
		int zi = FloatCast2Int(z);

		return ValCoord3D(m_seed, xi, yi, zi);
	}

	public float GetWhiteNoise(float x, float y) {
		int xi = FloatCast2Int(x);
		int yi = FloatCast2Int(y);

		return ValCoord2D(m_seed, xi, yi);
	}

	public float GetWhiteNoiseInt(int x, int y, int z, int w) {
		return ValCoord4D(m_seed, x, y, z, w);
	}

	public float GetWhiteNoiseInt(int x, int y, int z) {
		return ValCoord3D(m_seed, x, y, z);
	}

	public float GetWhiteNoiseInt(int x, int y) {
		return ValCoord2D(m_seed, x, y);
	}
}
