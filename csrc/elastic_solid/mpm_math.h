#ifndef MPM_MATH_H
#define MPM_MATH_H

#include <stdio.h>
#include <math.h>
#include "solver_options.h"
#include "node.h"
#include "material_point.h"//need material_point class



//Get the 2 nearest nodes to our material point mp
void get_near_nodes( struct material_point mps[] );




#endif
