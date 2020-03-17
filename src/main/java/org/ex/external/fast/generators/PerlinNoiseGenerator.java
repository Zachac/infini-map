package org.ex.external.fast.generators;

public class PerlinNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetPerlin(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetPerlin(x, y);
	}

	// Gradient Noise
	public float GetPerlinFractal(float x, float y, float z) {
		x *= m_frequency;
		y *= m_frequency;
		z *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SinglePerlinFractalFBM(x, y, z);
		case Billow:
			return SinglePerlinFractalBillow(x, y, z);
		case RigidMulti:
			return SinglePerlinFractalRigidMulti(x, y, z);
		default:
			return 0;
		}
	}

	private float SinglePerlinFractalFBM(float x, float y, float z) {
		int seed = m_seed;
		float sum = SinglePerlin(seed, x, y, z);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += SinglePerlin(++seed, x, y, z) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SinglePerlinFractalBillow(float x, float y, float z) {
		int seed = m_seed;
		float sum = Math.abs(SinglePerlin(seed, x, y, z)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SinglePerlin(++seed, x, y, z)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SinglePerlinFractalRigidMulti(float x, float y, float z) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SinglePerlin(seed, x, y, z));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SinglePerlin(++seed, x, y, z))) * amp;
		}

		return sum;
	}

	public float GetPerlin(float x, float y, float z) {
		return SinglePerlin(m_seed, x * m_frequency, y * m_frequency, z * m_frequency);
	}

	private float SinglePerlin(int seed, float x, float y, float z) {
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

		float xd0 = x - x0;
		float yd0 = y - y0;
		float zd0 = z - z0;
		float xd1 = xd0 - 1;
		float yd1 = yd0 - 1;
		float zd1 = zd0 - 1;

		float xf00 = Lerp(GradCoord3D(seed, x0, y0, z0, xd0, yd0, zd0), GradCoord3D(seed, x1, y0, z0, xd1, yd0, zd0),
				xs);
		float xf10 = Lerp(GradCoord3D(seed, x0, y1, z0, xd0, yd1, zd0), GradCoord3D(seed, x1, y1, z0, xd1, yd1, zd0),
				xs);
		float xf01 = Lerp(GradCoord3D(seed, x0, y0, z1, xd0, yd0, zd1), GradCoord3D(seed, x1, y0, z1, xd1, yd0, zd1),
				xs);
		float xf11 = Lerp(GradCoord3D(seed, x0, y1, z1, xd0, yd1, zd1), GradCoord3D(seed, x1, y1, z1, xd1, yd1, zd1),
				xs);

		float yf0 = Lerp(xf00, xf10, ys);
		float yf1 = Lerp(xf01, xf11, ys);

		return Lerp(yf0, yf1, zs);
	}

	public float GetPerlinFractal(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SinglePerlinFractalFBM(x, y);
		case Billow:
			return SinglePerlinFractalBillow(x, y);
		case RigidMulti:
			return SinglePerlinFractalRigidMulti(x, y);
		default:
			return 0;
		}
	}

	private float SinglePerlinFractalFBM(float x, float y) {
		int seed = m_seed;
		float sum = SinglePerlin(seed, x, y);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += SinglePerlin(++seed, x, y) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SinglePerlinFractalBillow(float x, float y) {
		int seed = m_seed;
		float sum = Math.abs(SinglePerlin(seed, x, y)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SinglePerlin(++seed, x, y)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SinglePerlinFractalRigidMulti(float x, float y) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SinglePerlin(seed, x, y));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SinglePerlin(++seed, x, y))) * amp;
		}

		return sum;
	}

	public float GetPerlin(float x, float y) {
		return SinglePerlin(m_seed, x * m_frequency, y * m_frequency);
	}

	private float SinglePerlin(int seed, float x, float y) {
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

		float xd0 = x - x0;
		float yd0 = y - y0;
		float xd1 = xd0 - 1;
		float yd1 = yd0 - 1;

		float xf0 = Lerp(GradCoord2D(seed, x0, y0, xd0, yd0), GradCoord2D(seed, x1, y0, xd1, yd0), xs);
		float xf1 = Lerp(GradCoord2D(seed, x0, y1, xd0, yd1), GradCoord2D(seed, x1, y1, xd1, yd1), xs);

		return Lerp(xf0, xf1, ys);
	}
}
