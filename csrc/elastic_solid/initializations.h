#ifndef INITIALIZATIONS_H
#define INITIALIZATIONS_H


#include "initial_state.h"
#include "node.h"
#include "material_point.h"
#include "mpm_math.h"



//Function that generates a mesh using the geometry at hand, then assigns it density/stress/velocity values based on the initial state.
void initialize_mesh( struct node nodes[] );



//Given the initialized mesh, take the number of material points between meshpoints and use it to generate all the material points and give their their field values.
void initialize_material_points(
	struct material_point mp_points[],
	struct node nodes[]
);

#endif
