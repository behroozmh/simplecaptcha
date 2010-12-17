package nl.captcha.text.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class DefaultWordRenderer implements WordRenderer {

	private static final List<Color> DEFAULT_COLORS = new ArrayList<Color>();
	private static final List<Font> DEFAULT_FONTS = new ArrayList<Font>();
	// The text will be rendered 25%/5% of the image height/width from the X and Y axes
	private static final double YOFFSET = 0.25;
	private static final double XOFFSET = 0.05;
    
	static {
		DEFAULT_COLORS.add(Color.BLACK);
		DEFAULT_FONTS.add(new Font("Arial", Font.BOLD, 40));
		DEFAULT_FONTS.add(new Font("Courier", Font.BOLD, 40));
	}
	
    private final List<Color> _colors = new ArrayList<Color>();
    private final List<Font> _fonts = new ArrayList<Font>();
    
    public DefaultWordRenderer() {
    	this(DEFAULT_COLORS, DEFAULT_FONTS);
    }
    
    public DefaultWordRenderer(List<Color> colors, List<Font> fonts) {
    	_colors.addAll(colors);
    	_fonts.addAll(fonts);
    }

    /**
     * Render a word onto a BufferedImage.
     * 
     * @param word The word to be rendered.
     * @param bi  The BufferedImage onto which the word will be painted on to
     */
    @Override
    public void render(final String word, BufferedImage image) {
        Graphics2D g = image.createGraphics();

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);

        Random generator = new SecureRandom();
        
        FontRenderContext frc = g.getFontRenderContext();
        int xBaseline = (int) Math.round(image.getWidth() * XOFFSET);
        int yBaseline =  image.getHeight() - (int) Math.round(image.getHeight() * YOFFSET);
        
        char[] chars = new char[1];
        for (char c : word.toCharArray()) {
            chars[0] = c;
            
            g.setColor(_colors.get(generator.nextInt(_colors.size())));

            int choiceFont = generator.nextInt(_fonts.size());
            Font font = _fonts.get(choiceFont);
            g.setFont(font);
            
            GlyphVector gv = font.createGlyphVector(frc, chars);
            g.drawChars(chars, 0, chars.length, xBaseline, yBaseline);

            int width = (int) gv.getVisualBounds().getWidth();
            xBaseline = xBaseline + width;
        }
    }
}