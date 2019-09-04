#version 400 core

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

void main(void){

	gl_Position = vec4(position, 0, 1.0);
	pass_textureCoords = textureCoords;

}
