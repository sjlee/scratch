package org.sjlee.alg;

public class ColorInterpolation {
	public static final int A = 0xFF000000;
	public static final int R_FILTER = 0x00FF0000;
	public static final int G_FILTER = 0x0000FF00;
	public static final int B_FILTER = 0x000000FF;
	
	public static int interpolate(int c0, int c1, float t) {
		int r0 = (c0 & R_FILTER) >> 16; System.out.println(Integer.toHexString(r0));
		int r1 = (c1 & R_FILTER) >> 16; System.out.println(Integer.toHexString(r1));
		int r = interpolateComponent(r0, r1, t) << 16; System.out.println(Integer.toHexString(r));
		int g0 = (c0 & G_FILTER) >> 8; System.out.println(Integer.toHexString(g0));
		int g1 = (c1 & G_FILTER) >> 8; System.out.println(Integer.toHexString(g1));
		int g = interpolateComponent(g0, g1, t) << 8; System.out.println(Integer.toHexString(g));
		int b0 = (c0 & B_FILTER); System.out.println(Integer.toHexString(b0));
		int b1 = (c1 & B_FILTER); System.out.println(Integer.toHexString(b1));
		int b = interpolateComponent(b0, b1, t); System.out.println(Integer.toHexString(b));
		return A | r | g | b;
	}
	
	private static int interpolateComponent(int i0, int i1, float t) {
		return (int)(i0 + (i1-i0)*t);
	}
	
	public static void main(String[] args) {
		int red = 0xFFFF0000;
		int blue = 0xFF0000FF;
		float t = 0.25f;
		int color = interpolate(red, blue, t);
		System.out.println(Integer.toHexString(color));
	}
}
