precision highp float;

const int MAX_JOINTS = 60;
//const int MAX_WEIGHTS = 3;

// MVP matrices
uniform mat4 u_MMatrix;
uniform mat4 u_VMatrix;
uniform mat4 u_PMatrix;

// mesh
attribute vec3 a_Position;
varying vec4 v_Position;

// animation
uniform bool u_Animated;
attribute vec4 in_jointIndices;
attribute vec4 in_weights;
uniform mat4 u_BindShapeMatrix;
uniform mat4 jointTransforms[MAX_JOINTS];

void main(){

    vec4 animatedPos = vec4(a_Position,1.0);
    if (u_Animated) {
        vec4 bindPos = u_BindShapeMatrix * vec4(a_Position, 1.0);
        vec4 posePosition = jointTransforms[int(in_jointIndices[0])] * bindPos;
        animatedPos = posePosition * in_weights[0];
        posePosition = jointTransforms[int(in_jointIndices[1])] * bindPos;
        animatedPos += posePosition * in_weights[1];
        posePosition = jointTransforms[int(in_jointIndices[2])] * bindPos;
        animatedPos += posePosition * in_weights[2];
        posePosition = jointTransforms[int(in_jointIndices[3])] * bindPos;
        animatedPos += posePosition * in_weights[3];
    }

    // calculate MVP matrix
    mat4 u_MVMatrix = u_VMatrix * u_MMatrix;
    mat4 u_MVPMatrix = u_PMatrix * u_MVMatrix;

	// calculate rendered position
	gl_Position = u_MVPMatrix * animatedPos;
	v_Position = animatedPos;
}