package de.Luca.GUI;

import java.util.ArrayList;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import de.Luca.Models.Texture;
import de.Luca.Text.Paragraph.TEXT_ALIGN;

public class GTextBox extends GButton{
	
	private String text;
	private String showText;
	private boolean selected;
	private boolean passwordBox;
	private ArrayList<CharInputCallback> callbacks;
	private ArrayList<TextFinishCallback> finishCallbacks;
	
	private Texture selectedDefault;
	private Texture selectedHover;
	private Texture selectedPressed;
	
	private Texture[] storeOld;
	private Vector4f oldColor;
	
	private long font;
	private Vector4f color;
	private TEXT_ALIGN align;
	private int margin;
	
	public GTextBox(int x, int y, int width, int height, long font, Vector4f color, TEXT_ALIGN align, int margin) {
		super(x, y, width, height);	
		selected = false;
		finishCallbacks = new ArrayList<TextFinishCallback>();
		callbacks = new ArrayList<CharInputCallback>();
		this.font = font;
		this.color = color;
		this.align = align;
		this.margin = margin;
		text = "";
		showText = "";
		callback();
	}
	
	public void setPasswordBox(boolean passwordBox) {
		this.passwordBox = passwordBox;
	}
	
	protected void setSelected(boolean selected) {
		this.selected = selected;
		updateTexture(selected);
	}
	
	public void setSelectedTextures(Texture selectedDefault, Texture selectedHover, Texture selectedPressed) {
		this.selectedDefault = selectedDefault;
		this.selectedHover = selectedHover;
		this.selectedPressed = selectedPressed;
	}
	
	private void updateTexture(boolean selected) {
		if(selected) {
			storeOld = new Texture[] {getDefaultTexture(), getHoverTexture(), getPressTexture()};
			if(selectedDefault != null) {
				setDefaultTexture(selectedDefault);
			}else {
				oldColor = getColor();
				setColor(new Vector4f(0, 1 , 0, 1));
			}
			if(selectedHover != null) {
				setHoverTexture(selectedHover);
			}
			if(selectedPressed != null) {
				setPressTexture(selectedPressed);
			}
		}else {
			if(storeOld != null) {
				setDefaultTexture(storeOld[0]);
				setHoverTexture(storeOld[1]);
				setPressTexture(storeOld[2]);
			}
			if(oldColor != null) {
				setColor(oldColor);
			}
			oldColor = null;
			storeOld = null;
		}
	}
	
	private void fireTextFinish() {
		for(TextFinishCallback tfc : finishCallbacks) {
			tfc.run(text);
		}
	}
	
	public void addTextFinishCallback(TextFinishCallback tfc) {
		finishCallbacks.add(tfc);
	}
	
	public void removeTextFinishCallback(TextFinishCallback tfc) {
		finishCallbacks.remove(tfc);
	}
	
	protected void fireCharInput(String letter, INPUT_MODE mode) {
		for(CharInputCallback cic : callbacks) {
			cic.run(letter, mode);
		}
	}
	
	protected boolean isSelected() {
		return selected;
	}
	
	protected enum INPUT_MODE{
		TEXT,
		DELETE,
		SEND
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	private void callback() {
		callbacks.add(new CharInputCallback() {
			
			@Override
			public void run(String letter, INPUT_MODE mode) {
				if(mode == INPUT_MODE.TEXT) {
					text += letter;
					if(passwordBox) {
						showText += "*";
					}else {
						showText += letter;
					}
					setText(showText, font, color, align, margin);
				}else if(mode == INPUT_MODE.DELETE) {
					if(text.length() > 0) {
						text = text.substring(0, text.length() - 1);
						showText = showText.substring(0, showText.length() - 1);
					}
					setText(showText, font, color, align, margin);
				}else if(mode == INPUT_MODE.SEND) {
					fireTextFinish();
				}
			}
		});
		
		addClickCallback(new ClickCallback() {
			
			@Override
			public void run(GUIComponent component, int key, int action, int mouseX, int mouseY) {
				if(key == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
					if(action == GLFW.GLFW_RELEASE) {
						if(!selected) {
							updateTexture(!selected);
						}
						selected = true;
					}
				}
			}
		});
		
	}
	

}
