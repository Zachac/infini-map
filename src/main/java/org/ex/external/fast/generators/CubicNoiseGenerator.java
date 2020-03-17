package org.ex.external.fast.generators;

public class CubicNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetCubic(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetCubic(x, y);
	}

	// Cubic Noise
	public float GetCubicFractal(float x, float y, float z) {
		x *= m_frequency;
		y *= m_frequency;
		z *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleCubicFractalFBM(x, y, z);
		case Billow:
			return SingleCubicFractalBillow(x, y, z);
		case RigidMulti:
			return SingleCubicFractalRigidMulti(x, y, z);
		default:
			return 0;
		}
	}

	private float SingleCubicFractalFBM(float x, float y, float z) {
		int seed = m_seed;
		float sum = SingleCubic(seed, x, y, z);
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += SingleCubic(++seed, x, y, z) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleCubicFractalBillow(float x, float y, float z) {
		int seed = m_seed;
		float sum = Math.abs(SingleCubic(seed, x, y, z)) * 2 - 1;
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SingleCubic(++seed, x, y, z)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleCubicFractalRigidMulti(float x, float y, float z) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleCubic(seed, x, y, z));
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleCubic(++seed, x, y, z))) * amp;
		}

		return sum;
	}

	public float GetCubic(float x, float y, float z) {
		return SingleCubic(m_seed, x * m_frequency, y * m_frequency, z * m_frequency);
	}

	private final static float CUBIC_3D_BOUNDING = 1 / (float) (1.5 * 1.5 * 1.5);

	private float SingleCubic(int seed, float x, float y, float z) {
		int x1 = FastFloor(x);
		int y1 = FastFloor(y);
		int z1 = FastFloor(z);

		int x0 = x1 - 1;
		int y0 = y1 - 1;
		int z0 = z1 - 1;
		int x2 = x1 + 1;
		int y2 = y1 + 1;
		int z2 = z1 + 1;
		int x3 = x1 + 2;
		int y3 = y1 + 2;
		int z3 = z1 + 2;

		float xs = x - (float) x1;
		float ys = y - (float) y1;
		float zs = z - (float) z1;

		return CubicLerp(
				CubicLerp(
						CubicLerp(ValCoord3D(seed, x0, y0, z0), ValCoord3D(seed, x1, y0, z0),
								ValCoord3D(seed, x2, y0, z0), ValCoord3D(seed, x3, y0, z0), xs),
						CubicLerp(ValCoord3D(seed, x0, y1, z0), ValCoord3D(seed, x1, y1, z0),
								ValCoord3D(seed, x2, y1, z0), ValCoord3D(seed, x3, y1, z0), xs),
						CubicLerp(ValCoord3D(seed, x0, y2, z0), ValCoord3D(seed, x1, y2, z0),
								ValCoord3D(seed, x2, y2, z0), ValCoord3D(seed, x3, y2, z0), xs),
						CubicLerp(ValCoord3D(seed, x0, y3, z0), ValCoord3D(seed, x1, y3, z0),
								ValCoord3D(seed, x2, y3, z0), ValCoord3D(seed, x3, y3, z0), xs),
						ys),
				CubicLerp(
						CubicLerp(ValCoord3D(seed, x0, y0, z1), ValCoord3D(seed, x1, y0, z1),
								ValCoord3D(seed, x2, y0, z1), ValCoord3D(seed, x3, y0, z1), xs),
						CubicLerp(ValCoord3D(seed, x0, y1, z1), ValCoord3D(seed, x1, y1, z1),
								ValCoord3D(seed, x2, y1, z1), ValCoord3D(seed, x3, y1, z1), xs),
						CubicLerp(ValCoord3D(seed, x0, y2, z1), ValCoord3D(seed, x1, y2, z1),
								ValCoord3D(seed, x2, y2, z1), ValCoord3D(seed, x3, y2, z1), xs),
						CubicLerp(ValCoord3D(seed, x0, y3, z1), ValCoord3D(seed, x1, y3, z1),
								ValCoord3D(seed, x2, y3, z1), ValCoord3D(seed, x3, y3, z1), xs),
						ys),
				CubicLerp(
						CubicLerp(ValCoord3D(seed, x0, y0, z2), ValCoord3D(seed, x1, y0, z2),
								ValCoord3D(seed, x2, y0, z2), ValCoord3D(seed, x3, y0, z2), xs),
						CubicLerp(ValCoord3D(seed, x0, y1, z2), ValCoord3D(seed, x1, y1, z2),
								ValCoord3D(seed, x2, y1, z2), ValCoord3D(seed, x3, y1, z2), xs),
						CubicLerp(ValCoord3D(seed, x0, y2, z2), ValCoord3D(seed, x1, y2, z2),
								ValCoord3D(seed, x2, y2, z2), ValCoord3D(seed, x3, y2, z2), xs),
						CubicLerp(ValCoord3D(seed, x0, y3, z2), ValCoord3D(seed, x1, y3, z2),
								ValCoord3D(seed, x2, y3, z2), ValCoord3D(seed, x3, y3, z2), xs),
						ys),
				CubicLerp(
						CubicLerp(ValCoord3D(seed, x0, y0, z3), ValCoord3D(seed, x1, y0, z3),
								ValCoord3D(seed, x2, y0, z3), ValCoord3D(seed, x3, y0, z3), xs),
						CubicLerp(ValCoord3D(seed, x0, y1, z3), ValCoord3D(seed, x1, y1, z3),
								ValCoord3D(seed, x2, y1, z3), ValCoord3D(seed, x3, y1, z3), xs),
						CubicLerp(ValCoord3D(seed, x0, y2, z3), ValCoord3D(seed, x1, y2, z3),
								ValCoord3D(seed, x2, y2, z3), ValCoord3D(seed, x3, y2, z3), xs),
						CubicLerp(ValCoord3D(seed, x0, y3, z3), ValCoord3D(seed, x1, y3, z3),
								ValCoord3D(seed, x2, y3, z3), ValCoord3D(seed, x3, y3, z3), xs),
						ys),
				zs) * CUBIC_3D_BOUNDING;
	}

	public float GetCubicFractal(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleCubicFractalFBM(x, y);
		case Billow:
			return SingleCubicFractalBillow(x, y);
		case RigidMulti:
			return SingleCubicFractalRigidMulti(x, y);
		default:
			return 0;
		}
	}

	private float SingleCubicFractalFBM(float x, float y) {
		int seed = m_seed;
		float sum = SingleCubic(seed, x, y);
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += SingleCubic(++seed, x, y) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleCubicFractalBillow(float x, float y) {
		int seed = m_seed;
		float sum = Math.abs(SingleCubic(seed, x, y)) * 2 - 1;
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SingleCubic(++seed, x, y)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleCubicFractalRigidMulti(float x, float y) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleCubic(seed, x, y));
		float amp = 1;
		int i = 0;

		while (++i < m_octaves) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleCubic(++seed, x, y))) * amp;
		}

		return sum;
	}

	public float GetCubic(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		return SingleCubic(0, x, y);
	}

	private final static float CUBIC_2D_BOUNDING = 1 / (float) (1.5 * 1.5);

	private float SingleCubic(int seed, float x, float y) {
		int x1 = FastFloor(x);
		int y1 = FastFloor(y);

		int x0 = x1 - 1;
		int y0 = y1 - 1;
		int x2 = x1 + 1;
		int y2 = y1 + 1;
		int x3 = x1 + 2;
		int y3 = y1 + 2;

		float xs = x - (float) x1;
		float ys = y - (float) y1;

		return CubicLerp(
				CubicLerp(ValCoord2D(seed, x0, y0), ValCoord2D(seed, x1, y0), ValCoord2D(seed, x2, y0),
						ValCoord2D(seed, x3, y0), xs),
				CubicLerp(ValCoord2D(seed, x0, y1), ValCoord2D(seed, x1, y1), ValCoord2D(seed, x2, y1),
						ValCoord2D(seed, x3, y1), xs),
				CubicLerp(ValCoord2D(seed, x0, y2), ValCoord2D(seed, x1, y2), ValCoord2D(seed, x2, y2),
						ValCoord2D(seed, x3, y2), xs),
				CubicLerp(ValCoord2D(seed, x0, y3), ValCoord2D(seed, x1, y3), ValCoord2D(seed, x2, y3),
						ValCoord2D(seed, x3, y3), xs),
				ys) * CUBIC_2D_BOUNDING;
	}
}
