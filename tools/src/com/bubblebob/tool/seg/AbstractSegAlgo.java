package com.bubblebob.tool.seg;

public abstract class AbstractSegAlgo{

	public abstract void trace(int x, int y);

	
	/**
	 * Implementation de bresenham
	 */
	public static int[] getNextSinglePoint(int x1, int y1, int x2, int y2){
		int dx = x2-x1;
		int[] result = new int[2];
		if (dx != 0){
			if (dx > 0){
				int dy = y2-y1;
				if (dy != 0){
					if (dy>0){
						// vecteur oblique dans le 1er quadran
						if (dx >= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 1er octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							x1++;
							e -= dy;
							if (e<0){
								y1++;
								e+=dx;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}else{
							// vecteur oblique proche de la verticale, dans le 2nd octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							y1++;
							e -= dx;
							if (e<0){
								x1++;
								e+=dy;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}
					}else{
						// dy < 0 (et dx > 0)
						// vecteur oblique dans le 4e cadran
						if (dx >= -dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 8e octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							x1++;
							e += dy;
							if (e<0){
								y1--;
								e+=dx;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}else{
							// vecteur oblique proche de la verticale, dans le 7e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							y1--;
							e += dx;
							if (e>0){
								x1++;
								e+=dy;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}
					}
				}else{
					// dy = 0 (et dx > 0)
					// vecteur horizontal vers la droite
					x1++;
					result[0] =  x1;
					result[1] =  y1;
					return result;
				}
			}else{
				// dx < 0
				int dy = y2-y1;
				if (dy != 0){
					if (dy>0){
						// vecteur oblique dans le 2nd quadran
						if (-dx >= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 1er octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							x1--;
							e += dy;
							if (e>=0){
								y1++;
								e+=dx;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}else{
							// vecteur oblique proche de la verticale, dans le 3e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							y1++;
							e += dx;
							if (e<=0){
								x1--;
								e+=dy;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}
					}else{
						// dy < 0 (et dx < 0)
						// vecteur oblique dans le 3e cadran
						if (dx <= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 8e octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							x1--;
							e -= dy;
							if (e>=0){
								y1--;
								e+=dx;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}else{
							// vecteur oblique proche de la verticale, dans le 6e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							y1--;
							e -= dx;
							if (e>=0){
								x1--;
								e+=dy;
							}
							result[0] =  x1;
							result[1] =  y1;
							return result;
						}
					}
				}else{
					// dy = 0 (et dx < 0)
					// vecteur horizontal vers la gauche
					x1--;
					result[0] =  x1;
					result[1] =  y1;
					return result;
				}
			}
		}else{
			// dx = 0
			int dy = y2-y1;
			if (dy != 0){
				if (dy> 0){
					// vecteur vertical croissant
					y1++;
					result[0] =  x1;
					result[1] =  y1;
					return result;
				}else{
					// dy < 0 (et dx = 0)
					y1--;
					result[0] =  x1;
					result[1] =  y1;
					return result;
				}
			}else{
				result[0] =  x1;
				result[1] =  y1;
				return result;
			}
		}
	}

	public void trace(int x1, int y1, int x2, int y2){
		//		System.out.println("[SegAlgo#trace] IN x1="+x1+" x2="+x2+" y1="+y1+" y2="+y2);
		int dx = x2-x1;
		if (dx != 0){
			if (dx > 0){
				int dy = y2-y1;
				if (dy != 0){
					if (dy>0){
						// vecteur oblique dans le 1er quadran
						if (dx >= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 1er octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							do {
								trace(x1, y1);
								x1++;
								e -= dy;
								if (e<0){
									y1++;
									e+=dx;
								}

							} while (x1!=x2);
						}else{
							// vecteur oblique proche de la verticale, dans le 2nd octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							do {
								trace(x1, y1);
								y1++;
								e -= dx;
								if (e<0){
									x1++;
									e+=dy;
								}
							} while (y1!=y2);
						}
					}else{
						// dy < 0 (et dx > 0)
						// vecteur oblique dans le 4e cadran
						if (dx >= -dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 8e octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							do {
								trace(x1, y1);
								x1++;
								e += dy;
								if (e<0){
									y1--;
									e+=dx;
								}
							} while (x1!=x2);
						}else{
							// vecteur oblique proche de la verticale, dans le 7e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							do {
								trace(x1, y1);
								y1--;
								e += dx;
								if (e>0){
									x1++;
									e+=dy;
								}
							} while (y1!=y2);
						}
					}
				}else{
					// dy = 0 (et dx > 0)
					// vecteur horizontal vers la droite
					do {
						trace(x1, y1);
						x1++;
					} while (x1!=x2);
				}
			}else{
				// dx < 0
				int dy = y2-y1;
				if (dy != 0){
					if (dy>0){
						// vecteur oblique dans le 2nd quadran
						if (-dx >= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 1er octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							do {
								trace(x1, y1);
								x1--;
								e += dy;
								if (e>=0){
									y1++;
									e+=dx;
								}
							} while (x1!=x2);
						}else{
							// vecteur oblique proche de la verticale, dans le 3e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							do {
								trace(x1, y1);
								y1++;
								e += dx;
								if (e<=0){
									x1--;
									e+=dy;
								}
							} while (y1!=y2);
						}
					}else{
						// dy < 0 (et dx < 0)
						// vecteur oblique dans le 3e cadran
						if (dx <= dy){
							// vecteur diagonal ou oblique proche de l’horizontale, dans le 8e octant
							int e = dx;
							dx = 2*e;
							dy = 2*dy;
							do {
								trace(x1, y1);
								x1--;
								e -= dy;
								if (e>=0){
									y1--;
									e+=dx;
								}
							} while (x1!=x2);
						}else{
							// vecteur oblique proche de la verticale, dans le 6e octant
							int e = dy;
							dy = 2*e;
							dx = 2*dx;
							do {
								trace(x1, y1);
								y1--;
								e -= dx;
								if (e>=0){
									x1--;
									e+=dy;
								}
							} while (y1!=y2);
						}
					}
				}else{
					// dy = 0 (et dx < 0)
					// vecteur horizontal vers la gauche
					do {
						trace(x1, y1);
						x1--;
					} while (x1!=x2);
				}
			}
		}else{
			// dx = 0
			int dy = y2-y1;
			if (dy != 0){
				if (dy> 0){
					// vecteur vertical croissant
					do {
						trace(x1, y1);
						y1++;
					} while (y1!=y2);
				}else{
					// dy < 0 (et dx = 0)
					do {
						trace(x1, y1);
						y1--;
					} while (y1!=y2);
				}
			}
		}
	}


}
