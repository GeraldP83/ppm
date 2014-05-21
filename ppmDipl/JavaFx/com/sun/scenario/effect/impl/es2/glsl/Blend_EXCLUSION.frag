#ifdef GL_ES
#extension GL_OES_standard_derivatives : enable
precision highp float;
precision highp int;
#define HIGHP highp
#define MEDIUMP mediump
#define LOWP lowp
#else
#define HIGHP
#define MEDIUMP
#define LOWP
#endif
varying vec2 texCoord0;
varying vec2 texCoord1;
uniform sampler2D botImg;
uniform sampler2D topImg;
uniform float opacity;
vec4 blend_exclusion(vec4 bot, vec4 top) {
vec4 res;
res.a = bot.a + top.a - (bot.a * top.a);
res.rgb = top.rgb + bot.rgb - (2.0 * top.rgb * bot.rgb);
return res;
}
void main() {
vec4 bot = texture2D(botImg, texCoord0);
vec4 top = texture2D(topImg, texCoord1) * opacity;
gl_FragColor = blend_exclusion(bot, top);
}
