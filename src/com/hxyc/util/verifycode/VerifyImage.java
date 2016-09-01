package com.hxyc.util.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class VerifyImage extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Random random = new Random();// 随机器
	private final static String SIMPLE_CAPCHA_SESSION_KEY = "rand";// 存放字符串的session名字

	private static char[] captchars = new char[] { '2', '3', '4', '5', '6',
			'7', '8', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
			'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y' };// 0，1，2易和字母的o，l,z易混淆，不生成,可以生成汉字
	// 设置备选汉字，剔除一些不雅的汉字
	private String base = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//修改的信息在此
		int num = 4;// 字符的多少
		int ImageWidth = num * 35;//图片的宽
		int ImageHeight = 65;//图片的高
		int line_left = 7;//从左上到右下的线条个数
		int line_right = 8;//从右上到左下的线条个数

		int car = captchars.length - 1;
		Font font = getFont();// 获得写的时候的字体

		// 随机写字母或汉字
		String test = "";
		if ((random.nextInt(2)) == 0) {
			for (int i = 0; i < num; i++) {
				// 1.随机得到num个字母或数字
				test += captchars[random.nextInt(car) + 1];
				font = new Font("Fixedsys", Font.PLAIN, 40);
			}
		} else {
			for (int i = 0; i < num; i++) {
				// 2.随机得到num个汉字
				// test += this.getRandomChar();
				// 3.第二种获得汉字常用方法,一般用这种
				int start = random.nextInt(base.length());
				test += base.substring(start, start + 1);
				font = new Font("Fixedsys", Font.BOLD, 28);
			}
		}

		req.getSession(true).setAttribute(
				VerifyImage.SIMPLE_CAPCHA_SESSION_KEY, test);// 将字符串写入session

		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(resp
				.getOutputStream());// 得到输出流

		BufferedImage bi = new BufferedImage(ImageWidth, ImageHeight,
				BufferedImage.TYPE_BYTE_INDEXED);

		Graphics2D graphics = bi.createGraphics();

		Color c = getRandColor(200, 250);
		graphics.setColor(c);// 设置背景色

		graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		graphics.setColor(Color.black);

		graphics.setFont(font);
		graphics.setColor(this.getRandColor());
		graphics.drawString(test, 10, 50);// 将字写到图片上

		TextLayout textTl = new TextLayout(test, new Font("Fixedsys",
				Font.PLAIN, 20), new FontRenderContext(null, true, false));// 获得字体一样的字，20是字体的大小
		textTl.draw(graphics, 30, 60);// 对字体加投影，第二个是左右相距，越大越远，第三个参数是上下两层相距距离，越大越近

		int w = bi.getWidth();
		int h = bi.getHeight();
		shear(graphics, w, h, c);// 使图片扭曲

		this.drawThickLine(graphics, 0, random.nextInt(ImageHeight) + 1,
				ImageWidth, random.nextInt(ImageHeight) + 1, 4, getRandColor(
						100, 200));// 加一道线

		// 从左上到右下加上多道干扰线
		graphics.setColor(getRandColor(160, 200));// 设置线条的颜色
		for (int i = 0; i < line_left; i++) {
			int x = random.nextInt(ImageWidth - 1);
			int y = random.nextInt(ImageHeight - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			graphics.drawLine(x, y, x + xl + 40, y + yl + 20);
		}
		// 从右上到左下加多道干扰线
		for (int i = 0; i < line_right; i++) {
			int x = random.nextInt(ImageWidth - 1);
			int y = random.nextInt(ImageHeight - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			graphics.drawLine(x, y, x - xl + 40, y - yl);
		}
		// 添加噪点
		float yawpRate = 0.012f;// 噪声率
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			bi.setRGB(x, y, rgb);
		}

		// 设置图片类型
		resp.setContentType("image/jpg");
		// 写出
		encoder.encode(bi);

	}

	// 添加噪点的方法
	private int getRandomIntColor() {
		int[] rgb = this.getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	// 扭曲方法
	private void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private void shearX(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private void shearY(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

	// 画一道粗线的方法
	private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2,
			int thickness, Color c) {

		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x and y increments from an endpoint needed to create a
		// rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
	}

	// 随机得到一个汉字的方法
	protected char getRandomChar() {
		final int minFirstByte = 176;
		final int maxFirstByte = 215;
		final int minSecondByte = 161;
		final int maxSecondByte = 249;

		byte[] b = new byte[2];
		b[0] = (byte) (getRandomIntBetween(minFirstByte, maxFirstByte));
		b[1] = (byte) (getRandomIntBetween(minSecondByte, maxSecondByte));
		try {
			String s = new String(b, "gb2312");
			assert s.length() == 1;
			return s.charAt(0);
		} catch (UnsupportedEncodingException uee) {
			// 重试
			return getRandomChar();
		}
	}

	/**
	 * 获取两个整数之间的某个随机数，包含较小的整数，不包含较大的整数
	 */
	private int getRandomIntBetween(int first, int second) {
		if (second < first) {
			int tmp = first;
			first = second;
			second = tmp;
		}
		return random.nextInt(second - first) + first;
	}

	/**
	 * 产生随机字体
	 */
	private Font getFont() {
		Random random = new Random();
		Font font[] = new Font[5];
		font[0] = new Font("Ravie", Font.PLAIN, 40);
		font[1] = new Font("Antique Olive Compact", Font.PLAIN, 40);
		font[2] = new Font("Fixedsys", Font.PLAIN, 40);
		font[3] = new Font("Wide Latin", Font.PLAIN, 40);
		font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, 40);
		return font[random.nextInt(5)];
	}

	/**
	 * 随机产生定义的颜色
	 */
	private Color getRandColor() {
		Random random = new Random();
		Color color[] = new Color[10];
		color[0] = new Color(32, 158, 25);
		color[1] = new Color(218, 42, 19);
		color[2] = new Color(31, 75, 208);
		return color[random.nextInt(3)];
	}

	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
