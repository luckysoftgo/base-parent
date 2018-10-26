package com.application.base.pay.quickbill.util;

/**
 * @desc base64 加密体
 * @author 孤狼
 */
public class Base64Binrary {

	private static final byte[] DECODEMAP = initDecodeMap();

	private static final char[] ENCODEMAP = initEncodeMap();

	private static final long serialVersionUID = 1L;

	private Base64Binrary() {
	}

	static int num1= 26,num2=52,num3=256,num4=65,num5=90,num6=97,num7=62,num8=122,num9=48,num10=57,num11=2,num12=4,num13=3;
	
	
	private static char[] initEncodeMap() {
		char[] map = new char[64];
		for (int i = 0; i < num1; i++) {
			{
				map[i] = (char) (65 + i);
			}
		}
		for (int i = num1; i < num2; i++) {
			{
				map[i] = (char) (97 + (i - 26));
			}
		}
		for (int i = num2; i < num7; i++) {
			{
				map[i] = (char) (48 + (i - num2));
			}
		}
		map[62] = '+';
		map[63] = '/';
		return map;
	}

	private static byte[] initDecodeMap() {
		byte[] map = new byte[256];
		for (int i = 0; i < num3; i++) {
			map[i] = -1;
		}

		for (int i = num4; i <= num5; i++) {
			map[i] = (byte) (i - 65);
		}

		for (int i = num6; i <= num8; i++) {
			map[i] = (byte) ((i - 97) + 26);
		}

		for (int i = num9; i <= num10; i++) {
			map[i] = (byte) ((i - 48) + num2);
		}

		map[43] = 62;
		map[47] = 63;
		map[61] = 127;
		return map;
	}

	private static int calcLength(char[] buf) {
		int len = buf.length;
		int base64count = 0;
		int paddingCount = 0;
		int i;
		for (i = 0; i < len; i++) {
			if (buf[i] == '=') {
				break;
			}
			if (buf[i] >= '\u0100') {
				return -1;
			}
			if (DECODEMAP[buf[i]] != -1) {
				base64count++;
			}
		}

		for (; i < len; i++) {
			if (buf[i] == '=') {
				paddingCount++;
			}
			else {
				if (buf[i] >= '\u0100') {
					return -1;
				}
				if (DECODEMAP[buf[i]] != -1) {
					return -1;
				}
			}
		}

		if (paddingCount > num11) {
			return -1;
		}
		if ((base64count + paddingCount) % num12 != 0) {
			return -1;
		} else {
			return ((base64count + paddingCount) / 4) * 3 - paddingCount;
		}
	}

	public static byte[] decodeBase64Binrary(String lexicalValue) {
		char[] buf = lexicalValue.toCharArray();
		int outlen = calcLength(buf);
		if (outlen == -1) {
			return null;
		}
		byte[] out = new byte[outlen];
		int o = 0;
		int len = buf.length;
		byte[] quadruplet = new byte[4];
		int q = 0;
		for (int i = 0; i < len; i++) {
			byte v = DECODEMAP[buf[i]];
			if (v != -1) {
				quadruplet[q++] = v;
			}
			if (q == 4) {
				out[o++] = (byte) (quadruplet[0] << 2 | quadruplet[1] >> 4);

				if (quadruplet[2] != 127) {
					out[o++] = (byte) (quadruplet[1] << 4 | quadruplet[2] >> 2);
				}

				if (quadruplet[3] != 127) {
					out[o++] = (byte) (quadruplet[2] << 6 | quadruplet[3]);
				}

				q = 0;
			}
		}

		if (q != 0) {
			throw new IllegalStateException();
		} else {
			return out;
		}
	}

	protected static char encode(int i) {
		return ENCODEMAP[i & 0x3f];
	}

	public static String encodeBase64Binrary(byte[] input) {
		StringBuffer r = new StringBuffer((input.length * 4) / 3);

		for (int i = 0; i < input.length; i += num13) {
			switch ((input.length - i)) {
			case 1:
				r.append(encode(input[i] >> 2));
				r.append(encode((input[i] & 0x3) << 4));
				r.append("==");
				break;

			case 2:
				r.append(encode(input[i] >> 2));
				r.append(encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xf));
				r.append(encode((input[i + 1] & 0xf) << 2));
				r.append("=");
				break;
			default:
				r.append(encode(input[i] >> 2));
				r.append(encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xf));
				r.append(encode((input[i + 1] & 0xf) << 2 | input[i + 2] >> 6 & 0x3));
				r.append(encode(input[i + 2] & 0x3f));
				break;
			}
		}

		return r.toString();
	}

}
