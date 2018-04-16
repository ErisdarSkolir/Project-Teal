#version 440

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D uniformTexture;

void main(void)
{
	vec4 color = texture(uniformTexture, pass_textureCoords);
	
	if(color.a < 0.5)
	{
		discard;
	}
	
	out_Color = color;
}