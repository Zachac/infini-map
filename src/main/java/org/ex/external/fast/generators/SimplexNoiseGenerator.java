package org.ex.external.fast.generators;

public class SimplexNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetSimplex(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetSimplex(x, y);
	}

	// Simplex Noise
	public float GetSimplexFractal(float x, float y, float z) {
		x *= m_frequency;
		y *= m_frequency;
		z *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleSimplexFractalFBM(x, y, z);
		case Billow:
			return SingleSimplexFractalBillow(x, y, z);
		case RigidMulti:
			return SingleSimplexFractalRigidMulti(x, y, z);
		default:
			return 0;
		}
	}

	private float SingleSimplexFractalFBM(float x, float y, float z) {
		int seed = m_seed;
		float sum = SingleSimplex(seed, x, y, z);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += SingleSimplex(++seed, x, y, z) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleSimplexFractalBillow(float x, float y, float z) {
		int seed = m_seed;
		float sum = Math.abs(SingleSimplex(seed, x, y, z)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SingleSimplex(++seed, x, y, z)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleSimplexFractalRigidMulti(float x, float y, float z) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleSimplex(seed, x, y, z));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;
			z *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleSimplex(++seed, x, y, z))) * amp;
		}

		return sum;
	}

	public float GetSimplex(float x, float y, float z) {
		return SingleSimplex(m_seed, x * m_frequency, y * m_frequency, z * m_frequency);
	}

	private final static float F3 = (float) (1.0 / 3.0);
	private final static float G3 = (float) (1.0 / 6.0);
	private final static float G33 = G3 * 3 - 1;

	private float SingleSimplex(int seed, float x, float y, float z) {
		float t = (x + y + z) * F3;
		int i = FastFloor(x + t);
		int j = FastFloor(y + t);
		int k = FastFloor(z + t);

		t = (i + j + k) * G3;
		float x0 = x - (i - t);
		float y0 = y - (j - t);
		float z0 = z - (k - t);

		int i1, j1, k1;
		int i2, j2, k2;

		if (x0 >= y0) {
			if (y0 >= z0) {
				i1 = 1;
				j1 = 0;
				k1 = 0;
				i2 = 1;
				j2 = 1;
				k2 = 0;
			} else if (x0 >= z0) {
				i1 = 1;
				j1 = 0;
				k1 = 0;
				i2 = 1;
				j2 = 0;
				k2 = 1;
			} else // x0 < z0
			{
				i1 = 0;
				j1 = 0;
				k1 = 1;
				i2 = 1;
				j2 = 0;
				k2 = 1;
			}
		} else // x0 < y0
		{
			if (y0 < z0) {
				i1 = 0;
				j1 = 0;
				k1 = 1;
				i2 = 0;
				j2 = 1;
				k2 = 1;
			} else if (x0 < z0) {
				i1 = 0;
				j1 = 1;
				k1 = 0;
				i2 = 0;
				j2 = 1;
				k2 = 1;
			} else // x0 >= z0
			{
				i1 = 0;
				j1 = 1;
				k1 = 0;
				i2 = 1;
				j2 = 1;
				k2 = 0;
			}
		}

		float x1 = x0 - i1 + G3;
		float y1 = y0 - j1 + G3;
		float z1 = z0 - k1 + G3;
		float x2 = x0 - i2 + F3;
		float y2 = y0 - j2 + F3;
		float z2 = z0 - k2 + F3;
		float x3 = x0 + G33;
		float y3 = y0 + G33;
		float z3 = z0 + G33;

		float n0, n1, n2, n3;

		t = (float) 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
		if (t < 0)
			n0 = 0;
		else {
			t *= t;
			n0 = t * t * GradCoord3D(seed, i, j, k, x0, y0, z0);
		}

		t = (float) 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
		if (t < 0)
			n1 = 0;
		else {
			t *= t;
			n1 = t * t * GradCoord3D(seed, i + i1, j + j1, k + k1, x1, y1, z1);
		}

		t = (float) 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
		if (t < 0)
			n2 = 0;
		else {
			t *= t;
			n2 = t * t * GradCoord3D(seed, i + i2, j + j2, k + k2, x2, y2, z2);
		}

		t = (float) 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
		if (t < 0)
			n3 = 0;
		else {
			t *= t;
			n3 = t * t * GradCoord3D(seed, i + 1, j + 1, k + 1, x3, y3, z3);
		}

		return 32 * (n0 + n1 + n2 + n3);
	}

	public float GetSimplexFractal(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		switch (m_fractalType) {
		case FBM:
			return SingleSimplexFractalFBM(x, y);
		case Billow:
			return SingleSimplexFractalBillow(x, y);
		case RigidMulti:
			return SingleSimplexFractalRigidMulti(x, y);
		default:
			return 0;
		}
	}

	private float SingleSimplexFractalFBM(float x, float y) {
		int seed = m_seed;
		float sum = SingleSimplex(seed, x, y);
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += SingleSimplex(++seed, x, y) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleSimplexFractalBillow(float x, float y) {
		int seed = m_seed;
		float sum = Math.abs(SingleSimplex(seed, x, y)) * 2 - 1;
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum += (Math.abs(SingleSimplex(++seed, x, y)) * 2 - 1) * amp;
		}

		return sum * m_fractalBounding;
	}

	private float SingleSimplexFractalRigidMulti(float x, float y) {
		int seed = m_seed;
		float sum = 1 - Math.abs(SingleSimplex(seed, x, y));
		float amp = 1;

		for (int i = 1; i < m_octaves; i++) {
			x *= m_lacunarity;
			y *= m_lacunarity;

			amp *= m_gain;
			sum -= (1 - Math.abs(SingleSimplex(++seed, x, y))) * amp;
		}

		return sum;
	}

	public float GetSimplex(float x, float y) {
		return SingleSimplex(m_seed, x * m_frequency, y * m_frequency);
	}

	private final static float F2 = (float) (1.0 / 2.0);
	private final static float G2 = (float) (1.0 / 4.0);

	private float SingleSimplex(int seed, float x, float y) {
		float t = (x + y) * F2;
		int i = FastFloor(x + t);
		int j = FastFloor(y + t);

		t = (i + j) * G2;
		float X0 = i - t;
		float Y0 = j - t;

		float x0 = x - X0;
		float y0 = y - Y0;

		int i1, j1;
		if (x0 > y0) {
			i1 = 1;
			j1 = 0;
		} else {
			i1 = 0;
			j1 = 1;
		}

		float x1 = x0 - i1 + G2;
		float y1 = y0 - j1 + G2;
		float x2 = x0 - 1 + F2;
		float y2 = y0 - 1 + F2;

		float n0, n1, n2;

		t = (float) 0.5 - x0 * x0 - y0 * y0;
		if (t < 0)
			n0 = 0;
		else {
			t *= t;
			n0 = t * t * GradCoord2D(seed, i, j, x0, y0);
		}

		t = (float) 0.5 - x1 * x1 - y1 * y1;
		if (t < 0)
			n1 = 0;
		else {
			t *= t;
			n1 = t * t * GradCoord2D(seed, i + i1, j + j1, x1, y1);
		}

		t = (float) 0.5 - x2 * x2 - y2 * y2;
		if (t < 0)
			n2 = 0;
		else {
			t *= t;
			n2 = t * t * GradCoord2D(seed, i + 1, j + 1, x2, y2);
		}

		return 50 * (n0 + n1 + n2);
	}

	public float GetSimplex(float x, float y, float z, float w) {
		return SingleSimplex(m_seed, x * m_frequency, y * m_frequency, z * m_frequency, w * m_frequency);
	}

	private static final byte[] SIMPLEX_4D = { 0, 1, 2, 3, 0, 1, 3, 2, 0, 0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 1, 2, 3, 0, 0, 2, 1, 3, 0, 0, 0, 0, 0, 3, 1, 2, 0, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 2, 0, 3, 0, 0, 0, 0, 1, 3, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 1, 2, 3, 1, 0, 1, 0, 2, 3,
			1, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 1, 0, 0, 0, 0, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 3, 0, 1, 2, 3, 0, 2, 1, 0, 0, 0, 0, 3, 1, 2, 0, 2, 1, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			3, 1, 0, 2, 0, 0, 0, 0, 3, 2, 0, 1, 3, 2, 1, 0 };

	private final static float F4 = (float) ((2.23606797 - 1.0) / 4.0);
	private final static float G4 = (float) ((5.0 - 2.23606797) / 20.0);

	private float SingleSimplex(int seed, float x, float y, float z, float w) {
		float n0, n1, n2, n3, n4;
		float t = (x + y + z + w) * F4;
		int i = FastFloor(x + t);
		int j = FastFloor(y + t);
		int k = FastFloor(z + t);
		int l = FastFloor(w + t);
		t = (i + j + k + l) * G4;
		float X0 = i - t;
		float Y0 = j - t;
		float Z0 = k - t;
		float W0 = l - t;
		float x0 = x - X0;
		float y0 = y - Y0;
		float z0 = z - Z0;
		float w0 = w - W0;

		int c = (x0 > y0) ? 32 : 0;
		c += (x0 > z0) ? 16 : 0;
		c += (y0 > z0) ? 8 : 0;
		c += (x0 > w0) ? 4 : 0;
		c += (y0 > w0) ? 2 : 0;
		c += (z0 > w0) ? 1 : 0;
		c <<= 2;

		int i1 = SIMPLEX_4D[c] >= 3 ? 1 : 0;
		int i2 = SIMPLEX_4D[c] >= 2 ? 1 : 0;
		int i3 = SIMPLEX_4D[c++] >= 1 ? 1 : 0;
		int j1 = SIMPLEX_4D[c] >= 3 ? 1 : 0;
		int j2 = SIMPLEX_4D[c] >= 2 ? 1 : 0;
		int j3 = SIMPLEX_4D[c++] >= 1 ? 1 : 0;
		int k1 = SIMPLEX_4D[c] >= 3 ? 1 : 0;
		int k2 = SIMPLEX_4D[c] >= 2 ? 1 : 0;
		int k3 = SIMPLEX_4D[c++] >= 1 ? 1 : 0;
		int l1 = SIMPLEX_4D[c] >= 3 ? 1 : 0;
		int l2 = SIMPLEX_4D[c] >= 2 ? 1 : 0;
		int l3 = SIMPLEX_4D[c] >= 1 ? 1 : 0;

		float x1 = x0 - i1 + G4;
		float y1 = y0 - j1 + G4;
		float z1 = z0 - k1 + G4;
		float w1 = w0 - l1 + G4;
		float x2 = x0 - i2 + 2 * G4;
		float y2 = y0 - j2 + 2 * G4;
		float z2 = z0 - k2 + 2 * G4;
		float w2 = w0 - l2 + 2 * G4;
		float x3 = x0 - i3 + 3 * G4;
		float y3 = y0 - j3 + 3 * G4;
		float z3 = z0 - k3 + 3 * G4;
		float w3 = w0 - l3 + 3 * G4;
		float x4 = x0 - 1 + 4 * G4;
		float y4 = y0 - 1 + 4 * G4;
		float z4 = z0 - 1 + 4 * G4;
		float w4 = w0 - 1 + 4 * G4;

		t = (float) 0.6 - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
		if (t < 0)
			n0 = 0;
		else {
			t *= t;
			n0 = t * t * GradCoord4D(seed, i, j, k, l, x0, y0, z0, w0);
		}
		t = (float) 0.6 - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
		if (t < 0)
			n1 = 0;
		else {
			t *= t;
			n1 = t * t * GradCoord4D(seed, i + i1, j + j1, k + k1, l + l1, x1, y1, z1, w1);
		}
		t = (float) 0.6 - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
		if (t < 0)
			n2 = 0;
		else {
			t *= t;
			n2 = t * t * GradCoord4D(seed, i + i2, j + j2, k + k2, l + l2, x2, y2, z2, w2);
		}
		t = (float) 0.6 - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
		if (t < 0)
			n3 = 0;
		else {
			t *= t;
			n3 = t * t * GradCoord4D(seed, i + i3, j + j3, k + k3, l + l3, x3, y3, z3, w3);
		}
		t = (float) 0.6 - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
		if (t < 0)
			n4 = 0;
		else {
			t *= t;
			n4 = t * t * GradCoord4D(seed, i + 1, j + 1, k + 1, l + 1, x4, y4, z4, w4);
		}

		return 27 * (n0 + n1 + n2 + n3 + n4);
	}

}
