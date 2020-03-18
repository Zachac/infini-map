package org.ex.external.fast.generators;

public class CellularNoiseGenerator extends AbstractNoiseGenerator {

	@Override
	public float GetNoise(float x, float y, float z) {
		return GetCellular(x, y, z);
	}

	@Override
	public float GetNoise(float x, float y) {
		return GetCellular(x, y);
	}

	// Cellular Noise
	public float GetCellular(float x, float y, float z) {
		x *= m_frequency;
		y *= m_frequency;
		z *= m_frequency;

		switch (m_cellularReturnType) {
		case CellValue:
		case NoiseLookup:
		case Distance:
			return SingleCellular(x, y, z);
		default:
			return SingleCellular2Edge(x, y, z);
		}
	}

	private float SingleCellular(float x, float y, float z) {
		int xr = FastRound(x);
		int yr = FastRound(y);
		int zr = FastRound(z);

		float distance = 999999;
		int xc = 0, yc = 0, zc = 0;

		switch (m_cellularDistanceFunction) {
		case Euclidean:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = vecX * vecX + vecY * vecY + vecZ * vecZ;

						if (newDistance < distance) {
							distance = newDistance;
							xc = xi;
							yc = yi;
							zc = zi;
						}
					}
				}
			}
			break;
		case Manhattan:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ);

						if (newDistance < distance) {
							distance = newDistance;
							xc = xi;
							yc = yi;
							zc = zi;
						}
					}
				}
			}
			break;
		case Natural:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = (Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ))
								+ (vecX * vecX + vecY * vecY + vecZ * vecZ);

						if (newDistance < distance) {
							distance = newDistance;
							xc = xi;
							yc = yi;
							zc = zi;
						}
					}
				}
			}
			break;
		}

		switch (m_cellularReturnType) {
		case CellValue:
			return ValCoord3D(0, xc, yc, zc);

		case NoiseLookup:
			Float3 vec = CELL_3D[Hash3D(m_seed, xc, yc, zc) & 255];
			return m_cellularNoiseLookup.GetNoise(xc + vec.x, yc + vec.y, zc + vec.z);

		case Distance:
			return distance - 1;
		default:
			return 0;
		}
	}

	private float SingleCellular2Edge(float x, float y, float z) {
		int xr = FastRound(x);
		int yr = FastRound(y);
		int zr = FastRound(z);

		float distance = 999999;
		float distance2 = 999999;

		switch (m_cellularDistanceFunction) {
		case Euclidean:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = vecX * vecX + vecY * vecY + vecZ * vecZ;

						distance2 = Math.max(Math.min(distance2, newDistance), distance);
						distance = Math.min(distance, newDistance);
					}
				}
			}
			break;
		case Manhattan:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ);

						distance2 = Math.max(Math.min(distance2, newDistance), distance);
						distance = Math.min(distance, newDistance);
					}
				}
			}
			break;
		case Natural:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					for (int zi = zr - 1; zi <= zr + 1; zi++) {
						Float3 vec = CELL_3D[Hash3D(m_seed, xi, yi, zi) & 255];

						float vecX = xi - x + vec.x;
						float vecY = yi - y + vec.y;
						float vecZ = zi - z + vec.z;

						float newDistance = (Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ))
								+ (vecX * vecX + vecY * vecY + vecZ * vecZ);

						distance2 = Math.max(Math.min(distance2, newDistance), distance);
						distance = Math.min(distance, newDistance);
					}
				}
			}
			break;
		default:
			break;
		}

		switch (m_cellularReturnType) {
		case Distance2:
			return distance2 - 1;
		case Distance2Add:
			return distance2 + distance - 1;
		case Distance2Sub:
			return distance2 - distance - 1;
		case Distance2Mul:
			return distance2 * distance - 1;
		case Distance2Div:
			return distance / distance2 - 1;
		default:
			return 0;
		}
	}

	public float GetCellular(float x, float y) {
		x *= m_frequency;
		y *= m_frequency;

		switch (m_cellularReturnType) {
		case CellValue:
		case NoiseLookup:
		case Distance:
			return SingleCellular(x, y);
		default:
			return SingleCellular2Edge(x, y);
		}
	}
	
	public class CellData {
		public float distance;
		public int xc, yc;
	}

	public float SingleCellular(float x, float y) {
		int xr = FastRound(x);
		int yr = FastRound(y);

		float distance = 999999;
		int xc = 0, yc = 0;
		
		switch (m_cellularDistanceFunction) {
		default:
		case Euclidean:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = vecX * vecX + vecY * vecY;

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		case Manhattan:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = (Math.abs(vecX) + Math.abs(vecY));

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		case Natural:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = (Math.abs(vecX) + Math.abs(vecY)) + (vecX * vecX + vecY * vecY);

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		}
		
		switch (m_cellularReturnType) {
		case CellValue:
			return ValCoord2D(0, xc, yc);

		case NoiseLookup:
			Float2 vec = CELL_2D[Hash2D(m_seed, xc, yc) & 255];
			return m_cellularNoiseLookup.GetNoise(xc + vec.x, yc + vec.y);

		case Distance:
			return distance - 1;
		default:
			return 0;
		}
	}

	public Float2 getCellCoordinates(float x, float y) {
		x *= this.m_frequency;
		y *= this.m_frequency;
		int xr = FastRound(x);
		int yr = FastRound(y);

		float distance = 999999;
		int xc = 0, yc = 0;
		
		switch (m_cellularDistanceFunction) {
		default:
		case Euclidean:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = vecX * vecX + vecY * vecY;

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		case Manhattan:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = (Math.abs(vecX) + Math.abs(vecY));

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		case Natural:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = (Math.abs(vecX) + Math.abs(vecY)) + (vecX * vecX + vecY * vecY);

					if (newDistance < distance) {
						distance = newDistance;
						xc = xi;
						yc = yi;
					}
				}
			}
			break;
		}
		

		return new Float2(xc, yc);
	}

	private float SingleCellular2Edge(float x, float y) {
		int xr = FastRound(x);
		int yr = FastRound(y);

		float distance = 999999;
		float distance2 = 999999;

		switch (m_cellularDistanceFunction) {
		default:
		case Euclidean:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = vecX * vecX + vecY * vecY;

					distance2 = Math.max(Math.min(distance2, newDistance), distance);
					distance = Math.min(distance, newDistance);
				}
			}
			break;
		case Manhattan:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = Math.abs(vecX) + Math.abs(vecY);

					distance2 = Math.max(Math.min(distance2, newDistance), distance);
					distance = Math.min(distance, newDistance);
				}
			}
			break;
		case Natural:
			for (int xi = xr - 1; xi <= xr + 1; xi++) {
				for (int yi = yr - 1; yi <= yr + 1; yi++) {
					Float2 vec = CELL_2D[Hash2D(m_seed, xi, yi) & 255];

					float vecX = xi - x + vec.x;
					float vecY = yi - y + vec.y;

					float newDistance = (Math.abs(vecX) + Math.abs(vecY)) + (vecX * vecX + vecY * vecY);

					distance2 = Math.max(Math.min(distance2, newDistance), distance);
					distance = Math.min(distance, newDistance);
				}
			}
			break;
		}

		switch (m_cellularReturnType) {
		case Distance2:
			return distance2 - 1;
		case Distance2Add:
			return distance2 + distance - 1;
		case Distance2Sub:
			return distance2 - distance - 1;
		case Distance2Mul:
			return distance2 * distance - 1;
		case Distance2Div:
			return distance / distance2 - 1;
		default:
			return 0;
		}
	}
}
