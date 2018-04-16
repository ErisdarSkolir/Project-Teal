#version 400 core

in vec2 position;

out vec2 pass_textureCoords;

uniform vec2 textureRowsAndColumns;
uniform vec2 textureOffset;

uniform mat4 orthographicMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

void main(void)
{
	gl_Position = orthographicMatrix * viewMatrix * transformationMatrix * vec4(position, 0.0, 1.0);
	pass_textureCoords = (vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0) / textureRowsAndColumns) + textureOffset;
}