#version 440

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D uniformTexture;

void main(void)
{
	out_Color = texture(uniformTexture, textureCoords);
}