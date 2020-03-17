package org.ex.external.fast.generators;

public class ValueNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetValueFractal(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetValueFractal(x, y);
	}

	// Value Noise
	public float GetValueFractal(float x, float y, float z) {
		x *= m_frequency;
		y *= m_frequency;
		z *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleValueFractalFBM(x, y, z);
		case Billow:
			return SingleValueFractalBillow(x, y, z);
		case RigidMulti:
			return SingleValueFractalRigidMulti(x, y, z);
		default:
			return 0;
		}
	}

	private float SingleValueFractalFBM(float x, float y, float z) {
		int seed = m_seed;
		float sum = SingleValue(seed, x, y, z);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += SingleValue(++seed, x, y, z) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleValueFractalBillow(float x, float y, float z) {
		int seed = m_seed;
		float sum = Math.abs(SingleValue(seed, x, y, z)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SingleValue(++seed, x, y, z)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleValueFractalRigidMulti(float x, float y, float z) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleValue(seed, x, y, z));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleValue(++seed, x, y, z))) * amp;
		}

		return sum;
	}

	public float GetValue(float x, float y, float z) {
		return SingleValue(m_seed, x * m_frequency, y * m_frequency, z * m_frequency);
	}

	private float SingleValue(int seed, float x, float y, float z) {
		int x0 = FastFloor(x);
		int y0 = FastFloor(y);
		int z0 = FastFloor(z);
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		int z1 = z0 + 1;

		float xs, ys, zs;
		switch (m_interp) {
		default:
		case Linear:
			xs = x - x0;
			ys = y - y0;
			zs = z - z0;
			break;
		case Hermite:
			xs = InterpHermiteFunc(x - x0);
			ys = InterpHermiteFunc(y - y0);
			zs = InterpHermiteFunc(z - z0);
			break;
		case Quintic:
			xs = InterpQuinticFunc(x - x0);
			ys = InterpQuinticFunc(y - y0);
			zs = InterpQuinticFunc(z - z0);
			break;
		}

		float xf00 = Lerp(ValCoord3D(seed, x0, y0, z0), ValCoord3D(seed, x1, y0, z0), xs);
		float xf10 = Lerp(ValCoord3D(seed, x0, y1, z0), ValCoord3D(seed, x1, y1, z0), xs);
		float xf01 = Lerp(ValCoord3D(seed, x0, y0, z1), ValCoord3D(seed, x1, y0, z1), xs);
		float xf11 = Lerp(ValCoord3D(seed, x0, y1, z1), ValCoord3D(seed, x1, y1, z1), xs);

		float yf0 = Lerp(xf00, xf10, ys);
		float yf1 = Lerp(xf01, xf11, ys);

		return Lerp(yf0, yf1, zs);
	}

	public float GetValueFractal(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleValueFractalFBM(x, y);
		case Billow:
			return SingleValueFractalBillow(x, y);
		case RigidMulti:
			return SingleValueFractalRigidMulti(x, y);
		default:
			return 0;
		}
	}

	private float SingleValueFractalFBM(float x, float y) {
		int seed = m_seed;
		float sum = SingleValue(seed, x, y);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += SingleValue(++seed, x, y) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleValueFractalBillow(float x, float y) {
		int seed = m_seed;
		float sum = Math.abs(SingleValue(seed, x, y)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			amp *= m_gain;
			sum += (Math.abs(SingleValue(++seed, x, y)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleValueFractalRigidMulti(float x, float y) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleValue(seed, x, y));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleValue(++seed, x, y))) * amp;
		}

		return sum;
	}

	public float GetValue(float x, float y) {
		return SingleValue(m_seed, x * m_frequency, y * m_frequency);
	}

	private float SingleValue(int seed, float x, float y) {
		int x0 = FastFloor(x);
		int y0 = FastFloor(y);
		int x1 = x0 + 1;
		int y1 = y0 + 1;

		float xs, ys;
		switch (m_interp) {
		default:
		case Linear:
			xs = x - x0;
			ys = y - y0;
			break;
		case Hermite:
			xs = InterpHermiteFunc(x - x0);
			ys = InterpHermiteFunc(y - y0);
			break;
		case Quintic:
			xs = InterpQuinticFunc(x - x0);
			ys = InterpQuinticFunc(y - y0);
			break;
		}

		float xf0 = Lerp(ValCoord2D(seed, x0, y0), ValCoord2D(seed, x1, y0), xs);
		float xf1 = Lerp(ValCoord2D(seed, x0, y1), ValCoord2D(seed, x1, y1), xs);

		return Lerp(xf0, xf1, ys);
	}
}
