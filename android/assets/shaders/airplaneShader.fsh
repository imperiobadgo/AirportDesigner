varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform vec4 u_Color;

void main(){
    vec4 resColor = texture2D(u_sampler2D, v_texCoord0) * u_Color;
    gl_FragColor = resColor;
}