#version 400 core

in vec2 position;

out vec2 textureCoords;

//uniform vec2 coordinateOffset;

uniform mat4 orthographicMatrix;
uniform mat4 transformationMatrix;

void main(void)
{
	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);//orthographicMatrix * transformationMatrix * vec4(position, 0.0, 1.0);
	textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}